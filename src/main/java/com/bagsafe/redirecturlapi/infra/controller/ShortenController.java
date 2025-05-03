package com.bagsafe.redirecturlapi.infra.controller;

import com.bagsafe.redirecturlapi.core.service.ShortenUrl;
import com.bagsafe.redirecturlapi.core.service.io.ShortUrlOutput;
import com.bagsafe.redirecturlapi.infra.controller.io.ShortenUrlRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
@RequiredArgsConstructor
public class ShortenController {

    private final ShortenUrl shortenUrlService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlOutput> shorten(@RequestBody ShortenUrlRequest request) {
        log.info("URL: {}", request.getUrl());
        final var shortUrl = shortenUrlService.shorten(request.getUrl());
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/url-api-shorten/r/{id}")
    public ResponseEntity redirect(@PathVariable String id) {
        log.info("ID: {}", id);
        final var url = shortenUrlService.redirectUrl(id);
        log.info("shortUrl={}", url.getOriginalUrl());
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url.getOriginalUrl())).build();
    }

    @PutMapping("disable/{id}")
    public ResponseEntity disable(@PathVariable String id) {
        log.info("ID: {}", id);
        shortenUrlService.disable(id);
        return ResponseEntity.ok().build();
    }
}
