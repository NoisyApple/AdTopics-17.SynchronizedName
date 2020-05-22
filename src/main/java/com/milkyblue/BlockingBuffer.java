package com.milkyblue;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingBuffer {

  private ArrayBlockingQueue<String> buffer;

  public BlockingBuffer(int length) {
    buffer = new ArrayBlockingQueue<String>(length);
  }

  public void put(String value) throws InterruptedException {
    buffer.put(value);
  }

  public String take() throws InterruptedException {
    return buffer.take();
  }

}