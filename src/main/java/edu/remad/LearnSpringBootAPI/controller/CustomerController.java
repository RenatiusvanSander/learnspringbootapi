package edu.remad.LearnSpringBootAPI.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import edu.remad.LearnSpringBootAPI.CustomerModelAssembler;
import edu.remad.LearnSpringBootAPI.CustomerNotFoundException;
import edu.remad.LearnSpringBootAPI.entity.Customer;
import edu.remad.LearnSpringBootAPI.repository.CustomerRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
   * assembler to create customer models
   */
  private final CustomerModelAssembler assembler;

  /**
   * Constructor
   *
   * @param customerRepository repository to load and save {@link Customer}
   * @param assembler          assembler for {@link CustomerModelAssembler}
   */
  public CustomerController(CustomerRepository customerRepository,
      CustomerModelAssembler assembler) {
    this.customerRepository = customerRepository;
    this.assembler = assembler;
  }

  /**
   * Method to get all customers
   *
   * @return all instances of {@link Customer}
   */
  @GetMapping(ENDPOINTS_MAPPING_BASE)
  public CollectionModel<EntityModel<Customer>> index() {
    List<EntityModel<Customer>> customers = this.customerRepository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(customers,
        linkTo(methodOn(CustomerController.class).index()).withSelfRel());
  }

  /**
   * Creates {@link Customer}
   *
   * @param customer customer to save
   * @return saved {@link Customer}
   */
  @PostMapping("ENDPOINTS_MAPPING_BASE")
  public EntityModel<Customer> createCustomer(@RequestBody Customer customer) {
    try {
      Customer savedCustomer = this.customerRepository.save(customer);

      return assembler.toModel(savedCustomer);
    } catch (Exception e) {
      throw new CustomerNotFoundException(0L);
    }
  }

  /**
   * Reads a {@link Customer}.
   *
   * @param id long-encoded identifier, as snynony primary kley of customer to load.
   * @return loaded {@link Customer}
   */
  @GetMapping(ENDPOINTS_MAPPING_BASE + "/{id}")
  public EntityModel<Customer> getCustomer(@PathVariable Long id) {
    Customer customer = this.customerRepository.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException(id));

    return assembler.toModel(customer);
  }

  /**
   * Updates or saves a {@link Customer}
   *
   * @param newCustomer new customer to update found
   * @param id          long-encodedidentifier, as synonym primary key of customer to update
   * @return updated or saved {@link Customer}
   */
  @PutMapping(ENDPOINTS_MAPPING_BASE + "/{id}")
  public EntityModel<Customer> updateCustomer(@RequestBody Customer newCustomer,
      @PathVariable Long id) {
    return this.customerRepository.findById(id).map(customer -> {
      customer.setFirstName(newCustomer.getFirstName());
      customer.setLastName(newCustomer.getLastName());
      Customer updatedCustomer = this.customerRepository.save(customer);

      return assembler.toModel(updatedCustomer);
    }).orElseGet(() -> {
      newCustomer.setId(id);
      Customer savedCustomer = this.customerRepository.save(newCustomer);

      return assembler.toModel(savedCustomer);
    });
  }

  /**
   * Deletes instance of {@link Customer}
   *
   * @param id long-value encoded identifier to delete matching {@link Customer}
   */
  @DeleteMapping(ENDPOINTS_MAPPING_BASE + "/{id}")
  public void deleteCustomer(@PathVariable Long id) {
    this.customerRepository.deleteById(id);
  }
}
