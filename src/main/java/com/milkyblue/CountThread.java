package com.milkyblue;

public class CountThread implements Runnable {

  private BlockingBuffer sharedBuffer;

  public CountThread(BlockingBuffer sharedBuffer) {
    this.sharedBuffer = sharedBuffer;
  }

  public void run() {

    try {

      Thread.sleep(100);

      for (int i = 0; i < 10; i++) {
        sharedBuffer.put(Integer.toString(i + 1));
        Thread.sleep(500);
      }

    } catch (InterruptedException e) {

    }

  }
}