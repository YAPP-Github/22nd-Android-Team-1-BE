package com.yapp.cvs.domain.member.vo

import com.yapp.cvs.domain.member.entity.Member

data class MemberVO(
        val memberId: Long,
        val nickname: String
) {
    companion object {
        fun from(member: Member): MemberVO {
            return MemberVO(
                    memberId = member.memberId!!,
                    nickname = member.nickName
            )
        }
    }
}