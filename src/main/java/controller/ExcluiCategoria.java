package controller;

import aplicacao.Usuario;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CategoriasDAO;

@WebServlet(name = "ExcluiCategoria", urlPatterns = {"/ExcluiCategoria"})
public class ExcluiCategoria extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        CategoriasDAO DAO = new CategoriasDAO();
        HttpSession session= request.getSession();
        if(session!=null){//checa login
            if(session.getAttribute("usuario")!=null){

                Usuario usuario=(Usuario)session.getAttribute("usuario");
                if(usuario.getSuspenso().equals("A")){//admin
                    
                    DAO.exclui((String)request.getParameter("categoria"));
                    RequestDispatcher rd=request.getRequestDispatcher("MostrarCategorias");
                    rd.forward(request,response);
                }else{
                    response.sendRedirect("Erro.jsp");
                }
            }else{
                response.sendRedirect("Erro.jsp");
            }
        }else{
            response.sendRedirect("Erro.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Erro.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}