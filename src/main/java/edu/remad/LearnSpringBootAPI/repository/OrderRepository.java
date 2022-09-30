package edu.remad.LearnSpringBootAPI.repository;


import edu.remad.LearnSpringBootAPI.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A JPA repository for ordering customers
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}