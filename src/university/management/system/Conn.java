
package university.management.system;

import java.sql.*;

public class Conn {
    public Connection con;  // <-- make public so accessible outside
    public Statement s;    // <-- make public too

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/universitymanagementsystem",
                "root",
                "mysql:<nej5sJ");
            s = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


