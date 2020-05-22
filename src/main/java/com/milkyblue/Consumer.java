package com.milkyblue;

import com.github.tomaslanger.chalk.Chalk;

public class Consumer implements Runnable {

  private BlockingBuffer sharedBuffer;

  public Consumer(BlockingBuffer sharedBuffer) {
    this.sharedBuffer = sharedBuffer;
  }

  public void run() {

    for (int rows = 0; rows < 10; rows++) {
      for (int cols = 0; cols < 5; cols++)
        try {
          String valueTaken = sharedBuffer.take();
          System.out.print((cols == 0) ? "[" + Chalk.on(valueTaken).cyan() + "]\t"
              : (cols == 5 - 1) ? valueTaken + "\n" : valueTaken + " ");
        } catch (InterruptedException e) {
        }
    }

  }

}