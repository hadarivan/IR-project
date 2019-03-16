package SearchEngine;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminGUI {
    public static JFrame frame;
    public static AdminGUI window;
    /**
     * Launch the application.
     */
    public static void admin() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window= new AdminGUI();
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
    public AdminGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(500, 200, 300, 300);
        frame.getContentPane().setLayout(null);
        JLabel title = new JLabel("AdminGUI");
        title.setFont(new Font("Serif",Font.BOLD,20));
        title.setBounds(100,0,100,100);
        frame.getContentPane().add(title);
        JButton btnAdd = new JButton("Add File");
        //Add file
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                AdminAdd.add();
            }
        });
        JButton btnDelete = new JButton("Delete File");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { AdminHide.delete();}
        });
        btnAdd.setBounds(93, 100, 89, 23);
        frame.getContentPane().add(btnAdd);
        btnDelete.setBounds(78,140,120,23);
        frame.getContentPane().add(btnDelete);
    }
}
