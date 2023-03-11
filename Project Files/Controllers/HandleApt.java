package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import Model.Contacts;
import Database.DBUsers;
import Database.*;
import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * this is the HandleApt (Handle Appointment) Class/Controller. it is used to modify the selected appointment in the appointment menu
 * start and end times in this form are populated based on business hours in EST and converted to your systems TimeZone
 */

public class HandleApt implements Initializable {
    @FXML
    public Label endDateDisplay;
    String apt = "New Appointment";
    String mod = "Modify Appointment";
    public ComboBox<Contacts> contactsBox;
    public Label aptLabel;
    public ComboBox <Customer>custBox;
    public ComboBox<Users>userBox;
    public TextField userID;
    public TextField aptID;
    public TextField locationField;
    public TextField typeField;
    public TextField descriptionField;
    public TextField titleField;
    private Appointments newApt;
    private Customer newCust;
    public int contactID;
    public int index;
    public Label endDate;
    public Contacts contact;
    public ComboBox<LocalTime> startTime;
    public ComboBox<LocalTime> endTime;
    public DatePicker datePicker;
    public LocalTime aptStartTime;
    public LocalTime aptEndTime;
    public LocalDate aDate;
    private static ObservableList<LocalTime> openHours = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> closeHours = FXCollections.observableArrayList();
    private static LocalTime openHoursEST = LocalTime.of(8,0);
    private static LocalTime closeHoursEST = LocalTime.of(22,0);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTime.setItems(getOpenHours());
        endTime.setItems(getCloseHours());
        custBox.setItems(DBCustomers.getAllCustomers());
        contactsBox.setItems(DBContacts.getAllContacts());
        userBox.setItems(DBUsers.getAllUsers());
        if (AptMenu.check == false) {
            aptLabel.setText(mod);
        } else if (AptMenu.check == true) {
            aptLabel.setText(apt);

        }

    }

    /**
     * this is the sendApt (Send Appointment) method. it gets the information from the selected appointment in the appointment menu
     * and populates the form with the proper data.
     * @param appointments
     * @param index
     * @throws SQLException
     */
    public void sendApt(Appointments appointments, int index) throws SQLException {

        newApt = appointments;
        this.index = index;
        userBox.setItems(DBUsers.getAllUsers());
        userBox.setValue(DBUsers.getUser(newApt.getUserID()));
        custBox.setItems(DBCustomers.getAllCustomers());
        custBox.setValue(DBCustomers.getCustomer(newApt.getCustomerID()));
        contactsBox.setItems(DBContacts.getAllContacts());
        contactsBox.setValue(DBContacts.getContact(newApt.getContactID()));
        aptID.setText(String.valueOf(newApt.getAppointmentID()));
        titleField.setText(String.valueOf(newApt.getTitle()));
        locationField.setText(String.valueOf(newApt.getLocation()));
        descriptionField.setText(String.valueOf(newApt.getDescription()));
        typeField.setText(String.valueOf(newApt.getType()));
        startTime.getSelectionModel().select(newApt.getStartTime().toLocalTime());
        endTime.getSelectionModel().select(newApt.getEndTime().toLocalTime());
        datePicker.setValue(newApt.getStartTime().toLocalDate());
        endDateDisplay.setText(newApt.getStartTime().toLocalDate().toString());
    }

    /**
     * this is the saveButton method in the HandleApt menu. It is used to save a new appointment or modify an existing one. this method controls both forms.
     * @param actionEvent
     * @throws IOException
     */
    public void saveButton(ActionEvent actionEvent) throws IOException {
        try {
            String title;
            String description;
            String location;
            String type;
            int usersID;
            int contactID;
            int customerID;
            int appointmentID;
            title = titleField.getText();
            description = descriptionField.getText();
            location = locationField.getText();
            if (aptID.getText().isEmpty()) {
                appointmentID = 0;
            } else {
                appointmentID = Integer.parseInt(aptID.getText());
            }
            type = typeField.getText();
            customerID = custBox.getSelectionModel().getSelectedItem().getCustomerID();
            usersID = userBox.getSelectionModel().getSelectedItem().getUserID();
            contactID = contactsBox.getSelectionModel().getSelectedItem().getContactID();
            aDate = datePicker.getValue();
            aptEndTime = endTime.getValue();
            aptStartTime = startTime.getValue();
            LocalDateTime bDate;
            bDate = LocalDateTime.of(aDate, aptStartTime);

                if (aptStartTime.equals(aptEndTime)) {
                    Alert alertC = new Alert(Alert.AlertType.ERROR);
                    alertC.setTitle("Time Error");
                    alertC.setContentText("Start and End times are the same");
                    Optional<ButtonType> resultB = alertC.showAndWait();
                    if (resultB.get() == ButtonType.OK) {
                        return;
                    }
                }
                if (aptEndTime.isBefore(aptStartTime)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Time Error");
                    alert.setContentText("End Time cannot be before Start Time");
                    alert.showAndWait();
                    return;
                }
                if (bDate.isBefore(LocalDateTime.now())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Date Error");
                    alert.setContentText("Selected Date is before Current Date");
                    alert.showAndWait();
                    return;
                }
                LocalDateTime start = LocalDateTime.of(aDate, aptStartTime);
                LocalDateTime end = LocalDateTime.of(aDate, aptEndTime);

                if (titleField.getText().isEmpty() || descriptionField.getText().isEmpty() || typeField.getText().isEmpty() || locationField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Appointment Error");
                    alert.setContentText("One or more of the given fields are empty and require user input, please try again");
                    alert.showAndWait();
                    return;
                }
                Appointments appointments = new Appointments(appointmentID, title, description, location, type, contactID, customerID, usersID, start, end);

                if (DBAppointments.getOverlapApts(start, end, appointmentID)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Search Error");
                    alert.setContentText("This appointment conflicts with another previously scheduled appointment");
                    alert.showAndWait();
                    return;
                } else {
                    if (AptMenu.check == false) {

                        DBAppointments.updateAppointment(appointments);

                    } else if (AptMenu.check) {

                        DBAppointments.addAppointment(appointments);
                    }
                }

        } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment Error");
                alert.setContentText("One or more of the given fields are empty and require user input, please try again");
                alert.showAndWait();
                return;

        }
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/AptMenu.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Appointment Menu");
            stage.setScene(scene);
            stage.show();

    }

    /**
     * this is the cancelButton method. it is used to send the user back to the customer menu while also
     * cancelling the creating of a new customer.
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButton (ActionEvent actionEvent) throws IOException {

            Alert alertB = new Alert(Alert.AlertType.CONFIRMATION);
            alertB.setTitle("Time Error");
            alertB.setContentText("Are you sure you want to cancel? Any changes will not be saved");
            Optional<ButtonType> resultB = alertB.showAndWait();
            if (resultB.get() == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FXML/AptMenu.fxml"));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Appointment Menu");
                stage.setScene(scene);
                stage.show();
            }

            else
            {
                return;
            }
    }

    /**
     * this is the contactsBoxAction method. it sets the contacts combo box in the form with selectable contact names.
     * @param actionEvent
     */
        public void contactsBoxAction (ActionEvent actionEvent){
            contact = contactsBox.getSelectionModel().getSelectedItem();
        }
    /**
     * this is the setLabel method. it overrides the toString method.
     * @param actionEvent
     */

        @FXML
        public void setLabel (ActionEvent actionEvent){
            endDateDisplay.setText(datePicker.getValue().toString());
        }

    /**
     * this is the createLBH (create local business hours) method. it is used to populate the start and end time boxes with
     * the proper business hours in EST converted into your systems time zone
     */
    public static void createLBH(){
            openHours.clear();
            closeHours.clear();
            ZonedDateTime startZDT = ZonedDateTime.of(LocalDate.now(), openHoursEST, ZoneId.of("America/New_York"));
            ZonedDateTime closeZDT = ZonedDateTime.of(LocalDate.now(), closeHoursEST, ZoneId.of("America/New_York"));
            LocalDateTime start = startZDT.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime end = closeZDT.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            System.out.println(start);
            while(start.isBefore(end)){
                openHours.add(LocalTime.from(LocalDateTime.from(start)));
                start = start.plusMinutes(60);
                closeHours.add(LocalTime.from(LocalDateTime.from(start)));
            }
    }
    public static ObservableList<LocalTime> getOpenHours(){
        if(openHours.size() == 0 || closeHours.size() == 0){
            createLBH();
        }
        return openHours;
    }
    public static ObservableList<LocalTime> getCloseHours(){
        if(openHours.size() == 0 || closeHours.size() == 0){
            createLBH();
        }
        return closeHours;
    }
}

