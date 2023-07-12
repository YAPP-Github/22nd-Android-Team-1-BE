package com.yapp.cvs.domain.like.application

import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.domain.like.entity.ProductLikeSummary
import com.yapp.cvs.domain.like.repository.ProductLikeSummaryRepository
import com.yapp.cvs.domain.like.vo.ProductLikeSummaryVO
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service


@Service
class ProductLikeSummaryService(
    private val productLikeSummaryRepository: ProductLikeSummaryRepository
) {
    fun getProductLikeSummary(productId: Long): ProductLikeSummaryVO {
        return ProductLikeSummaryVO.from(productLikeSummaryRepository.findByProductId(productId))
    }

    fun findProductLikeSummary(productId: Long): ProductLikeSummary {
        return productLikeSummaryRepository.findByProductId(productId)
    }

    @Async(value = "productLikeSummaryTaskExecutor")
    @DistributedLock(type = DistributedLockType.PRODUCT_LIKE, keys = ["productId"])
    fun likeProductLikeSummary(productId: Long) {
        val productLikeSummary = productLikeSummaryRepository.findByProductId(productId)
        productLikeSummary.like()
        productLikeSummaryRepository.save(productLikeSummary)
    }

    @Async(value = "productLikeSummaryTaskExecutor")
    @DistributedLock(type = DistributedLockType.PRODUCT_LIKE, keys = ["productId"])
    fun dislikeProductLikeSummary(productId: Long) {
        val productLikeSummary = productLikeSummaryRepository.findByProductId(productId)
        productLikeSummary.dislike()
        productLikeSummaryRepository.save(productLikeSummary)
    }
    @Async(value = "productLikeSummaryTaskExecutor")
    @DistributedLock(type = DistributedLockType.PRODUCT_LIKE, keys = ["productId"])
    fun cancelLikeProductLikeSummary(productId: Long) {
        val productLikeSummary = productLikeSummaryRepository.findByProductId(productId)
        productLikeSummary.cancelLike()
        productLikeSummaryRepository.save(productLikeSummary)
    }

    @Async(value = "productLikeSummaryTaskExecutor")
    @DistributedLock(type = DistributedLockType.PRODUCT_LIKE, keys = ["productId"])
    fun cancelDislikeProductLikeSummary(productId: Long) {
        val productLikeSummary = productLikeSummaryRepository.findByProductId(productId)
        productLikeSummary.cancelDislike()
        productLikeSummaryRepository.save(productLikeSummary)
    }
}
