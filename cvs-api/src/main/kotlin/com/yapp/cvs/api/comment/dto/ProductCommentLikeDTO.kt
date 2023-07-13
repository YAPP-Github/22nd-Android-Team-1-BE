package com.yapp.cvs.api.comment.dto

import com.yapp.cvs.domain.comment.vo.ProductCommentLikeVO

data class ProductCommentLikeDTO(
        val productCommentId: Long,
        val productId: Long,
        val memberId: Long,
        val liked: Boolean
) {
    companion object {
        fun of(productCommentId: Long, productCommentLikeVO: ProductCommentLikeVO): ProductCommentLikeDTO {
            return ProductCommentLikeDTO(
                    productCommentId = productCommentId,
                    productId = productCommentLikeVO.productId,
                    memberId = productCommentLikeVO.memberId,
                    liked = productCommentLikeVO.liked
            )
        }
    }
}