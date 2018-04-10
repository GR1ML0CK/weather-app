package com.asuscomm.gr1ml0ck.weatherapp.configs;

import java.net.URISyntaxException;
import javax.cache.Caching;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config Class for enabling cache and setting up the cache manager
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

  private static final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

  /**
   * Value that can be set to true this disable caching for App
   */
  @Value("${cache.disabled}")
  private boolean cacheDisabled;

  @Bean
  public CacheManager cacheManager() throws URISyntaxException {
    log.info("Is cache disabled? {}", cacheDisabled);
    if (cacheDisabled) {
      return new NoOpCacheManager();
    }
    return new JCacheCacheManager(
        Caching.getCachingProvider().getCacheManager(
            getClass().getResource("/ehcache.xml").toURI(),
            CacheConfiguration.class.getClassLoader()
        )
    );
  }
}
