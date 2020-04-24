package com.linkly.analytics.Repository;

import com.linkly.analytics.Collection.UrlClicks;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnalyticsRepository extends MongoRepository<UrlClicks, String> {
    List<UrlClicks> findByShortURL(String ShortURL);
}
