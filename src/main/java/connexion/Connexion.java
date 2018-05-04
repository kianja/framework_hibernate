package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class Connexion {
      Connection conex;
	public Connection getConnect() throws SQLException {
           
		try{
			Class.forName("com.mysql.jdbc.Driver");		
			conex=DriverManager.getConnection("jdbc:mysql://localhost/frame","root","root");
                        System.out.println("connection etablie");
		}
		catch(Exception e){
                  System.out.println(e);
                }
		return conex;
	}
}
