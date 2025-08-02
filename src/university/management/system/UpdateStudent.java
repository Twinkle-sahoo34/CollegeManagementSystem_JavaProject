package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class UpdateStudent extends JFrame implements ActionListener {

    Choice crollno;
    JTextField tfname, tffname, tfrollno, tfaddress, tfphone, tfemail, tfclassx, tfclassxii, tfaadhar, tfcourse, tfbranch;
    JDateChooser dateChooser;
    JButton submit, cancel;

    public UpdateStudent() {
        setSize(800, 600);
        setLocation(400, 150);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Update Student Details");
        heading.setBounds(250, 20, 300, 30);
        heading.setFont(new Font("serif", Font.ITALIC, 20));
        add(heading);

        JLabel lblroll = new JLabel("Select Roll No:");
        lblroll.setBounds(50, 80, 150, 20);
        lblroll.setFont(new Font("serif", Font.PLAIN, 18));
        add(lblroll);

        crollno = new Choice();
        crollno.setBounds(250, 80, 150, 25);
        add(crollno);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT rollno FROM student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(50, 120, 150, 20);
        lblname.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(lblname);

        tfname = new JTextField();
        tfname.setBounds(250, 120, 150, 25);
        add(tfname);

        JLabel lblfname = new JLabel("Father's Name:");
        lblfname.setBounds(450, 120, 150, 20);
        lblfname.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(lblfname);

        tffname = new JTextField();
        tffname.setBounds(600, 120, 150, 25);
        add(tffname);

        JLabel lblrollno = new JLabel("Roll Number:");
        lblrollno.setBounds(50, 160, 150, 20);
        lblrollno.setFont(new Font("serif", Font.BOLD, 18));
        add(lblrollno);

        tfrollno = new JTextField();
        tfrollno.setBounds(250, 160, 150, 25);
        tfrollno.setEditable(false);
        add(tfrollno);

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

        JLabel lblemail = new JLabel("Email Id:");
        lblemail.setBounds(50, 240, 150, 20);
        lblemail.setFont(new Font("serif", Font.BOLD, 18));
        add(lblemail);

        tfemail = new JTextField();
        tfemail.setBounds(250, 240, 150, 25);
        add(tfemail);

        JLabel lblclassx = new JLabel("Class X (%):");
        lblclassx.setBounds(450, 240, 150, 20);
        lblclassx.setFont(new Font("serif", Font.BOLD, 18));
        add(lblclassx);

        tfclassx = new JTextField();
        tfclassx.setBounds(600, 240, 150, 25);
        add(tfclassx);

        JLabel lblclassxii = new JLabel("Class XII (%):");
        lblclassxii.setBounds(50, 280, 150, 20);
        lblclassxii.setFont(new Font("serif", Font.BOLD, 18));
        add(lblclassxii);

        tfclassxii = new JTextField();
        tfclassxii.setBounds(250, 280, 150, 25);
        add(tfclassxii);

        JLabel lblaadhar = new JLabel("Aadhar Number:");
        lblaadhar.setBounds(450, 280, 150, 20);
        lblaadhar.setFont(new Font("serif", Font.BOLD, 18));
        add(lblaadhar);

        tfaadhar = new JTextField();
        tfaadhar.setBounds(600, 280, 150, 25);
        add(tfaadhar);

        JLabel lblcourse = new JLabel("Course:");
        lblcourse.setBounds(50, 320, 150, 20);
        lblcourse.setFont(new Font("serif", Font.BOLD, 18));
        add(lblcourse);

        tfcourse = new JTextField();
        tfcourse.setBounds(250, 320, 150, 25);
        add(tfcourse);

        JLabel lblbranch = new JLabel("Branch:");
        lblbranch.setBounds(450, 320, 150, 20);
        lblbranch.setFont(new Font("serif", Font.BOLD, 18));
        add(lblbranch);

        tfbranch = new JTextField();
        tfbranch.setBounds(600, 320, 150, 25);
        add(tfbranch);

        submit = new JButton("Update");
        submit.setBounds(250, 400, 120, 30);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Tahoma", Font.BOLD, 15));
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(400, 400, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancel.addActionListener(this);
        add(cancel);

        loadStudentDetails();
        crollno.addItemListener(e -> loadStudentDetails());

        setVisible(true);
    }

    private void loadStudentDetails() {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM student WHERE rollno = '" + crollno.getSelectedItem() + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tffname.setText(rs.getString("fname"));
                tfrollno.setText(rs.getString("rollno"));

                java.util.Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("dob"));
                dateChooser.setDate(dob);

                tfaddress.setText(rs.getString("address"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                tfclassx.setText(rs.getString("class_x"));
                tfclassxii.setText(rs.getString("class_xii"));
                tfaadhar.setText(rs.getString("aadhar"));
                tfcourse.setText(rs.getString("course"));
                tfbranch.setText(rs.getString("branch"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            try {
                String dob = new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate());

                String query = "UPDATE student SET " +
                        "name='" + tfname.getText() + "', " +
                        "fname='" + tffname.getText() + "', " +
                        "dob='" + dob + "', " +
                        "address='" + tfaddress.getText() + "', " +
                        "phone='" + tfphone.getText() + "', " +
                        "email='" + tfemail.getText() + "', " +
                        "class_x='" + tfclassx.getText() + "', " +
                        "class_xii='" + tfclassxii.getText() + "', " +
                        "aadhar='" + tfaadhar.getText() + "', " +
                        "course='" + tfcourse.getText() + "', " +
                        "branch='" + tfbranch.getText() + "' " +
                        "WHERE rollno='" + tfrollno.getText() + "'";

                Conn c = new Conn();
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Student Details Updated Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateStudent();
    }
}
