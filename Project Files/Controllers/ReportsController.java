package Controllers;

import Database.DBAppointments;
import Database.DBContacts;
import Database.DBFirstLvlDivisions;
import Database.DBReports;
import Model.Appointments;
import Model.Contacts;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


/**
 * this is the reports controller. it is used to control all aspects of the reports form.
 */

public class ReportsController implements Initializable {

    public TableView<Appointments> contactSchedule;
    public TableColumn<ReportsController, Integer> contactIDCol;
    public TableColumn<ReportsController, Integer> aptIDCol;
    public TableColumn<ReportsController, Integer> custIDCol;
    public TableColumn<ReportsController, Integer> userIDCol;
    public TableColumn<ReportsController, String> titleCol;
    public TableColumn<ReportsController, String> locCol;
    public TableColumn<ReportsController, String> descCol;
    public TableColumn<ReportsController, String> typeCol;
    public TableColumn<ReportsController, LocalDateTime> startCol;
    public TableColumn<ReportsController, LocalDateTime> endCol;
    public Label numApts;
    public ComboBox<String> monthBox;
    public ComboBox<Contacts> contactBox;
    public ComboBox<Appointments> typeBox;
    public Label reportLabel;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableList(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        aptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        contactBox.setItems(DBContacts.getAllContacts());
        monthBox.setItems(list);
        reportLabel.setText("3) Total Number of Appointments Currently Scheduled: " + DBReports.getAllTimeCount());
        }

    /**
     * this is the toApts (To Appointments) method. it sends the user back to the appointments form
     * * @param actionEvent
     * @throws IOException
     */
    public void toApts(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/AptMenu.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * this is the month select box. it is used to populate the month combo box with the proper values and populates the type
     * box with values based on the selected month.
     * @param actionEvent
     */
    public void monthSelect(ActionEvent actionEvent) {
        String c = monthBox.getSelectionModel().getSelectedItem();
        if (c != null) {
            typeBox.setItems(DBReports.getType());
        }
    }

    /**
     * this is the typeSelect method. it is used to populate the typeBox combo box and set the report to the proper value
     * @param actionEvent
     */
    public void typeSelect(ActionEvent actionEvent) {
        String c = monthBox.getSelectionModel().getSelectedItem();
        LocalDate monthStart = DBReports.getMonthDates(c);
        LocalDate monthEnd = monthStart.plusMonths(1);
        String type = String.valueOf(typeBox.getSelectionModel().getSelectedItem());
        //DBReports.getAptCount(monthStart,monthEnd, type);
        numApts.setText("Number of appointments by Month and Type: " + DBReports.getAptCount(monthStart, monthEnd, type));
    }

    /**
     * this is the contactSelect method. it is used to populate the contact box with the proper values as well as the tableview
     * with the appropriate schedule
     * @param actionEvent
     */
    public void contactSelect(ActionEvent actionEvent) {
        String name = contactBox.getSelectionModel().getSelectedItem().toString();
        int contID = DBReports.getContactID(name);
        Contacts C = contactBox.getSelectionModel().getSelectedItem();
        if (C != null) {
            contactSchedule.setItems(DBReports.getContactAppointments(contID));
        }
    }
}
