package com.milkyblue;

import com.github.tomaslanger.chalk.Chalk;

public class PrinterThread implements Runnable {

  private int rowsToPrint;
  private int cols;
  private BlockingBuffer sharedBuffer;

  public PrinterThread(int rowsToPrint, int cols, BlockingBuffer sharedBuffer) {
    this.rowsToPrint = rowsToPrint;
    this.cols = cols;
    this.sharedBuffer = sharedBuffer;
  }

  public void run() {

    for (int row = 0; row < rowsToPrint; row++) {
      for (int col = 0; col < cols; col++)
        try {

          String valueTaken = sharedBuffer.take();

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