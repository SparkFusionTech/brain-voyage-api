package com.sparkfusion.quiz.brainvoyage.api.jwt;

public class JwtUnexpectedException extends RuntimeException {

  public JwtUnexpectedException(String message) {
    super(message);
  }
}
