package Database;

import Model.Appointments;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import static java.time.temporal.TemporalAdjusters.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * this is the DBAppointments Class. it is used to handle all aspects of sending information to and from the database regarding
 * appointments
 */
public class DBAppointments {

    boolean checkID;

    /**
     * this is the getAllAppointments method. it is used to get all appointments from the DB
     * @return
     */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                int appointmentID = rs.getInt("Appointment_ID");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                Appointments C = new Appointments(appointmentID, title, description, location, type, contactID, customerID, userID, start, end);
                allAppointments.add(C);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * this is the getSoonApts method. it is used upon login to get appointments within 15 minutes of login.
     * @param username
     * @return
     * @throws SQLException
     */
    public static boolean getSoonApts(String username) throws SQLException {
        boolean check = false;
        try {
            ObservableList<Appointments> appointments = FXCollections.observableArrayList();
            String startQuery = "SELECT * FROM appointments WHERE User_ID = (SELECT User_ID FROM users WHERE User_Name = '" + username + "') AND Start >= '" + LocalDateTime.now(ZoneOffset.UTC) + "' AND Start <= DATE_ADD('" + LocalDateTime.now(ZoneOffset.UTC) + "', INTERVAL 15 MINUTE)";
            System.out.println(startQuery);
            PreparedStatement ss = DBConnection.connection.prepareStatement(startQuery);
            ResultSet ts = ss.executeQuery();

            while (ts.next()) {

                String startTime = ts.getString("Start");
                int aptID = ts.getInt("Appointment_ID");
                String title = ts.getString("Title");
                String description = ts.getString("Description");
                String location = ts.getString("Location");
                String type = ts.getString("Type");
                int appointmentID = ts.getInt("Appointment_ID");
                int customerID = ts.getInt("Customer_ID");
                int userID = ts.getInt("User_ID");
                int contactID = ts.getInt("Contact_ID");
                LocalDateTime start = ts.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = ts.getTimestamp("End").toLocalDateTime();
                Appointments C = new Appointments(appointmentID, title, description, location, type, contactID, customerID, userID, start, end);
                appointments.add(C);
                check = true;
                if (check) {
                    Alert allert = new Alert(Alert.AlertType.INFORMATION);
                    allert.setTitle("Appointment Notification");
                    allert.setContentText("You have an appointment within 15 minutes starting at " + start.toLocalTime() + " with Appointment ID: " + appointmentID);
                    allert.showAndWait();
                }

                return check;
            }
            if (!check) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Notification");
                alert.setContentText("You dont have any upcoming appointments");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    /**
     * this is the getAptsForWeek method. it is used to get all appointments for the current week using a radio button
     * @return
     * @throws ParseException
     * @throws SQLException
     */
    public static ObservableList<Appointments> getAptsForWeek() throws ParseException, SQLException {
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        LocalDate today = LocalDate.now();
        if (DBAppointments.getWeekNumber(today) != 0) {
            LocalDate todaysDate = LocalDate.now();
            LocalDate start1 = todaysDate.with(DayOfWeek.MONDAY).minusDays(1);
            LocalDate end1 = start1.plusDays(7);

            try {
                String startQuery = "SELECT * FROM appointments WHERE Start >= '" + start1 + "' AND Start < '" + end1 + "'";
                System.out.println(startQuery);
                PreparedStatement ss = DBConnection.connection.prepareStatement(startQuery);
                ResultSet ts = ss.executeQuery();
                while (ts.next()) {

                    String title = ts.getString("Title");
                    String description = ts.getString("Description");
                    String location = ts.getString("Location");
                    String type = ts.getString("Type");
                    int appointmentID = ts.getInt("Appointment_ID");
                    int customerID = ts.getInt("Customer_ID");
                    int userID = ts.getInt("User_ID");
                    int contactID = ts.getInt("Contact_ID");
                    LocalDateTime start = ts.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime end = ts.getTimestamp("End").toLocalDateTime();
                    Appointments C = new Appointments(appointmentID, title, description, location, type, contactID, customerID, userID, start, end);
                    appointments.add(C);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return appointments;
    }

    /**
     * this is the getWeekNumber method. it is used to get the number of the current week within the year. this is used in conjunction
     * with the above method to retrieve appointments within the current week.
     * @param today
     * @return
     * @throws SQLException
     */
    private static int getWeekNumber(LocalDate today) throws SQLException {
        String startQuery = "SELECT WEEK('" + today + "')";
        PreparedStatement ss = DBConnection.connection.prepareStatement(startQuery);
        ResultSet ts = ss.executeQuery();
        while(ts.next()){
            return ts.getInt(1);
        }

        return 0;
    }

    /**
     * this is the getAptsForMonth method. it is used to get all appointments for the current Month using a radio button
     * @return
     */
    public static ObservableList<Appointments> getAptsForMonth() {

        LocalDate initial = LocalDate.now();
        LocalDate start1 = initial.withDayOfMonth(1);
        LocalDate end1 = initial.withDayOfMonth(initial.lengthOfMonth());
        LocalDate end2 = end1.plusDays(1);
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();

        try {
            String startQuery = "SELECT * FROM appointments WHERE Start >= '" + start1 + "' AND Start < '" + end2 + "'";
            System.out.println(startQuery);
            PreparedStatement ss = DBConnection.connection.prepareStatement(startQuery);
            ResultSet ts = ss.executeQuery();
            while (ts.next()) {

                String title = ts.getString("Title");
                String description = ts.getString("Description");
                String location = ts.getString("Location");
                String type = ts.getString("Type");
                int appointmentID = ts.getInt("Appointment_ID");
                int customerID = ts.getInt("Customer_ID");
                int userID = ts.getInt("User_ID");
                int contactID = ts.getInt("Contact_ID");
                LocalDateTime start = ts.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = ts.getTimestamp("End").toLocalDateTime();
                Appointments C = new Appointments(appointmentID, title, description, location, type, contactID, customerID, userID, start, end);
                appointments.add(C);
            }
        } catch (SQLException e) {

        }
        return appointments;
    }

    /**
     * this is the addAppointment method. it is used to send all user provided information in the form to the DB
     * @param appointments
     */
    public static void addAppointment(Appointments appointments) {

        try {
            String sql =
                    "INSERT INTO appointments(" + "Title, Description, Location, Type, Create_Date, Last_Update, Start, End, Customer_ID, User_ID, Contact_ID, Created_By, Last_Updated_By) " +
                            "VALUES(?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
            ps.setString(1, appointments.getTitle());
            ps.setString(2, appointments.getDescription());
            ps.setString(3, appointments.getLocation());
            ps.setString(4, appointments.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appointments.getStartTime()));
            ps.setTimestamp(6, Timestamp.valueOf(appointments.getEndTime()));
            ps.setInt(7, appointments.getCustomerID());
            ps.setInt(8, appointments.getUserID());
            ps.setInt(9, appointments.getContactID());
            ps.setString(10, DBUsers.getLoggedInUser().getUserName());
            ps.setString(11, DBUsers.getLoggedInUser().getUserName());
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * this is the updateAppointment method. it is used to update the selected apointment with all relevant and changed data
     * provided by the user into the DB
     * @param selectedAppointment
     */
    public static void updateAppointment(Appointments selectedAppointment) {

        try {
            String sql =

                    "UPDATE appointments " +
                            "SET Title = ?, Description = ?, Type = ?, Location = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?, " +
                            "Last_Update = CURRENT_TIMESTAMP(), Start = ?, End = ?, Last_Updated_By = ? WHERE Appointment_ID = ?";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, selectedAppointment.getTitle());
            ps.setString(2, selectedAppointment.getDescription());
            ps.setString(3, selectedAppointment.getType());
            ps.setString(4, selectedAppointment.getLocation());
            ps.setInt(5, selectedAppointment.getCustomerID());
            ps.setInt(6, selectedAppointment.getUserID());
            ps.setInt(7, selectedAppointment.getContactID());
            ps.setTimestamp(8, Timestamp.valueOf(selectedAppointment.getStartTime()));
            ps.setTimestamp(9, Timestamp.valueOf(selectedAppointment.getEndTime()));
            ps.setString(10, DBUsers.getLoggedInUser().getUserName());

            ps.setInt(11, selectedAppointment.getAppointmentID());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    /**
     * this is the deleteAppointment method. it is used to delete the selected appointment
     * @param selectedAppointment
     * @return
     * @throws SQLException
     */
    public static boolean deleteAppointment(Appointments selectedAppointment) throws SQLException {
        String deleteQuery = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ss = DBConnection.connection.prepareStatement(deleteQuery);
        try {
            PreparedStatement preparedStatement = DBConnection.connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, selectedAppointment.getAppointmentID());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * this is thegetOverlapApts method, it is used to check for any appointment overlaps currently in the DB
     * @param start
     * @param end
     * @param appointmentID
     * @return
     */
    public static boolean getOverlapApts(LocalDateTime start, LocalDateTime end, int appointmentID) {
        boolean anything = false;
        try {
            String overlapQuery = "SELECT * FROM appointments WHERE (Appointment_ID != ? AND ((Start = ? OR End = ?) OR (Start > ? AND Start < ?) OR (End > ? AND End < ?) OR " +
                    "(? < Start AND ? > End) OR (? > Start AND ? < End)))";

            PreparedStatement ss = DBConnection.connection.prepareStatement(overlapQuery);
            ss.setInt(1, appointmentID);
            ss.setTimestamp(2, Timestamp.valueOf(start));
            ss.setTimestamp(3, Timestamp.valueOf(end));
            ss.setTimestamp(4, Timestamp.valueOf(start));
            ss.setTimestamp(5, Timestamp.valueOf(end));
            ss.setTimestamp(6, Timestamp.valueOf(start));
            ss.setTimestamp(7, Timestamp.valueOf(end));
            ss.setTimestamp(8, Timestamp.valueOf(start));
            ss.setTimestamp(9, Timestamp.valueOf(end));
            ss.setTimestamp(10, Timestamp.valueOf(start));
            ss.setTimestamp(11, Timestamp.valueOf(end));
            System.out.println(ss);
            ResultSet ts = ss.executeQuery();

            if (ts.next()) {
                anything = true;
            }

        }
        catch (SQLException e) {

        }
        return anything;
    }

    /**
     * this is the getContactAppointments method. it is used to gather all contact appointments and displays them in the reports tableview
     * @return
     */
    public static ObservableList<Appointments> getContactAppointments() {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                int appointmentID = rs.getInt("Appointment_ID");
                int customerID= rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                Appointments C = new Appointments(appointmentID, title, description, location, type, contactID, customerID, userID, start, end);
                allAppointments.add(C);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }
}





