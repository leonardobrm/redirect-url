package com.bagsafe.redirecturlapi.service;

import com.bagsafe.redirecturlapi.core.model.UrlModel;
import com.bagsafe.redirecturlapi.core.service.ShortenUrl;
import com.bagsafe.redirecturlapi.core.service.ShortenUrlImpl;
import com.bagsafe.redirecturlapi.infra.database.repository.UrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class ShortenUrTests {

    private final ShortenUrl shortenUrl;

    private final UrlRepository urlRepository;

    ShortenUrTests() {

        urlRepository = Mockito.mock(UrlRepository.class);
        this.shortenUrl = new ShortenUrlImpl(urlRepository);
    }

    @Test
    void testShortenUrl() {
        final var url = "https://www.google.com.br";
        Mockito.when(urlRepository.findByOriginalUrl(url)).thenReturn(null);
        final var shortUrl = shortenUrl.shorten(url);
        final var expected = "https://localhost:8080/shorten-url/1";
        Assertions.assertEquals(expected, shortUrl);
    }


    @Test
    void shouldThrowExceptionWhenUrlIsNull() {
        final var errorMessage = Assertions.assertThrows(RuntimeException.class, () -> shortenUrl.shorten(null));
        final var errorExpected = "Url is empty";
        Assertions.assertEquals(errorExpected, errorMessage.getMessage());
    }

    @Test
    void shoudReturnUrlWhenAlreadyExists() {
        final var url = "https://www.google.com.br";

        final var model = UrlModel.builder()
                .shortUrl("https://localhost:8080/shorten-url/1")
                .originalUrl(url).build();

        Mockito.when(urlRepository.findByOriginalUrl(url)).thenReturn(Optional.of(model));
        final var urlOutput = shortenUrl.shorten(url);
        final var expected = "https://localhost:8080/shorten-url/1";
        Assertions.assertEquals(expected, urlOutput.getShortUrl());
        Assertions.assertEquals(url, urlOutput.getOriginalUrl());
    }
}
