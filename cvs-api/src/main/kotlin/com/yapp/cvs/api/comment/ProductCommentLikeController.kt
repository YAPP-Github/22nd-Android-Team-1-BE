package com.yapp.cvs.api.comment

import com.yapp.cvs.api.comment.dto.ProductCommentLikeDTO
import com.yapp.cvs.domain.comment.application.ProductCommentLikeProcessor
import com.yapp.cvs.domain.comment.application.ProductCommentProcessor
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product/comment")
class ProductCommentLikeController(
        private val productCommentProcessor: ProductCommentProcessor,
        private val productCommentLikeProcessor: ProductCommentLikeProcessor
) {
    private val memberId = 1L //TODO : security context 에서 memberId 가져오기

    @PostMapping("/{commentId}/like")
    @Operation(summary = "해당 코멘트에 좋아요를 설정합니다.")
    fun likeComment(@PathVariable commentId: Long): ProductCommentLikeDTO {
        val productCommentVO = productCommentProcessor.getComment(commentId)
        return ProductCommentLikeDTO.of(
                commentId,
                productCommentLikeProcessor.likeComment(productCommentVO.productId, productCommentVO.memberId, memberId)
        )
    }

    @PostMapping("/{commentId}/cancel")
    @Operation(summary = "해당 코멘트 좋아요를 취소합니다.")
    fun cancelCommentLike(@PathVariable commentId: Long): ProductCommentLikeDTO {
        val productCommentVO = productCommentProcessor.getComment(commentId)
        return ProductCommentLikeDTO.of(
                commentId,
                productCommentLikeProcessor.cancelCommentLike(productCommentVO.productId, productCommentVO.memberId, memberId)
        )
    }
}