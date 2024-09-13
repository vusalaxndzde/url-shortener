package com.vusalaxndzde.url_shortener.controller;

import com.vusalaxndzde.url_shortener.dto.UrlRequest;
import com.vusalaxndzde.url_shortener.dto.UrlResponse;
import com.vusalaxndzde.url_shortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController
{

    private final UrlService urlService;

    public UrlController(UrlService urlService)
    {
        this.urlService = urlService;
    }

    @GetMapping("/{shortenedUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortenedUrl)
    {
        String originalUrl = urlService.findOriginalUrl(shortenedUrl);

        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(originalUrl))
            .build();
    }

    @PostMapping
    public ResponseEntity<UrlResponse> shortUrl(@RequestBody UrlRequest request)
    {
        return ResponseEntity.ok(urlService.shortUrl(request));
    }

}
