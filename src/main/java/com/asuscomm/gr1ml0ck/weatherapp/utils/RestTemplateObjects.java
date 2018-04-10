package com.asuscomm.gr1ml0ck.weatherapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Simple wrapper for the Spring RestTemplate. Separating this out froms service implementations
 * provides better separation of concerns and easier mocking when testing services.
 */
@Component
public class RestTemplateObjects {

  private final RestTemplate restTemplate;

  @Autowired
  public RestTemplateObjects () {
    this.restTemplate = new RestTemplate();
  }

  public RestTemplate getRestTemplate() {
    return restTemplate;
  }
}