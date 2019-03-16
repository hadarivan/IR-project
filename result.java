package SearchEngine;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class result {
    public static JFrame frame;
    public static result window;
    /**
     * Launch the application.
     */
    public static void admin() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window= new result();
                    window.frame.setVisible(true);
                    //window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void showResult(Stack<String> fileName) {
        frame = new JFrame();
        int x= 50;
        frame.setBounds(330, 85, 700, 400);
        frame.getContentPane().setLayout(null);
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JButton) {
                    String text = ((JButton) e.getSource()).getText();
                    textGUI.text(text);
                }
            }
        };
        if(!(fileName.empty())) {
            for (int i = 0; i < fileName.size() && i<=4; i++) { //maximum 5 documents
                JButton[] Name = new JButton[10];
                for (int index = 0; index < Name.length; index++) {
                    Name[index] = new JButton(String.valueOf(fileName.get(i)));
                    Name[index].addActionListener(listener);
                    frame.add(Name[index]);
                    Name[index].setBounds(10, 0 + x, 200, 23);
                    frame.getContentPane().add(Name[index]);
                }
                JLabel sum;
                try {
                    StringBuilder summary = new StringBuilder();
                    File myObj = new File("storage/" + fileName.get(i));
                    Scanner myReader = new Scanner(myObj);
                    for (int j = 0; j < 3; j++) {
                        summary.append(myReader.nextLine());
                        summary.append(" ");
                        sum = new JLabel(summary.toString());
                        sum.setBounds(10, 20 + x, 700, 23);
                        frame.getContentPane().add(sum);
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                x += 50;
            }
        }
        else
        {
            JLabel notFound=new JLabel("No files exist with this word");
            notFound.setBounds(10, 20, 300, 23);
            frame.getContentPane().add(notFound);
        }


        JPanel panel = new JPanel(new GridLayout(4,3));

    }

}
