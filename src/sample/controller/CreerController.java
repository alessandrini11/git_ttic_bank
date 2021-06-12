package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.model.Compte;

import javafx.event.ActionEvent;

public class CreerController {
    @FXML
    private TableView<Compte> tvcompte;
    @FXML
    private TextField nom;
    @FXML
    private TextField solde;
    @FXML
    private Button creer;

    public void creercompte(ActionEvent event){
        if(event.getSource() == creer){
            Compte compte = new Compte();
            double montont = Double.parseDouble(solde.getText());
            compte.createCompte(nom.getText(),montont);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("");
            alert.setContentText("Le nouveau client "+nom.getText()+" a été enregistré");
            alert.showAndWait();
        }
    }

}
