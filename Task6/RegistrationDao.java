import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrationDao {
    String url = "jdbc:mysql://localhost:3306/eventdb";
    String user = "root";
    String pwd = "root";

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pwd);
    }

    void close(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Could not close the connection");
        }
    }

    boolean exists(String regNo, String eventName) throws SQLException {
        String sql = "SELECT id FROM registrations WHERE reg_no=? AND event_name=?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, regNo);
            ps.setString(2, eventName);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            close(con, ps, rs);
        }
    }

    void insert(Registration r) throws SQLException {
        String sql = "INSERT INTO registrations(reg_no, name, event_name) VALUES (?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, r.regNo);
            ps.setString(2, r.name);
            ps.setString(3, r.eventName);
            ps.executeUpdate();
        } finally {
            close(con, ps, null);
        }
    }

    ArrayList<Registration> findAll() throws SQLException {
        ArrayList<Registration> list = new ArrayList<Registration>();
        String sql = "SELECT reg_no, name, event_name FROM registrations";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Registration(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } finally {
            close(con, ps, rs);
        }
        return list;
    }

    ArrayList<Registration> findByEvent(String eventName) throws SQLException {
        ArrayList<Registration> list = new ArrayList<Registration>();
        String sql = "SELECT reg_no, name, event_name FROM registrations WHERE event_name=?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, eventName);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Registration(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } finally {
            close(con, ps, rs);
        }
        return list;
    }
}
