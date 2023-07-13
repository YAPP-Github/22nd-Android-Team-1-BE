package com.yapp.cvs.infrastructure.redis.service

import com.yapp.cvs.infrastructure.redis.RedisKey
import java.time.Duration
import java.time.Instant
import java.util.concurrent.TimeUnit

interface RedisService {
    fun <T> get(key: RedisKey): T?
    fun <T> getAndExpire(key: RedisKey, duration: Duration): T?
    fun <T> set(key: RedisKey, value: T)
    fun <T> set(key: RedisKey, value: T, timeout: Long, unit: TimeUnit)
    fun <T> setIfAbsent(key: RedisKey, value: T, duration: Duration): Boolean
    fun delete(key: RedisKey)
    fun <T> expire(key: RedisKey, duration: Duration)
    fun <T> expire(key: RedisKey, instant: Instant)
    fun getAtomicLong(key: RedisKey): Long
    fun getAtomicLongOrNull(key: RedisKey): Long?
    fun setAtomicLong(key: RedisKey, value: Long)
    fun increment(key: RedisKey): Long
    fun decrement(key: RedisKey): Long
}