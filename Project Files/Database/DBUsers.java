package Database;

import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * this is the DBUsers Class. it is used to gather all information about Users from the DB
 */

public class DBUsers {
    public static Users loggedInUser;
    public int userID;
    public String userName;
    public String passWord;
    public LocalDateTime create_Date;
    public String last_Update;
    public String created_By;
    public String lastUpdatedBy;

    /**
     * this is the getAllUsers method. it is used to get all users from the DB to supply to the program.
     * @return
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> userList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from users";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String passWord = rs.getString(("Password"));
                String created_By = rs.getString("Created_By");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                Users C = new Users(userID, userName, passWord, created_By, lastUpdatedBy);
                userList.add(C);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return userList;
    }

    /**
     * this is the verifyLogin method. it verifies the provided user input with information in the DB allowing a user to login
     * to the program or to be declined access.
     * @param username
     * @param password
     * @return
     */
    public static boolean verifyLogin(String username, String password) {
        try {

            String query = "SELECT User_ID, User_Name, Password FROM users ";

            PreparedStatement ps = DBConnection.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userID = rs.getInt("User_ID");

                if(username.equals(rs.getString("User_Name")) &&
                        password.equals(rs.getString("Password"))) {
                    loggedInUser = new Users(userID, username);
                    return true;
                }
            }
        } catch(SQLException se) {

        }
        return false;
    }

    /**
     * this is the getLoggedInUser method. it is used to get the name of the currently logged in user.
     * @return
     */
    public static Users getLoggedInUser(){
        return loggedInUser;
    }

    /**
     * this is the getUser method. it is used to get a specific user from the DB
     * @param userID
     * @return
     * @throws SQLException
     */
    public static Users getUser(int userID) throws SQLException {

            Users C = null;
            String sql = "SELECT * from users WHERE User_ID = " + userID;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                String UserName;
                UserName = rs.getString("User_Name");
                int userID2 = rs.getInt("User_ID");
                C = new Users(userID2, UserName);
            }
            return C;
        }
}
