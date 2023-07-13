package com.yapp.cvs.api.comment.dto

import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.enums.ProductLikeType
import java.time.LocalDateTime

data class ProductCommentDetailDTO(
        val productCommentId: Long,
        val content: String,
        val commentLikeCount: Long,
        val isOwner: Boolean,
        val createdAt: LocalDateTime,
        val likeType: ProductLikeType,
        val liked: Boolean,

        val memberId: Long,
        val nickname: String,

        val productId: Long
) {
    companion object {
        fun from(productCommentDetailVO: ProductCommentDetailVO): ProductCommentDetailDTO {
            return ProductCommentDetailDTO(
                    productCommentId = productCommentDetailVO.productCommentId,
                    content = productCommentDetailVO.content,
                    commentLikeCount = productCommentDetailVO.commentLikeCount,
                    isOwner = productCommentDetailVO.isOwner,
                    createdAt = productCommentDetailVO.createdAt,
                    likeType = productCommentDetailVO.likeType,
                    liked = productCommentDetailVO.liked,
                    memberId = productCommentDetailVO.memberId,
                    nickname = productCommentDetailVO.nickname,
                    productId = productCommentDetailVO.productId
            )
        }
    }
}
