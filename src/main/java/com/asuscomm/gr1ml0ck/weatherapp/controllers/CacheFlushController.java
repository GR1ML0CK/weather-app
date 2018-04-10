package com.asuscomm.gr1ml0ck.weatherapp.controllers;

import com.asuscomm.gr1ml0ck.weatherapp.models.simpleresponse.SimpleResponse;
import com.asuscomm.gr1ml0ck.weatherapp.models.simpleresponse.ResponseType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller responsible for flushing the cache. Depending on the usage of this application
 *  this might not be something you would want to expose, but did this just for an example
 */
@RestController
@RequestMapping(value = "/api/v1/", method = RequestMethod.GET)
public class CacheFlushController {

  @Value("${cache.flush-key}")
  private String secret;

  @CacheEvict(value = "weather", allEntries = true)
  @RequestMapping(value = "/cacheflush", method = RequestMethod.GET)
  public ResponseEntity<?> clear(@RequestParam String secret) {
    SimpleResponse resBody;
    if (secret.equals(this.secret)) {
      resBody = new SimpleResponse(ResponseType.success, "Cache Cleared");
    } else {
      resBody = new SimpleResponse(ResponseType.error, "Invalid Key");
    }

    return new ResponseEntity<>(resBody, HttpStatus.OK);
  }
}
