package SearchEngine;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;

public class AdminAdd {
    public static JFrame frame;
    public static AdminAdd window;
    private JTextField fileName;
    private JTextArea text, summary;

    /**
     * Launch the application.
     */
    public static void add() {
        //createIndex.create();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window= new AdminAdd();
                    window.frame.setVisible(true);
                    window.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public AdminAdd() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(250, 75, 800, 550);
        frame.getContentPane().setLayout(null);
        JLabel title = new JLabel("Add File");
        title.setFont(new Font("Serif",Font.BOLD,20));
        title.setBounds(490,0,100,100);
        frame.getContentPane().add(title);
        JButton btnAdd = new JButton("Add File");
        JLabel fname = new JLabel("File Name");
        JLabel txt = new JLabel("Text");
        fname.setBounds(100,25,100,100);
        txt.setBounds(100,120,100,100);
        fileName = new JTextField();

        fileName.setBounds(204, 70, 180, 20);
        frame.getContentPane().add(fileName);
        frame.getContentPane().add(fname);
        frame.getContentPane().add(txt);
        fileName.setColumns(10);


        text = new JTextArea();
        text.setBounds(204, 120, 500, 300);
        frame.getContentPane().add(text);
        text.setColumns(100);
        text.setRows(30);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String name,doc;
                name= fileName.getText();
                doc=text.getText();
                try {
                    //First index table
                    FileWriter newDoc= new FileWriter("source/" + name + ".txt",true);
                    newDoc.append(doc);
                    newDoc.close();
                    createIndex.create();
                }
                catch(IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();

                }
                frame.setVisible(false);
            }
        });
        btnAdd.setBounds(204, 450, 89, 23);

        frame.getContentPane().add(btnAdd);
    }
}
