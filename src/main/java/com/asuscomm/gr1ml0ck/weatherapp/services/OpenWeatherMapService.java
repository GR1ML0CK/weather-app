package com.asuscomm.gr1ml0ck.weatherapp.services;

import com.asuscomm.gr1ml0ck.weatherapp.utils.RestTemplateObjects;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service for communicating directly with the OpenWeatherMap API (https://openweathermap.org)
 */
@Service
@CacheConfig(cacheNames = "weather")
public class OpenWeatherMapService {

  private static final Logger log = LoggerFactory.getLogger(OpenWeatherMapService.class);

  /**
   * The required api key. Don't have one? get one here -> https://openweathermap.org/price
   */
  @Value("${api.key}")
  private String apiKey;

  /**
   * The url for the site the weather service will be pulling data from
   */
  private static final String DATA_SOURCE_URL =
      "https://api.openweathermap.org/data/2.5/weather?zip={zip}&appid={appid}";

  /**
   * The url parameter key for a zipcode value for the openweathermap API
   */
  private static final String ZIP_PARAM = "zip";

  /**
   * The url parameter key for the api key value for the openweathermap API
   */
  private static final String API_PARAM = "appid";

  private final RestTemplateObjects restTemplateObjects;

  @Autowired
  public OpenWeatherMapService(RestTemplateObjects restTemplateObjects) {
    this.restTemplateObjects = restTemplateObjects;
  }

  /**
   * Gets a rest template that will preform the GET Request for the weather data
   */
  @Bean
  public RestTemplate rest() {
    return this.restTemplateObjects.getRestTemplate();
  }

  /**
   * Gets the json data of weather info for a local from the external weather API.
   *
   * @param zipCode the zip/postal code to get the weather content for
   * @param country the country code to combine with the zip for the search. defaults to "us"
   * @return the json string of all the weather data for the specific location.
   */

  @Cacheable(
      cacheNames = "weather",
      // Disables cache if ?noCache=true is added to the url
      condition = "!#noCache",
      key = "#zipCode + #country",
      unless = "#result == null"
  )
  @Nullable
  public synchronized String getDataFromAPI(String zipCode, String country, boolean noCache) {
    if (apiKey == null) {
      log.error("API key is missing from configuration. Either add to application.properties "
          + "or execute the application withe --api.key param");
      return null;
    }

    Map<String, String> params = getParams(zipCode, country);

    String json = this.rest().getForObject(DATA_SOURCE_URL, String.class, params);

    if (json.length() == 0) {
      log.error("Failed to retrieve json data from external api: {}", params);
      return null;
    }

    return json;
  }

  /**
   * Gets a mapping of the params that will be applied to the external api path
   * @param zipCode the localation zip code
   * @param country the location country code
   * @return the mapping of params including apikey, zipcode, and country code
   */
  private Map<String, String> getParams(String zipCode, String country) {
    Map<String, String> params = new HashMap<>(2);
    params.put(API_PARAM, apiKey);
    params.put(ZIP_PARAM, zipCode + "," + country);
    return params;
  }
}
