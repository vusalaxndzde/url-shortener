package com.vusalaxndzde.url_shortener.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vusalaxndzde.url_shortener.entity.Url;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponse
{

    private String shortenedUrl;

    private String originalUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiresAt;

    public UrlResponse(Url url)
    {
        this.shortenedUrl = url.getShortenedUrl();
        this.originalUrl = url.getOriginalUrl();
        this.expiresAt = url.getExpiresAt();
    }

}
