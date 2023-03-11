package Model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * this is the Appointments Class/Model. it is used to pass all information regarding appointments to the proper place.
 */

public class Appointments extends Reports {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private int contactID;
    private int customerID;
    private int userID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Appointments(int appointmentID, String title, String description, String location, String type, int contactID,
                        int customerID, int userID, LocalDateTime startTime, LocalDateTime endTime) {


        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.contactID = contactID;
        this.customerID = customerID;
        this.userID = userID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Appointments(int appointmentID, String title, String description, String location, String type, int contactID, String customerID, int usersID, LocalTime aptStartTime, LocalTime aptEndTime) {
        super();
    }

    public Appointments(String type) {
        this.type = type;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(String aptType) {
        this.contactID = contactID;
    }

    public LocalDateTime getStartTime() { return startTime; }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString(){
        return type;
    }

}
