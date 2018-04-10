package com.asuscomm.gr1ml0ck.weatherapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validator Class for zip codes that will be passed to the weather API
 * Unfortunately the zip code api is limited to numerical values only so there's not much
 * need to validate more than that.
 *
 * https://openweathermap.desk.com/customer/portal/questions/17229700-zip-code-formats-especially-for-canada-
 */
public class ZipCodeValidator {
  private static final Logger log = LoggerFactory.getLogger(ZipCodeValidator.class);

  private static final String USA_REGEX = "^[0-9]{5}$";

  /**
   * This is not really a accurate pattern for non us postal codes, but OpenWeatherMap
   * only supports numerical values.
   */
  private static final String NON_USA_REGEX = "^[0-9]+$";

  private static final Pattern usaPattern = Pattern.compile(USA_REGEX);

  private static final Pattern nonUsaPattern = Pattern.compile(NON_USA_REGEX);

  /**
   * Validates whether a zipCode value is of an acceptable structure as determined by the
   *  OpenWeatherMap API.
   * @param zipCode the zip/postal code value to validate
   * @param country the country code. If "us" we can be a little more specific with validation.
   * @return true if the zip code is of an acceptable structure to be sent to the external API
   */
  public static boolean isValidZipCode(String zipCode, String country) {
    log.info("Starting zip/postal code validation: {" + zipCode +", " + country +"}");
    Pattern pattern = country.equals("us") ? usaPattern : nonUsaPattern;
    Matcher matcher = pattern.matcher(zipCode);
    boolean isValid = matcher.matches();
    if (!isValid) {
      log.error("Input zip/postal is not valid: {" + zipCode +", " + country +"}");
    }
    return matcher.matches();
  }
}
