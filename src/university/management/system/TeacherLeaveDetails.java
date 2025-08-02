package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TeacherLeaveDetails extends JFrame {

    JTable table;

    TeacherLeaveDetails() {
        setSize(800, 600);
        setLocation(300, 100);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Heading
        JLabel heading = new JLabel("Teacher Leave Details");
        heading.setBounds(280, 20, 300, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        // Table column headers
        String[] columnNames = {"Teacher ID", "Date", "Duration"};
        String[][] data = new String[100][3];

        int i = 0;
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM teacherleave");

            while (rs.next() && i < 100) {
                data[i][0] = rs.getString("teacher_id");
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
        new TeacherLeaveDetails();
    }
}
