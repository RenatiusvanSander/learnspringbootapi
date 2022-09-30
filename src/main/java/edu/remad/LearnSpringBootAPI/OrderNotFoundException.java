package edu.remad.LearnSpringBootAPI;

public class OrderNotFoundException extends Exception{

  /**
   * Constructor
   *
   * @param id primary key of not found of order
   */
  public OrderNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}
