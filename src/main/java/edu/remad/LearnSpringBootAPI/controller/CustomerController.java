package edu.remad.LearnSpringBootAPI.controller;

import edu.remad.LearnSpringBootAPI.entity.Customer;
import edu.remad.LearnSpringBootAPI.repository.CustomerRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
/**
 * Controls REST endpoints concerning {@link Customer}. Endpoints are reachable via http://localhost:8080/customer
 */
public class CustomerController {

  /**
   * customer repository loads and save {@link Customer}
   */
  private final CustomerRepository customerRepository;

  /**
   * Constructor
   *
   * @param customerRepository repository to load and save {@link Customer}
   */
  public CustomerController(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  /**
   * Method to get all customers
   *
   * @return all instances of {@link Customer}
   */
  @GetMapping("")
  public List<Customer> index() {
    return customerRepository.findAll();
  }

  @PostMapping("/customers")
  public Customer createCustomer(@RequestBody Customer customer) {
    try {
      return customerRepository.save(
          new Customer(customer.getFirstName(), customer.getLastName()));
    } catch (Exception e) {
      return null;
    }
  }
}
