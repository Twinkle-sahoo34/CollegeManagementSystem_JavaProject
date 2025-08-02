package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class StudentLeave extends JFrame implements ActionListener {
    Choice crollno, ctime;
    JDateChooser dcdate;
    JButton submit, cancel;

    StudentLeave() {
        setSize(500, 550);
        setLocation(550, 100);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Heading
        JLabel heading = new JLabel("Apply Leave (Student)");
        heading.setBounds(40, 50, 250, 30);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(heading);

        // Label: Roll Number
        JLabel lblrollno = new JLabel("Search By Roll Number");
        lblrollno.setBounds(60, 100, 250, 20);
        lblrollno.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lblrollno);

        // Choice: Roll Number dropdown
        crollno = new Choice();
        crollno.setBounds(60, 130, 200, 20);
        add(crollno);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Label: Date
        JLabel lbldate = new JLabel("Select Date");
        lbldate.setBounds(60, 170, 200, 20);
        lbldate.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lbldate);

        // Date Picker
        dcdate = new JDateChooser();
        dcdate.setBounds(60, 200, 200, 30);
        add(dcdate);

        // Label: Time Duration
        JLabel lbltime = new JLabel("Time Duration");
        lbltime.setBounds(60, 250, 200, 20);
        lbltime.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(lbltime);

        // Choice: Duration Dropdown
        ctime = new Choice();
        ctime.setBounds(60, 280, 200, 20);
        ctime.add("Full Day");
        ctime.add("Half Day");
        add(ctime);

        // Submit Button
        submit = new JButton("Submit");
        submit.setBounds(60, 350, 100, 25);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        submit.addActionListener(this);
        add(submit);

        // Cancel Button
        cancel = new JButton("Cancel");
        cancel.setBounds(200, 350, 100, 25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancel.addActionListener(this);
        add(cancel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String rollno = crollno.getSelectedItem();
            String date = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();
            String duration = ctime.getSelectedItem();

            String query = "INSERT INTO studentleave VALUES('" + rollno + "', '" + date + "', '" + duration + "')";

            try {
                Conn c = new Conn();
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Leave Confirmed");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error while submitting leave.");
            }

        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentLeave();
    }
}
