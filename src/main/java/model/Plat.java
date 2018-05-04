package model;

import java.util.List;
import java.util.Iterator;
import org.hibernate.*;
import annotation.*;
import javax.persistence.*;
@Entity
@DBTable(table="plat")
public class Plat extends BaseModel
{
	private String nom = null;

	public Plat() {}
	public Plat(int id, String nom) {
		setId(id); setTable("plat");
		setNom(nom);
	}

	@GetColumn(column="nom")
	public String getNom() { return this.nom; }
	@SetColumn(column="nom")
	public void setNom(String nom) { this.nom = nom; }

	@Override
	public void setDBColomnAttributes(List<Object> values)
	{
		int nbColumn = 1;
		if (values.size() < nbColumn)
			throw new IllegalArgumentException(
				"La taille de la liste ne correspond pas au nombre de colonne de la table");
		Iterator<Object> iterator = values.iterator();
		setId((int)iterator.next());
		setNom((String)iterator.next());
	}
}
