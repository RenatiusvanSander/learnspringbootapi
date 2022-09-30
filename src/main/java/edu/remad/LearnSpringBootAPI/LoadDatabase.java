package edu.remad.LearnSpringBootAPI;

import edu.remad.LearnSpringBootAPI.entity.Order;
import edu.remad.LearnSpringBootAPI.entity.Status;
import edu.remad.LearnSpringBootAPI.repository.CustomerRepository;
import edu.remad.LearnSpringBootAPI.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Loads database and populate the order repository.
 */
@Configuration
class LoadDatabase {

  /**
   * The logger for writing to log files.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabase.class);

  /**
   * Initializes data base
   *
   * @param customerRepository the customer repository to access persistence layer
   * @param orderRepository    the order repository to access persistence layer
   * @return the command line runner
   */
  @Bean
  CommandLineRunner initDatabase(CustomerRepository customerRepository,
      OrderRepository orderRepository) {

    return args -> {
      orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
      orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
      orderRepository.findAll().forEach(order -> {
        LOGGER.info("Preloaded " + order);
      });
    };
  }
}