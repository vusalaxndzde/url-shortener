package com.vusalaxndzde.url_shortener.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlRequest
{

    @NotBlank
    private String url;

    @Valid
    private Ttl ttl;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Ttl
    {

        @Min(0)
        @Max(5)
        @NotNull
        private Integer days;

        @Min(0)
        @Max(23)
        @NotNull
        private Integer hours;

        @Min(0)
        @Max(59)
        @NotNull
        private Integer minutes;

    }

}
