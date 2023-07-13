package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.comment.entity.ProductCommentLike

data class ProductCommentLikeVO(
        val productId: Long,
        val memberId: Long,
        val liked: Boolean
) {
    companion object {
        fun from(productCommentLike: ProductCommentLike): ProductCommentLikeVO {
            return ProductCommentLikeVO(
                    productId = productCommentLike.productId,
                    memberId = productCommentLike.memberId,
                    liked = productCommentLike.valid
            )
        }
    }
}