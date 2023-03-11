package Model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * this is the reports Class/Model. it is used to pass all information regarding reports to the proper place.
 */
public class Reports {
    int contactID;
    int appointmentID;
    int customerID;
    int userID;
    String title;
    String type;
    String location;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;


    public Reports(int appointmentID, String title, String description, String location, String type, int contactID, int customerID, int userID, LocalDateTime startTime, LocalDateTime endTime) {

        this.appointmentID = appointmentID;
        this.contactID = contactID;
        this.customerID = customerID;
        this.title = title;
        this.type = type;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.userID = userID;
    }

    public Reports() {
    }

    public int getAptID() {return  appointmentID;}

    public void setAptID(int aptID) { this.appointmentID = aptID; }

    public int getCustomerID(){return customerID;}

    public void setCustID(int custID) { this.customerID = custID; }

    public int getContactID(){return contactID;}

    public void setContactID(int contactID) { this.contactID = contactID; }

    public String getTitle(){return title;}

    public void setTitle(String title) { this.title = title; }

    public String getType(){return type;}

    public void setType(String type) { this.type = type; }

    public String getDescription(){return description;}

    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartTime(){return LocalDateTime.from(startTime);}

    public void setStartTime(LocalDateTime startTime) { this.startTime = (startTime); }

    public LocalDateTime getEndTime(){return LocalDateTime.from(endTime);}

    public void setEndTime(LocalDateTime endTime) { this.endTime = (endTime); }



}
