package sample.controller;

import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.model.Compte;
import sample.model.Dbe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainController {
    @FXML
    public TableView<Compte> tvcompte;
    @FXML
    private TableColumn<Compte,Integer> colid;
    @FXML
    private TableColumn<Compte,Integer> colNumero;
    @FXML
    private TableColumn<Compte,String> colTitulaire;
    @FXML
    private TableColumn<Compte,Double> colSolde;
    @FXML
    private TableColumn<Compte,Double> colDebitMax;
    @FXML
    private TableColumn<Compte,Double> colDecouvert;
    @FXML
    private Button crediter;
    @FXML
    private Button virement;
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;
    @FXML
    public Button ouvrircompte;

    @FXML
    public Button actualiser;
    @FXML
    public void initialize(){
        voirComptes();
    }

    /**
     * recupère la liste des comptes
     * @return
     */
    public ObservableList<Compte> getComptes(){
        ObservableList<Compte> compteslist = FXCollections.observableArrayList();
        Connection connection = Dbe.getConnection();
        String query = "SELECT * FROM compte ";
        Statement statement;
        ResultSet rs;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            Compte comptes;
            while(rs.next()){
                comptes = new Compte(
                        rs.getInt("id"),
                        rs.getInt("numero"),
                        rs.getString("titulaire"),
                        rs.getDouble("solde"),
                        rs.getDouble("debitmax"),
                        rs.getDouble("decouvert")
                );
                compteslist.add(comptes);
            }
        }catch (SQLException e){
            System.out.println("Erreur :" +e.getMessage());
        }
        return compteslist;
    }

    /**
     * lie les colonne du tableau avec les elements à afficher dans le tableau
     */
    public void voirComptes(){
        ObservableList<Compte> comptes = getComptes();
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colTitulaire.setCellValueFactory(new PropertyValueFactory<>("titulaire"));
        colSolde.setCellValueFactory(new PropertyValueFactory<>("solde"));
        colDebitMax.setCellValueFactory(new PropertyValueFactory<>("debitmax"));
        colDecouvert.setCellValueFactory(new PropertyValueFactory<>("decouvert"));

        tvcompte.setItems(comptes);
    }

    /**
     * gestion des eventements sur les bouttons
     * @param event
     */
    public void handler(ActionEvent event){
        if (event.getSource() == crediter) {
            try{
                Stage stage = (Stage) crediter.getScene().getWindow();
                Stage ps = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/sample/vue/crediter.fxml"));
                ps.setScene(new Scene(root,600,600));
                ps.setTitle("creer un compte");
                ps.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else if(event.getSource() == virement){
            try{
                Stage stage = (Stage) virement.getScene().getWindow();
                Stage ps = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/sample/vue/virement.fxml"));
                ps.setScene(new Scene(root,600,600));
                ps.setTitle("creer un compte");
                ps.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else if (event.getSource() == actualiser){
            voirComptes();
        }
        else if(event.getSource() == modifier){

        }else if(event.getSource() == supprimer){

        }else if(event.getSource() == ouvrircompte){

            try{
                Stage stage = (Stage) ouvrircompte.getScene().getWindow();
                Stage ps = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/sample/vue/creer.fxml"));
                ps.setScene(new Scene(root,600,600));
                ps.setTitle("creer un compte");
                ps.show();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
