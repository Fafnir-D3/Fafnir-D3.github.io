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
import model.UsuariosDAO;

@WebServlet(name = "SuspendeUsuario", urlPatterns = {"/SuspendeUsuario"})
public class SuspendeUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        request.setCharacterEncoding("UTF-8");
        UsuariosDAO DAO = new UsuariosDAO();
        HttpSession session= request.getSession();
        if(session!=null){
            if(session.getAttribute("usuario")!=null){
            Usuario aux=(Usuario)session.getAttribute("usuario");
                if(aux.getSuspenso().equals("A")){
                    DAO.suspende((String)request.getParameter("cpf"));
                    RequestDispatcher rd=request.getRequestDispatcher("MostrarCadastro");
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