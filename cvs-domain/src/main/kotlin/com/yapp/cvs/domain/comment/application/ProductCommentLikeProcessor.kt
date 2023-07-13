package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.vo.ProductCommentLikeVO
import com.yapp.cvs.domain.enums.DistributedLockType
import com.yapp.cvs.infrastructure.redis.lock.DistributedLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductCommentLikeProcessor(
        private val productCommentLikeService: ProductCommentLikeService,
        private val productCommentLikeSummaryService: ProductCommentLikeSummaryService
) {
    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productId", "memberId"])
    fun likeComment(productId: Long, memberId: Long, likeMemberId: Long): ProductCommentLikeVO {
        val commentLike = productCommentLikeService.like(productId, memberId, memberId)
        productCommentLikeSummaryService.increaseCommentLikeCount(productId, memberId)
        return ProductCommentLikeVO.from(commentLike)
    }

    @DistributedLock(DistributedLockType.MEMBER_PRODUCT, ["productId", "memberId"])
    fun cancelCommentLike(productId: Long, memberId: Long, likeMemberId: Long): ProductCommentLikeVO {
        val commentLike = productCommentLikeService.cancel(productId, memberId, memberId)
        productCommentLikeSummaryService.decreaseCommentLikeCount(productId, memberId)
        return ProductCommentLikeVO.from(commentLike)
    }
}
