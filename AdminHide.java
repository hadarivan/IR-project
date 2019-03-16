package SearchEngine;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;

public class AdminHide {
    public static JFrame frame;
    public static AdminHide window;
    private JTextField fileName;

    /**
     * Launch the application.
     */
    public static void delete() {
        //createIndex.create();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window= new AdminHide();
                    window.frame.setVisible(true);
                    //window.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public AdminHide() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(250, 75, 500, 300);
        frame.getContentPane().setLayout(null);
        JLabel title = new JLabel("Hide File");
        title.setFont(new Font("Serif",Font.BOLD,20));
        title.setBounds(100,0,100,100);
        frame.getContentPane().add(title);
        JButton btnHide = new JButton("Hide File");
        JButton btnShow = new JButton("Show File");
        JLabel fname = new JLabel("File Name");
        fname.setBounds(100,40,100,100);
        fileName = new JTextField();

        fileName.setBounds(204, 80, 180, 20);
        frame.getContentPane().add(fileName);
        frame.getContentPane().add(fname);
        fileName.setColumns(10);


        btnHide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String name;
                name= fileName.getText();
                createIndex.changeFlag(name);
                frame.setVisible(false);
            }
        });
        btnHide.setBounds(250, 200, 89, 23);

        frame.getContentPane().add(btnHide);

        btnShow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String name;
                name= fileName.getText();
                createIndex.changeFlagShow(name);
                frame.setVisible(false);

            }
        });
        btnShow.setBounds(100, 200, 100, 23);

        frame.getContentPane().add(btnShow);
    }
}
