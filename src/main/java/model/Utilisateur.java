package model;
import java.util.List;
import java.util.Iterator;
import annotation.*;
@DBTable(table="utilisateur")
public class Utilisateur extends BaseModel{
    private int id;
    private String nom;
    private String prenom;
    public Utilisateur(){}
    
    public Utilisateur(int id,String nom,String prenom){
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
    }
    @SetColumn(column="nom")
    public void setNom(String nom){
        this.nom=nom;
    }
    @SetColumn(column="prenom")
    public void setPrenom(String prenom){
        this.prenom=prenom;
    }
    @SetColumn(column="nom")
    public String getNom(){
        return this.nom;
    }
    @SetColumn(column="prenom")
    public String getPrenom(){
        return this.prenom;
    }
}