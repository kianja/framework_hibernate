package dao;

import java.util.*;
import java.sql.*;
import model.*;
import connexion.Connexion;
public class UtilisateurDAO implements InterfaceDAO<Utilisateur>{
    @Override
    public void findById(Utilisateur baseModele)throws SQLException
        {
            Connexion c = new Connexion();            
            Connection conn = c.getConnect();
            //BaseModele base ;
            PreparedStatement state=null;
             try
            {
                String query="SELECT * from utilisateur where id='" + baseModele.getId() + "' ";
                state =conn.prepareStatement(query);  
                ResultSet result=state.executeQuery();
                while (result.next())
                {
                    baseModele.setNom(result.getString(1));
                    baseModele.setPrenom(result.getString(2));                
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
        @Override
    public List<Utilisateur> findAll()throws SQLException
        {
            List<Utilisateur> plat = new ArrayList<>();
            Connexion c = new Connexion();            
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
              String query="SELECT * from utilisateur ";
               state =conn.prepareStatement(query);  
              ResultSet result=state.executeQuery();
             // List<Utilisateur> listeResult=new ArrayList();
              
              while (result.next())
                {

                    String nom=result.getString(1);
                    String prenom=result.getString(2);
                    
                    Utilisateur listeResult= new Utilisateur(10,nom,prenom);
                    plat.add(listeResult)  ;  
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
        @Override
        public void insert(Utilisateur baseModele)throws SQLException
        {
            Connexion c = new Connexion();            
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
                String query="insert into utilisateur(nom,prenom) values('"+ baseModele.getNom() +"','"+ baseModele.getPrenom() +"')";
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
        @Override
        public void delete(Utilisateur baseModele)throws SQLException
        {
            Connexion c = new Connexion();            
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
                String query="delete from utilisateur where id='"+ baseModele.getId() +"'";
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
        @Override
        public void update(Utilisateur baseModele)throws SQLException
        {
            Connexion c = new Connexion();            
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
                String query="update utilisateur set nom='"+ baseModele.getNom() +"',prenom='"+ baseModele.getPrenom() +"' where id='"+ baseModele.getId() +"'";
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