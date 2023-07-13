package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.comment.entity.ProductCommentOrderType

data class ProductCommentSearchVO(
        val pageSize: Long,
        val offsetProductCommentId: Long?,
        val orderBy: ProductCommentOrderType,
        val productId: Long?
)
