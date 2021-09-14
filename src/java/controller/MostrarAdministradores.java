package controller;

import aplicacao.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdministradoresDAO;

@WebServlet(name = "MostrarAdministradores", urlPatterns = {"/MostrarAdministradores"})
public class MostrarAdministradores extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        AdministradoresDAO DAO = new AdministradoresDAO();
        HttpSession session= request.getSession();
        if(session!=null){//checa login
            if(session.getAttribute("usuario")!=null){

                Usuario usuario=(Usuario)session.getAttribute("usuario");
                if(usuario.getSuspenso().equals("A")){//admin
                    ArrayList<Usuario> administradores=DAO.getLista();
                    request.setAttribute("administradores", administradores);
                    RequestDispatcher rd=request.getRequestDispatcher("Administradores.jsp");
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