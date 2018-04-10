package com.asuscomm.gr1ml0ck.weatherapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.asuscomm.gr1ml0ck.weatherapp")
@EnableAutoConfiguration
@SpringBootApplication
public class WeatherApp {
  private static final Logger log = LoggerFactory.getLogger(WeatherApp.class);

  public static void main(String[] args) {
    log.info("Weather App started");
    SpringApplication.run(WeatherApp.class, args);
  }
}
