package com.milkyblue;

public class StringThread implements Runnable {

  private String strToDisplay;
  private int position;
  private int maxThreads;
  private int iterations;
  private BlockingBuffer sharedBuffer;
  private int sleepMultiplier;

  public StringThread(String strToDisplay, int position, int maxThreads, int iterations, BlockingBuffer sharedBuffer,
      int sleepMultiplier) {
    this.strToDisplay = strToDisplay;
    this.position = position;
    this.maxThreads = maxThreads;
    this.iterations = iterations;
    this.sharedBuffer = sharedBuffer;
    this.sleepMultiplier = sleepMultiplier;
  }

  public void run() {

    try {

      Thread.sleep(position * sleepMultiplier);

      for (int i = 0; i < iterations; i++) {
        sharedBuffer.put(strToDisplay);
        Thread.sleep((maxThreads + 1) * sleepMultiplier);
      }

    } catch (InterruptedException e) {

    }

  }
}