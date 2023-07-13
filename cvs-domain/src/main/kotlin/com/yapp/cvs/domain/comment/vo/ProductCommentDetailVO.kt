package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.enums.ProductLikeType
import com.yapp.cvs.domain.extension.ifNotNull
import com.yapp.cvs.domain.member.vo.MemberVO
import java.time.LocalDateTime

class ProductCommentDetailVO(
        val productCommentId: Long,
        val content: String,
        val commentLikeCount: Long,
        val createdAt: LocalDateTime,
        val likeType: ProductLikeType,
        val productId: Long,

        val memberVO: MemberVO?,
        val isOwner: Boolean
) {
    companion object {
        fun of(productComment: ProductComment, memberId: Long): ProductCommentDetailVO {
            return ProductCommentDetailVO(
                    productCommentId = productComment.productCommentId!!,
                    content = productComment.content,
                    commentLikeCount = 10L, //todo : 좋아요 카운트 필요
                    createdAt = productComment.createdAt,
                    likeType = productComment.memberProductLikeMappingList.firstOrNull()?.likeType ?: ProductLikeType.NONE,
                    productId = productComment.productId,
                    memberVO = productComment.member.ifNotNull { MemberVO.from(it) },
                    isOwner = productComment.member?.memberId!! == memberId
            )
        }
    }
}
