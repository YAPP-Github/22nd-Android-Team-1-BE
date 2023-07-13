package com.yapp.cvs.domain.comment.entity

import com.yapp.cvs.domain.base.BaseEntity
import com.yapp.cvs.domain.like.entity.MemberProductLikeMapping
import com.yapp.cvs.domain.member.entity.Member
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "product_comments")
class ProductComment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val productCommentId: Long? = null,

        val productId: Long,

        val memberId: Long,

        var content: String,

        var valid: Boolean = true,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "memberId", referencedColumnName = "memberId", insertable = false, updatable = false)
        val member: Member? = null,

        @OneToMany(fetch = FetchType.LAZY)
        @JoinColumn(name = "productId", referencedColumnName = "productId", insertable = false, updatable = false)
        @JoinColumn(name = "memberId", referencedColumnName = "memberId", insertable = false, updatable = false)
        val memberProductLikeMappingList: Set<MemberProductLikeMapping> = setOf()
): BaseEntity(), Serializable