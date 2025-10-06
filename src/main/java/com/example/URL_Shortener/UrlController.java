package com.example.URL_Shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UrlController {
    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlCacheService urlCacheService;

    @PostMapping("/resolve")
    public ResponseEntity<?> resolveUrl(@RequestBody Map<String, String> payload) {
        String urlInput = payload.get("url");
        if (urlInput.startsWith("http://short.url/")) {
            return getOriginalUrl(payload.get("url"));
        } else {
            URL url = urlService.createShortUrl(urlInput);
            return ResponseEntity.ok(Map.of("shortUrl", url.getShortUrl()));
        }
    }

    public ResponseEntity<?> getOriginalUrl(String shortUrl) {
        if (shortUrl == null || shortUrl.isEmpty()) {
            return ResponseEntity.badRequest().body("shortUrl is required");
        }

        String shortCode = shortUrl.substring(shortUrl.lastIndexOf('/') + 1);

        String isUrlCached = urlCacheService.getOriginalUrl(shortCode);
        if (isUrlCached != null) {
            return ResponseEntity.ok(Map.of("originalUrlCached",isUrlCached));
        }

        Optional<URL> urlOpt = urlService.getUrlByShortCode(shortCode);

        if (urlOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        URL url = urlOpt.get();

        if (new Date().after(url.getExpiryDate())) {
            return ResponseEntity.status(410).body("Link expired");
        }

        return ResponseEntity.ok(Map.of("originalUrl",url.getOriginalUrl()));
    }
}
