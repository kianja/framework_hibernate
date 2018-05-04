import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import model.*;
import dao.*;

@WebServlet(name = "Insert", urlPatterns = {"/insert"})
public class Insert extends HttpServlet {
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
            new UtilisateurDAO().insert(new Utilisateur(12,"isis","eden"));
            new PlatDAO().insert(new Plat(10,  "dessert"));
            new PlatDAO().delete(new Plat(10,"misao"));

            new CategorieDAO().insert(new Categorie(46,"entree"));

            
        } catch (Exception ex) {}
    }
}
