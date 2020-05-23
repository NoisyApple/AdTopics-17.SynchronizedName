package com.milkyblue;

import java.util.concurrent.ArrayBlockingQueue;

// BlockingBuffer Class. Models a Buffer based object that stores values 
// in an ArrayBlockingQueue instance.
public class BlockingBuffer {

  private ArrayBlockingQueue<String> buffer;

  // Class constructor.
  public BlockingBuffer(int length) {
    buffer = new ArrayBlockingQueue<String>(length);
  }

  // Puts a passed String value into the buffer.
  public void put(String value) throws InterruptedException {
    buffer.put(value);
  }

  // Takes out the last value from the buffer.
  public String take() throws InterruptedException {
    return buffer.take();
  }

}