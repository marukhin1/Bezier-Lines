package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static public Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Bezier Lines");
        stage.setResizable(false);
        stage.setScene(scene);
        primaryStage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
