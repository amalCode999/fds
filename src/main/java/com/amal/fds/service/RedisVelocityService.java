package com.amal.fds.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisVelocityService {
    private final StringRedisTemplate redisTemplate;

    public RedisVelocityService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Mengecek apakah akun telah melebihi batas transaksi dalam jendela waktu tertentu.
     * 
     * @param accountId ID pengguna/akun
     * @param maxAllowed Jumlah maksimal transaksi yang diizinkan
     * @param windowTime Durasi jendela waktu
     * @return true jika melebihi batas (terindikasi fraud), false jika aman
     */
    public boolean isVelocityExceeded(String accountId, int maxAllowed, Duration windowTime) {
        String key = "fraud:velocity:account:" + accountId;

        Long currentCount = redisTemplate.opsForValue().increment(key);

        if (currentCount != null && currentCount == 1) {
            redisTemplate.expire(key, windowTime);
        }

        return currentCount != null && currentCount > maxAllowed;
    }
}
