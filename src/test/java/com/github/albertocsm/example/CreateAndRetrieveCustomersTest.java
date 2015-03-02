package com.github.albertocsm.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

@Test(suiteName = "fdb-tests")
public class CreateAndRetrieveCustomersTest {

  // members
  protected static final Logger log = LoggerFactory.getLogger(CreateAndRetrieveCustomersTest.class);
  private SessionFactory sessionFactory;

  @BeforeClass
  public void setUpClass() throws Exception {

    //add package if used eg: addPackage("com.xyz")
    sessionFactory = new Configuration().configure().

    addAnnotatedClass(Customer.class)//
        .addAnnotatedClass(Order.class)//
        .addAnnotatedClass(Address.class)//
        .buildSessionFactory();
  }

  @AfterClass
  public void tearDownClass() throws Exception {

  }

  @Test (groups = "setup")
  public void create_first_customer_with_order_and_address() {

    Session session = sessionFactory.openSession();

    session.beginTransaction();

    Customer customer = new Customer(1, "Thomas", "New Customer", new Date());
    session.save(customer);

    Order firstOrder = new Order();
    firstOrder.setOrderDate(new Date());
    firstOrder.setOrderInfo("Too many items");
    customer.addOrder(firstOrder);

    Address primary = new Address();
    primary.setCity("Boston");
    primary.setState("MA");
    primary.setAddressInfo("123 Main Street");
    customer.addAddress(primary);

    session.persist(customer);
    session.flush();
    session.getTransaction().commit();

    session.close();
  }

  @Test(dependsOnGroups = "setup")
  public void list_customers() {

    Session session = sessionFactory.openSession();
    session.beginTransaction();

//    session.createCriteria(Customer.class)
//            .setProjection(Projections.projectionList()
//                            .add(Projections.groupProperty("someColumn"))
//                            .add(Projections.max("someColumn"))
//                            .add(Projections.min("someColumn"))
//                            .add(Projections.count("someColumn"))
//            ).list();

    List result = session.createQuery("from Customer").list();

    for (Customer customer : (List<Customer>) result) {

      log.info("Customer (" + customer.getId() + ", " + customer.getRand_id() + ") : " + customer.getName());

      for (Order order : customer.getOrders()) {
        log.info("    Order (" + order.getId() + ") : " + order.getOrderInfo());
      }

      for (Address address : customer.getAddresses()) {
        log.info("    Address (" + address.getId() + ") : " + address.getCity());
      }
    }

    session.getTransaction().commit();
    session.close();

  }

}
