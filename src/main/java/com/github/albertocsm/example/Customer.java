package com.github.albertocsm.example;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Table;

@Entity
@javax.persistence.Table(name="CUSTOMERS")
@Table(appliesTo="CUSTOMERS")
@AccessType("property")
public class Customer {
    
    public Customer() {
    }
    
    public Customer(long rand_id, String name, String customer_info,
            Date birthday) {
        this.rand_id = rand_id;
        this.name = name;
        this.customer_info = customer_info;
        this.birthday = birthday;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="customer_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRand_id() {
        return rand_id;
    }

    public void setRand_id(long rand_id) {
        this.rand_id = rand_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer_info() {
        return customer_info;
    }

    public void setCustomer_info(String customer_info) {
        this.customer_info = customer_info;
    }

    @javax.persistence.Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    @OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinColumn(name="customer_id")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    @OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinColumn(name="customer_id")
    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    private long id;
    private long rand_id;
    private String name;
    private String customer_info;
    private Date birthday;
    private Set<Order> orders = new HashSet<Order>();
    private Set<Address> addresses = new HashSet<Address>();
}
