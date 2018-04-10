package com.asuscomm.gr1ml0ck.weatherapp.models;

import java.io.Serializable;
import org.springframework.stereotype.Component;

/**
 * This interface defined the wind conditions for the current weather of a specific
 * location base on zip/postal code and country code (e.g 90210, us)
 *
 * This interface for mapping a JSON string from the weather APi to an object structure
 */
@Component
public interface WindConditions extends Serializable {

  /**
   * The Wind speed in meter/sec
   */
  double getSpeed();

  /**
   * The Wind direction in deg° (meteorological)
   */
  double getDeg();
}
