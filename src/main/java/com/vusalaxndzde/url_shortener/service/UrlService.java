package com.vusalaxndzde.url_shortener.service;

import com.vusalaxndzde.url_shortener.config.AppConfig;
import com.vusalaxndzde.url_shortener.dto.UrlRequest;
import com.vusalaxndzde.url_shortener.dto.UrlResponse;
import com.vusalaxndzde.url_shortener.entity.Url;
import com.vusalaxndzde.url_shortener.exception.UrlNotFoundException;
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

    public String findOriginalUrl(String shortenedUrl)
    {
        return repository.findById(shortenedUrl).map(Url::getOriginalUrl)
            .orElseThrow(UrlNotFoundException::new);
    }

    public UrlResponse shortUrl(UrlRequest urlRequest)
    {
        String originalUrl  = urlRequest.getUrl();
        String shortedUrl   = urlShortenerHelper.generateShortUrl(originalUrl);
        Long   ttl          = calculateTtlInSeconds(urlRequest.getExpireDay(), urlRequest.getExpireHour(), urlRequest.getExpireMinute());

        ttl = ttl == 0 ? appConfig.getDefaultTtl() : ttl;

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortenedUrl(shortedUrl);
        url.setExpiresAt(LocalDateTime.now().plusSeconds(ttl));
        url.setTtl(ttl);
        repository.save(url);

        return UrlResponse.builder()
            .originalUrl(originalUrl)
            .shortenedUrl(appConfig.getBaseUri() + "/" + shortedUrl)
            .expiresAt(url.getExpiresAt())
            .build();
    }

    private Long calculateTtlInSeconds(Integer expireDay, Integer expireHour, Integer expireMinute)
    {
        expireDay = (expireDay == null) ? 0 : expireDay;
        expireHour = (expireHour == null) ? 0 : expireHour;
        expireMinute = (expireMinute == null) ? 0 : expireMinute;

        return (long) (expireDay * 24 * 60 * 60 + expireHour * 60 * 60 + expireMinute * 60);
    }

}
