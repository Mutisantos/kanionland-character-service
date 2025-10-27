package com.kanionland.charsheet.exp.infrastructure.exceptions;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

class GlobalControllerAdviceTest {

  private GlobalControllerAdvice exceptionHandler;

  @BeforeEach
  void setUp() {
    exceptionHandler = new GlobalControllerAdvice();
  }

  @Test
  void whenHandleMethodArgumentNotValid_thenReturnsBadRequest() {
    // Given
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("object", "field", "defaultMessage");
    when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    when(ex.getBindingResult()).thenReturn(bindingResult);
    // When
    Map<String, String> response = exceptionHandler.handleValidationExceptions(ex);
    // Then
    assertThat(response).hasSize(1);
    assertThat(response).containsKey("field");
    assertThat(response.get("field")).isEqualTo("defaultMessage");
  }

  @Test
  void whenHandleHttpClientErrorException_thenReturnsBadRequest() {
    // Given
    FieldError fieldError = new FieldError("object", "field", "defaultMessage");
    HttpClientErrorException ex = mock(HttpClientErrorException.class);
    when(ex.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
    // When
    final ResponseEntity<String> stringResponseEntity =
        exceptionHandler.handleHttpClientErrorException(ex);
    // Then
    assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(stringResponseEntity.getBody()).contains("Error calling external service: ");
  }

  @Test
  void whenHandleGenericException_thenReturnsBadRequest() {
    // Given
    FieldError fieldError = new FieldError("object", "field", "defaultMessage");
    Exception ex = mock(Exception.class);
    // When
    final ResponseEntity<String> stringResponseEntity =
        exceptionHandler.handleGenericException(ex);
    // Then
    assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(stringResponseEntity.getBody()).contains("An unexpected error occurred: ");
  }

  @Test
  void whenHandleResponseStatusException_thenReturnsCorrectStatus() {
    // Given
    ResponseStatusException ex = new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "Error message"
    );
    // When
    ResponseEntity<Map<String, String>> response =
        exceptionHandler.handleResponseStatusException(ex);
    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).containsKey("message");
    assertThat(response.getBody().get("message")).isEqualTo("Error message");
  }
}