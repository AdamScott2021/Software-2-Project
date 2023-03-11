package Controllers;

import Database.DBAppointments;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.event.KeyEvent;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the Appointment Menu Class/Controller. its purpose is to define how to navigate the appointment form and
 * display all relevant appointment information as well as filtering appointments and searching them.
 */


public class AptMenu implements Initializable {

    private final DBAppointments apt = new DBAppointments();
    public TableView<Appointments> aptTable;
    public TableColumn<Appointments, Integer> aptIDCol;
    public TableColumn<Appointments, Integer> custIDCol;
    public TableColumn<Appointments, String> titleCol;
    public TableColumn<Appointments, String> descCol;
    public TableColumn<Appointments, String> locCol;
    public TableColumn<Appointments, Integer> contactCol;
    public TableColumn<Appointments, String> typeCol;
    public TableColumn<Appointments, Integer> userIDCol;
    public TableColumn<Appointments, LocalDateTime> startTimeCol;
    public TableColumn<Appointments, LocalDateTime> endTimeCol;

    public TextField searchApt;
    public static Boolean check;
    public static LocalDateTime oneWeek;
    public static RadioButton radioWeek;

    /** Lambda #2, it is used to filter appointments using a Text/Search field in the Appointments Menu.
     it does this by referencing the Customer ID supplied by the user and is activated by pressing enter.
     I chose to put the lambda here because i felt it was a good use of the onAction functionality.
     Additionally, it was a simple enough function to not over complicate, yet a good way to improve the code.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        aptTable.setItems(DBAppointments.getAllAppointments());
        aptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));



        searchApt.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                ObservableList<Appointments> appointmentSearch = FXCollections.observableArrayList();
                boolean errorFound = true;
                if (!searchApt.getText().trim().isEmpty()) {
                    try {
                        int search = Integer.parseInt(searchApt.getText());
                        for (Appointments aptSearch : DBAppointments.getAllAppointments()) {
                            if (aptSearch.getCustomerID() == search) {
                                errorFound = false;
                                appointmentSearch.add(aptSearch);
                                aptTable.setItems(appointmentSearch);
                                aptTable.getSelectionModel().select(aptSearch);
                            }
                        }
                    } catch (NumberFormatException e) {
                        String search = searchApt.getText();
                        for (Appointments aptSearch : DBAppointments.getAllAppointments()) {
                            if (aptSearch.getDescription().contains(search)) {
                                errorFound = false;
                                appointmentSearch.add(aptSearch);
                                aptTable.setItems(appointmentSearch);
                                aptTable.getSelectionModel().select(aptSearch);
                                break;
                            }
                        }
                    }
                    if (errorFound == true) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Search Error");
                        alert.setContentText("No matching results found");
                        alert.showAndWait();
                    }
                }
                else
                {
                    aptTable.setItems(DBAppointments.getAllAppointments());
                    return;
                }
            }
            });
        }

    /**
     * This is the toCustomer Method. It sends the user to the Customer Form when the button is clicked.
     * @param actionEvent
     * @throws IOException
     */

    public void toCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/CustMenu.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the modApt (Modify Appointment) method. its purpose is to send the user to the modify appointment form
     * when an existing appointment is selected.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void modApt(ActionEvent actionEvent) throws IOException, SQLException {
        check = false;
        if(aptTable.getSelectionModel().getSelectedItems().size() == 1) {
            int index = aptTable.getSelectionModel().getSelectedIndex();
            Object item = aptTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/HandleApt.fxml"));
            Parent parent = loader.load();
            HandleApt modController = loader.getController();
            modController.sendApt(aptTable.getSelectionModel().getSelectedItem(), index);
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Appointment");
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert allert = new Alert(Alert.AlertType.CONFIRMATION);
            allert.setTitle("Error");
            allert.setContentText("Please Select an Appointment to Modify");
            Optional<ButtonType> r = allert.showAndWait();
        }
    }

    /**
     * this is the schedApt (Schedule Appointment) Method. This method sends the user to the Schedule Appointment form
     * @param actionEvent
     * @throws IOException
     */

    public void schedApt(ActionEvent actionEvent) throws IOException {
        check = true;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/HandleApt.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Create Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the RadWeek Method, it filters appointments in the appointment form tableview to only display appointments for the current week.
     * @param actionEvent
     * @throws ParseException
     * @throws SQLException
     */
    public void radWeek(ActionEvent actionEvent) throws ParseException, SQLException {
            aptTable.setItems(DBAppointments.getAptsForWeek());
        }

    /**
     * this is the radMonth Method, it filters appointments in the appointment form tableview to only display appointments for the current month.
     * @param actionEvent
     */
    public void radMonth(ActionEvent actionEvent)
    {
        aptTable.setItems(DBAppointments.getAptsForMonth());
    }

    /**
     *  this is the radAll method, it filters appointments in the appointment form tableview to display all appointments in the database.
     * @param actionEvent
     */
    public void radAll(ActionEvent actionEvent) {
        aptTable.setItems(DBAppointments.getAllAppointments());
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (((Optional<?>) result).isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/LoginScreen.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("View/Login");
            stage.setScene(scene);
            stage.show();

        }
    }

    /**
     * this is the delApt (Delete Appointment) method, it deletes the selected appointment from the database.
     * @param actionEvent
     * @throws SQLException
     */
    public void delApt(ActionEvent actionEvent) throws SQLException {
        if (aptTable.getSelectionModel().getSelectedItems().size() == 1) {
            Appointments aptA = aptTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment?");
            alert.setContentText("Are you sure you want to delete Appointment # " + aptA.getAppointmentID() + "? it is a(n) " + aptA.getType() + " Type Appointment.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){

                if (apt.deleteAppointment(aptTable.getSelectionModel().getSelectedItem())) ;
                {
                    aptTable.getItems().remove(aptTable.getSelectionModel().getSelectedItem());
                }
            }
        }
        else {
            Alert allert = new Alert(Alert.AlertType.CONFIRMATION);
            allert.setTitle("Delete Appointment?");
            allert.setContentText("Please Select an Appointment to Delete");
            Optional<ButtonType> r = allert.showAndWait();
        }
    }

    /**
     * this is the toReports Method. it sends the user to the reports form.
     * @param actionEvent
     * @throws IOException
     */
    public void toReports(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/Reports.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
}
