package com.vusalaxndzde.url_shortener.service;

import com.vusalaxndzde.url_shortener.config.AppConfig;
import com.vusalaxndzde.url_shortener.dto.UrlRequest;
import com.vusalaxndzde.url_shortener.dto.UrlResponse;
import com.vusalaxndzde.url_shortener.entity.Url;
import com.vusalaxndzde.url_shortener.repository.UrlRepository;
import com.vusalaxndzde.url_shortener.helper.UrlShortenerHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlService
{

    private final UrlRepository repository;
    private final UrlShortenerHelper urlShortenerHelper;
    private final AppConfig appConfig;

    public UrlService(UrlRepository repository, UrlShortenerHelper urlShortenerHelper, AppConfig appConfig)
    {
        this.repository = repository;
        this.urlShortenerHelper = urlShortenerHelper;
        this.appConfig = appConfig;
    }

    public UrlResponse shortUrl(UrlRequest urlRequest)
    {
        String originalUrl = urlRequest.getUrl();
        String shortedUrl  = urlShortenerHelper.generateShortUrl(originalUrl);

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortenedUrl(shortedUrl);
        url.setExpiresAt(LocalDateTime.now().plusMinutes(30));
        repository.save(url);

        return UrlResponse.builder()
            .originalUrl(originalUrl)
            .shortenedUrl(appConfig.getBaseUri() + "/" + shortedUrl)
            .expiresAt(url.getExpiresAt())
            .build();
    }

    public UrlResponse get(String shortenedUrl)
    {
        return repository.findById(shortenedUrl).map(UrlResponse::new).
            orElseThrow(RuntimeException::new);
    }

}
