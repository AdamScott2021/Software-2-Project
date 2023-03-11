package Main;
import Controllers.HandleApt;
import Database.DBAppointments;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Database.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.util.Locale;

/**
 * this is the main method. it is used to initialize the program.
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/LoginScreen.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 260, 350));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
            HandleApt.createLBH();
            DBConnection.openConnection();
          //  DBAppointments.getOverlapApts(LocalDateTime.now(), LocalDateTime.now());
            launch(args);
            DBConnection.closeConnection();
        }
    }



