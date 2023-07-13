package com.yapp.cvs.domain.like.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.product.entity.Product
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.math.max

@Entity
@Table(name = "product_like_summaries")
class ProductLikeSummary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productLikeSummaryId: Long? = null,

    val productId: Long,

    var likeCount: Long,

    var totalCount: Long
) : BaseEntity() {
    fun getDislikeCount(): Long {
        return totalCount - likeCount
    }

    fun getLikeRatio(): Int {
        if (totalCount == 0L) return 0
        return ((likeCount / totalCount) * 100).toInt()
    }

    fun getDislikeRatio(): Int {
        if (totalCount == 0L) return 0
        return 100 - getLikeRatio()
    }

    fun like(){
        likeCount += 1
        totalCount += 1
    }

    fun dislike() {
        totalCount += 1
    }

    fun cancelLike() {
        likeCount = max(likeCount - 1, 0)
        totalCount = max(totalCount - 1, 0)
    }

    fun cancelDislike(){
        totalCount = max(totalCount - 1, 0)
    }

    companion object {
        fun empty(productId: Long): ProductLikeSummary {
            return ProductLikeSummary(
                productId = productId,
                likeCount = 0,
                totalCount = 0
            )
        }
    }
}
