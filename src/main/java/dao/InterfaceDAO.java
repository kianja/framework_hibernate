package dao;

import java.util.List;
import java.sql.SQLException;
import model.BaseModel;

public interface InterfaceDAO<T extends BaseModel>
{
	public List<T> findAll() throws SQLException;
	public List<T> findAll(int page, int nbPage) throws SQLException;
	public void findById(T obj) throws SQLException;
	public void insert(T obj) throws SQLException;
	public void update(T obj) throws SQLException;
	public void delete(T obj) throws SQLException;
}
