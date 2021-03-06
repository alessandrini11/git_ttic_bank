package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class Compte implements BankAppLog {
    private int id;
    private int numero;
    private String titulaire;
    private double solde;
    private double debitmax;
    private double decouvert;

    public Compte(int id, int numero, String titulaire, double solde, double debitmax, double decouvert) {
        this.id = id;
        this.numero = numero;
        this.titulaire = titulaire;
        this.solde = solde;
        this.debitmax = debitmax;
        this.decouvert = decouvert;
    }

    /**
     * le constructeur qui initialisera le compte
     * @param nom
     * @param decouvert
     * @param debitMax
     * @param solde
     */
    public Compte(String nom,double decouvert,double debitMax,double solde){
        this.numero = genererNum();
        this.titulaire = nom;
        this.solde = Math.abs(solde);
        this.decouvert = Math.abs(decouvert);
        this.debitmax = Math.abs(debitMax);
    }
    public Compte(){ }
    /**
     * constructeur appellé en cas d'absence de decouvert et debit max
     * @param nom
     * @param solde
     */
    public Compte(String nom,double solde){
        this.numero = genererNum();
        this.solde = Math.abs(solde);
        this.titulaire = nom;
        this.decouvert = 800.0;
        this.debitmax = 3000.0;
    }
    /**
     * constructeur appelé en cas d'absence de solde, decouvert, debit max
     */
    public Compte(String nom){
        genererNum();
        this.solde = 0.0;
        this.titulaire = nom;
        this.decouvert = 800.0;
        this.debitmax = 3000.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public double getDebitmax() {
        return debitmax;
    }

    public void setDebitmax(double debitmax) {
        this.debitmax = debitmax;
    }

    public double getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(double decouvert) {
        this.decouvert = decouvert;
    }

    /**
     * on va créditer le compte du montant
     */
    public void crediter(Compte compte, double montant){
        double somme = compte.getSolde();
        String statut = "reussie";
        String type = "crediter";
        somme += montant;
        String titulaire = compte.getTitulaire();
        String query = "UPDATE compte SET solde="+somme+" WHERE id="+compte.getId()+"";
        executeQuery(query);
        addLog(somme,statut,compte.titulaire,type,compte.id);
    }
    /**
     * debiter le solde du montant selectionné
     * @param montant
     */
    public void debiter(double montant){
        if(montant > this.debitmax){
            System.out.println("Opérartion annulée, le montant à debiter est supperieur au montant maximum a débiter");
        }else if ((this.solde - Math.abs(montant)) < - this.decouvert  ){
            System.out.println("Opération annulée, le solde de votre compte sera inférieur au decouvert de votre compte");
        }else{
            this.solde -= montant;
        }
    }

    /**
     * on recupere le compte débité et compte à créditer
     * on additionne la somme à transferer au solde du compte selectionné
     * @param compte
     * @param montant
     */
    public void virement(Compte compte,double montant){
        String statut = "";
        String type = "virement";
        if(montant > this.debitmax){
            statut = "echec";
            addLog(montant,statut,compte.getTitulaire(),type,this.id);
            String info = "Opérartion annulée, le montant à debiter est supperieur au débit maximum autorisé";
            alert(info);
        }else if ((this.solde - Math.abs(montant)) < - this.decouvert  ){
            statut = "echec";
            addLog(montant,statut,compte.getTitulaire(),type,this.id);
            String info = "Opération annulée, le solde de votre compte sera inférieur au decouvert de votre compte";
            alert(info);
        }else{
            statut = "reussie";
            this.solde -= montant;
            String queryexpediteur = "UPDATE compte SET solde="+this.solde+" WHERE id="+this.id+"";
            executeQuery(queryexpediteur);
            addLog(montant,statut,compte.getTitulaire(),type,this.id);
            compte.crediter(compte, montant);
            String info = montant+" FCA a été viré de "+this.getTitulaire()+" au profit de "+compte.getTitulaire();
            alert(info);
        }

    }
    public void modifier(Compte compte){
        String type = "modification";
        String statut = "reussie";
        double montant = 0;
        String query = "UPDATE compte SET debitmax ="+compte.debitmax+",decouvert="+compte.getDecouvert()+" WHERE id="+compte.getId()+"";
        executeQuery(query);
        addLog(montant,statut,compte.titulaire,type,compte.id);
        String info = "le compte a été mofifié";
        alert(info);
    }

    /**
     * cette méthode cré les compte en fonction du solde de départ
     * @param nomPropritaire
     * @param solde
     */
    public void createCompte(String nomPropritaire,double solde){

        if (solde == 0 ){
            this.numero = genererNum();
            this.titulaire = "'"+nomPropritaire+"'";
            this.solde = 0;
            this.solde = 0.0;
            this.decouvert = 800;
            this.debitmax = 3000;
            String query = "INSERT INTO compte(id,numero,titulaire,solde,debitmax,decouvert) VALUES("+null+","+this.numero+","+this.titulaire+","+this.solde+","+this.debitmax+","+this.decouvert+")";
            executeQuery(query);

        }else{
            this.numero = genererNum();
            this.solde = solde;
            this.decouvert = solde*0.1;
            this.debitmax = solde*0.5;
            this.titulaire = "'"+nomPropritaire+"'";
            String query = "INSERT INTO compte (id,numero,titulaire,solde,debitmax,decouvert) VALUES ("+null+","+this.numero+","+this.titulaire+","+this.solde+","+this.debitmax+","+this.decouvert+")";
            executeQuery(query);
            String info = "le compte a été bien créé";
            alert(info);
        }
    }
    public void supprimer(Compte compte){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous supprimer "+compte.getTitulaire()+" ?");
        alert.setContentText("");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            String query = "DELETE FROM compte WHERE id="+compte.getId()+"";
            executeQuery(query);
            String query2 = "DELETE FROM transaction WHERE compteid="+compte.getId()+"";
            executeQuery(query2);
            String info = "le compte a été supprimé avec success";
            alert(info);
        }

    }

    /**
     * cette méthode affiche toutes les information du compte
     */
    public void afficher(Compte compte){
        System.out.println("le numéro du compte est : "+this.numero);
        System.out.println("le propritaire du compte est : "+this.titulaire);
        System.out.println("le solde du compte est : "+this.solde+" FCFA");
        System.out.println("le decouvert du compte est : "+this.decouvert+" FCFA");
        System.out.println("le debit maximum du compte est : "+this.debitmax+" FCFA");
        compte.situationCompte(compte);
    }

    /**
     * verifie si le solde du compte est supérieur ou inferieur à zero
     * et nous revoie l'état du compte
     */
    public void situationCompte(Compte compte){
        if(compte.solde >= 0 ){
            String info = "Votre compte est en règle";
            alert(info);

            System.out.println("ok");
        }else if(compte.solde < 0){
            String info = "votre compte est à decouvert";
            alert(info);

            System.out.println("ok");
        }
    }
    /**
     * cette méthode execute les requete qui sont effectuées dans la base de donnée
     * @param query
     */
    private void executeQuery(String query){
        Connection connection = Dbe.getConnection();
        Statement statement;
        try {
            if (connection != null){
                statement = connection.createStatement();
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    /**
     * methode qui génère un nombre à 8 chiffre de manière aléatoire
     * et affecte la valeur au numéro du compte
     * il recupère les numéros de compte dans la base de donnée et compare au numéro générer
     * si ce numéro est deja attribuer
     */
    private int genererNum(){
        Connection connection = Dbe.getConnection();
        ArrayList<Integer> numeroCompte = new ArrayList<>();
        ResultSet resultSet;
        int min = 10000000;
        int max = 99999999;
        int nombre = 0;
        String qeury = "SELECT numero FROM compte ";
        try {
            PreparedStatement statement = connection.prepareStatement(qeury);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                numeroCompte.add(resultSet.getInt("numero"));
            }
            do {
                nombre = (int)(Math.random()*(max-min)) + min;
            }while (numeroCompte.contains(nombre));
            /*nombre = 68977923;
            if (numeroCompte.contains(nombre)){
                System.out.println("Ce numéro de compte est deja attribué à un client de la bank");
            }
             */
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }

    /**
     * cette méthode cré des petit boite de dialog nous donnant des informations
     * @param information
     */
    private void alert(String information){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("");
        alert.setContentText(information);
        alert.showAndWait();
    }

    /**
     * retourne un tableau de transaction
     * @param compteid
     * @return
     */
    @Override
    public ObservableList<Transaction> getLog(int compteid) {
        ObservableList<Transaction> transList = FXCollections.observableArrayList();
        Connection connection = Dbe.getConnection();
        String query = "SELECT * FROM transaction WHERE compteid="+compteid+"";
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

    /**
     * ajouter chaque opérations éffectué en base de donnée
     * @param montant
     * @param statut
     * @param dest
     * @param type
     * @param compteid
     */
    @Override
    public void addLog(double montant, String statut, String dest, String type,int compteid) {
            String st = "'"+statut+"'";
            String dst = "'"+dest+"'";
            String typ = "'"+type+"'";
            String query = "INSERT INTO transaction (id,montant,statut,destinataire,type,compteid) "
                    +"VALUES ("+null+","+montant+","+st+","+dst+","+typ+","+compteid+")";
            executeQuery(query);
    }
}
