package controller;

import aplicacao.Conta;
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
import model.ContasDAO;

@WebServlet(name = "MostrarContas", urlPatterns = {"/MostrarContas"})
public class MostrarContas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ContasDAO DAO = new ContasDAO();
        HttpSession session= request.getSession();
        if(session!=null){//checa login
            if(session.getAttribute("usuario")!=null){

                Usuario usuario=(Usuario)session.getAttribute("usuario");
                String link;
                ArrayList<Conta> aux= new ArrayList<>();
                if(usuario.getSuspenso().equals("A")){//admin
                    aux= DAO.getLista();
                    link="ContasAdmin.jsp";
                }else{//usuario
                    aux= DAO.getLista(usuario.getCpf());
                    link="Contas.jsp";
                }
                request.setAttribute("contas", aux);
                RequestDispatcher rd=request.getRequestDispatcher(link);
                rd.forward(request,response);
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