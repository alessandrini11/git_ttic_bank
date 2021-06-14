package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Compte;
import sample.model.Dbe;
import sample.model.Transaction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainController {

    public TableView<Transaction> tvoperation;
    public TableColumn<Transaction,Integer> opid;
    public TableColumn<Transaction,Double> opmontant;
    public TableColumn<Transaction,String> opstatut;
    public TableColumn<Transaction,String> optype;
    public TableColumn<Transaction,String> opdestinataire;
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
    public Button situation;

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
                ps.setScene(new Scene(root));
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
                ps.setScene(new Scene(root));
                ps.setTitle("creer un compte");
                ps.getIcons().add(new Image("/sample/icon.png"));
                ps.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else if (event.getSource() == actualiser){
            voirComptes();
        }
        else if(event.getSource() == modifier){
            MouseEvent ev = null;
            Compte c2 = mousehandle(ev);
            double debmx = c2.getDebitmax();
            double decou = c2.getDecouvert();
            int id = c2.getId();
            String titulaire = c2.getTitulaire();
            try{
                Stage stage = (Stage) modifier.getScene().getWindow();
                Stage ps = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/vue/modifier.fxml"));
                Parent root = (Parent) loader.load();
                ps.setScene(new Scene(root));
                ps.setTitle("modifier un compte");
                ps.getIcons().add(new Image("/sample/icon.png"));
                ModifierController mc = loader.getController();
                mc.func(id,titulaire,debmx,decou);
                ps.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else if(event.getSource() == supprimer){
            MouseEvent e = null;
            Compte compte = mousehandle(e);
            compte.supprimer(compte);
            voirComptes();


        }else if(event.getSource() == ouvrircompte){

            try{
                Stage stage = (Stage) ouvrircompte.getScene().getWindow();
                Stage ps = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/sample/vue/creer.fxml"));
                ps.setScene(new Scene(root));
                ps.setTitle("creer un compte");
                ps.getIcons().add(new Image("/sample/icon.png"));
                ps.show();
            }catch (IOException e){
                e.printStackTrace();
            }

        }else if(event.getSource() == situation){
            MouseEvent e = null;
            Compte compte = mousehandle(e);
            compte.situationCompte(compte);
        }
    }
    public Compte mousehandle(MouseEvent event){
        Compte compte = tvcompte.getSelectionModel().getSelectedItem();
        ObservableList<Transaction> transaction = compte.getLog(compte.getId());
        opid.setCellValueFactory(new PropertyValueFactory<>("compteid"));
        opdestinataire.setCellValueFactory(new PropertyValueFactory<>("destinataire"));
        opmontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        opstatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        optype.setCellValueFactory(new PropertyValueFactory<>("type"));
        tvoperation.setItems(transaction);
        return compte;
    }
}
