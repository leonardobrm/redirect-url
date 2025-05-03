package com.bagsafe.redirecturlapi.infra.database.repository;

import com.bagsafe.redirecturlapi.core.model.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlModel, String> {

    Optional<UrlModel> findByShortUrl(String shortUrl);

    Optional<UrlModel> findByOriginalUrl(String originalUrl);
}
