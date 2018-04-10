package com.asuscomm.gr1ml0ck.weatherapp.models.simpleresponse;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ResponseTypeTest {

  @Test
  public void testToString() {
    assertEquals("success", ResponseType.success.toString());
  }
}