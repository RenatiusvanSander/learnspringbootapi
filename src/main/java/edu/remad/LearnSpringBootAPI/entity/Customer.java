package edu.remad.LearnSpringBootAPI.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class, which creates a table on database for {@link Customer}
 */
@Entity
public class Customer {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Customer)) {
      return false;
    }
    Customer customer = (Customer) o;

    return getId() == customer.getId() && getFirstName().equals(customer.getFirstName())
        && getLastName().equals(customer.getLastName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getFirstName(), getLastName());
  }

  /**
   * Constructor
   */
  public Customer() {
  }

  /**
   * Constructor
   *
   * @param firstName first name of customer to set
   * @param lastName  last name of customer to set
   */
  public Customer(String firstName, String lastName) {
  }

  /**
   * Gets id
   *
   * @return id of customer
   */
  public long getId() {
    return id;
  }

  /**
   * Gets first name.
   *
   * @return first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets first name
   *
   * @param firstName first name of customer to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets last name
   *
   * @return last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets last name
   *
   * @param lastName last name for customer to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * the id, as synonym unique primary key, for a data set
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /**
   * first name of a customer, also column for table customer on data base
   */
  private String firstName;

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        '}';
  }

  /**
   * last name of customer, also column for table customer on data base
   */
  private String lastName;

  /**
   * Sets id.
   *
   * @param id numeric unique primary key to set
   */
  public void setId(Long id) {
    this.id = id;
  }
}
