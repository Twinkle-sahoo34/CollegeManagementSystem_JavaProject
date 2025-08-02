package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentFeeForm extends JFrame implements ActionListener {
    Choice crollno;
    JComboBox<String> cbcourse, cbbranch, cbsemester;
    JTextField tfname, tffname, tfcourse, tfbranch, tfsemester, tftotal;
    JButton update, pay, back;

    // DB connection info - CHANGE THESE TO YOUR DB CONFIG
    private static final String DB_URL = "jdbc:mysql://localhost:3306/universitydb"; // your DB URL
    private static final String DB_USERNAME = "root"; // your DB username
    private static final String DB_PASSWORD = "password"; // your DB password

    public StudentFeeForm() {
        setSize(900, 500);
        setLocation(300, 100);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/fee.jpg"));
        Image i2 = i1.getImage().getScaledInstance(400, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 50, 500, 300);
        add(image);

        // Roll Number Label + Choice
        JLabel lblrollnumber = new JLabel("Select Roll Number:");
        lblrollnumber.setBounds(40, 40, 150, 20);
        lblrollnumber.setFont(new Font("serif", Font.PLAIN, 20));
        add(lblrollnumber);

        crollno = new Choice();
        crollno.setBounds(200, 40, 150, 25);
        add(crollno);

        // Load roll numbers from DB
        loadRollNumbers();

        // Name Label + TextField
        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(40, 80, 150, 20);
        lblname.setFont(new Font("serif", Font.BOLD, 18));
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(200, 80, 150, 25);
        tfname.setEditable(false);
        add(tfname);

        // Father's Name Label + TextField
        JLabel lblfname = new JLabel("Father's Name:");
        lblfname.setBounds(40, 120, 150, 20);
        lblfname.setFont(new Font("serif", Font.BOLD, 18));
        add(lblfname);

        tffname = new JTextField();
        tffname.setBounds(200, 120, 150, 25);
        tffname.setEditable(false);
        add(tffname);

        // Course Label + ComboBox + TextField
        JLabel lblcourse = new JLabel("Course:");
        lblcourse.setBounds(40, 160, 150, 20);
        lblcourse.setFont(new Font("serif", Font.BOLD, 18));
        add(lblcourse);

        String[] courseOptions = {"B.Tech", "BBA", "BCA", "Bsc", "Msc", "MBA", "MCA", "MCom", "MA", "BA"};
        cbcourse = new JComboBox<>(courseOptions);
        cbcourse.setBounds(200, 160, 150, 25);
        add(cbcourse);

      

        // Branch Label + ComboBox + TextField
        JLabel lblbranch = new JLabel("Branch:");
        lblbranch.setBounds(40, 200, 150, 20);
        lblbranch.setFont(new Font("serif", Font.BOLD, 18));
        add(lblbranch);

        String[] branchOptions = {"Computer Science", "Electronics", "Mechanical", "Civil", "IT"};
        cbbranch = new JComboBox<>(branchOptions);
        cbbranch.setBounds(200, 200, 150, 25);
        add(cbbranch);

        
        // Semester Label + ComboBox + TextField
        JLabel lblsemester = new JLabel("Semester:");
        lblsemester.setBounds(40, 240, 150, 20);
        lblsemester.setFont(new Font("serif", Font.BOLD, 18));
        add(lblsemester);

        String[] semesterOptions = {
            "semester1", "semester2", "semester3", "semester4",
            "semester5", "semester6", "semester7", "semester8"
        };
        cbsemester = new JComboBox<>(semesterOptions);
        cbsemester.setBounds(200, 240, 150, 25);
        add(cbsemester);


        // Total Fee Label + TextField (non-editable)
        JLabel lbltotal = new JLabel("Total Payable:");
        lbltotal.setBounds(40, 280, 150, 20);
        lbltotal.setFont(new Font("serif", Font.BOLD, 18));
        add(lbltotal);

        tftotal = new JTextField();
        tftotal.setBounds(200, 280, 150, 25);
        tftotal.setEditable(false);
        add(tftotal);

        // Buttons
        update = new JButton("Update");
        update.setBounds(40, 340, 100, 30);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        update.setFont(new Font("Tahoma", Font.BOLD, 16));
        update.addActionListener(this);
        add(update);

        pay = new JButton("Pay Fee");
        pay.setBounds(160, 340, 100, 30);
        pay.setBackground(Color.BLACK);
        pay.setForeground(Color.WHITE);
        pay.setFont(new Font("Tahoma", Font.BOLD, 16));
        pay.addActionListener(this);
        add(pay);

        back = new JButton("Back");
        back.setBounds(280, 340, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 16));
        back.addActionListener(this);
        add(back);

        // Load student details when rollno changes
        crollno.addItemListener(e -> loadStudentDetails());

        // Load first student details by default
        if (crollno.getItemCount() > 0) {
            crollno.select(0);
            loadStudentDetails();
        }

        setVisible(true);
    }

    private void loadRollNumbers() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load driver
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT rollno FROM student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading roll numbers from DB");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }

    private void loadStudentDetails() {
        String roll = crollno.getSelectedItem();
        if (roll == null) return;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load driver
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String sql = "SELECT * FROM student WHERE rollno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, roll);
            rs = ps.executeQuery();

            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tffname.setText(rs.getString("fname"));

                String course = rs.getString("course");
                cbcourse.setSelectedItem(course);
                tfcourse.setText(course);

                String branch = rs.getString("branch");
                cbbranch.setSelectedItem(branch);
                tfbranch.setText(branch);

                // clear semester textfield & set combo
                tfsemester.setText("");
                cbsemester.setSelectedIndex(0);

                tftotal.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading student details");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String course = (String) cbcourse.getSelectedItem();
            String semester = (String) cbsemester.getSelectedItem();

            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                // Assuming fee table has course, semester and amount columns
                String query = "SELECT amount FROM fee WHERE course = ? AND semester = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, course);
                ps.setString(2, semester);
                rs = ps.executeQuery();

                if (rs.next()) {
                    String amount = rs.getString("amount");
                    tftotal.setText(amount);
                } else {
                    tftotal.setText("0");
                    JOptionPane.showMessageDialog(null, "Fee data not found for selected course and semester");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching fee data");
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception ignored) {}
                try { if (ps != null) ps.close(); } catch (Exception ignored) {}
                try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            }

        } else if (ae.getSource() == pay) {
            String rollno = crollno.getSelectedItem();
            String course = tfcourse.getText().trim();
            String semester = (String) cbsemester.getSelectedItem();
            String branch = tfbranch.getText().trim();
            String total = tftotal.getText().trim();

            if (rollno == null || total.isEmpty() || total.equals("0")) {
                JOptionPane.showMessageDialog(null, "Please select roll number and update fee first");
                return;
            }

            Connection conn = null;
            PreparedStatement ps = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                String sql = "INSERT INTO collegefee (rollno, course, semester, branch, total) VALUES (?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, rollno);
                ps.setString(2, course);
                ps.setString(3, semester);
                ps.setString(4, branch);
                ps.setString(5, total);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "College fee submitted successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error submitting fee");
            } finally {
                try { if (ps != null) ps.close(); } catch (Exception ignored) {}
                try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentFeeForm();
    }
}
