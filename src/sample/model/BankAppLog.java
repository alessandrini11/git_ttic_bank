package sample.model;
import javafx.collections.ObservableList;
public interface BankAppLog {
    public ObservableList<Transaction> getLog(int compteid);
    public void addLog(double montant,String statut,String dest,String type,int compteid);
}
