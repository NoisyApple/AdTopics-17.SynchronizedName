package com.milkyblue;

public class MiddleNameThread implements Runnable {

  private BlockingBuffer sharedBuffer;

  public MiddleNameThread(BlockingBuffer sharedBuffer) {
    this.sharedBuffer = sharedBuffer;
  }

  public void run() {

    try {

      Thread.sleep(300);

      for (int i = 0; i < 10; i++) {
        sharedBuffer.put("Alejandro");
        Thread.sleep(500);
      }

    } catch (InterruptedException e) {

    }

  }
}