package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Compte {
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
        somme += montant;
        String titulaire = compte.getTitulaire();
        String query = "UPDATE compte SET solde="+somme+" WHERE id="+compte.getId()+"";
        executeQuery(query);
    }

    public ArrayList<Compte> getComptes(){
        ArrayList<Compte> comptes = new ArrayList();

        return comptes;
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
     * on recupere le compte désiré et la somme a transferer
     * on additionne la somme à transferer au solde du compte selectionné
     * @param compte
     * @param montant
     */
    public void virement(Compte compte,double montant){
        if(montant > this.debitmax){
            System.out.println("Opérartion annulée, le montant à debiter est supperieur au montant maximum a débiter");
        }else if ((this.solde - Math.abs(montant)) < - this.decouvert  ){
            System.out.println("Opération annulée, le solde de votre compte sera inférieur au decouvert de votre compte");
        }else{
            this.solde -= montant;
            compte.crediter(compte, montant);
        }

    }
    public void afficher(){
        System.out.println("le numéro du compte est : "+this.numero);
        System.out.println("le propritaire du compte est : "+this.titulaire);
        System.out.println("le solde du compte est : "+this.solde+" FCFA");
        System.out.println("le decouvert du compte est : "+this.decouvert+" FCFA");
        System.out.println("le debit maximum du compte est : "+this.debitmax+" FCFA");
        situationCompte();
    }
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
            System.out.println("added record ");

        }else{
            this.numero = genererNum();
            this.solde = solde;
            this.decouvert = solde*0.1;
            this.debitmax = solde*0.5;
            this.titulaire = "'"+nomPropritaire+"'";
            String query = "INSERT INTO compte (id,numero,titulaire,solde,debitmax,decouvert) VALUES ("+null+","+this.numero+","+this.titulaire+","+this.solde+","+this.debitmax+","+this.decouvert+")";
            executeQuery(query);
            System.out.println("added record avec solde ");
        }
    }
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
     * verifie si le solde du compte est supérieur ou inferieur à zero
     * et nous revoie l'état du compte
     */
    public void situationCompte(){
        if(this.solde >= 0 ){
            System.out.println("Votre compte est en règle");
        }else if(this.solde < 0){
            System.out.println("votre compte est à decouvert");
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

    public static void main(String[] args) {
        Compte c1 = new Compte();
        c1.createCompte("jay jay okocha",500000);
        c1.afficher();
    }
}
