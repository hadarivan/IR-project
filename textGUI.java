package SearchEngine;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class textGUI {
    public static JFrame frame;
    public static textGUI window;
    /**
     * Launch the application.
     */
    public static void text(String fileName) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window= new textGUI(fileName);
                    window.frame.setVisible(true);
                    //window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public textGUI(String fileName) {
        initialize(fileName);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(String fileName) {
        frame = new JFrame();
        frame.setBounds(400, 85, 500, 500);
        frame.getContentPane().setLayout(null);
        JTextArea text;
        try {
            StringBuilder summary = new StringBuilder();
            File myObj = new File("storage/" + fileName);
            Scanner myReader = new Scanner(myObj);
            for (int i=0; myReader.hasNextLine(); i++) {
                summary.append(myReader.nextLine());
                summary.append("\r\n");
            }
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            text = new JTextArea(50,0);
            text.append(summary.toString());
            text.setBounds(10, 10, 400, 400);
            frame.getContentPane().add(text);
            scrollPane.setBounds(20,20,400,400);
            scrollPane.getViewport().add(text);
            frame.add(scrollPane);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
