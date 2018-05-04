package model;
import java.util.List;
import java.util.Iterator;

import annotation.*;


@DBTable(table="commande")
public class Commande extends BaseModel{
	@DBColumn(name="idUtilisateur", getMethod="getUtilisateur", setMethod="setUtilisateur")
    private int utilisateur;
	@DBColumn(name="daty", getMethod="getDaty", setMethod="setDaty")
    private String daty;
    public Commande(){}
    public Commande(int id,int utilisateur,String daty){
        this.setId(id);
        this.setUtilisateur(utilisateur);
        this.setDaty(daty);
        setTable("commande");
    }

    public void setUtilisateur(int utilisateur){
        this.utilisateur=utilisateur;
    }
    public void setDaty(String daty){
        this.daty=daty;
    }
    public int getUtilisateur(){
        return this.utilisateur;
    }
    public String getDaty(){
        return this.daty;
    }
}
