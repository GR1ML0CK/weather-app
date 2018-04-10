package com.asuscomm.gr1ml0ck.weatherapp.models.simpleresponse;

/**
 * A model for a simple response object to use to return in a ResponseEntity
 */
public class SimpleResponse {

  /**
   * The type of response (ie "success", "error")
   */
  private String type;

  /**
   * The message to return with the response.
   */
  private String message;

  public SimpleResponse(ResponseType type, String msg) {
    this.type = type.toString();
    this.message = msg;
  }

  public String getMessage() {
    return message;
  }

  public String getType() {
    return type;
  }
}
