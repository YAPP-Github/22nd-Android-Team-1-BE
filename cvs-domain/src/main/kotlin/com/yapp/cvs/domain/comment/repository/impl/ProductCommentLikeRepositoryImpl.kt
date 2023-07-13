package com.yapp.cvs.domain.comment.repository.impl

import com.yapp.cvs.domain.comment.entity.ProductCommentLike
import com.yapp.cvs.domain.comment.entity.QProductCommentLike.productCommentLike
import com.yapp.cvs.domain.comment.repository.ProductCommentLikeCustom
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ProductCommentLikeRepositoryImpl: QuerydslRepositorySupport(ProductCommentLike::class.java), ProductCommentLikeCustom {
    override fun findLatestLike(productId: Long, memberId: Long, likeMemberId: Long): ProductCommentLike? {
        return from(productCommentLike)
                .where(
                        productCommentLike.productId.eq(productId),
                        productCommentLike.memberId.eq(memberId),
                        productCommentLike.likeMemberId.eq(likeMemberId),
                        productCommentLike.valid.isTrue
                )
                .orderBy(productCommentLike.productCommentLikeId.desc())
                .fetchFirst()
    }

    override fun countByProductIdAndMemberId(productId: Long, memberId: Long): Long {
        return from(productCommentLike)
                .where(
                        productCommentLike.productId.eq(productId),
                        productCommentLike.memberId.eq(memberId),
                        productCommentLike.valid.isTrue
                )
                .fetchCount()
    }
}
