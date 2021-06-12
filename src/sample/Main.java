package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("vue/sample.fxml"));
        Parent createForm = FXMLLoader.load(getClass().getResource("vue/main.fxml"));
        Scene create = new Scene(createForm);
        primaryStage.setTitle("GIT/TTIC Bank");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setScene(create);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
