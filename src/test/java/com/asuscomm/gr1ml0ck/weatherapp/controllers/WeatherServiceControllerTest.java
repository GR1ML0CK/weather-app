package com.asuscomm.gr1ml0ck.weatherapp.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.asuscomm.gr1ml0ck.weatherapp.models.WindConditions;
import com.asuscomm.gr1ml0ck.weatherapp.services.WeatherService;
import com.asuscomm.gr1ml0ck.weatherapp.utils.ZipCodeValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

@SpringBootTest
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    WeatherService.class,
    WindConditions.class,
    ZipCodeValidator.class
})
public class WeatherServiceControllerTest {

  @Mock
  private WindConditions windConditions;

  @Mock
  private WeatherService service;

  private WeatherServiceController controller;

  @Before
  public void setup() {
    mockStatic(ZipCodeValidator.class);

    when(windConditions.getSpeed()).thenReturn(8.2);
    when(windConditions.getDeg()).thenReturn(340.0);

    controller = new WeatherServiceController(service);
  }

  /**
   * Tests an successful request the get Wind Conditions by zip code.
   */
  @Test
  public void testWeatherByZipSuccess() {
    mockServiceRequest(windConditions);
    mockZipCodeValidator(true);

    ResponseEntity<?> res = controller.weatherByZip("12345", "us", true);
    assertNotNull(res);
    assertEquals(res.getStatusCodeValue(), 200);

    WindConditions wind = (WindConditions) res.getBody();
    assertNotNull(wind);

    assertEquals(340.0, wind.getDeg(), 0.0);
  }

  /**
   * Tests the result of an invalid zip code in the request
   */
  @Test
  public void testWeatherByZipInvalidZipcode() {
    mockZipCodeValidator(false);
    mockServiceRequest(windConditions);

    ResponseEntity<?> res = controller.weatherByZip("abcde", "us", true);
    assertNotNull(res);
    assertEquals(404, res.getStatusCodeValue());
  }

  /**
   * Test the result of a failed API request
   */
  @Test
  public void testWeatherByZipFailedReq() {
    mockZipCodeValidator(true);
    mockServiceRequest(null);

    ResponseEntity<?> res = controller.weatherByZip("abcde", "us", true);
    assertNotNull(res);
    assertEquals(404, res.getStatusCodeValue());
  }

  /**
   * Sets the result of a mocked validation of a zip code
   * @param mockResult true if zips should be considered valid
   */
  private void mockZipCodeValidator(boolean mockResult) {
    when(ZipCodeValidator.isValidZipCode(anyString(), anyString())).thenReturn(mockResult);
  }

  /**
   * Sets up the OpenWeatherMapService to return a mock WeatherConditions object or null possibly
   * @param mockResult the data for the service to return
   */
  private void mockServiceRequest(@Nullable WindConditions mockResult) {
    when(service.getWeatherByZip(anyString(), anyString(), anyBoolean())).thenReturn(mockResult);
  }
}
