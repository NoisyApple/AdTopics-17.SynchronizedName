package com.milkyblue;

// StringThread Class. Models a Producer based Thread that stores a passed 
// strToDisplay into a BlockingBuffer.
public class StringThread implements Runnable {

  private String strToDisplay;
  private int position;
  private int maxThreads;
  private int iterations;
  private BlockingBuffer sharedBuffer;
  private int sleepMultiplier;

  // Class constructor. Besides the BlockingBuffer and String parameters, three
  // int parameters are also required, these to syncrhonize each thread based on
  // its position, the max amount of threads and the number of iterations. Also a
  // sleepMultiplier int is required to define the amount of delay to achieve a
  // good synchronization.
  public StringThread(String strToDisplay, int position, int maxThreads, int iterations, BlockingBuffer sharedBuffer,
      int sleepMultiplier) {
    this.strToDisplay = strToDisplay;
    this.position = position;
    this.maxThreads = maxThreads;
    this.iterations = iterations;
    this.sharedBuffer = sharedBuffer;
    this.sleepMultiplier = sleepMultiplier;
  }

  // Method implemented from Runnable. Sleeps the thread based on passed position
  // and sleepMultiplier arguments, then strToDisplay is stored into sharedBuffer
  // as many times as the value of iterations in an interval of time based on
  // maxThreads and sleepMultiplier.
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