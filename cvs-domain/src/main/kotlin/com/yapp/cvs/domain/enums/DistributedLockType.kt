package com.yapp.cvs.domain.enums

enum class DistributedLockType(
    val lockName: String
) {
    MEMBER("memberLock"),
    LIKE("productLikeLock"),
    COMMENT("productCommentLock")
}