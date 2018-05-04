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

	private DBColumn columnAnnotation(Field cl)
	{
		Annotation[] annotations = cl.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof DBColumn)
				return (DBColumn) annotation;
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
		setColumnAnnotations();
		for (int i=0; i<this.getMethods.size();i++)
		{
			//String columnName = annotations.get(i).column();
			Method getMethod = getMethodByName(this.getMethods.get(i));
			Method setMethod = getMethodByName(this.setMethods.get(i));
			setMethod.invoke(obj, new Object[] {rset.getObject(columnIndex(names.get(i), rset))});
		}
	}

	private int columnIndex(String column, ResultSet rset) throws Exception {
		ResultSetMetaData rsmd = rset.getMetaData();
		for (int i=1; i<=rsmd.getColumnCount();i++)
			if (rsmd.getColumnName(i).equals(column))
				return i;
		return -1;
	}

	private List<String> names = new ArrayList<>();
	private List<Boolean> isPrimary = new ArrayList<>();
	private List<String> getMethods = new ArrayList<>();
	private List<String> setMethods = new ArrayList<>();

	private void setColumnAnnotations() throws SQLException{
		names = new ArrayList<>();
		isPrimary = new ArrayList<>();
		getMethods = new ArrayList<>();
		setMethods = new ArrayList<>();
		List<Field> fields = fields(this.type);
		List<DBColumn> annotations = new ArrayList<>();
		String tmp =  "";
		for (int i=0; i<fields.size();i++) {
			Field f = fields.get(i);
			DBColumn annotation = columnAnnotation(f);
			if (annotation==null)
				continue;
			String name = annotation.name();
			boolean isPrim = annotation.isPrimary();
			String getMethod = annotation.getMethod();
			String setMethod = annotation.setMethod();
			if (name.length()==0)
				name = f.getName();
			//annotations.add(annotation);
			names.add(name);
			isPrimary.add(isPrim);
			getMethods.add(getMethod);
			setMethods.add(setMethod);
		}
		}

	private Method getMethodByName(String name) {
		Method[] methods = this.type.getMethods();
		for (int i=0; i<methods.length;i++) {
			if (methods[i].getName().equals(name))
				return methods[i];
		}
		return null;
	}

	private List<Field> fields(Class cl) {
		List<Field> fields = new ArrayList<>();
        	while (cl != Object.class) {
            		fields.addAll(Arrays.asList(cl.getDeclaredFields()));
            		cl = cl.getSuperclass();
        	}
		return fields;
	}

	@Override
	public List<T> findAll() throws SQLException {
		return findAll(-1, -1);
	}

	@Override
	public List<T> findAll(int page, int nbPage) throws SQLException {
		List<T> obj = new ArrayList<>();
		Connexion c = new Connexion();
        	Connection conn = c.getConnect();
		PreparedStatement preparedStatement = null;
		ResultSet rset = null;
		try {
			String pagination = "";
			if (page >= 0 && nbPage > 0)
				pagination = " LIMIT " + (page*nbPage) + ", " + nbPage;
			String queryString = "SELECT * FROM " + nomTable(this.type) + pagination;
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
			setColumnAnnotations();
			List<Object> data = new ArrayList<>();
			for (int i=0; i<this.getMethods.size();i++)
			{
				Method getMethod = getMethodByName(this.getMethods.get(i));
				Object result = getMethod.invoke(condition, new Object[] {});
				//GetColumn annot = annotations.get(i);
				String tmp = this.names.get(i) + "=? ";
				if (this.isPrimary.get(i)==true) {
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
			setColumnAnnotations();
			List<Object> data = new ArrayList<>();
			for (int i=0; i<this.getMethods.size();i++)
			{
				Method getMethod = getMethodByName(this.getMethods.get(i));
				String tmp = names.get(i) + "=?";
				if (isPrimary.get(i)==false) {
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
			setColumnAnnotations();
			List<Object> data = new ArrayList<>();
			boolean isFirst = true;
			for (int i=0; i<this.getMethods.size();i++)
			{
				Method getMethod = getMethodByName(this.getMethods.get(i));
				if (isPrimary.get(i)==true)
					continue;
				if (!isFirst) {
					cols += ",";
					vals += ",";
				}
				cols += names.get(i);
				vals += "?";
				data.add(getMethod.invoke(condition, new Object[] {}));
				if (isFirst)
					isFirst = false;
			}

			String queryString = "INSERT INTO " + nomTable(this.type) + " ("+cols+") VALUES ("+vals+")";
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
			setColumnAnnotations();
			List<Object> data = new ArrayList<>();
			for (int i=0; i<this.getMethods.size();i++)
			{
				Method getMethod = getMethodByName(this.getMethods.get(i));
				String tmp = names.get(i) + "=?";
				if (isPrimary.get(i)==true) {
					primary += " and " + tmp;
					data.add(getMethod.invoke(condition, new Object[] {}));
				}
			}

			String queryString = "DELETE FROM " + nomTable(this.type) + " WHERE 1<2" + primary;
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
}
