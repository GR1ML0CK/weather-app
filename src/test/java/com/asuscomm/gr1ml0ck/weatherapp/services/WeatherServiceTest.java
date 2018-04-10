package com.asuscomm.gr1ml0ck.weatherapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;

import com.asuscomm.gr1ml0ck.weatherapp.models.WindConditions;
import com.asuscomm.gr1ml0ck.weatherapp.utils.JsonMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import utils.TestUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OpenWeatherMapService.class)
public class WeatherServiceTest {

  private static final String MOCK_ZIP = "90210";

  private static final String MOCK_COUNTRY = "us";

  @Mock
  private OpenWeatherMapService weatherMapService;

  private WeatherService service;


  @Before
  public void setup() {
    service = new WeatherService(new JsonMapper(), weatherMapService);
  }

  /**
   * Tests the return of a request that successfully retrieves data.
   */
  @Test
  public void testGetWeatherByZipSuccess() {
    mockGetDataFromAPI(TestUtils.getMockJsonString("mock-weather-data.json"));
    WindConditions wind = service.getWeatherByZip(MOCK_ZIP, MOCK_COUNTRY, false);

    assertEquals(8.2, wind.getSpeed(), 0.0);
    assertEquals(340.0, wind.getDeg(), 0.0);
  }

  /**
   * Test the result of a request that returns no data
   */
  @Test
  public void testGetWeatherByZipFail() {
    mockGetDataFromAPI(null);
    assertNull(service.getWeatherByZip(MOCK_ZIP, MOCK_COUNTRY, false));
  }

  /**
   * Sets the mock response for a request made by OpenWeatherMapService.
   *
   * @param response the content the mock request to return when called
   */


  private void mockGetDataFromAPI(String response) {
    when(weatherMapService.getDataFromAPI(anyString(), anyString(), anyBoolean()))
        .thenReturn(response);
  }
}
