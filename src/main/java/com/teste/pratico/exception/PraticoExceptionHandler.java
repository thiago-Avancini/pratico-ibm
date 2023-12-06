package com.teste.pratico.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PraticoExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", Instant.now());
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}
