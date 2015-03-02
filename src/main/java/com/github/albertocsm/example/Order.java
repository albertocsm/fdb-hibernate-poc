package com.github.albertocsm.example;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

@Entity
@javax.persistence.Table(name = "ORDERS")
@Table(appliesTo = "ORDERS")
public class Order {

  // members
  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  @Column(name = "order_id")
  private long id;

  @Column(length = 300)
  private String order_info;

  @javax.persistence.Temporal(TemporalType.DATE)
  private Date order_date;

  @ManyToOne
  @JoinColumn(name = "customer_id", insertable = false, updatable = false)
  private Customer customer;

  //getter & setters
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getOrderInfo() {
    return order_info;
  }

  public void setOrderInfo(String orderInfo) {
    this.order_info = orderInfo;
  }

  public Date getOrderDate() {
    return order_date;
  }

  public void setOrderDate(Date order_date) {
    this.order_date = order_date;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  //public API
  public Order() {

  }
}
