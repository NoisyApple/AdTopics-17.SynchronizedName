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
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.tomaslanger.chalk.Chalk;

public class SyncNameGUI {

  private JFrame mainFrame;
  private JPanel mainPanel, topPanel, centerPanel, bottomPanel;
  private JLabel lblAdvice, lblFullName, lblIterations;
  private JTextField txtFullName, txtIterations;
  private JButton btnExecute;

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

    addAttributes();
    addListeners();
    build();
    launch();
  }

  private void addAttributes() {
    centerPanel.setLayout(new GridBagLayout());

    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setResizable(false);
  }

  private void addListeners() {
    btnExecute.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        printName(txtFullName.getText(), Integer.parseInt(txtIterations.getText()));
      }
    });
  }

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

  private void launch() {
    mainFrame.setVisible(true);
    mainFrame.pack();
    mainFrame.setLocationRelativeTo(null);
  }

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
        threads[i] = new StringThread(strRow[i - 1], i + 1, threads.length - 2, iterations, sharedBuffer,
            sleepMultiplier);
      }
    }

    ExecutorService executor = Executors.newCachedThreadPool();

    for (Runnable thread : threads)
      executor.execute(thread);
  }

}