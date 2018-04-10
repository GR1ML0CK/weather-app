package com.asuscomm.gr1ml0ck.weatherapp.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ZipCodeValidatorTest {

  /**
   * Valid US zip code
   */
  @Test
  public void testIsValidZipCodeValidUS() {
    String[] zipCodes = {"12345", "67890", "90210", "89109"};
    for (String zipCode : zipCodes) {
      assertTrue(ZipCodeValidator.isValidZipCode(zipCode, "us"));
    }
  }

  /**
   * Invalid US zip code
   */
  @Test
  public void testIsValidZipCodeInvalidUS() {
    String[] zipCodes = {"abcde", "1234", "", "123456"};
    for (String zipCode : zipCodes) {
      assertFalse(ZipCodeValidator.isValidZipCode(zipCode, "us"));
    }
  }

  /**
   * Valid Non-US zip code
   */
  @Test
  public void testIsValidZipCodeValidNonUS() {
    String[] zipCodes = {"123", "12345", "1234567890"};
    for (String zipCode : zipCodes) {
      assertTrue(ZipCodeValidator.isValidZipCode(zipCode, "de"));
    }
  }

  /**
   * Invalid Non-US zip code
   */
  @Test
  public void testIsValidZipCodeInvalidNonUS() {
    String[] zipCodes = {"abcde", "12 3-45ff6", "Castlevania X: Rondo of Blood", "¯\\_(:/)_/¯"};
    for (String zipCode : zipCodes) {
      assertFalse(ZipCodeValidator.isValidZipCode(zipCode, "de"));
    }
  }
}
