package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {
    private int id;
    private double montant;
    private String statut;
    private String destinataire;
    private String type;
    private int compteid;

    public Transaction(){};
    public Transaction(int id, double montant, String statut, String destinataire, String type, int compteid) {
        this.id = id;
        this.montant = montant;
        this.statut = statut;
        this.destinataire = destinataire;
        this.type = type;
        this.compteid = compteid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(String destinataire) {
        this.destinataire = destinataire;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCompteid() {
        return compteid;
    }

    public void setCompteid(int compteid) {
        this.compteid = compteid;
    }

    /**
     * retourne un tableau de transaction
     * @param compteid
     * @return
     */
    /*
    public ObservableList<Transaction> getTransactions(int compteid) {
        ObservableList<Transaction> transList = FXCollections.observableArrayList();
        Connection connection = Dbe.getConnection();
        String query = "SELECT * FROM transaction WHERE compteid="+id+"";
        Statement statement;
        ResultSet rs;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            Transaction transaction;
            while(rs.next()){
                transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getDouble("montant"),
                        rs.getString("statut"),
                        rs.getString("destinataire"),
                        rs.getString("type"),
                        rs.getInt("compteid")
                );
                transList.add(transaction);
            }
        }catch (SQLException e){
            System.out.println("Erreur :" +e.getMessage());
        }
        return transList;
    }
    */
}
