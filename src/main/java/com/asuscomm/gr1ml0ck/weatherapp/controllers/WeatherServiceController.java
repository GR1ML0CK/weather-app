package com.asuscomm.gr1ml0ck.weatherapp.controllers;

import com.asuscomm.gr1ml0ck.weatherapp.models.simpleresponse.SimpleResponse;
import com.asuscomm.gr1ml0ck.weatherapp.models.WindConditions;
import com.asuscomm.gr1ml0ck.weatherapp.services.WeatherService;
import com.asuscomm.gr1ml0ck.weatherapp.models.simpleresponse.ResponseType;
import com.asuscomm.gr1ml0ck.weatherapp.utils.ZipCodeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controls requests for current wind conditions for an input location
 */
@RestController
@RequestMapping(value = "/api/v1/wind", method = RequestMethod.GET)
public class WeatherServiceController {

  private static final Logger log = LoggerFactory.getLogger(WeatherServiceController.class);

  /**
   * Error msg to return in the response object when the request fails to get data from the API.
   */
  private static final String ERROR_FAILED_REQ = "Failed to retrieve weather data for zip code";

  /**
   * Error msg to return in the response object when the input zip code is an invalid structure.
   */
  private static final String ERROR_INVALID_ZIP = "Invalid zip code structure";

  /**
   * Default country code
   */
  private static final String US = "us";

  private final WeatherService weatherService;

  @Autowired
  public WeatherServiceController(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  /**
   * Request Mapping for getting the weather data for a location based on it's zip code. Optionally
   * the country code can be added as well.
   *
   * @param zipCode the zip/postal code of the local to get the weather for
   * @param country the country code for the zip code [default="us"]
   * @return the ResponseEntity for the weather data or an error object
   */
  @RequestMapping(
      value = {
          "/{zipCode}",
          "/{zipCode}/{country}"
      },
      method = RequestMethod.GET
  )
  public ResponseEntity<?> weatherByZip(
      @PathVariable String zipCode,
      @PathVariable(required = false) @Nullable String country,
      @RequestParam(required = false) boolean noCache) {

    // Defaults the country to "us" since it is optional
    String countryCode = country != null ? country : US;

    log.info("Fetching weather for zip code data " + zipCode + " and country code " + countryCode);

    // checks if the zip is a valid structure. If not an error is response is returned
    if (!ZipCodeValidator.isValidZipCode(zipCode, countryCode)) {
      return this.getErrorResponse(zipCode, ERROR_INVALID_ZIP);
    }

    WindConditions windConditions = weatherService.getWeatherByZip(zipCode, countryCode, noCache);

    // Return error if request for windConditions data was not successful
    if (windConditions == null) {
      return this.getErrorResponse(zipCode, ERROR_FAILED_REQ);
    }

    return new ResponseEntity<>(windConditions, HttpStatus.OK);
  }

  /**
   * Gets the ResponseEntity for a failed request to get weather data.
   *
   * @param zipCode the zip code of the fail request
   * @param msg the error message to display to the user
   * @return the ResponseEntity object with the enclosed error response data
   */
  private ResponseEntity<SimpleResponse> getErrorResponse(String zipCode, String msg) {
    String errMessage = msg + ": " + zipCode;
    log.error(errMessage);
    SimpleResponse resBody = new SimpleResponse(ResponseType.error, errMessage);

    return new ResponseEntity<>(resBody, HttpStatus.NOT_FOUND);
  }
}
