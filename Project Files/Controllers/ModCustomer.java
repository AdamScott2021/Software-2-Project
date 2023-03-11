package Controllers;
import Database.DBCountries;
import Database.DBCustomers;
import Database.DBFirstLvlDivisions;
import Model.Countries;
import Model.Customer;
import Model.FirstLevelDivision;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * this is the modCustomer (Modify Customer) Class/Controller. it is used to modify all aspects of the selected customer
 */
public class ModCustomer implements Initializable {
    public ComboBox<FirstLevelDivision> divisionBox;
    public ComboBox<Countries> countryBox;
    private Customer newCust;
    private int index;
    public TextField modName;
    public TextField modAddress;
    public TextField modZip;
    public TextField modPhone;
    public TextField modID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(DBCountries.getAllCountries());
    }

    /**
     *     * this is the sendCust (Send Customer) method. it gets the information from the selected customer in the customer menu
     *      * and populates the form with the proper data.
     * @param customer
     * @param index
     */
    public void sendCust(Customer customer, int index) {
        newCust = customer;
        System.out.println(newCust.getDivisionID() + newCust.getCustomerID() + newCust.getCustomerName() + newCust.getDivision() + newCust.getCustomerAddress() + newCust.getPhoneNumber());
        System.out.println(customer.toString());
        this.index = index;
        System.out.println(modID.toString());
        System.out.println(modAddress.toString());
        System.out.println(modName.toString());
        System.out.println(modZip.toString());
        modID.setText(String.valueOf(newCust.getCustomerID()));
        modName.setText(String.valueOf(newCust.getCustomerName()));
        modAddress.setText(String.valueOf(newCust.getCustomerAddress()));
        modZip.setText(String.valueOf(newCust.getZipCode()));
        modPhone.setText(String.valueOf(newCust.getPhoneNumber()));
        countryBox.setItems(DBCountries.getAllCountries());
        divisionBox.setItems(DBFirstLvlDivisions.getByCountry(DBFirstLvlDivisions.getCountryID(newCust.getDivisionID())));

        countryBox.setValue(DBCountries.getCountry(newCust.getCountry()));
        divisionBox.setValue(DBFirstLvlDivisions.getFLD(newCust.getDivisionID()));
    }

    /**
     * this is the saveCust (Save Customer) method. it is used to save the information provided by the user and import it into the database.
     * @param actionEvent
     * @throws IOException
     */
    public void saveCust(ActionEvent actionEvent) throws IOException {

        try {
            String name;
            String address;
            String zip;
            String phone;
            int divisionID;
            int ID;
            ID = Integer.parseInt(modID.getText());
            name = modName.getText();
            address = modAddress.getText();
            zip = modZip.getText();
            phone = modPhone.getText();
            divisionID = divisionBox.getSelectionModel().getSelectedItem().getDivisionID();
            Customer customer = new Customer(ID, name, address, zip, phone, divisionID);
            DBCustomers.updateCustomer(customer);

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
     * this is the canCust (cancel customer) method. it is used to send the user back to the customer menu while also
     *      * cancelling the changes made to the selected customer.
     * @param actionEvent
     * @throws IOException
     */
    public void canCust(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Any changes you've made will not be saved, Continue?");
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
     * this is the countryBoxAction method. it populates the countryBox combo box with the appropriate values
     * @param actionEvent
     */
    public void countryBoxAction(ActionEvent actionEvent) {
        Countries c = countryBox.getSelectionModel().getSelectedItem();
        if (c != null) {
            divisionBox.setItems(DBFirstLvlDivisions.getByCountry(c.getCountryID()));
            divisionBox.setValue(null);
        }
    }
}
