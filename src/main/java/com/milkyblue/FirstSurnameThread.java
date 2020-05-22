package com.milkyblue;

public class FirstSurnameThread implements Runnable {

  private BlockingBuffer sharedBuffer;

  public FirstSurnameThread(BlockingBuffer sharedBuffer) {
    this.sharedBuffer = sharedBuffer;
  }

  public void run() {

    try {

      Thread.sleep(400);

      for (int i = 0; i < 10; i++) {
        sharedBuffer.put("Aguirre");
        Thread.sleep(500);
      }

    } catch (InterruptedException e) {

    }

  }
}