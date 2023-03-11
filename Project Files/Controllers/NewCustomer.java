package Controllers;
import Database.DBCountries;
import Database.DBCustomers;
import Database.DBFirstLvlDivisions;
import Model.Countries;
import Model.FirstLevelDivision;
import javafx.fxml.FXML;
import Model.Customer;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * this is the newCustomer class/comtroller. it is used to control all aspects of the new customer form.
 */

public class NewCustomer implements Initializable {
    public ComboBox<FirstLevelDivision> divisionBox;
    public ComboBox<Countries> countryBox;
    public TextField customerName;
    public TextField customerAddress;
    public TextField zipCode;
    public TextField phoneNumber;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBCountries.getAllCountries());
    }

    /**
     * this is the saveCustomer method. it is used to save all the provided information by the user and import it into the database.
     * @param actionEvent
     * @throws IOException
     */
    public void saveCustomer(ActionEvent actionEvent) throws IOException {
        try {
            String zip;
            String name;
            String address;
            String phone;
            int division;
            name = customerName.getText();
            address = customerAddress.getText();
            zip = zipCode.getText();
            phone = phoneNumber.getText();
            division = divisionBox.getSelectionModel().getSelectedItem().getDivisionID();
            System.out.println(division);
            Customer customer = new Customer(name, address, zip, phone, division);
            DBCustomers.addCustomer(customer);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ALERT");
            alert.setContentText("One or more of the provided text fields are either empty or do not contain the correct data type.");
            alert.showAndWait();
            return;

        }

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
     * this is the canCust (cancel customer) method. it is used to send the user back to the previous form as well as
     * remove all changes made to the new customer.
     * @param actionEvent
     * @throws IOException
     */
    public void canCust(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will not save any changes you've made, Cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/CustMenu.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Customer Menu");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * this is the countryBoxAction method. it populates the countryBox combo box with the appropriate values. division box
     * is populated based on the selection of this combo box.
     * @param actionEvent
     */
    public void countryBoxAction(ActionEvent actionEvent) {
            Countries c = countryBox.getSelectionModel().getSelectedItem();
            if (c != null) {
                divisionBox.setItems(DBFirstLvlDivisions.getByCountry(c.getCountryID()));
        }
    }
}
