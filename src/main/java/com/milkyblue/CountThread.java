package com.milkyblue;

public class CountThread implements Runnable {

  private int position;
  private int maxThreads;
  private BlockingBuffer sharedBuffer;
  private int sleepMultiplier;

  public CountThread(int position, int maxThreads, BlockingBuffer sharedBuffer, int sleepMultiplier) {
    this.position = position;
    this.maxThreads = maxThreads;
    this.sharedBuffer = sharedBuffer;
    this.sleepMultiplier = sleepMultiplier;
  }

  public void run() {

    try {

      Thread.sleep(position * sleepMultiplier);

      for (int i = 0; i < 10; i++) {
        sharedBuffer.put(Integer.toString(i + 1));
        Thread.sleep((maxThreads + 1) * sleepMultiplier);
      }

    } catch (InterruptedException e) {

    }

  }
}