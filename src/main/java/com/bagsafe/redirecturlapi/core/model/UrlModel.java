package com.bagsafe.redirecturlapi.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Setter
@Table(name = "url")
public class UrlModel {

   @Id
   private String id;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "disable_at")
    private LocalDateTime disableAt;

    @Column(name = "access_count")
    private int accessCount;

    @Column(name = "is_active")
    private boolean isActive;
}
