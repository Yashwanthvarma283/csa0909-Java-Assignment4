import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeeService {
    String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "root";
    String pwd = "root";
    String lastReceipt = "No payment made yet";

    String validate(String regNo, String amountText) {
        if (regNo.trim().equals("")) {
            return "Enter the register number";
        }
        if (amountText.trim().equals("")) {
            return "Enter the amount";
        }
        try {
            double a = Double.parseDouble(amountText);
            if (a <= 0) {
                return "Amount must be more than zero";
            }
        } catch (NumberFormatException e) {
            return "Amount must be a number";
        }
        return "OK";
    }

    String pay(String regNo, String amountText) {
        String check = validate(regNo, amountText);
        if (!check.equals("OK")) {
            return check;
        }
        double amount = Double.parseDouble(amountText);
        String sql = "INSERT INTO fees(reg_no, amount) VALUES (?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DriverManager.getConnection(url, user, pwd);
            ps = con.prepareStatement(sql);
            ps.setString(1, regNo);
            ps.setDouble(2, amount);
            ps.executeUpdate();
            lastReceipt = "Receipt: " + regNo + " paid Rs " + amount;
            return "Payment successful";
        } catch (SQLException e) {
            return "Payment failed: " + e.getMessage();
        } finally {
            try {
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
    }
}
