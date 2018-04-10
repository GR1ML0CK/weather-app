package com.asuscomm.gr1ml0ck.weatherapp.models.simpleresponse;

public enum ResponseType {
  success("success"),
  error("error");

  private final String text;

  ResponseType(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
