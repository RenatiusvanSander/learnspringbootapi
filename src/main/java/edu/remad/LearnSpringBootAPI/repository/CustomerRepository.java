package edu.remad.LearnSpringBootAPI.repository;

import edu.remad.LearnSpringBootAPI.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for instances of {@link Customer}. Does concern things for data-base operation on data set for {@link Customer}
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
