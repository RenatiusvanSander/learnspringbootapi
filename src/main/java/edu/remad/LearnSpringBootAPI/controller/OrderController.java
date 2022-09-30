package edu.remad.LearnSpringBootAPI.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import edu.remad.LearnSpringBootAPI.OrderModelAssembler;
import edu.remad.LearnSpringBootAPI.OrderNotFoundException;
import edu.remad.LearnSpringBootAPI.entity.Order;
import edu.remad.LearnSpringBootAPI.entity.Status;
import edu.remad.LearnSpringBootAPI.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller concerning orders of customers
 */
@RestController
public class OrderController {

  /**
   * the repository of customer orders
   */
  private final OrderRepository orderRepository;

  /**
   * the assembler to create {@link EntityModel}
   */
  private final OrderModelAssembler assembler;

  /**
   * Constructor
   *
   * @param orderRepository the order repository to load orders from data base
   * @param assembler       assembles entity models of customer orders
   */
  OrderController(OrderRepository orderRepository, OrderModelAssembler assembler) {

    this.orderRepository = orderRepository;
    this.assembler = assembler;
  }

  /**
   * All customer orders
   *
   * @return all customer orders. Otherwise empty list
   */
  @GetMapping("/orders")
  public CollectionModel<EntityModel<Order>> all() {

    List<EntityModel<Order>> orders = orderRepository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(orders, //
        linkTo(methodOn(OrderController.class).all()).withSelfRel());
  }

  /**
   * Creates order
   *
   * @param order the order data object
   * @return a response entity, which wraps enity model of {@link Order}
   */
  @PostMapping("/orders")
  ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order)
      throws OrderNotFoundException {
    order.setStatus(Status.IN_PROGRESS);
    Order newOrder = orderRepository.save(order);

    return ResponseEntity //
        .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri()) //
        .body(assembler.toModel(newOrder));
  }

  /**
   * Gets one order.
   *
   * @param id the order id
   * @return the order
   */
  @GetMapping("/orders/{id}")
  public ResponseEntity<EntityModel<Order>> one(@PathVariable Long id)
      throws OrderNotFoundException {
    Order order = orderRepository.findById(id) //
        .orElseThrow(() -> new OrderNotFoundException(id));
    EntityModel<Order> entityModel = assembler.toModel(order);

    return ResponseEntity //
        .created(linkTo(
            methodOn(OrderController.class).one(entityModel.getContent().getId())).toUri()) //
        .body(entityModel);
  }

  /**
   * Cancels the order
   *
   * @param id the numeric order identifier
   * @return response entity of type {@link ResponseEntity}
   * @throws OrderNotFoundException
   */
  @DeleteMapping("/orders/{id}/cancel")
  public ResponseEntity<?> cancel(@PathVariable Long id) throws OrderNotFoundException {
    Order order = orderRepository.findById(id) //
        .orElseThrow(() -> new OrderNotFoundException(id));

    if (order.getStatus() == Status.IN_PROGRESS) {
      order.setStatus(Status.CANCELLED);
      return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
    }

    return ResponseEntity //
        .status(HttpStatus.METHOD_NOT_ALLOWED) //
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
        .body(Problem.create() //
            .withTitle("Method not allowed") //
            .withDetail(
                "You can't cancel an order that is in the " + order.getStatus() + " status"));
  }

  /**
   * Completes an order.
   *
   * @param id the numeric order identifier
   * @return response entity of type {@link ResponseEntity}
   * @throws OrderNotFoundException
   */
  @PutMapping("/orders/{id}/complete")
  public ResponseEntity<?> complete(@PathVariable Long id) throws OrderNotFoundException {
    Order order = orderRepository.findById(id) //
        .orElseThrow(() -> new OrderNotFoundException(id));

    if (order.getStatus() == Status.IN_PROGRESS) {
      order.setStatus(Status.COMPLETED);
      return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
    }

    return ResponseEntity //
        .status(HttpStatus.METHOD_NOT_ALLOWED) //
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
        .body(Problem.create() //
            .withTitle("Method not allowed") //
            .withDetail(
                "You can't complete an order that is in the " + order.getStatus() + " status"));
  }
}