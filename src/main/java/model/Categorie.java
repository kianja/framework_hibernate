package model;
import java.util.List;
import java.util.Iterator;

import annotation.*;

@DBTable(table="categorie")
public class Categorie extends BaseModel{
    private String nom_categorie;
    public Categorie(int id, String nom_categorie){
        setId(id);
        this.setNom(nom_categorie);
        setTable("categorie");
    }
    public Categorie(){}
    
    @SetColumn(column="nom")
    public void setNom(String nom_categorie){
        this.nom_categorie=nom_categorie;
    }
    @GetColumn(column="nom")
    public String getNom(){
        return this.nom_categorie;
    }
}