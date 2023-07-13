package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.enums.ProductLikeType
import java.time.LocalDateTime

data class ProductCommentDetailVO(
        val productCommentId: Long,
        val content: String,
        val createdAt: LocalDateTime,
        val likeType: ProductLikeType,
        var commentLikeCount: Long,
        val productId: Long,
        val memberId: Long,
        val nickname: String,
        val liked: Boolean,
        val isOwner: Boolean,
)
