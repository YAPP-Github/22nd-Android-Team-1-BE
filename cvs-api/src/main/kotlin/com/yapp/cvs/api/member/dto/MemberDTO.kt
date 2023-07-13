package com.yapp.cvs.api.member.dto

import com.yapp.cvs.domain.member.vo.MemberVO

data class MemberDTO (
    val memberId: Long,
    val nickname: String
) {
    companion object {
        fun from(memberVO: MemberVO): MemberDTO {
            return MemberDTO(
                    memberId = memberVO.memberId,
                    nickname = memberVO.nickname
            )
        }
    }
}