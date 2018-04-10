package com.asuscomm.gr1ml0ck.weatherapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PrepareForTest({OpenWeatherMapService.class})
public class OpenWeatherMapServiceTest {

  /**
   * Creates a test Cache config for testing the functionality of the cache settings
   *  of OpenWeatherMapService
   */
  @Configuration
  @EnableCaching
  public static class TestCacheConfig {

    @Bean
    public CacheManager cacheManager(){
      SimpleCacheManager cacheManager = new SimpleCacheManager();
      List<Cache> caches = new ArrayList<>();
      caches.add(cacheBean().getObject());
      cacheManager.setCaches(caches );
      return cacheManager;
    }

    @Bean
    public ConcurrentMapCacheFactoryBean cacheBean(){
      ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
      cacheFactoryBean.setName("weather");
      return cacheFactoryBean;
    }

    @Bean
    OpenWeatherMapService weatherMapService() {
      return mock(OpenWeatherMapService.class);
    }
  }

  @Autowired
  OpenWeatherMapService weatherMapService;

  /**
   * Tests the cache functionality of getDataFromAPI
   */
  @Test
  public void testCache() {
    String response1 = "1st Response";
    String response2 = "2nd Response";

    when(weatherMapService.getDataFromAPI(anyString(), anyString(), anyBoolean()))
        .thenReturn(response1, response2);

    String initial = weatherMapService.getDataFromAPI("12345", "us", false);
    String cached = weatherMapService.getDataFromAPI("12345", "us", false);
    String unCached = weatherMapService.getDataFromAPI("12345", "us", true);

    assertEquals(initial, cached);
    assertNotEquals(initial, unCached);
  }

}
