package dao;

import java.util.*;
import java.sql.*;
import model.*;
import connexion.Connexion;
public class CategorieDAO implements InterfaceDAO<Categorie>{
    @Override
    public void findById(Categorie baseModele)throws SQLException
        {

            Connexion c = new Connexion();
            Connection conn = c.getConnect();

            PreparedStatement state=null;
             try
            {
            String query="SELECT * from categorie where id='" + baseModele.getId() + "' ";
             state =conn.prepareStatement(query);
            ResultSet result=state.executeQuery();
            if(result.next())
            {
            baseModele.setNom(result.getString(2));
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
        public List<Categorie> findAll() throws SQLException {
    	    return findAll(-1, -1);
        }

        @Override
        public List<Categorie> findAll(int page, int nbPage) throws SQLException {
            List<Categorie> plat = new ArrayList<>();
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


              while (result.next())
                {

              //      int categorie=result.getInt(1);
                    int id=result.getInt(1);
                    String nom_categorie=result.getString(2);

                    Categorie listeResult= new Categorie(id, nom_categorie);
                    plat.add(listeResult) ;
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
        public void insert(Categorie baseModele)throws SQLException
        {
            Connexion c = new Connexion();
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
                String query="insert into categorie(nom) values('"+ baseModele.getNom() +"')";
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
        public void delete(Categorie baseModele)throws SQLException
        {
            Connexion c = new Connexion();
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
                String query="delete from categorie where id='"+ baseModele.getId() +"'";
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
        public void update(Categorie baseModele)throws SQLException
        {
            Connexion c = new Connexion();
            Connection conn = c.getConnect();
            PreparedStatement state=null;
             try
            {
                String query="update categorie set categorie='"+ baseModele.getNom() +"' where id='"+ baseModele.getId() +"'";
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
