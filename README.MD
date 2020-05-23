# [Advanced Programming Topics] #17 Synchronized Name

This is the **17th** project part of my Advanced Programming Topics course at Leon Institute of Thechnology :mortar_board::lion:.

## :dart:GOAL

Dinamically create a group of **Producer based Threads**, each Thread will take a part from a full name stored into a String and store that part into a **buffer** object, then a **Consumer based Thread** will print each of those values in a syncrhonized way that each value will form again the full named passed.

## :eyeglasses:BRIEF EXPLANATION

The GUI consists of two text fields, one of the to specify the full name String, and the other for the amount of iterations that each row containing the values from each Producer Thread will be displayed.

<p align="center">
<img src="./snapshots/Annotation 2020-05-23 041944.png"/>
</p>

When the fields are filled and the execute button pressed, the program will take the full name String and split it into multiple parts to then store them into an array. If blank spaces exists before the first letter in the name or after the last, these are then removed, an then with the use of a **Regular Expression** the array is created taking each group of non space characters separated by one or more spaces.

```java
String[] strRow = fullName.replaceAll("^\\s+|\\s+$", "").split("(\\s+)");

/*
** EXAMPLE-1:
** INPUT: "firstName middleName lastName"
** OUTPUT: ["firstName", "middleName", "lastName"]
**
** EXAMPLE-2:
** INPUT: "     firstName     middleName  lastName    "
** OUTPUT: ["firstName", "middleName", "lastName"]
*/
```

After that, a `BlockingBuffer` object is created with a size of 1, and also an array of `Thread` objects, with a size of `strRow.length + 2`, considering that it will contain:

- **At the beginning** a `CountThread` object that will be adding to the buffer a count based on the number of iterations specified on the second text field.
- **At the middle** a `StringThread` object that will be adding to the buffer a String based on each String from `strRow`.
- **At the end** a `PrinterThread` object that will be consuming data inserted into the buffer to then print it.

A `sleepMultiplier` int is also passed to `CountThread` and `StringThread` objects, this value will control how accurate the syncrhonization will be.

```java
int sleepMultiplier = 50;

BlockingBuffer sharedBuffer = new BlockingBuffer(1);
Runnable[] threads = new Runnable[strRow.length + 2];

for (int i = 0; i < threads.length; i++) {
  if (i == 0) { // Beginning.
    threads[i] = new CountThread(i + 1, threads.length - 2, iterations, sharedBuffer, sleepMultiplier);
  } else if (i == threads.length - 1) { // End
    threads[i] = new PrinterThread(iterations, threads.length - 1, sharedBuffer);
  } else { // Middle
    threads[i] = new StringThread(strRow[i - 1], i + 1, threads.length - 2, iterations, sharedBuffer,
        sleepMultiplier);
  }
}
```

Finally, an **ExecutorService** object executes each of the Threads inside the `threads` array an therefore the program will start printing into terminal.

```java
ExecutorService executor = Executors.newCachedThreadPool();

for (Runnable thread : threads)
  executor.execute(thread);
```

---

Now, lets test the program by input the name **"Elon Reeve Musk"** and **10** as the value for iterations:

<p align="center">
<img src="./snapshots/Annotation 2020-05-23 050132.png"/>
<img style="margin-left: 20px" src="./snapshots/Annotation 2020-05-23 050232.png"/>
</p>

And finally with a shorter name, **"Bill Gates"** and **8** iterations:

<p align="center">
<img src="./snapshots/Annotation 2020-05-23 050305.png"/>
<img style="margin-left: 20px" src="./snapshots/Annotation 2020-05-23 050326.png"/>
</p>
