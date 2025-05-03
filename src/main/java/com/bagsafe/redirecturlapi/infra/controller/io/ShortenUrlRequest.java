package com.bagsafe.redirecturlapi.infra.controller.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShortenUrlRequest {

    private String url;
}
