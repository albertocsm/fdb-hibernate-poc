package com.github.albertocsm.example;

import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.Table(name="TRIPS")
@Table(appliesTo="TRIPS")
public class Trips {

  // members
  @Id
  @Column(name = "id")
  private int id;

  @Column()
  private int day;

  // getters & setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  // public API
  public Trips() {
  }

  public Trips(int id, int day) {
    this.id = id;
    this.day = day;
  }
}
