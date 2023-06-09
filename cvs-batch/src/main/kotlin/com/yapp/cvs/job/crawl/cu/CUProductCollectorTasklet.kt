package com.yapp.cvs.job.crawl.cu

import com.yapp.cvs.domain.collect.ProductRawDataVO
import com.yapp.cvs.domain.collect.application.ProductDataProcessor
import com.yapp.cvs.domain.enums.RetailerType
import com.yapp.cvs.support.CUProductCollectSupport
import com.yapp.cvs.support.JsoupHandler
import com.yapp.cvs.support.ProductCategoryRule
import com.yapp.cvs.support.ProductDataParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

open class CUProductCollectorTasklet(
    private val productDataProcessor: ProductDataProcessor,
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val cuProductCollectSupport = CUProductCollectSupport.values().filter { it.promotionType == null }
        cuProductCollectSupport.forEach {
            saveProductData(collectRawData(it))
        }
        return RepeatStatus.FINISHED
    }

    protected fun collectRawData(category: CUProductCollectSupport): List<ProductRawDataVO> {
        log.info("Target Category : ${category.productCategoryType?.displayName ?: category.name}")
        val productCollections = mutableListOf<ProductRawDataVO>()
        var pageIndex = 1

        do {
            val document = JsoupHandler.doFormPost(
                category.url,
                mapOf("pageIndex" to pageIndex.toString(),)
            )
            val body = document.body()
            val productElements = body.getElementsByClass("prod_list")

            productElements.forEach { productRawElement ->
                val title = productRawElement.getElementsByClass("name").text()
                val price = productRawElement.getElementsByClass("price").text()
                val imgURL = productRawElement.getElementsByClass("prod_img")[1].absUrl("src")

                productCollections.add(
                    ProductRawDataVO(
                        name = ProductDataParser.parseProductName(title),
                        brandName = ProductDataParser.parseBrandName(title),
                        price = ProductDataParser.parsePrice(price),
                        categoryType = category.productCategoryType ?: ProductCategoryRule.parse(title),
                        barcode = ProductDataParser.parseProductCode(imgURL) ?: "",
                        imageUrl = imgURL,
                        retailerType = RetailerType.CU,
                        isPbProduct = category.isPbProduct
                    )
                )
            }
            pageIndex++
        } while (productElements.size > 0)
        return productCollections
    }

    private fun saveProductData(productRawDataVOList: List<ProductRawDataVO>) {
        log.info("${productRawDataVOList.size}건 저장")
        productRawDataVOList.forEach {
            productDataProcessor.saveProduct(it)
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
