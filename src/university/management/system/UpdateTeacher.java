package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateTeacher extends JFrame implements ActionListener {

    Choice cempid;
    JTextField tfname, tffname, tfempid, tfaddress, tfphone, tfemail, tfqualification, tfdepartment, tfadhar;
    JDateChooser dateChooser;
    JButton update, cancel;

    public UpdateTeacher() {
        setSize(800, 600);
        setLocation(400, 150);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Update Teacher Details");
        heading.setBounds(250, 20, 400, 30);
        heading.setFont(new Font("serif", Font.BOLD, 22));
        add(heading);

        JLabel lblempid = new JLabel("Select Emp ID:");
        lblempid.setBounds(50, 80, 150, 20);
        lblempid.setFont(new Font("serif", Font.PLAIN, 18));
        add(lblempid);

        cempid = new Choice();
        cempid.setBounds(250, 80, 150, 25);
        add(cempid);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT empid FROM teacher");
            while (rs.next()) {
                cempid.add(rs.getString("empid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(50, 120, 150, 20);
        lblname.setFont(new Font("serif", Font.BOLD, 18));
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(250, 120, 150, 25);
        add(tfname);

        JLabel lblfname = new JLabel("Father's Name:");
        lblfname.setBounds(450, 120, 150, 20);
        lblfname.setFont(new Font("serif", Font.BOLD, 18));
        add(lblfname);

        tffname = new JTextField();
        tffname.setBounds(600, 120, 150, 25);
        add(tffname);

        JLabel lbleid = new JLabel("Emp ID:");
        lbleid.setBounds(50, 160, 150, 20);
        lbleid.setFont(new Font("serif", Font.BOLD, 18));
        add(lbleid);

        tfempid = new JTextField();
        tfempid.setBounds(250, 160, 150, 25);
        tfempid.setEditable(false);
        add(tfempid);

        JLabel lbldob = new JLabel("Date of Birth:");
        lbldob.setBounds(450, 160, 150, 20);
        lbldob.setFont(new Font("serif", Font.BOLD, 18));
        add(lbldob);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(600, 160, 150, 25);
        dateChooser.setDateFormatString("yyyy-MM-dd");
        add(dateChooser);

        JLabel lbladdress = new JLabel("Address:");
        lbladdress.setBounds(50, 200, 150, 20);
        lbladdress.setFont(new Font("serif", Font.BOLD, 18));
        add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(250, 200, 150, 25);
        add(tfaddress);

        JLabel lblphone = new JLabel("Phone:");
        lblphone.setBounds(450, 200, 150, 20);
        lblphone.setFont(new Font("serif", Font.BOLD, 18));
        add(lblphone);

        tfphone = new JTextField();
        tfphone.setBounds(600, 200, 150, 25);
        add(tfphone);

        JLabel lblemail = new JLabel("Email:");
        lblemail.setBounds(50, 240, 150, 20);
        lblemail.setFont(new Font("serif", Font.BOLD, 18));
        add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(250, 240, 150, 25);
        add(tfemail);

        JLabel lblqual = new JLabel("Qualification:");
        lblqual.setBounds(450, 240, 150, 20);
        lblqual.setFont(new Font("serif", Font.BOLD, 18));
        add(lblqual);

        tfqualification = new JTextField();
        tfqualification.setBounds(600, 240, 150, 25);
        add(tfqualification);

        JLabel lbldept = new JLabel("Department:");
        lbldept.setBounds(50, 280, 150, 20);
        lbldept.setFont(new Font("serif", Font.BOLD, 18));
        add(lbldept);

        tfdepartment = new JTextField();
        tfdepartment.setBounds(250, 280, 150, 25);
        add(tfdepartment);

        JLabel lbladhar = new JLabel("Aadhar:");
        lbladhar.setBounds(450, 280, 150, 20);
        lbladhar.setFont(new Font("serif", Font.BOLD, 18));
        add(lbladhar);

        tfadhar = new JTextField();
        tfadhar.setBounds(600, 280, 150, 25);
        add(tfadhar);

        update = new JButton("Update");
        update.setBounds(250, 350, 120, 30);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        update.setFont(new Font("Tahoma", Font.BOLD, 15));
        update.addActionListener(this);
        add(update);

        cancel = new JButton("Cancel");
        cancel.setBounds(400, 350, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancel.addActionListener(this);
        add(cancel);

        loadTeacherDetails();
        cempid.addItemListener(e -> loadTeacherDetails());

        setVisible(true);
    }

    private void loadTeacherDetails() {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM teacher WHERE empid = '" + cempid.getSelectedItem() + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tffname.setText(rs.getString("fname"));
                tfempid.setText(rs.getString("empid"));
                dateChooser.setDate(java.sql.Date.valueOf(rs.getString("dob")));
                tfaddress.setText(rs.getString("address"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                tfqualification.setText(rs.getString("qualification"));
                tfdepartment.setText(rs.getString("department"));
                tfadhar.setText(rs.getString("aadhar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            try {
                String query = "UPDATE teacher SET " +
                        "name='" + tfname.getText() + "', " +
                        "fname='" + tffname.getText() + "', " +
                        "dob='" + ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText() + "', " +
                        "address='" + tfaddress.getText() + "', " +
                        "phone='" + tfphone.getText() + "', " +
                        "email='" + tfemail.getText() + "', " +
                        "qualification='" + tfqualification.getText() + "', " +
                        "department='" + tfdepartment.getText() + "', " +
                        "aadhar='" + tfadhar.getText() + "' " +
                        "WHERE empid='" + tfempid.getText() + "'";

                Conn c = new Conn();
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Teacher Details Updated Successfully");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateTeacher();
    }
}
