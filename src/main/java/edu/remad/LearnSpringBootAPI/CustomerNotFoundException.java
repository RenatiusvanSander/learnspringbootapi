package edu.remad.LearnSpringBootAPI;

/**
 * error message for not found customer
 */
public class CustomerNotFoundException extends RuntimeException{

  /**
   * Constructor
   *
   * @param id primary key of not found customer
   */
  public CustomerNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}
