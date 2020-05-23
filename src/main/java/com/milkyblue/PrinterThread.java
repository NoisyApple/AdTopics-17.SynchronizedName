package com.milkyblue;

import com.github.tomaslanger.chalk.Chalk;

// PrinterThread Class. Models a Consumer based Thread that consumes 
// values from a BlockingBuffer and then prints them.
public class PrinterThread implements Runnable {

  private int rowsToPrint;
  private int cols;
  private BlockingBuffer sharedBuffer;

  // Class constructor.
  public PrinterThread(int rowsToPrint, int cols, BlockingBuffer sharedBuffer) {
    this.rowsToPrint = rowsToPrint;
    this.cols = cols;
    this.sharedBuffer = sharedBuffer;
  }

  // Method implemented from Runnable. Prints a row of values consumed from the
  // BlockingBuffer as many times as specified on rowToPrint.
  public void run() {

    for (int row = 0; row < rowsToPrint; row++) {
      for (int col = 0; col < cols; col++)
        try {

          String valueTaken = sharedBuffer.take();

          // Changes format depending on the position of the actual col.
          if (col == 0) {
            valueTaken = "[" + Chalk.on(valueTaken).cyan() + "]\t";
          } else {
            valueTaken += (col == cols - 1) ? "\n" : " ";
          }

          System.out.print(valueTaken);

        } catch (InterruptedException e) {
        }
    }

  }

}