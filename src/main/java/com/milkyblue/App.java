package com.milkyblue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.tomaslanger.chalk.Chalk;

public class App {
    public static void main(String[] args) {

        Chalk.setColorEnabled(true);

        BlockingBuffer sharedBuffer = new BlockingBuffer(1);

        CountThread cThread = new CountThread(sharedBuffer);
        FirstNameThread fThread = new FirstNameThread(sharedBuffer);
        MiddleNameThread mThread = new MiddleNameThread(sharedBuffer);
        FirstSurnameThread fSThread = new FirstSurnameThread(sharedBuffer);
        SecondSurnameThread sSThread = new SecondSurnameThread(sharedBuffer);
        Consumer consumer = new Consumer(sharedBuffer);

        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(cThread);
        executor.execute(fThread);
        executor.execute(mThread);
        executor.execute(fSThread);
        executor.execute(sSThread);
        executor.execute(consumer);

    }
}
