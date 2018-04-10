package com.asuscomm.gr1ml0ck.weatherapp.models.simpleresponse;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ResponseType.class)
public class SimpleResponseTest {

  private SimpleResponse simpleResponse;

  @Before
  public void setup() {
    ResponseType responseType = mock(ResponseType.class);
    when(responseType.toString()).thenReturn("success");
    simpleResponse = new SimpleResponse(responseType, "success msg");
  }

  @Test
  public void testGetType() {
    assertEquals("success", simpleResponse.getType());
  }

  @Test
  public void testGetMessage() {
    assertEquals("success msg", simpleResponse.getMessage());
  }
}
