package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("vue/sample.fxml"));
        Parent createForm = FXMLLoader.load(getClass().getResource("vue/login.fxml"));
        Scene create = new Scene(createForm);
        primaryStage.setTitle("GIT/TTIC Bank");
        primaryStage.getIcons().add(new Image("/sample/icon.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setScene(create);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
