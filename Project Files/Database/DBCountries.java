package Database;

import Model.Countries;
import Model.FirstLevelDivision;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.*;

/**
 * this is the DBCountries class. it is used to get any information regarding countries from the DB
 */
public class DBCountries {


    public  static  ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countryList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from Countries";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryID, countryName);
                countryList.add(C);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return countryList;
    }

    /**
     * this is the getCountry class. it gets the selected country based on its ID
     * @param country
     * @return
     */
    public static Countries getCountry (String country) {
        Countries C = null;

        try {
            String sql = "SELECT Country from countries WHERE Country = '" + country + "'";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println(country);

            while (rs.next())
            {
                C = new Countries(country);
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return C;
    }

    public static String getCountryName(int countryID) throws SQLException {

        try {

            String sql = "SELECT Country from countries WHERE Country_ID = " + countryID;
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String country = rs.getString("Country");
                return country;

            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return "X";
    }
}

