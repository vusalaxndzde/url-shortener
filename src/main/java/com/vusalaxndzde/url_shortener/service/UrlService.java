package com.vusalaxndzde.url_shortener.service;

import com.vusalaxndzde.url_shortener.dto.UrlRequest;
import com.vusalaxndzde.url_shortener.dto.UrlResponse;
import com.vusalaxndzde.url_shortener.entity.Url;
import com.vusalaxndzde.url_shortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlService
{

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository)
    {
        this.urlRepository = urlRepository;
    }

    public Url save(UrlRequest urlRequest)
    {
        Url url = new Url();
        url.setOriginalUrl(urlRequest.getUrl());
        url.setShortenedUrl(urlRequest.getUrl() + "shorted");
        url.setExpiresAt(LocalDateTime.now().plusMinutes(30));

        return urlRepository.save(url);
    }


    public UrlResponse get(String shortenedUrl)
    {
        return urlRepository.findById(shortenedUrl)
            .map(UrlResponse::new).
            orElseThrow(RuntimeException::new);
    }

}
