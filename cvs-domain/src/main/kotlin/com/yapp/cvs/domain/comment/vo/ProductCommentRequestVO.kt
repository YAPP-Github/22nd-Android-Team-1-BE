package com.yapp.cvs.domain.comment.vo

import com.yapp.cvs.domain.like.entity.MemberProductMappingKey

class ProductCommentRequestVO(
    val productId: Long,
    val memberId: Long
) {
    val memberProductMappingKey: MemberProductMappingKey = MemberProductMappingKey(
        productId = productId,
        memberId = memberId
    )
}