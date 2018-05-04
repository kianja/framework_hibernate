package model;
import java.util.List;
import java.util.Iterator;
import annotation.*;
@DBTable(table="utilisateur")
public class Utilisateur extends BaseModel{
	@DBColumn(name="nom", getMethod="getNom", setMethod="setNom")
    private String nom;
    @DBColumn(name="prenom", getMethod="getPrenom", setMethod="setPrenom")
    private String prenom;
    public Utilisateur(){}

    public Utilisateur(int id,String nom,String prenom){
        this.setId(id);
        this.setNom(nom);
        this.setPrenom(prenom);
    }
    public void setNom(String nom){
        this.nom=nom;
    }
    public void setPrenom(String prenom){
        this.prenom=prenom;
    }
    public String getNom(){
        return this.nom;
    }
    public String getPrenom(){
        return this.prenom;
    }
}
