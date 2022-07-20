package edu.remad.LearnSpringBootAPI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import edu.remad.LearnSpringBootAPI.controller.CustomerController;
import edu.remad.LearnSpringBootAPI.entity.Customer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

  @Override
  public EntityModel<Customer> toModel(Customer customer) {
    return EntityModel.of(customer, //
        linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel(),
        linkTo(methodOn(CustomerController.class).index()).withRel("customers"));
  }

  @Override
  public CollectionModel<EntityModel<Customer>> toCollectionModel(
      Iterable<? extends Customer> customers) {
    return RepresentationModelAssembler.super.toCollectionModel(customers);
  }
}
