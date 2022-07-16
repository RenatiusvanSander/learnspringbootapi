package edu.remad.LearnSpringBootAPI.controller;

import edu.remad.LearnSpringBootAPI.CustomerNotFoundException;
import edu.remad.LearnSpringBootAPI.entity.Customer;
import edu.remad.LearnSpringBootAPI.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * Controls REST endpoints concerning {@link Customer}. Endpoints are reachable via http://localhost:8080/customer
 */
public class CustomerController {

  /**
   * endpoints mapping base
   */
  private static final String ENDPOINTS_MAPPING_BASE = "/customers";

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
  @GetMapping(ENDPOINTS_MAPPING_BASE + "/all")
  public List<Customer> index() {
    return customerRepository.findAll();
  }

  /**
   * Creates {@link Customer}
   *
   * @param customer customer to save
   * @return saved {qlink Customer}
   */
  @PostMapping("ENDPOINTS_MAPPING_BASE")
  public Customer createCustomer(@RequestBody Customer customer) {
    try {
      return customerRepository.save(customer);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Reads a {@link Customer}.
   *
   * @param id long-encoded identifier, as snynony primary kley of customer to load.
   * @return loaded {@link Customer}
   */
  @GetMapping(ENDPOINTS_MAPPING_BASE + "/{id}")
  public Customer getCustomer(@PathVariable Long id) {
    return this.customerRepository.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException(id));
  }

  /**
   * Updates or saves a {@link Customer}
   *
   * @param newCustomer new customer to update found
   * @param id          long-encodedidentifier, as synonym primary key of customer to update
   * @return updated or saved {@link Customer}
   */
  @PutMapping(ENDPOINTS_MAPPING_BASE + "/{id}")
  public Customer updateCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {
    return this.customerRepository.findById(id).map(customer -> {
      customer.setFirstName(newCustomer.getFirstName());
      customer.setLastName(newCustomer.getLastName());

      return this.customerRepository.save(customer);
    }).orElseGet(() -> {
      newCustomer.setId(id);

      return this.customerRepository.save(newCustomer);
    });
  }

  /**
   * Deletes ionstance of {@link Customer}
   *
   * @param id long-value encoded identifier to delete matching {@link Customer}
   */
  @DeleteMapping(ENDPOINTS_MAPPING_BASE + "/{id}")
  public void deleteCustomer(@PathVariable Long id) {
    this.customerRepository.deleteById(id);
  }
}
