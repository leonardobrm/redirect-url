CREATE TABLE url (
                     id VARCHAR(36) NOT NULL,
                     original_url VARCHAR(2048) NOT NULL,
                     short_url VARCHAR(255) NOT NULL,
                     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                     disable_at TIMESTAMP,
                     access_count INTEGER DEFAULT 0,
                     is_active BOOLEAN DEFAULT true,
                     PRIMARY KEY (id),
                     CONSTRAINT uk_original_url UNIQUE (original_url),
                     CONSTRAINT uk_short_url UNIQUE (short_url)
);

CREATE INDEX idx_url_active_created ON url (is_active, created_at);

CREATE INDEX idx_url_disable_at ON url (disable_at);

CREATE INDEX idx_url_access_count ON url (access_count DESC);

CREATE UNIQUE INDEX idx_url_short_url ON url(short_url);