package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentLeaveDetails extends JFrame {

    JTable table;

    StudentLeaveDetails() {
        setSize(800, 600);
        setLocation(300, 100);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Title Label
        JLabel heading = new JLabel("Student Leave Details");
        heading.setBounds(300, 20, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        // Table column headers
        String[] columnNames = {"Roll No", "Date", "Duration"};
        String[][] data = new String[100][3];

        int i = 0;
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM studentleave";
            ResultSet rs = c.s.executeQuery(query);

            while (rs.next()) {
                data[i][0] = rs.getString("rollno");
                data[i][1] = rs.getString("date");
                data[i][2] = rs.getString("duration");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Table setup
        table = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 80, 700, 400);
        add(sp);

        setVisible(true);
    }

    public static void main(String[] args) {
        new StudentLeaveDetails();
    }
}
