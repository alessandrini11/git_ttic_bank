package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.model.Compte;

public class ModifierController {
    @FXML
    public TextField titulaire;
    @FXML
    private TextField id;
    @FXML
    private TextField debitmax;
    @FXML
    private TextField decouvert;
    @FXML
    private Button modifier;

    @FXML
    public void initialize(){

    }
    public void func(int d,String nom,double dm, double decou){
        id.setText(Integer.toString(d));
        debitmax.setText(Double.toString(dm));
        decouvert.setText(Double.toString(decou));
        titulaire.setText(nom);
    }

    public void modifiercompte(ActionEvent event) {
        Compte compte = new Compte();
        compte.setId(Integer.parseInt(id.getText()));
        compte.setDebitmax(Double.parseDouble(debitmax.getText()));
        compte.setDecouvert(Double.parseDouble(decouvert.getText()));
        compte.setTitulaire(titulaire.getText());
        compte.modifier(compte);
    }
}
