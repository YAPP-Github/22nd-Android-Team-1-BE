package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductCommentLike
import com.yapp.cvs.domain.comment.repository.ProductCommentLikeRepository
import com.yapp.cvs.exception.BadRequestException
import org.springframework.stereotype.Service

@Service
class ProductCommentLikeService(
        val productCommentLikeRepository: ProductCommentLikeRepository,
) {
    fun like(productId: Long, memberId: Long, likeMemberId: Long): ProductCommentLike {
        validateCommentLikeDuplication(productId, memberId, likeMemberId)
        val commentLike = ProductCommentLike.like(productId, memberId, likeMemberId)
        return productCommentLikeRepository.save(commentLike)
    }

    fun cancel(productId: Long, memberId: Long, likeMemberId: Long): ProductCommentLike {
        return productCommentLikeRepository.findLatestLike(productId, memberId, likeMemberId)
                ?.apply { valid = false }
                ?: throw BadRequestException("이미 좋아요를 취소한 코멘트입니다.")
    }

    private fun validateCommentLikeDuplication(productId: Long, memberId: Long, likeMemberId: Long) {
        if (productCommentLikeRepository.findLatestLike(productId, memberId, likeMemberId) != null) {
            throw BadRequestException("이미 좋아요 요청한 코멘트입니다.")
        }
    }
}
