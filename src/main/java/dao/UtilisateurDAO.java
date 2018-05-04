package dao;

import java.util.*;
import java.sql.*;
import model.*;
import connexion.Connexion;
public class UtilisateurDAO implements InterfaceDAO<Utilisateur>{
	@Override
        public List<Utilisateur> findAll() throws SQLException {
    	    return findAll(-1, -1);
        }

        @Override
        public List<Utilisateur> findAll(int page, int nbPage) throws SQLException {
            List<Utilisateur> plat = new ArrayList<>();
            Connexion c = new Connexion();
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
		    String pagination = "";
      	  if (page >= 0 && nbPage > 0)
      		  pagination = " LIMIT " + (page*nbPage) + ", " + nbPage;
      	  String query = "SELECT * FROM " + Plat.getTable() + pagination;
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
        public void findById(Utilisateur obj) throws SQLException {
            Connexion c = new Connexion();
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
      	  String query = "SELECT * FROM utilisateur WHERE id="+obj.getId();
               state =conn.prepareStatement(query);
              ResultSet result=state.executeQuery();
             // List<Utilisateur> listeResult=new ArrayList();

              if (result.next())
                {

                    String nom=result.getString(1);
                    String prenom=result.getString(2);

                    obj.setNom(nom); obj.setPrenom(prenom);
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
