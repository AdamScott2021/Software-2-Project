package Database;

import Model.Appointments;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * this is the DBReports class. it is used to gather all information from other parts of the DB to populate the reports form.
 */
public class DBReports {


    public static ObservableList<Appointments> getContactAppointments(int contID) {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from appointments WHERE Contact_ID = '" + contID + "'";
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
     * this is the getContactID method. it is used to get the ID of the selected contact. data provided by the user.
     * @param name
     * @return
     */
    public static int getContactID(String name) {
        ObservableList<Contacts> getContactID = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Contact_ID from contacts WHERE Contact_Name = '" + name + "'";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                Contacts C = new Contacts(contactID);
                getContactID.add(C);
                return contactID;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }

    /**
     * this is the getType method. it is used to get the types of appointments from the DB to populate a combo box.
     * @return
     */
    public static ObservableList<Appointments> getType() {
        ObservableList<Appointments> getType = FXCollections.observableArrayList();
        try{
            String sql = "SELECT DISTINCT type FROM appointments";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String type = rs.getString("Type");
                Appointments C = new Appointments(type);
                getType.add(C);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return getType;
    }

    /**
     * this is the getAptCount method, it is used to get the number of appointments in the DB used in reports.
     * @param monthStart
     * @param monthEnd
     * @param type
     * @return
     */
    public static int getAptCount(LocalDate monthStart , LocalDate monthEnd, String type) {
        ObservableList<Appointments> getAptCount = FXCollections.observableArrayList();
        try{
            String sql = "SELECT COUNT(*) FROM appointments WHERE Start >= '" + monthStart + "' AND START <= '" + monthEnd + "' AND Type = '" + type + "'";
            System.out.println(sql);
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
            return rs.getInt(1);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * this is the getMonthDates method. it is used to get month names and set them to the proper date ranges. used in the reports form.
     * @param c
     * @return
     */
    public static LocalDate getMonthDates(String c) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int month = 0;
        if(c == "January"){
            month = 1;
        }
       else if(c == "February"){
            month = 2;
        }
       else if(c == "March"){
            month =3;
        }
       else if(c == "April"){
            month = 4;
        }
       else if(c == "May"){
            month = 5;
        }
        else if(c == "June"){
            month = 6;
        }
        else if(c == "July"){
            month = 7;
        }
        else if(c == "August"){
            month = 8;
        }
        else if(c == "September"){
            month = 9;
        }
        else if(c == "October"){
            month = 10;
        }
        else if(c == "November"){
            month = 11;
        }
        else if(c == "December"){
            month = 12;
        }
        LocalDate monthStart = LocalDate.of(currentYear, month, 1);

        return monthStart;
    }

    /**
     * this is the getAllTimeCount method. it is used to get the number of appointments currently scheduled. used in the reports form.
     * @return
     */
    public static int getAllTimeCount() {
        ObservableList<Appointments> getAptCount = FXCollections.observableArrayList();
        try{
            String sql = "SELECT COUNT(*) FROM appointments";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                return rs.getInt(1);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }


}


