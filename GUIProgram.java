package SearchEngine;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIProgram {

    public JFrame frame1, frame2;
    private JTextField nameField, passField;
    static GUIProgram window;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        createIndex.create();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new GUIProgram();
                    window.frame1.setVisible(true);
                    //window.frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUIProgram() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame1 = new JFrame();
        frame1.setBounds(280, 75, 780, 500);
        frame1.getContentPane().setLayout(null);
        JLabel title = new JLabel("Login");
        JLabel Admin = new JLabel("AdminGUI Login");
        JLabel User = new JLabel("User Login");
        title.setFont(new Font("Serif",Font.BOLD,20));
        title.setBounds(500,0,100,100);
        Admin.setBounds(93,50,100,100);
        User.setBounds(500,50,100,100);
        JLabel Name = new JLabel("Name");
        JLabel Pass = new JLabel("Password");
        Name.setBounds(93, 120, 46, 14);
        Pass.setBounds(93, 150, 100, 14);
        frame1.getContentPane().add(Name);
        frame1.getContentPane().add(Pass);
        frame1.getContentPane().add(Pass);
        frame1.getContentPane().add(title);
        frame1.getContentPane().add(Admin);
        frame1.getContentPane().add(User);
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String name = nameField.getText();
                String pass = passField.getText();
                //window.frame1.setVisible(false); // ?
                AdminGUI.admin();
            }
        });
        JButton btnSearch = new JButton("Start Search!");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userGUI.admin();
            }
        });
        btnSubmit.setBounds(93, 190, 89, 23);
        frame1.getContentPane().add(btnSubmit);
        btnSearch.setBounds(500, 120, 120, 23);
        frame1.getContentPane().add(btnSearch);

        nameField = new JTextField();
        nameField.setBounds(204, 120, 167, 20);
        frame1.getContentPane().add(nameField);
        nameField.setColumns(10);

        passField = new JTextField();
        passField.setBounds(204, 150, 167, 20);
        frame1.getContentPane().add(passField);
        passField.setColumns(10);
    }
}
