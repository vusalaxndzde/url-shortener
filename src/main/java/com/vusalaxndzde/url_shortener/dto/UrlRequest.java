package com.vusalaxndzde.url_shortener.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String url;

    @Min(0)
    @Max(5)
    private Integer expireDay;

    @Min(0)
    @Max(24)
    private Integer expireHour;

    @Min(0)
    @Max(60)
    private Integer expireMinute;

}
