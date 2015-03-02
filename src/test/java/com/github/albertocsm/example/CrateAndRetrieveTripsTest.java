package com.github.albertocsm.example;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CrateAndRetrieveTripsTest {

  // members
  protected static final Logger log = LoggerFactory
      .getLogger(CreateAndRetrieveCustomersTest.class);
  private SessionFactory sessionFactory;

  @BeforeClass
  public void setUpClass() throws Exception {

    // add package if used eg: addPackage("com.xyz")
    sessionFactory = new Configuration().configure().

    addAnnotatedClass(Trips.class)//
        .buildSessionFactory();
  }

  @AfterClass
  public void tearDownClass() throws Exception {

  }

  @Test(groups = "setup")
  public void create_trips() {

    int totalTrips = 1000000;
    int batchSize = 10000;

    int id = 0;
    for (int i = 0; i < totalTrips / batchSize; i++) {

      id = create_batch(batchSize, id);
    }

  }

  @Test(dependsOnGroups = "setup")
  public void count_trips_by_day() {

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Stopwatch stopwatch = Stopwatch.createStarted();
    List result = session.createCriteria(Trips.class)
        .setProjection(Projections.projectionList()//
            .add(Projections.groupProperty("day"))//
            .add(Projections.count("id"))//
        ).list();

    session.getTransaction().commit();
    session.close();
    stopwatch.stop();
    long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
    log.info(String.format("time to aggregate number of trips per day: %s ms",
            millis));

    Assert.assertTrue(result.size() == 29);
  }

  private int create_batch(int batchTotalTrips, int tripId) {

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    Trips t;
    Random r = new Random();
    int low = 1;
    int high = 30;

    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; i < batchTotalTrips; i++) {

      t = new Trips(tripId, r.nextInt(high - low) + low);
      session.save(t);
      tripId++;
    }

    session.flush();
    session.getTransaction().commit();
    session.close();

    // log the latency
    stopwatch.stop();
    long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
    log.info(String.format("time to generate %s trips in: %s ms",
        batchTotalTrips, millis));

    return tripId;
  }
}
