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
import java.util.Optional;

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
        Long   ttl          = getEffectiveTtl(urlRequest.getTtl());

        Url url = getUrl(originalUrl, shortedUrl, ttl);
        repository.save(url);

        return entityToResponse(url);
    }

    private Long getEffectiveTtl(UrlRequest.Ttl ttl)
    {
        return calculateTtlInSeconds(ttl) == 0 ? appConfig.getDefaultTtl() : calculateTtlInSeconds(ttl);
    }

    private Long calculateTtlInSeconds(UrlRequest.Ttl ttl)
    {
        int expireDay = Optional.ofNullable(ttl)
            .map(t -> t.getDays() * 24 * 60 * 60)
            .orElse(0);

        int expireHour = Optional.ofNullable(ttl)
            .map(t -> t.getHours() * 60 * 60)
            .orElse(0);

        int expireMinute = Optional.ofNullable(ttl)
            .map(t -> t.getMinutes() * 60)
            .orElse(0);

        return (long) (expireDay + expireHour + expireMinute);
    }

    private Url getUrl(String originalUrl, String shortedUrl, Long ttl)
    {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortenedUrl(shortedUrl);
        url.setExpiresAt(LocalDateTime.now().plusSeconds(ttl));
        url.setTtl(ttl);
        return url;
    }

    private UrlResponse entityToResponse(Url url)
    {
        return UrlResponse.builder()
            .originalUrl(url.getOriginalUrl())
            .shortenedUrl(appConfig.getBaseUri() + "/" + url.getShortenedUrl())
            .expiresAt(url.getExpiresAt())
            .build();
    }

}
