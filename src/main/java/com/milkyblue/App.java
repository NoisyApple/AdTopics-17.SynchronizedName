package com.milkyblue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.tomaslanger.chalk.Chalk;

public class App {
    public static void main(String[] args) {
        Chalk.setColorEnabled(true);

        int sleepMultiplier = 50;

        String fullName = "Leonel Alejandro Aguirre Serrano";

        String[] strRow = fullName.replaceAll("^\\s+|\\s+$", "").split("(\\s+)");

        BlockingBuffer sharedBuffer = new BlockingBuffer(1);

        Runnable[] threads = new Runnable[strRow.length + 2];
        for (int i = 0; i < threads.length; i++) {
            if (i == 0) {
                threads[i] = new CountThread(i + 1, threads.length - 2, sharedBuffer, sleepMultiplier);
            } else if (i == threads.length - 1) {
                threads[i] = new PrinterThread(10, threads.length - 1, sharedBuffer);
            } else {
                threads[i] = new StringThread(strRow[i - 1], i + 1, threads.length - 2, sharedBuffer, sleepMultiplier);
            }
        }

        ExecutorService executor = Executors.newCachedThreadPool();

        for (Runnable thread : threads)
            executor.execute(thread);

    }
}
