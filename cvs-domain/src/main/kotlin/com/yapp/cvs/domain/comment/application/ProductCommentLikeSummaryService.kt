package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.repository.ProductCommentLikeRepository
import com.yapp.cvs.domain.comment.repository.ProductCommentRepository
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.exception.BadRequestException
import com.yapp.cvs.exception.NotFoundSourceException
import com.yapp.cvs.infrastructure.redis.RedisKey
import com.yapp.cvs.infrastructure.redis.RedisKeyType
import com.yapp.cvs.infrastructure.redis.service.RedisService
import org.springframework.stereotype.Service

@Service
class ProductCommentLikeSummaryService(
        val productCommentLikeRepository: ProductCommentLikeRepository,
        val redisService: RedisService
) {
    fun getCommentLikeCount(productId: Long, memberId: Long): Long {
        val redisKey = generateRedisKey(productId, memberId)
        return redisService.getAtomicLongOrNull(redisKey)
                ?: productCommentLikeRepository.countByProductIdAndMemberId(productId, memberId)
                        .also { count -> redisService.setAtomicLong(redisKey, count) }
    }

    fun increaseCommentLikeCount(productId: Long, memberId: Long) {
        redisService.increment(generateRedisKey(productId, memberId))
    }

    fun decreaseCommentLikeCount(productId: Long, memberId: Long) {
        redisService.decrement(generateRedisKey(productId, memberId))
    }

    private fun generateRedisKey(productId: Long, memberId: Long): RedisKey {
        return RedisKey.createKey(RedisKeyType.PRODUCT_COMMENT_LIKE, productId.toString(), memberId.toString())
    }
}
