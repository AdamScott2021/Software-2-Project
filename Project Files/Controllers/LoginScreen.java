package Controllers;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import Database.DBCustomers;
import Database.DBUsers;
import Main.Main;
import Localization.FrenchResourceBundle;
import Database.DBAppointments;
import Model.Customer;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * this is the LoginScreen class/controller. it controls all aspects of the login screen
 */

public class LoginScreen implements Initializable {
    public static Users loggedInUser;
    public static String loginButton;
    public TextField userName;
    public TextField passWord;
    @FXML
    private Label lsLabel;
    @FXML
    private Label lsLabel2;
    @FXML
    private Label unLabel;
    @FXML
    private Label pwLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Button loginButtonB;
    @FXML
    private Button closePgm;
    @FXML
    private Label locationLabel2;

    ResourceBundle rb = ResourceBundle.getBundle("Localization/ResourceBundle");

    /**
     * Lambda #3. it is used to login and verify/authenticate users. the lambda is attached to the login button and is activated upon clicking
     * the login button. I chose to use a lambda here because i felt it was a good use of the onAction functionality. Additionally, since you
     * only use this button once (to login), i felt it was an appropriate place to put it and it was a good option on how to improve the code.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String bundleName = "/Localization/FrenchResourceBundle.getContents";
        lsLabel.setText(rb.getString("Login"));
        lsLabel2.setText(rb.getString("Screen"));
        unLabel.setText(rb.getString("UserName"));
        pwLabel.setText(rb.getString("Password"));
        loginButtonB.setText(rb.getString("Login"));
        closePgm.setText(rb.getString("Exit"));
        String tz = TimeZone.getDefault().toZoneId().toString();
        locationLabel.setText("Location: " + tz);

        loginButtonB.setOnAction(actionEvent -> {
            if (actionEvent.getSource() == loginButtonB) {
                Locale locale = Locale.getDefault();
                String username = userName.getText();
                String password = passWord.getText();

                try {
                    if (DBUsers.verifyLogin(username, password)) {
                        DBAppointments.getSoonApts(username);
                        File logins = new File("login_activity.txt");
                        FileWriter fw = new FileWriter(logins, true);
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println("Successful Login");
                        pw.write(username + " ");
                        pw.write(password + " ");
                        pw.write(String.valueOf(LocalDateTime.now()));
                        pw.println();
                        pw.println("-----------------------------------------");
                        pw.close();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/FXML/AptMenu.fxml"));
                        Parent parent = loader.load();
                        Scene scene = new Scene(parent);
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setTitle("Appointment View");
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        File logins = new File("login_activity.txt");
                        FileWriter fw = new FileWriter(logins, true);
                        PrintWriter pw = new PrintWriter(fw);
                        pw.println("Failed Login");
                        pw.write(username + " ");
                        pw.write(password + " ");
                        pw.write(String.valueOf(LocalDateTime.now()));
                        pw.println();
                        pw.println("-----------------------------------------");
                        pw.close();
                        if (locale.getLanguage().equals("fr")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Alerte");
                            alert.setContentText("La connexion a échoué, le nom d'utilisateur et/ou le mot de passe sont incorrects.");
                            alert.showAndWait();
                            return;
                        } else if (locale.getLanguage().equals("en")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("ALERT");
                            alert.setContentText("Login Failed, UserName and/or Password are incorrect.");
                            alert.showAndWait();
                            return;
                        }
                    }

                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * this is the closePgm method. it is used to close and exit the application.
     *
     * @param actionEvent
     */
    public void closePgm(ActionEvent actionEvent) {
        ((Stage) (((Node) actionEvent.getSource()).getScene().getWindow())).close();
    }

}
