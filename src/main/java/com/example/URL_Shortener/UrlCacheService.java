package com.example.URL_Shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UrlCacheService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    private final String PREFIX = "shorturl:";

    public void cacheUrl(String shortCode, String originalUrl) {
        redisTemplate.opsForValue().set(PREFIX+shortCode, originalUrl, 2, TimeUnit.MINUTES);
    }

    public String getOriginalUrl(String shortCode) {
        return redisTemplate.opsForValue().get(PREFIX + shortCode);
    }
}
