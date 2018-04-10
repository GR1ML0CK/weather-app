package com.asuscomm.gr1ml0ck.weatherapp.services;

import com.asuscomm.gr1ml0ck.weatherapp.models.WindConditions;
import com.asuscomm.gr1ml0ck.weatherapp.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * Service responsible for retrieving weather data from an 
 * external weather API and deserializing it.
 */
@Service
public class WeatherService {
  private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

  private final JsonMapper mapper;
  
  private final OpenWeatherMapService weatherMapService;

  @Autowired
  public WeatherService(JsonMapper mapper, OpenWeatherMapService weatherMapService) {
    this.mapper = mapper;
    this.weatherMapService = weatherMapService;
  }

  /**
   * Gets a WindConditions object with data for wind direction and speed for a given zip code and
   * country retrieved from an external API.
   *
   * @param zipCode the zip/postal code to get the weather content for
   * @param country the country code to combine with the zip for the search. defaults to "us"
   * @param noCache true if the request should be made even if there is cache for the resource.
   * @return the WindConditions object. Only the "wind" property data from the data returned from
   * the API is returned.
   */
  @Nullable
  public WindConditions getWeatherByZip(String zipCode, String country, boolean noCache) {
    if (mapper.isValid()) {
      log.info("Request started for zipcode: {}", zipCode);
      String json = weatherMapService.getDataFromAPI(zipCode, country, noCache);
      log.info("Request finished for zipcode: {}", zipCode);

      return mapper.getWindConditionsFromWeatherJson(json);
    }

    return null;
  }
}
