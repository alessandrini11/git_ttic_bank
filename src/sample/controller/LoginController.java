package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import sample.model.Admin;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Button connexion;

    public void handler(ActionEvent event){
        if (event.getSource() == connexion){
            String user = login.getText();
            String pass = password.getText();
            int id = 1;
            Admin admin = new Admin(id,user,pass);
            admin.connect(admin,event);

        }
    }
}
