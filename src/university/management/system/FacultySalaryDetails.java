package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FacultySalaryDetails extends JFrame implements ActionListener {

    Choice cfid;
    JTextField tfname, tfdepartment, tfposition, tfsalary;
    JComboBox<String> cbmonth, cbyear;
    JButton btnUpdate, btnPay, btnBack;

    // DB connection info - UPDATE TO YOUR CONFIG
    private static final String DB_URL = "jdbc:mysql://localhost:3306/universitydb";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";

    public FacultySalaryDetails() {
        setSize(900, 500);
        setLocation(300, 100);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Faculty Salary Details");
        title.setBounds(300, 20, 300, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 26));
        add(title);

        // Faculty ID Selection
        JLabel lblFid = new JLabel("Select Faculty ID:");
        lblFid.setBounds(40, 80, 150, 25);
        lblFid.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblFid);

        cfid = new Choice();
        cfid.setBounds(200, 80, 150, 25);
        add(cfid);

        loadFacultyIDs();

        // Name Label + TextField (readonly)
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(40, 120, 150, 25);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblName);

        tfname = new JTextField();
        tfname.setBounds(200, 120, 200, 25);
        tfname.setEditable(false);
        add(tfname);

        // Department Label + TextField (readonly)
        JLabel lblDept = new JLabel("Department:");
        lblDept.setBounds(40, 160, 150, 25);
        lblDept.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblDept);

        tfdepartment = new JTextField();
        tfdepartment.setBounds(200, 160, 200, 25);
        tfdepartment.setEditable(false);
        add(tfdepartment);

        // Position Label + TextField (readonly)
        JLabel lblPosition = new JLabel("Position:");
        lblPosition.setBounds(40, 200, 150, 25);
        lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblPosition);

        tfposition = new JTextField();
        tfposition.setBounds(200, 200, 200, 25);
        tfposition.setEditable(false);
        add(tfposition);

        // Month Dropdown
        JLabel lblMonth = new JLabel("Month:");
        lblMonth.setBounds(40, 240, 150, 25);
        lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblMonth);

        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        cbmonth = new JComboBox<>(months);
        cbmonth.setBounds(200, 240, 150, 25);
        add(cbmonth);

        // Year Dropdown
        JLabel lblYear = new JLabel("Year:");
        lblYear.setBounds(40, 280, 150, 25);
        lblYear.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblYear);

        String[] years = new String[10];
        int yearNow = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < years.length; i++) {
            years[i] = Integer.toString(yearNow - 5 + i);
        }
        cbyear = new JComboBox<>(years);
        cbyear.setBounds(200, 280, 150, 25);
        add(cbyear);

        // Salary Label + TextField (readonly)
        JLabel lblSalary = new JLabel("Salary Amount:");
        lblSalary.setBounds(40, 320, 150, 25);
        lblSalary.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblSalary);

        tfsalary = new JTextField();
        tfsalary.setBounds(200, 320, 150, 25);
        tfsalary.setEditable(false);
        add(tfsalary);

        // Buttons
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(40, 380, 100, 30);
        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.addActionListener(this);
        add(btnUpdate);

        btnPay = new JButton("Pay Salary");
        btnPay.setBounds(160, 380, 120, 30);
        btnPay.setBackground(Color.BLACK);
        btnPay.setForeground(Color.WHITE);
        btnPay.addActionListener(this);
        add(btnPay);

        btnBack = new JButton("Back");
        btnBack.setBounds(300, 380, 100, 30);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(this);
        add(btnBack);

        // Load faculty details on selection
        cfid.addItemListener(e -> loadFacultyDetails());

        if (cfid.getItemCount() > 0) {
            cfid.select(0);
            loadFacultyDetails();
        }

        setVisible(true);
    }

    private void loadFacultyIDs() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT faculty_id FROM faculty");

            while (rs.next()) {
                cfid.add(rs.getString("faculty_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading faculty IDs");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }

    private void loadFacultyDetails() {
        String fid = cfid.getSelectedItem();
        if (fid == null) return;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            String sql = "SELECT * FROM faculty WHERE faculty_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, fid);
            rs = ps.executeQuery();

            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tfdepartment.setText(rs.getString("department"));
                tfposition.setText(rs.getString("position"));
                tfsalary.setText("");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading faculty details");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnUpdate) {
            String dept = tfdepartment.getText();
            String pos = tfposition.getText();

            if (dept.isEmpty() || pos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Department or Position not found");
                return;
            }

            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                String query = "SELECT amount FROM salary WHERE department = ? AND position = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, dept);
                ps.setString(2, pos);

                rs = ps.executeQuery();

                if (rs.next()) {
                    String amount = rs.getString("amount");
                    tfsalary.setText(amount);
                } else {
                    tfsalary.setText("0");
                    JOptionPane.showMessageDialog(null, "Salary details not found for this department and position");
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error fetching salary data");
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception ignored) {}
                try { if (ps != null) ps.close(); } catch (Exception ignored) {}
                try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            }

        } else if (ae.getSource() == btnPay) {
            String fid = cfid.getSelectedItem();
            String month = (String) cbmonth.getSelectedItem();
            String year = (String) cbyear.getSelectedItem();
            String dept = tfdepartment.getText();
            String pos = tfposition.getText();
            String amount = tfsalary.getText();

            if (fid == null || amount.isEmpty() || amount.equals("0")) {
                JOptionPane.showMessageDialog(null, "Please update salary first before paying.");
                return;
            }

            Connection conn = null;
            PreparedStatement ps = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                String insertSQL = "INSERT INTO facultysalary (faculty_id, month, year, department, position, amount) VALUES (?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(insertSQL);
                ps.setString(1, fid);
                ps.setString(2, month);
                ps.setString(3, year);
                ps.setString(4, dept);
                ps.setString(5, pos);
                ps.setString(6, amount);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Salary paid successfully.");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error processing salary payment.");
            } finally {
                try { if (ps != null) ps.close(); } catch (Exception ignored) {}
                try { if (conn != null) conn.close(); } catch (Exception ignored) {}
            }

        } else if (ae.getSource() == btnBack) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new FacultySalaryDetails();
    }
}

