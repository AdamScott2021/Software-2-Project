package Database;

import Model.Contacts;
import Model.Countries;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  * this is the DBCustomers class. it is used to get any information regarding customers from the DB
 */
public class DBCustomers {

    /**
     * this is the addCustomer method. it is used to add customer information provided by the user into the DB
     * @param customer
     * @return
     */
    public static int addCustomer(Customer customer) {
        String sql =
                "INSERT INTO customers(" + "Customer_Name, Address, Postal_Code, Phone, Division_ID, Create_Date, Last_Update, Created_By, Last_Updated_By) " +
                        "VALUES(?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?)";
        try {
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setString(3, customer.getZipCode());
            ps.setString(4, customer.getPhoneNumber());
            ps.setInt(5, customer.getDivisionID());
            ps.setString(6, DBUsers.getLoggedInUser().getUserName());
            ps.setString(7, DBUsers.getLoggedInUser().getUserName());
            ps.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * this is the getAllCustomers method. it is used to get all customers and all information pertaining to them from the DB
     * @return
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from customers";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerZip = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                String division1 = DBFirstLvlDivisions.getDivisionName(divisionID);
                int countryID = DBFirstLvlDivisions.getCountryIDNum(divisionID);
                String country1 = DBCountries.getCountryName(countryID);
                String division = String.valueOf(division1);
                String country = String.valueOf(country1);

                Customer C = new Customer(customerID, customerName, customerAddress, customerZip, customerPhone, country, division, divisionID);
                allCustomers.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allCustomers;
    }

    /**
     * this is the updateCustomer method. it is used to update the DB with any changed user provided data in the form
     * @param selectedCustomer
     */
    public static void updateCustomer(Customer selectedCustomer) {
        try {
            String sql = "UPDATE customers " +
                    "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_Update = CURRENT_TIMESTAMP(), Last_Updated_By = ?" +
                    "WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, selectedCustomer.getCustomerName());
            ps.setString(2, selectedCustomer.getCustomerAddress());
            ps.setString(3, selectedCustomer.getZipCode());
            ps.setString(4, selectedCustomer.getPhoneNumber());
            ps.setInt(5, selectedCustomer.getDivisionID());
            ps.setString(6, DBUsers.getLoggedInUser().getUserName());
            ps.setInt(7, selectedCustomer.getCustomerID());


            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    /**
     * this is the getCustomerID method. it is used to get the customer ID based on the selected customer name
     * @param customerName
     * @return
     */
    public static ObservableList<Customer> getCustomerID(String customerName) {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Customer_ID FROM customers WHERE Customer_Name = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            System.out.println(sql);
            ps.setString(1, customerName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String customerName1 = rs.getString("Customer_Name");
                Customer C = new Customer(customerName1);
                allCustomers.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * this is thegetCustomer method. it is used to get a specific customer from the DB based on customer ID
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static Customer getCustomer(int customerID) throws SQLException {
        Customer C = null;
        String sql = "SELECT * from customers WHERE Customer_ID = " + customerID;
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            String CustomerName = null;
            CustomerName = rs.getString("Customer_Name");
            int customerID2 = rs.getInt("Customer_ID");
            C = new Customer(customerID2, CustomerName);
        }
        return C;
    }

    /**
     * this is the deleteCustomer method. it is used to delete the selected customer from the DB
     * as well as any appointments associated with that customer.
     * @param selectedCustomer
     * @return
     */
    public boolean deleteCustomers (Customer selectedCustomer) {
            String aptCheck = "DELETE * FROM appointments WHERE Customer_ID = " + selectedCustomer.getCustomerID();
            String deleteQuery = "DELETE FROM customers " + "WHERE Customer_ID = ?";
            try {
                PreparedStatement appointmentCheck = DBConnection.connection.prepareStatement(aptCheck);
                PreparedStatement preparedStatement = DBConnection.connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, selectedCustomer.getCustomerID());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
}
