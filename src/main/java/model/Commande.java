package model;
import java.util.List;
import java.util.Iterator;

import annotation.*;


@DBTable(table="commande")
public class Commande extends BaseModel{
    private int id;
    private int utilisateur;

    private String daty;
    public Commande(){}
    public Commande(int id,int utilisateur,String daty){
        this.setId(id);
        this.setUtilisateur(utilisateur);
        this.setDaty(daty);
        setTable("commande");
    }
    
    @GetColumn(column="idUtilisateur")
    public void setUtilisateur(int utilisateur){
        this.utilisateur=utilisateur;
    }
    @GetColumn(column="daty")
    public void setDaty(String daty){
        this.daty=daty;
    }
    @GetColumn(column="idUtilisateur")
    public int getUtilisateur(){
        return this.utilisateur;
    }
    @GetColumn(column="daty")
    public String getDaty(){
        return this.daty;
    }
}