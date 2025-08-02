package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JButton login, cancel;
    JTextField tfusername;
    JPasswordField tfpassword;

    Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40, 20, 100, 20);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(150, 20, 150, 20);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 60, 100, 20);
        add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(150, 60, 150, 20);
        add(tfpassword);

        login = new JButton("Login");
        login.setBounds(40, 140, 120, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Tahoma", Font.BOLD, 15));
        login.addActionListener(this);
        add(login);

        cancel = new JButton("Cancel");
        cancel.setBounds(180, 140, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 0, 200, 200);
        add(image);

        setSize(600, 300);
        setLocation(500, 250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == login) {
        String username = tfusername.getText();
        // Use getPassword() for JPasswordField and convert to String safely
        String password = new String(tfpassword.getPassword());

        String query = "SELECT * FROM login WHERE username='" + username + "' AND password='" + password + "'";

        Conn c = null;
        ResultSet rs = null;

        try {
            c = new Conn();
            rs = c.s.executeQuery(query);

            if (rs.next()) {
                setVisible(false);
                new Project();  // Replace with your actual main screen class
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
                // Keep login visible so user can retry
            }
            c.s.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (c != null) {
                    if (c.s != null) c.s.close();
                    if (c.con != null) c.con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } else if (ae.getSource() == cancel) {
        setVisible(false);
    }
}
}