package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.repository.ProductCommentRepository
import com.yapp.cvs.domain.comment.vo.ProductCommentDetailVO
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.domain.like.entity.MemberProductMappingKey
import com.yapp.cvs.exception.BadRequestException
import com.yapp.cvs.exception.NotFoundSourceException
import org.springframework.stereotype.Service

@Service
class ProductCommentService(
        val productCommentRepository: ProductCommentRepository
) {
    fun findById(commentId: Long): ProductComment {
        return productCommentRepository.findLatestById(commentId)
                ?: throw NotFoundSourceException("commentId: $commentId 에 해당하는 코멘트를 찾을 수 없습니다.")
    }

    fun findProductCommentsPage(productId: Long,
                                memberId: Long,
                                productCommentSearchVO: ProductCommentSearchVO): List<ProductCommentDetailVO> {
        return productCommentRepository.findAllByProductIdAndPageOffset(productId, memberId, productCommentSearchVO)
    }

    fun write(productId: Long, memberId: Long, content: String): ProductComment {
        validateCommentDuplication(productId, memberId)
        val comment = ProductComment(productId = productId, memberId = memberId, content = content)
        return productCommentRepository.save(comment)
    }

    fun update(productId: Long, memberId: Long, content: String): ProductComment {
        inactivate(MemberProductMappingKey(productId = productId, memberId = memberId))
        val newComment = ProductComment(productId = productId, memberId = memberId, content = content)
        return productCommentRepository.save(newComment)
    }

    fun activate(memberProductMappingKey: MemberProductMappingKey) {
        productCommentRepository.findLatestByProductIdAndMemberId(
            memberProductMappingKey.productId, memberProductMappingKey.memberId)
            ?.apply { if(!valid) valid = true }
    }

    fun inactivate(memberProductMappingKey: MemberProductMappingKey) {
        productCommentRepository.findLatestByProductIdAndMemberId(
            memberProductMappingKey.productId, memberProductMappingKey.memberId)
            ?.apply { if(valid) valid = false }
            ?: throw NotFoundSourceException("productId: $memberProductMappingKey.productId 에 대한 코멘트가 존재하지 않습니다.")
    }

    fun inactivateIfExist(memberProductMappingKey: MemberProductMappingKey) {
        productCommentRepository.findLatestByProductIdAndMemberId(
            memberProductMappingKey.productId, memberProductMappingKey.memberId)
            ?.apply { if(valid) valid = false }
    }

    private fun validateCommentDuplication(productId: Long, memberId: Long) {
        if (productCommentRepository.existsByProductIdAndMemberIdAndValidTrue(productId, memberId)) {
            throw BadRequestException("productId: $productId 에 대한 코멘트가 이미 존재합니다.")
        }
    }
}
