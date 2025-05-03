package com.bagsafe.redirecturlapi.core.service;

import com.bagsafe.redirecturlapi.core.model.UrlModel;
import com.bagsafe.redirecturlapi.core.service.io.ShortUrlOutput;
import com.bagsafe.redirecturlapi.infra.database.repository.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortenUrlImpl implements ShortenUrl {

    private final UrlRepository urlRepository;

    private static final String BASE_URL = "http:localhost:8080/api/v1/url-api-shorten/r/";

    /*
    * TODO -> check if url is valid
    *      -> added unit tests
    *      -> update base_url variable
    * */

    @Override
    @Transactional
    public ShortUrlOutput shorten(String url) {
        log.info("URL: {}", url);
        if (Objects.isNull(url)) {
            throw new RuntimeException("Url is empty");
        }

        final var urlAlreadyExists = urlRepository.findByOriginalUrl(url);

        if (urlAlreadyExists.isPresent()) {
            log.info("URL: {} already exists", url);
            final var output = convertTo(urlAlreadyExists.get());
            log.info("output={}", output);
            return output;
        }

        final var generateId = UUID.randomUUID().toString().substring(0, 5);

        final var shortUrl = BASE_URL + generateId;

        final var urlModel = UrlModel.builder()
                .id(generateId)
                .originalUrl(url)
                .shortUrl(shortUrl)
                .isActive(true)
                .build();

        final var savedModel = urlRepository.save(urlModel);
        log.info("saved url: {}", savedModel.getOriginalUrl());
        final var output = convertTo(savedModel);
        log.info("output={}", output);
        return output;
    }

    @Override
    @Transactional
    public ShortUrlOutput redirectUrl(String id) {
        log.info("ID: {}", id);

        if (Objects.isNull(id)) {
            log.error("id is null");
            throw new RuntimeException("id is null");
        }

        final var urlModel = urlRepository.findById(id);

        if (urlModel.isEmpty()) {
            log.error("Url not found");
            throw new RuntimeException("Url not found");
        }
        urlModel.get().setAccessCount(urlModel.get().getAccessCount() + 1);
        urlRepository.save(urlModel.get());
        final var output = convertTo(urlModel.get());
        log.info("output={}", output);
        return output;
    }

    @Override
    @Transactional
    public void disable(String id) {
        log.info("ID: {}", id);
        if (Objects.isNull(id)) {
            log.error("id is null");
            throw new RuntimeException("id is null");
        }
        final var urlModel = urlRepository.findById(id);
        disabledUrl(urlModel.get());
        if (urlModel.isEmpty()) {
            log.error("Url not found");
            throw new RuntimeException("Url not found");
        }
        urlRepository.save(urlModel.get());
        log.info("Url disabled");
    }

    private void disabledUrl(final UrlModel urlModel) {
        urlModel.setDisableAt(java.time.LocalDateTime.now());
        urlModel.setActive(false);
    }
}
