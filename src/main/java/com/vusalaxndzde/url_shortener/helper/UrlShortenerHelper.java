package com.vusalaxndzde.url_shortener.helper;

import com.google.common.hash.Hashing;
import com.vusalaxndzde.url_shortener.repository.UrlRepository;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class UrlShortenerHelper
{

    private static final String CONVERSION_CODE = "FjTG0s5dgWkbLf_8etOZqMzNhmp7u6lUJoXIDiQB9-wRxCKyrPcv4En3Y21aASHV";
    private static final int SHORT_URL_LENGTH = 8;

    private final UrlRepository urlRepository;

    public UrlShortenerHelper(UrlRepository urlRepository)
    {
        this.urlRepository = urlRepository;
    }

    public boolean doesShortUrlExist(String shortUrl)
    {
        return urlRepository.findById(shortUrl).isPresent();
    }

    public String generateShortUrl(String originalUrl)
    {
        String shortUrl;

        do
        {
            String randomString      = generateRandomString(8);
            String urlWithRandomness = originalUrl + randomString;

            byte[] hash = Hashing.sha256()
                .hashString(urlWithRandomness, StandardCharsets.UTF_8)
                .asBytes();

            StringBuilder shortUrlBuilder = new StringBuilder();

            // Use first 8 bytes of the hash to generate a unique URL
            for (int i = 0; i < SHORT_URL_LENGTH; i++)
            {
                int index = hash[i] & 0xFF;
                shortUrlBuilder.append(CONVERSION_CODE.charAt(index % CONVERSION_CODE.length()));
            }

            shortUrl = shortUrlBuilder.toString();
        }
        while (doesShortUrlExist(shortUrl));

        return shortUrl;
    }

    private String generateRandomString(int length)
    {
        SecureRandom random      = new SecureRandom();
        byte[]       randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).substring(0, length);
    }

}
