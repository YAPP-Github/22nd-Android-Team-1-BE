package com.yapp.cvs.api.comment.dto

import com.yapp.cvs.api.member.dto.MemberDTO
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.member.vo.MemberVO
import java.time.LocalDateTime

data class ProductCommentDetailDTO(
        val productCommentId: Long,
        val productId: Long,
        val member: MemberDTO?,
        val isOwner: Boolean,
        val likeType: ProductLikeType,
        val content: String,
        val commentLikeCount: Long,
        val createdAt: LocalDateTime,
) {
    companion object {
        fun from(productCommentDetailVO: ProductCommentDetailVO): ProductCommentDetailDTO {
            return ProductCommentDetailDTO(
                    productCommentId = productCommentDetailVO.productCommentId,
                    productId = productCommentDetailVO.productId,
                    member = productCommentDetailVO.memberVO ?.let { MemberDTO.from(it) },
                    isOwner = productCommentDetailVO.isOwner,
                    likeType = productCommentDetailVO.likeType,
                    content = productCommentDetailVO.content,
                    commentLikeCount = productCommentDetailVO.commentLikeCount,
                    createdAt = productCommentDetailVO.createdAt
            )
        }
    }
}
