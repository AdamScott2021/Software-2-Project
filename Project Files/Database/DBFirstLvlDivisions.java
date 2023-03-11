package Database;

import Model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this is the getFirstLvlDivisions class. it is used to get all information pertaining to FLD's in the DB
 */
public class DBFirstLvlDivisions {

    /**
     * this is the getAllFLD method. it is used to get all first level divisions from the DB.
     * @return
     */
    public static ObservableList<FirstLevelDivision> getAllFLD() {
        ObservableList<FirstLevelDivision> fldList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from First_Level_Divisions";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                FirstLevelDivision C = new FirstLevelDivision(divisionID, division);
                fldList.add(C);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return fldList;
    }

    /**
     * this is the getFLD method. it is used to get all information pertaining to a specific FLD (first level division)
     * based on the division ID
     * @param Division_ID
     * @return
     */
    public static FirstLevelDivision getFLD(int Division_ID) {
        FirstLevelDivision C = null;

        try {
            String sql = "SELECT * from first_level_divisions WHERE Division_ID = " + Division_ID;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                String division = rs.getString("Division");
                int divisionID = rs.getInt("Division_ID");
                C = new FirstLevelDivision(divisionID, division);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return C;
    }

    /**
     * this is the getCountryID method. it is used to get the country id based on the provided division ID
     * @param Division_ID
     * @return
     */
    public static int getCountryID(int Division_ID) {
        int CountryID = 0;
        try {
            String sql = "SELECT Country_ID from first_level_divisions WHERE Division_ID = " + Division_ID;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                CountryID = rs.getInt("Country_ID");

            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return CountryID;
    }

    /**
     * this is the getByCountry method. it is used to get all first level divisions in the country based on the selected country ID
     * @param countryCode
     * @return
     */
    public static ObservableList<FirstLevelDivision> getByCountry(int countryCode){
        ObservableList<FirstLevelDivision> fldList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from first_level_divisions WHERE Country_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, countryCode);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                FirstLevelDivision C = new FirstLevelDivision(divisionID, division);
                fldList.add(C);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return fldList;
    }

    public static String getDivisionName(int divisionID) {
        try {

            String sql = "SELECT Division from first_level_divisions WHERE Division_ID = " + divisionID;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String division = rs.getString("Division");
                return division;

            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return "X";
    }
    public static int getCountryIDNum(int divisionID) throws SQLException {

        try {

            String sql = "SELECT Country_ID from first_level_divisions WHERE Division_ID = " + divisionID;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                return countryID;

            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return 0;
    }
}

