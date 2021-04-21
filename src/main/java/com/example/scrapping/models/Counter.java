package com.example.scrapping.models;

public class Counter {
  int counter = 0;

  public int getCounter() {
    return counter;
  }

  public void setCounter(int counter) {
    this.counter = counter;
  }

  public void increment() {
    this.counter++;
  }

  public void decrement() {
    this.counter--;
  }

  public void reset() {
    this.counter = 0;
  }
}
