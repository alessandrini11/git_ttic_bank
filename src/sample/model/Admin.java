package sample.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Admin {
    private int id;
    private String pseudo;
    private String password;

    public Admin(int id, String pseudo, String password) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
    }

    public Admin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void connect(Admin admin, ActionEvent event){
        String login = "'"+admin.getPseudo()+"'";
        String password = "'"+admin.getPassword()+"'";

        if (login.equals("") && password.equals("")){
            String info = "remplissez tous les champs";
            alert(info);
        }else{
            Connection connection = Dbe.getConnection();
            String query = "SELECT * FROM admin WHERE pseudo ="+login+" and password ="+password+"";
            PreparedStatement statement = null;
            ResultSet resultSet;
            try {
                if (connection != null){
                    statement = connection.prepareStatement(query);
                    resultSet = statement.executeQuery();

                    if(resultSet.next()){
                        String info = "Bienvenu "+login+" à GIT/TTIC Bank";
                        alert(info);
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                        try{
                            Parent root = FXMLLoader.load(getClass().getResource("/sample/vue/sample.fxml"));
                            Parent createForm = FXMLLoader.load(getClass().getResource("/sample/vue/main.fxml"));
                            Scene create = new Scene(createForm);
                            Stage primaryStage = new Stage();
                            primaryStage.setTitle("GIT/TTIC Bank");
                            primaryStage.getIcons().add(new Image("/sample/icon.png"));
                            primaryStage.setScene(new Scene(root, 300, 275));
                            primaryStage.setScene(create);
                            primaryStage.show();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }else{
                        String info = "Pseudo ou Password Incorrect";
                        alert(info);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param query
     */
    private Statement executeQuery(String query){
        Connection connection = Dbe.getConnection();
        Statement statement = null;
        try {
            if (connection != null){
                statement = connection.createStatement();
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;

    }

    /**
     *
     * @param information
     */
    private void alert(String information){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("");
        alert.setContentText(information);
        alert.showAndWait();
    }
}
