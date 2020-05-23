package com.milkyblue;

// CountThread Class. Models a Producer based Thread that stores values 
// from 1 to the value pased as iterations.
public class CountThread implements Runnable {

  private int position;
  private int maxThreads;
  private int iterations;
  private BlockingBuffer sharedBuffer;
  private int sleepMultiplier;

  // Class constructor. Besides the BlockingBuffer, three int parameters are also
  // required, these to syncrhonize each thread based on its position, the max
  // amount of threads and the number of iterations. Also a sleepMultiplier int is
  // required to define the amount of delay to achieve a good synchronization.
  public CountThread(int position, int maxThreads, int iterations, BlockingBuffer sharedBuffer, int sleepMultiplier) {
    this.position = position;
    this.maxThreads = maxThreads;
    this.iterations = iterations;
    this.sharedBuffer = sharedBuffer;
    this.sleepMultiplier = sleepMultiplier;
  }

  // Method implemented from Runnable. Sleeps the thread based on passed position
  // and sleepMultiplier arguments, then the actual index is stored into
  // sharedBuffer as many times as the value of iterations in an interval of time
  // based on maxThreads and sleepMultiplier.
  public void run() {

    try {

      Thread.sleep(position * sleepMultiplier);

      for (int i = 0; i < iterations; i++) {
        sharedBuffer.put(Integer.toString(i + 1));
        Thread.sleep((maxThreads + 1) * sleepMultiplier);
      }

    } catch (InterruptedException e) {

    }

  }
}