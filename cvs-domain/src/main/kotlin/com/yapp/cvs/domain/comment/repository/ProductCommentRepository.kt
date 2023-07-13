package com.yapp.cvs.domain.comment.repository

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import org.springframework.data.jpa.repository.JpaRepository

interface ProductCommentRepository : JpaRepository<ProductComment, Long>, ProductCommentCustom {
    fun existsByProductIdAndMemberIdAndValidTrue(productId: Long, memberId: Long): Boolean
}

interface ProductCommentCustom {
    fun findLatestByProductIdAndMemberId(productId: Long, memberId: Long): ProductComment?
    fun findAllByCondition(productCommentSearchVO: ProductCommentSearchVO): List<ProductComment>
}
