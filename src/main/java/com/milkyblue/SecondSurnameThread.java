package com.milkyblue;

public class SecondSurnameThread implements Runnable {

  private BlockingBuffer sharedBuffer;

  public SecondSurnameThread(BlockingBuffer sharedBuffer) {
    this.sharedBuffer = sharedBuffer;
  }

  public void run() {

    try {

      Thread.sleep(500);

      for (int i = 0; i < 10; i++) {
        sharedBuffer.put("Serrano");
        Thread.sleep(500);
      }

    } catch (InterruptedException e) {

    }

  }
}