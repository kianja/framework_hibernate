import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.*;

import model.*;
import dao.*;

@WebServlet(name = "Generic", urlPatterns = {"/generic"})
public class GenericServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        //response.getWriter().print("Hello, World!");
            doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        //String name = request.getParameter("name");
        //if (name == null) name = "World";
        //request.setAttribute("user", name);
        //request.getRequestDispatcher("response.jsp").forward(request, response);
        try {
            //new PlatDAO().insert(new Categorie( "dessert"));
            GenericDAO<Plat> gDAO = new GenericDAO<>(new Plat());
            List<Plat> plats = gDAO.findAll();
            response.getWriter().println("size="+plats.size());
            Plat p = new Plat(44, "popo");
            //gDAO.update(p);
            p = new Plat(1, "hihihi");
            gDAO.insert(p);
            gDAO.delete(new Plat(2, "hihihi"));
            /*bgDAO.update(p);
	    response.getWriter().println("cat.nom="+p.getNom());
            GenericDAO<Categorie> gCatDAO = new GenericDAO<>(new Categorie());
            Categorie cat=new Categorie(2,"sorti");
            gCatDAO.insert(new Categorie(1, "Categorie 1"));
            gCatDAO.insert(new Categorie(1, "Categorie 2"));
            gCatDAO.findById(cat);

            List<Categorie> categories = gCatDAO.findAll();
            response.getWriter().println("result_categorie="+cat.getNom()+" nom:"+cat.getNom());
            response.getWriter().println("size_categories="+categories.size());
*/        } catch (Exception ex) {
            ex.printStackTrace(response.getWriter());
        }
    }
}
