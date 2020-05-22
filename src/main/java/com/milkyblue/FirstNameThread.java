package com.milkyblue;

public class FirstNameThread implements Runnable {

  private BlockingBuffer sharedBuffer;

  public FirstNameThread(BlockingBuffer sharedBuffer) {
    this.sharedBuffer = sharedBuffer;
  }

  public void run() {

    try {

      Thread.sleep(200);

      for (int i = 0; i < 10; i++) {
        sharedBuffer.put("Leonel");
        Thread.sleep(500);
      }

    } catch (InterruptedException e) {

    }

  }
}