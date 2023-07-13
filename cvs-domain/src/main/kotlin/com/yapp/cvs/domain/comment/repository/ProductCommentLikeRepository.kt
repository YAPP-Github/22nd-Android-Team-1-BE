package com.yapp.cvs.domain.comment.repository

import com.yapp.cvs.domain.comment.entity.ProductCommentLike
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommentLikeRepository : JpaRepository<ProductCommentLike, Long>, ProductCommentLikeCustom

interface ProductCommentLikeCustom {
    fun findLatestLike(productId: Long, memberId: Long, likeMemberId: Long): ProductCommentLike?
    fun countByProductIdAndMemberId(productId: Long, memberId: Long): Long
}
