package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;

    static Stage getStage () {
        return stage;
    }

    @Override
    public void start (Stage primaryStage) throws Exception {
        stage = primaryStage;

        BorderPane root = FXMLLoader.load (Main.class.getResource ("main.fxml"));

        stage.setMinWidth (600);
        stage.setMinHeight (400);
        stage.setTitle ("Dictionary Writer");
        stage.setScene (new Scene (root, 800, 600));
        stage.show();
    }


    public static void main (String[] args) {
        launch(args);
    }
}
