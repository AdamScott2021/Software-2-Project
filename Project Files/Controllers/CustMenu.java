package Controllers;

import Database.DBCustomers;
import Model.Customer;
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
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * this is the CustMenu Class/controller. it enables the user to navigate the customer form adn control all elements within.
 */
public class CustMenu implements Initializable {

    private DBCustomers customer = new DBCustomers();
    public TableView<Customer> custTable;
    public TableColumn<Customer, Integer> idCol;
    public TableColumn<Customer, String> nameCol;
    public TableColumn<Customer, String> addressCol;
    public TableColumn<Customer, String> zipCol;
    public TableColumn<Customer, String> phCol;
    public TableColumn<Customer, Integer> divCol;
    public TableColumn<Customer, Integer> countryCol;
    public TableColumn<Customer, Integer> divIDCol;
    public TextField searchCust;

    /** Lambda #1, it is used to filter Customers using a Text/Search field in the Customer Menu.
     it does this by referencing the Customer ID supplied by the user and is activated by pressing enter
     I chose to put the lambda here because i felt it was a good use of the onAction functionality.
     Additionally, it was a simple enough function to not over complicate, yet a good way to improve the code.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custTable.setItems(DBCustomers.getAllCustomers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        phCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        searchCust.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                ObservableList<Customer> customerSearch = FXCollections.observableArrayList();
                boolean errorFound = true;

                if (!searchCust.getText().trim().isEmpty()) {
                    try {
                        int search = Integer.parseInt(searchCust.getText());
                        for (Customer custSearch : DBCustomers.getAllCustomers()) {
                            if (custSearch.getCustomerID() == search) {
                                errorFound = false;
                                customerSearch.add(custSearch);
                                custTable.setItems(customerSearch);
                                custTable.getSelectionModel().select(custSearch);
                            }
                        }
                    } catch (NumberFormatException e) {
                        String search = searchCust.getText();
                        for (Customer custSearch : DBCustomers.getAllCustomers()) {
                            if (custSearch.getCustomerName().contains(search)) {
                                errorFound = false;
                                customerSearch.add(custSearch);
                                custTable.setItems(customerSearch);
                                custTable.getSelectionModel().select(custSearch);
                                break;
                            }
                        }
                    }
                } else {
                    custTable.setItems(DBCustomers.getAllCustomers());
                    return;
                }
            }

        });
    }

    /**
     * This is the newCust (New Customer) method. it is used to send the user to the new customer form where a customer
     * can be added into the database.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void newCust(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/NewCustomer.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("New Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * this is the back method. it is used to send the user back to the appointment menu
     *
     * @param actionEvent
     * @throws IOException
     */
    public void back(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/AptMenu.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment View");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * this is the modCust (Modify Customer) method, it is used to modify the currently selected customer in the
     * modify form.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void modCust(ActionEvent actionEvent) throws IOException {

        if (custTable.getSelectionModel().getSelectedItems().size() == 1) {
            int index = custTable.getSelectionModel().getSelectedIndex();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/ModifyCustomer.fxml"));
            Parent parent = loader.load();
            ModCustomer modController = loader.getController();
            modController.sendCust(custTable.getSelectionModel().getSelectedItem(), index);
            System.out.println(custTable.getSelectionModel().getSelectedItem().getCountry());
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("View/Modify Customer");
            stage.setScene(scene);
            stage.show();
        } else if (custTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modify Customer");
            alert.setContentText("Please select a customer to modify");
            alert.showAndWait();
        }
    }

    /**
     * this is the delCust (Delete Customer) method. it is used to delete the currently selected customer and also deletes all appointments
     * associated with that customer.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void delCust(ActionEvent actionEvent) throws IOException {

        if (custTable.getSelectionModel().getSelectedItems().size() == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setContentText("Are you sure you would like to delete this customer? This will also delete any associated appointments with this Customer");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (customer.deleteCustomers(custTable.getSelectionModel().getSelectedItem())) ;
                {
                    custTable.getItems().remove(custTable.getSelectionModel().getSelectedItem());
                }
            } else {
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Customer");
            alert.setContentText("Please select a Customer to delete");
            alert.showAndWait();
        }
    }
}
