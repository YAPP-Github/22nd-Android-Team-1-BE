package com.yapp.cvs.api.comment.dto

import com.yapp.cvs.domain.comment.entity.ProductCommentOrderType
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO


data class ProductCommentSearchDTO(
        val pageSize: Long = 10,
        val offsetProductCommentId: Long? = null,
        val orderBy: ProductCommentOrderType = ProductCommentOrderType.RECENT
) {
    fun toVO(productId: Long ?= null): ProductCommentSearchVO {
        return ProductCommentSearchVO(
                pageSize = pageSize,
                offsetProductCommentId = offsetProductCommentId,
                orderBy = orderBy,
                productId = productId
        )
    }
}