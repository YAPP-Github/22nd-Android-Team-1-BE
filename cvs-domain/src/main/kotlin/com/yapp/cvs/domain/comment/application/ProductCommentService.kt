package com.yapp.cvs.domain.comment.application

import com.yapp.cvs.domain.comment.entity.ProductComment
import com.yapp.cvs.domain.comment.repository.ProductCommentRepository
import com.yapp.cvs.domain.comment.vo.ProductCommentSearchVO
import com.yapp.cvs.domain.like.entity.MemberProductMappingKey
import com.yapp.cvs.exception.BadRequestException
import com.yapp.cvs.exception.NotFoundSourceException
import org.springframework.stereotype.Service

@Service
class ProductCommentService(
        val productCommentRepository: ProductCommentRepository
) {
    fun findProductCommentsPage(productCommentSearchVO: ProductCommentSearchVO): List<ProductComment> {
        return productCommentRepository.findAllByCondition(productCommentSearchVO)
    }

    fun write(memberProductMappingKey: MemberProductMappingKey, content: String): ProductComment {
        validateCommentDuplication(memberProductMappingKey)
        return productCommentRepository.save(ProductComment(
                productId = memberProductMappingKey.productId,
                memberId = memberProductMappingKey.memberId,
                content = content
        ))
    }

    fun update(memberProductMappingKey: MemberProductMappingKey, content: String): ProductComment {
        inactivate(memberProductMappingKey)
        return productCommentRepository.save(ProductComment(
                productId = memberProductMappingKey.productId,
                memberId = memberProductMappingKey.memberId,
                content = content
        ))
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

    private fun validateCommentDuplication(memberProductMappingKey: MemberProductMappingKey) {
        if (productCommentRepository.existsByProductIdAndMemberIdAndValidTrue(
                        memberProductMappingKey.productId, memberProductMappingKey.memberId)) {
            throw BadRequestException("productId: ${memberProductMappingKey.productId} 에 대한 코멘트가 이미 존재합니다.")
        }
    }
}
