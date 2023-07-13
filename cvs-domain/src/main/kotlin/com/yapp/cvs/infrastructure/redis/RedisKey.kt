package com.yapp.cvs.infrastructure.redis

class RedisKey constructor(
    val value: String
) {
    companion object {
        private const val KEY_SEPARATOR = ":"

        fun createKey(redisKeyType: RedisKeyType, vararg keys: String): RedisKey {
            return RedisKey(redisKeyType.key + KEY_SEPARATOR + keys.joinToString(KEY_SEPARATOR))
        }
    }
}

enum class RedisKeyType(
    val key: String
) {
    MEMBER_ACCESS_TOKEN("member-access-token"),
    PRODUCT_VIEW("product-view"),
    PRODUCT_LIKE("product-like"),
    PRODUCT_COMMENT_LIKE("product-comment-like"),
}