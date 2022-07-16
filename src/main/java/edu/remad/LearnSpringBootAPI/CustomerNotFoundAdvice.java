package edu.remad.LearnSpringBootAPI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Render error message for customer not found
 */
@ControllerAdvice
public class CustomerNotFoundAdvice {

  /**
   * Method to create not found html page.
   *
   * @param ex thrown {@link CustomerNotFoundException}
   * @return string encoded html error page
   */
  @ResponseBody
  @ExceptionHandler(CustomerNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String customerNotFoundHandler(CustomerNotFoundException ex) {
    return ex.getMessage();
  }
}
