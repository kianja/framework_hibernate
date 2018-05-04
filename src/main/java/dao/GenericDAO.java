package dao;

import java.util.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.sql.*;
import model.*;
import annotation.*;
import connexion.Connexion;

public class GenericDAO<T extends BaseModel> implements InterfaceDAO<T>
{
	private Class type = null;
	public GenericDAO(T type)
	{
		this.type = type.getClass();
	}

	private String nomTable(Class cl)
	{
		Annotation[] annotations = cl.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof DBTable) {
				DBTable monAnnotation = (DBTable) annotation;
				return monAnnotation.table();
			}
		}
		return null;
	}

	private SetColumn setMethodAnnotation(Method m)
	{
		Annotation[] annotations = m.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof SetColumn) {
				return (SetColumn) annotation;
			}
		}
		return null;
	}

	private GetColumn getMethodAnnotation(Method m)
	{
		Annotation[] annotations = m.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof GetColumn) {
				return (GetColumn) annotation;
			}
		}
		return null;
	}

	private void setAttributes(T obj, ResultSet rset) throws Exception
	{
		Method[] methods = this.type.getMethods();
		List<Method> ms = new ArrayList<>();
		List<SetColumn> annotations = new ArrayList<>();
		for (int i=0; i<methods.length;i++)
		{
			SetColumn annotation = setMethodAnnotation(methods[i]);
			if (annotation==null)
				continue;
			annotations.add(annotation);
			ms.add(methods[i]);
		}
		//if (true)
		//	throw new Exception("methods.length="+methods.length+", methods.size="+ms.size());
		for (int i=0; i<ms.size();i++)
		{
			String columnName = annotations.get(i).column();
			ms.get(i).invoke(obj, new Object[] {rset.getObject(columnIndex(columnName, rset))});
		}
	}

	private int columnIndex(String column, ResultSet rset) throws Exception
	{
		ResultSetMetaData rsmd = rset.getMetaData();
		for (int i=1; i<=rsmd.getColumnCount();i++)
			if (rsmd.getColumnName(i).equals(column))
				return i;
		return -1;
	}

	@Override
	public List<T> findAll() throws SQLException {
		List<T> obj = new ArrayList<>();
		//Connection connection = null;
		Connexion c = new Connexion();            
        Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet rset = null;
		try {
			String queryString = "SELECT * FROM " + nomTable(this.type);
			//if (true)
			//	throw new SQLException(queryString);
			preparedStatement = conn.prepareStatement(queryString);
			rset = preparedStatement.executeQuery();
			while (rset.next()) {
				T tmp = (T)this.type.newInstance();
				setAttributes(tmp, rset);
				obj.add(tmp);
			}
		}
		catch (SQLException ex) {
			//ex.printStackTrace();
			throw ex;
		}
		catch (Exception ex) {
			throw new SQLException(ex.getMessage());
		}
		finally {
			if (rset!=null)
				rset.close();
			if (preparedStatement!=null)
				preparedStatement.close();
			if (conn!=null)
				conn.close();
		}
		return obj;
	}

	@Override
	public void findById(T condition) throws SQLException {
		Connexion c = new Connexion();            
        Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet rset = null;
		try {
			String primary = "";
			Method[] methods = this.type.getMethods();
			List<Method> ms = new ArrayList<>();
			List<GetColumn> annotations = new ArrayList<>();
			for (int i=0; i<methods.length;i++)
			{
				GetColumn annotation = getMethodAnnotation(methods[i]);
				if (annotation==null)
					continue;
				annotations.add(annotation);
				ms.add(methods[i]);
			}
			List<Object> data = new ArrayList<>();
			for (int i=0; i<ms.size();i++)
			{
				Method getMethod = ms.get(i);
				Object result = getMethod.invoke(condition, new Object[] {});
				GetColumn annot = annotations.get(i);
				String tmp = annot.column() + "=? ";
				if (annot.isPrimary()==true) {
					primary += " and " + tmp;
					data.add(result);
				}
			}
			String queryString = "SELECT * FROM " + nomTable(this.type) + " WHERE 1<2 "+primary;
			preparedStatement = conn.prepareStatement(queryString);
			for (int i=0; i<data.size();i++)
				preparedStatement.setObject(i+1, data.get(i));
			rset = preparedStatement.executeQuery();
			if (rset.next()) {
				setAttributes(condition, rset);
			}
		}
		catch (SQLException ex) {
			//ex.printStackTrace();
			throw ex;
		}
		catch (Exception ex) {
			;
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
	public void update(T condition) throws SQLException {
		Connexion c = new Connexion();            
        Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		try {

			String set = ""; String primary = "";
			Method[] methods = this.type.getMethods();
			List<Method> ms = new ArrayList<>();
			List<GetColumn> annotations = new ArrayList<>();
			for (int i=0; i<methods.length;i++)
			{
				GetColumn annotation = getMethodAnnotation(methods[i]);
				if (annotation==null)
					continue;
				annotations.add(annotation);
				ms.add(methods[i]);
			}
			List<Object> data = new ArrayList<>();
			for (int i=0; i<ms.size();i++)
			{
				GetColumn annot = annotations.get(i);
				Method getMethod = ms.get(i);
				String tmp = annot.column() + "=?";
				if (annot.isPrimary()==false) {
					if (i!=0)
						set += ",";
					set += tmp;
				} else {
					primary += " and " + tmp; 
				}
				data.add(getMethod.invoke(condition, new Object[] {}));
			}

			String queryString = "UPDATE " + nomTable(this.type) + " SET "+set+" WHERE 1<2" + primary;
			preparedStatement = conn.prepareStatement(queryString);
			for (int i=0; i<data.size();i++)
				preparedStatement.setObject(i+1, data.get(i));
			preparedStatement.executeUpdate();
		}
		catch (SQLException ex) {
			//ex.printStackTrace();
			throw ex;
		}
		catch (Exception ex) {
			throw new SQLException(ex.getMessage());
		}
		finally {
			if (preparedStatement!=null)
				preparedStatement.close();
			if (conn!=null)
				conn.close();
		}
	}

	@Override
	public void insert(T condition) throws SQLException {
		Connexion c = new Connexion();            
        Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		try {

			String cols = ""; String vals = "";
			Method[] methods = this.type.getMethods();
			List<Method> ms = new ArrayList<>();
			List<GetColumn> annotations = new ArrayList<>();
			for (int i=0; i<methods.length;i++)
			{
				GetColumn annotation = getMethodAnnotation(methods[i]);
				if (annotation==null)
					continue;
				annotations.add(annotation);
				ms.add(methods[i]);
			}
			List<Object> data = new ArrayList<>();
			boolean isFirst = true;
			for (int i=0; i<ms.size();i++)
			{
				GetColumn annot = annotations.get(i);
				Method getMethod = ms.get(i);
				if (annot.isPrimary()==true)
					continue;
				if (!isFirst) {
					cols += ",";
					vals += ",";
				}
				cols += annot.column();
				vals += "?";
				data.add(getMethod.invoke(condition, new Object[] {}));
				if (isFirst)
					isFirst = false;
			}

			String queryString = "INSERT INTO " + nomTable(this.type) + " ("+cols+") VALUES ("+vals+")";
			//if (true)
			//	throw new SQLException(queryString);
			preparedStatement = conn.prepareStatement(queryString);
			for (int i=0; i<data.size();i++)
				preparedStatement.setObject(i+1, data.get(i));
			preparedStatement.executeUpdate();
		}
		catch (SQLException ex) {
			//ex.printStackTrace();
			throw ex;
		}
		catch (Exception ex) {
			throw new SQLException(ex.getMessage());
		}
		finally {
			if (preparedStatement!=null)
				preparedStatement.close();
			if (conn!=null)
				conn.close();
		}
	}

	@Override
	public void delete(T condition) throws SQLException {
		Connexion c = new Connexion();            
        Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		try {

			String primary = "";
			Method[] methods = this.type.getMethods();
			List<Method> ms = new ArrayList<>();
			List<GetColumn> annotations = new ArrayList<>();
			for (int i=0; i<methods.length;i++)
			{
				GetColumn annotation = getMethodAnnotation(methods[i]);
				if (annotation==null)
					continue;
				annotations.add(annotation);
				ms.add(methods[i]);
			}
			for (int i=0; i<ms.size();i++)
			{
				GetColumn annot = annotations.get(i);
				Method getMethod = ms.get(i);
				String tmp = annot.column() + "='" + getMethod.invoke(condition, new Object[] {}) + "'";
				if (annot.isPrimary()==true)
					primary += " and " + tmp;
			}

			String queryString = "DELETE FROM " + nomTable(this.type) + " WHERE 1<2" + primary;
			//if (true)
			//	throw new SQLException(queryString);
			preparedStatement = conn.prepareStatement(queryString);
			preparedStatement.executeUpdate();
		}
		catch (SQLException ex) {
			//ex.printStackTrace();
			throw ex;
		}
		catch (Exception ex) {
			throw new SQLException(ex.getMessage());
		}
		finally {
			if (preparedStatement!=null)
				preparedStatement.close();
			if (conn!=null)
				conn.close();
		}
	}
}
