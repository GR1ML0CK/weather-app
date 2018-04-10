package com.asuscomm.gr1ml0ck.weatherapp.controllers;

import com.asuscomm.gr1ml0ck.weatherapp.models.simpleresponse.ResponseType;
import com.asuscomm.gr1ml0ck.weatherapp.models.simpleresponse.SimpleResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A simpler error handler. Currently does nothing more than replacing the OOTB /error response
 * with a generic "Invalid Request"
 */
@RestController
public class CustomErrorController implements ErrorController {

  private static final String PATH = "/error";

  @RequestMapping(value = PATH)
  public ResponseEntity<?> error() {
    SimpleResponse resBody = new SimpleResponse(ResponseType.error, "Invalid Request");
    return new ResponseEntity<>(resBody, HttpStatus.BAD_REQUEST);
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}