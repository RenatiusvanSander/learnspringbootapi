package edu.remad.LearnSpringBootAPI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import edu.remad.LearnSpringBootAPI.controller.OrderController;
import edu.remad.LearnSpringBootAPI.entity.Order;
import edu.remad.LearnSpringBootAPI.entity.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public
class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

  @Override
  public EntityModel<Order> toModel(Order order) {

    // Unconditional links to single-item resource and aggregate root

    EntityModel<Order> orderModel = null;
    try {
      orderModel = EntityModel.of(order,
          linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
          linkTo(methodOn(OrderController.class).all()).withRel("orders"));
    } catch (OrderNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Conditional links based on state of the order

    if (order.getStatus() == Status.IN_PROGRESS) {
      try {
        orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
      } catch (OrderNotFoundException e) {
        throw new RuntimeException(e);
      }
      try {
        orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
      } catch (OrderNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

    return orderModel;
  }
}