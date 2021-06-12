package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CrediterController {
    @FXML
    private TextField montantcrediter;
    @FXML
    private Button crediter;
    @FXML
    private ComboBox<String> choixcompte;

    @FXML
    public void initialize(){
        getComptes();
    }
    public void crediter(ActionEvent event){
        if (event.getSource() == crediter){
            String nom = choixcompte.getValue().toString();
            double montant = Double.parseDouble(montantcrediter.getText());
            ArrayList<String> uniquecompte = getUniqueCompte(nom);
            int id = Integer.parseInt(uniquecompte.get(0));
            int numero = Integer.parseInt(uniquecompte.get(1));
            String proprietaire = uniquecompte.get(2);
            double solde = Double.parseDouble(uniquecompte.get(3));
            double debitmax = Double.parseDouble(uniquecompte.get(4));
            double decouvert = Double.parseDouble(uniquecompte.get(5));
            Compte compte = new Compte(id,numero,proprietaire,solde,debitmax,decouvert);
            compte.crediter(compte,montant);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("");
            alert.setContentText("Le compte du client "+nom+" a été créditer d'un montant de "+montant+" FCFA");
            alert.showAndWait();


        }
    }
    public void getComptes(){
        Connection connection = Dbe.getConnection();
        try {
            ObservableList<String> titulaireList = FXCollections.observableArrayList();
            String query = "SELECT titulaire FROM compte ORDER BY titulaire ASC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            Compte compte;
            while (resultSet.next()){
                /*titulaireList.add(new Compte(
                        resultSet.getString("titulaire")
                ));*/
                titulaireList.add(resultSet.getString("titulaire"));
                choixcompte.setItems(titulaireList);
            }
        }catch (SQLException e){
            System.out.println("Erreur: "+e.getMessage());
        }
    }
    public ArrayList<String> getUniqueCompte(String nom){
        ArrayList<String> uniquecompte = new ArrayList<String>();
        String titulaire = "'"+nom+"'";
        Connection connection = Dbe.getConnection();
        String query = "SELECT * FROM compte WHERE compte.titulaire="+titulaire+"";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            Compte compte;
            while (resultSet.next()){
                uniquecompte.add(0,resultSet.getString("id"));
                uniquecompte.add(1,resultSet.getString("numero"));
                uniquecompte.add(2,resultSet.getString("titulaire"));
                uniquecompte.add(3,resultSet.getString("solde"));
                uniquecompte.add(4,resultSet.getString("debitmax"));
                uniquecompte.add(5,resultSet.getString("decouvert"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return  uniquecompte;
    }

}
