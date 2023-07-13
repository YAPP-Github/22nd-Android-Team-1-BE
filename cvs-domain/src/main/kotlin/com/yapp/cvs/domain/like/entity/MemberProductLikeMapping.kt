package com.yapp.cvs.domain.like.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.enums.ProductLikeType
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "member_product_like_mappings")
class MemberProductLikeMapping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberProductLikeMappingId: Long? = null,

    val productId: Long,

    val memberId: Long,

    @Enumerated(EnumType.STRING)
    var likeType: ProductLikeType
): BaseEntity(), Serializable {

    companion object {
        fun like(memberProductMappingKey: MemberProductMappingKey): MemberProductLikeMapping{
            return MemberProductLikeMapping(
                productId = memberProductMappingKey.productId,
                memberId = memberProductMappingKey.memberId,
                likeType = ProductLikeType.LIKE
            )
        }

        fun dislike(memberProductMappingKey: MemberProductMappingKey): MemberProductLikeMapping{
            return MemberProductLikeMapping(
                productId = memberProductMappingKey.productId,
                memberId = memberProductMappingKey.memberId,
                likeType = ProductLikeType.DISLIKE
            )
        }

        fun from(productLikeHistory: ProductLikeHistory): MemberProductLikeMapping {
            return MemberProductLikeMapping(
                    productId = productLikeHistory.productId,
                    memberId = productLikeHistory.memberId,
                    likeType = productLikeHistory.likeType
            )
        }
    }
}

class MemberProductMappingKey(
    val productId: Long,
    val memberId: Long
)
