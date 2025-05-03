package com.bagsafe.redirecturlapi.core.service;

import com.bagsafe.redirecturlapi.core.service.io.ShortUrlOutput;
import com.bagsafe.redirecturlapi.utils.MapperUtils;

public interface ShortenUrl {

    ShortUrlOutput shorten(String url);

    ShortUrlOutput redirectUrl(String url);

    void disable(String id);

    default <T> ShortUrlOutput convertTo(T input) {
        return MapperUtils.map(input, ShortUrlOutput.class);
    }
}
