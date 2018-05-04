package dao;

import java.util.*;
import java.sql.*;
import model.*;
import connexion.Connexion;
public class CommandeDAO implements InterfaceDAO<Commande>{
    public void findById(Commande baseModele)throws SQLException
        {
            Connexion c = new Connexion();            
            Connection conn = c.getConnect();
            //Commande base ;
            PreparedStatement state=null;
             try
            {
            String query="SELECT * from commande where id='" + baseModele.getId() + "' ";
             state =conn.prepareStatement(query);  
            ResultSet result=state.executeQuery();
            while (result.next())
            {
                baseModele.setId(result.getInt(1));
                baseModele.setUtilisateur(result.getInt(2));
                baseModele.setDaty(result.getString(3));

           
            }
            
            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
               state.close();
               conn.close();
            }
           
        }
    public List<Commande> findAll()throws SQLException
        {
            List<Commande> plat = new ArrayList<>();
            Connexion c = new Connexion();            
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
              String query="SELECT * from commande ";
               state =conn.prepareStatement(query);  
              ResultSet result=state.executeQuery();
              
              
              while (result.next())
                {

              int id=result.getInt(1);
                int utilisateur=result.getInt(2);
                String daty=result.getString(3);
            
                    Commande listeResult= new Commande(id,utilisateur,daty);   
                    plat.add(listeResult); 
                }
              return plat;
            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
               state.close();
               conn.close();
            }
        }
        public void insert(Commande baseModele)throws SQLException
        {
            Connexion c = new Connexion();            
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
                String query="insert into commande(utilisateur,daty) values('"+ baseModele.getUtilisateur() +"','"+ baseModele.getDaty() +"')";
                 state =conn.prepareStatement(query);
                int res=state.executeUpdate();
            }
             catch(Exception e)
             {
                 throw e;
             }
             finally
             {
                 state.close();
                 conn.close();
             }	 

        }
        public void delete(Commande baseModele)throws SQLException
        {
            Connexion c = new Connexion();            
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
                String query="delete from commande where id='"+ baseModele.getId() +"'";
                 state =conn.prepareStatement(query);
                int res=state.executeUpdate();
            }
             catch(Exception e)
             {
                 throw e;
             }
             finally
             {
                 state.close();
                 conn.close();
             }	 

        }
        public void update(Commande baseModele)throws SQLException
        {
            Connexion c = new Connexion();            
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
                String query="update commande set utilisateur='"+ baseModele.getUtilisateur() +"',daty='"+ baseModele.getDaty()+"' where id='"+ baseModele.getId() +"'";
                 state =conn.prepareStatement(query);
                int res=state.executeUpdate();
            }
             catch(Exception e)
             {
                 throw e;
             }
             finally
             {
                 state.close();
                 conn.close();
             }	 

        }
}  