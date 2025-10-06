package com.example.URL_Shortener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UrlService {
    @Autowired
    private UrlRepo urlRepository;

    @Autowired
    private UrlCacheService urlCacheService;

    private final String BASE_URL = "http://short.url/";

    // Simple Base62 character set for encoding
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Convert numeric ID to Base62 string
    private String encodeBase62(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62.charAt(num % 62));
            num /= 62;
        }
        return sb.reverse().toString();
    }

    public URL createShortUrl(String originalUrl) {
        URL url = new URL();
        url.setOriginalUrl(originalUrl);
        url.setCreatedAt(new Date());

        // Set expiry date, e.g., 7 days from now
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(url.getCreatedAt());
        calendar.add(Calendar.DATE, 7);
        url.setExpiryDate(calendar.getTime());

        url = urlRepository.save(url);

        String shortCode = encodeBase62(url.getUrlID());
        String shortUrl = BASE_URL + shortCode;
        url.setShortUrl(shortUrl);

        urlCacheService.cacheUrl(shortCode, originalUrl);

        return urlRepository.save(url);
    }

    public Optional<URL> getUrlByShortCode(String shortCode) {
        // Decode Base62 back to ID
        int id = decodeBase62(shortCode);
        return urlRepository.findById(id);
    }

    private int decodeBase62(String str) {
        int num = 0;
        for (char c : str.toCharArray()) {
            num = num * 62 + BASE62.indexOf(c);
        }
        return num;
    }
}
