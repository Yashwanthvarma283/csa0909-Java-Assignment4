import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrationService {
    RegistrationDao dao = new RegistrationDao();

    String register(String regNo, String name, String eventName) {
        if (regNo.trim().equals("")) {
            return "Enter the register number";
        }
        if (name.trim().equals("")) {
            return "Enter the name";
        }
        try {
            if (dao.exists(regNo, eventName)) {
                return "Already registered for this event";
            }
            dao.insert(new Registration(regNo, name, eventName));
            return "Registered successfully";
        } catch (SQLException e) {
            return "Database error: " + e.getMessage();
        }
    }

    ArrayList<Registration> list(String eventName) throws SQLException {
        if (eventName.equals("All")) {
            return dao.findAll();
        }
        return dao.findByEvent(eventName);
    }
}
