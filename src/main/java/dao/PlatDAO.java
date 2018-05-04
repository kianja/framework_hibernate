package dao;

import java.util.*;
import java.sql.*;
import model.Plat;
import connexion.Connexion;

public class PlatDAO implements InterfaceDAO<Plat>
{
	@Override
	public List<Plat> findAll() throws SQLException {
		List<Plat> plat = new ArrayList<>();
		//Connection connection = null;
		Connexion c = new Connexion();            
        Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet rset = null;
		try {
			
			String queryString = "SELECT * FROM " + Plat.getTable();
			preparedStatement = conn.prepareStatement(queryString);
			rset = preparedStatement.executeQuery();
			while (rset.next()) {
				List<Object> obj = new ArrayList<>();
				Plat tmp = new Plat(rset.getInt(1), rset.getString(2));
				plat.add(tmp);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (rset!=null)
				rset.close();
			if (preparedStatement!=null)
				preparedStatement.close();
			if (conn!=null)
				conn.close();
		}
		return plat;
	}

	@Override
	public void findById(Plat condition) throws SQLException {
		Connexion c = new Connexion();            
        Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet rset = null;
		try {
			String queryString = "SELECT * FROM " + Plat.getTable() + " WHERE id="+condition.getId();
			preparedStatement = conn.prepareStatement(queryString);
			rset = preparedStatement.executeQuery();
			if (rset.next()) {
				condition.setId(rset.getInt(1));
				condition.setNom(rset.getString(2));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (rset!=null)
				rset.close();
			if (preparedStatement!=null)
				preparedStatement.close();
			if (conn!=null)
				conn.close();
		}
		
	}

	@Override
	public void update(Plat condition) throws SQLException {
		Connexion c = new Connexion();            
        Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet rset = null;
		try {
			
			String queryString = "UPDATE " + Plat.getTable() + " SET nom='"+condition.getNom()+"' WHERE id="+condition.getId();
			preparedStatement = conn.prepareStatement(queryString);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (preparedStatement!=null)
				preparedStatement.close();
			if (conn!=null)
				conn.close();
		}
	}

	@Override
	public void insert(Plat condition) throws SQLException {
		Connexion c = new Connexion();            
        Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		try {
			
			String queryString = "INSERT INTO plat (nom) VALUES ('"+condition.getNom()+"')";
			preparedStatement = conn.prepareStatement(queryString);
			//preparedStatement.setValue(1, condition.getNom());
			preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (preparedStatement!=null)
				preparedStatement.close();
			if (conn!=null)
				conn.close();
		}
	}

	@Override
	public void delete(Plat condition) throws SQLException {
		Connexion c = new Connexion();            
        Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		try {
			
			String queryString = "DELETE FROM plat WHERE id="+condition.getId();
			preparedStatement = conn.prepareStatement(queryString);
			//preparedStatement.setValue(1, condition.getNom());
			preparedStatement.executeUpdate();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (preparedStatement!=null)
				preparedStatement.close();
			if (conn!=null)
				conn.close();
		}
	}
}
