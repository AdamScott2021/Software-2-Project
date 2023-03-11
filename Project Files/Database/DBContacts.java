package Database;

import Controllers.HandleApt;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this is the DBContacts class. it is used to get any information regarding contacts from the DB
 */
public class DBContacts {
    /**
     * this is the getAllContacts class. it is used to get all contacts from the DB
     * @return
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from contacts";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Contacts C = new Contacts(contactID, contactName);
                allContacts.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allContacts;
    }

    /**
     * this is the getContact class. it is used to get a specific contact from the DB based on their ID
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static Contacts getContact (int contactID) throws SQLException {
        Contacts C = null;
        String sql = "SELECT * from contacts WHERE Contact_ID = " + contactID;
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            String ContactName = null;
            ContactName = rs.getString("Contact_Name");
            int contactID2 = rs.getInt("Contact_ID");
            C = new Contacts(contactID2, ContactName);
        }
        return C;
    }
}
