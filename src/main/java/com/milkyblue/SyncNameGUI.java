package com.milkyblue;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.tomaslanger.chalk.Chalk;

// SyncNameGUI Class. Models the GUI.
public class SyncNameGUI {

  private JFrame mainFrame;
  private JPanel mainPanel, topPanel, centerPanel, bottomPanel;
  private JLabel lblAdvice, lblFullName, lblIterations;
  private JTextField txtFullName, txtIterations;
  private JButton btnExecute;

  // Class Constructor.
  public SyncNameGUI() {
    Chalk.setColorEnabled(true);

    mainFrame = new JFrame("Syncrhonized Name");
    mainPanel = new JPanel(new BorderLayout());
    topPanel = new JPanel();
    centerPanel = new JPanel();
    bottomPanel = new JPanel();
    lblAdvice = new JLabel("Fill the fields and press Execute");
    lblFullName = new JLabel("Full Name:");
    lblIterations = new JLabel("Iterations:");
    txtFullName = new JTextField(10);
    txtIterations = new JTextField(10);
    btnExecute = new JButton("Execute");

    // Main methods are called.
    addAttributes();
    addListeners();
    build();
    launch();
  }

  // Adds attributes to elements in the class.
  private void addAttributes() {
    centerPanel.setLayout(new GridBagLayout());

    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setResizable(false);
  }

  // Adds listeners to elements in the GUI.
  private void addListeners() {
    // When btnExecute is pressed, then is checked if there is a valid input on both
    // text fields if so, printName method will be called, otherwise an error
    // message will appear.
    btnExecute.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        try {
          String fullName = txtFullName.getText();
          int iterations = Integer.parseInt(txtIterations.getText());

          if (fullName.length() == 0 || iterations == 0)
            throw new Exception();

          printName(fullName, iterations);

        } catch (Exception error) {
          JOptionPane.showMessageDialog(null,
              "<html><span style='font-weight: bold; color: red'>ERROR: </span>Type valid information.<html>", "Error",
              JOptionPane.PLAIN_MESSAGE);
        }
      }
    });
  }

  // Builds the GUI.
  private void build() {
    topPanel.add(lblAdvice);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.insets = new Insets(2, 2, 2, 2);
    centerPanel.add(lblFullName, gbc);
    gbc.gridx = 2;
    centerPanel.add(txtFullName, gbc);
    gbc.gridx = 1;
    gbc.gridy = 2;
    centerPanel.add(lblIterations, gbc);
    gbc.gridx = 2;
    centerPanel.add(txtIterations, gbc);

    bottomPanel.add(btnExecute);

    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(centerPanel, BorderLayout.CENTER);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    mainFrame.add(mainPanel);
  }

  // Launches the frame by setting its visible value to true.
  private void launch() {
    mainFrame.setVisible(true);
    mainFrame.pack();
    mainFrame.setLocationRelativeTo(null);
  }

  // Takes values from both of the text fields, from txtFullName the text is
  // spitted into multiple Strings and stored in a new array, then each String
  // from that array si printed in a synchronized way as many times as specified
  // on the txtIterations text value.
  private void printName(String fullName, int iterations) {
    int sleepMultiplier = 50;

    String[] strRow = fullName.replaceAll("^\\s+|\\s+$", "").split("(\\s+)");

    BlockingBuffer sharedBuffer = new BlockingBuffer(1);
    Runnable[] threads = new Runnable[strRow.length + 2];

    for (int i = 0; i < threads.length; i++) {
      if (i == 0) {
        threads[i] = new CountThread(i + 1, threads.length - 2, iterations, sharedBuffer, sleepMultiplier);
      } else if (i == threads.length - 1) {
        threads[i] = new PrinterThread(iterations, threads.length - 1, sharedBuffer);
      } else {
        threads[i] = new StringThread(strRow[i - 1], i + 1, strRow.length, iterations, sharedBuffer, sleepMultiplier);
      }
    }

    ExecutorService executor = Executors.newCachedThreadPool();

    for (Runnable thread : threads)
      executor.execute(thread);
  }

}