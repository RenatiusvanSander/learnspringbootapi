package edu.remad.LearnSpringBootAPI.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {

  /**
   * the id for database table
   */
  private @Id
  @GeneratedValue Long id;

  /**
   * descritption about Order
   */
  private String description;

  /**
   * the status of order
   */
  private Status status;

  /**
   * Constructor
   */
  Order() {
  }

  /**
   * Constructor
   *
   * @param description
   * @param status
   */
  public Order(String description, Status status) {

    this.description = description;
    this.status = status;
  }

  /**
   * Gets id, primary key
   *
   * @return the database id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Gets description
   *
   * @return the description of customer
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Gets {@link Status}
   *
   * @return the current status
   */
  public Status getStatus() {
    return this.status;
  }

  /**
   * Sets ID
   *
   * @param id the database identifier, primary key to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Sets description
   *
   * @param description description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Sets status.
   *
   * @param status the status to set
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (!(o instanceof Order)) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(this.id, order.id) && Objects.equals(this.description, order.description)
        && this.status == order.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.description, this.status);
  }

  @Override
  public String toString() {
    return "Order{" + "id=" + this.id + ", description='" + this.description + '\'' + ", status="
        + this.status + '}';
  }
}
