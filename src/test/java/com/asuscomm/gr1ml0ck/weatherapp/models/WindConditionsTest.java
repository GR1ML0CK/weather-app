package com.asuscomm.gr1ml0ck.weatherapp.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(WindConditions.class)
public class WindConditionsTest {

  @Mock
  private WindConditions windConditions;

  @Test
  public void testGetSpeed() {
    assertEquals(0.0, windConditions.getSpeed(), 0.0);
  }

  @Test
  public void testGetDeg() {
    assertEquals(0.0, windConditions.getDeg(), 0.0);
  }

}
