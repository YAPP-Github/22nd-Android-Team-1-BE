package com.yapp.cvs.infrastructure.redis.service

import com.yapp.cvs.domain.extension.ifTrue
import com.yapp.cvs.infrastructure.redis.RedisKey
import org.redisson.api.RBatch
import org.redisson.api.RBucket
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.concurrent.TimeUnit

@Component
class RedissonRedisService(
    private val redissonClient: RedissonClient
): RedisService {
    private fun <T> rBucket(key: String): RBucket<T> {
        return redissonClient.getBucket(key)
    }

    private fun rBatch(): RBatch {
        return redissonClient.createBatch()
    }

    override fun <T> get(key: RedisKey): T? {
        return rBucket<T>(key.value).get()
    }

    override fun <T> getAndExpire(key: RedisKey, duration: Duration): T? {
        return rBucket<T>(key.value).getAndExpire(duration)
    }

    override fun <T> set(key: RedisKey, value: T) {
        rBucket<T>(key.value).set(value)
    }

    override fun <T> set(key: RedisKey, value: T, timeout: Long, unit: TimeUnit) {
        rBucket<T>(key.value).set(value, timeout, unit)
    }

    override fun <T> setIfAbsent(key: RedisKey, value: T, duration: Duration): Boolean {
        return rBucket<T>(key.value).setIfAbsent(value, duration)
    }

    override fun delete(key: RedisKey) {
        rBucket<String>(key.value).delete()
    }

    override fun <T> expire(key: RedisKey, duration: Duration) {
        rBucket<T>(key.value).expire(duration)
    }

    override fun <T> expire(key: RedisKey, instant: Instant) {
        rBucket<T>(key.value).expire(instant)
    }

    override fun getAtomicLong(key: RedisKey): Long {
        return redissonClient.getAtomicLong(key.value).get()
    }

     override fun getAtomicLongOrNull(key: RedisKey): Long? {
        return redissonClient.getAtomicLong(key.value).takeIf { it.isExists } ?.get()
    }

    override fun setAtomicLong(key: RedisKey, value: Long) {
        redissonClient.getAtomicLong(key.value).set(value)
    }

    override fun increment(key: RedisKey): Long {
        return redissonClient.getAtomicLong(key.value).incrementAndGet()
    }

    override fun decrement(key: RedisKey): Long {
        return redissonClient.getAtomicLong(key.value).decrementAndGet()
    }
}