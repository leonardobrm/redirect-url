package com.bagsafe.redirecturlapi.core.service.io;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class ShortUrlOutput {

    private String originalUrl;

    private String shortUrl;

    private LocalDateTime createdAt;

    private LocalDateTime disableAt;

    private int accessCount;

    private boolean isActive;

}
