package model;

import java.util.List;
import java.util.Iterator;

import annotation.*;
import org.hibernate.*;
public abstract class BaseModel
{
	private int id ;
	private static String table ;
	
	@GetColumn(column="id", isPrimary=true)
	public int getId() { return id; }
	public static String getTable() { return table; }

	@SetColumn(column="id")
	public void setId(int id) { this.id = id; }
	public void setTable(String table) { this.table = table; }

	public void setDBColomnAttributes(List<Object> values)
	{
		int nbColumn = 1;
		if (values.size() < nbColumn)
			throw new IllegalArgumentException(
				"La taille de la liste ne correspond pas au nombre de colonne de la table");
		Iterator<Object> iterator = values.iterator();
		setId((int)iterator.next());
	}
}
