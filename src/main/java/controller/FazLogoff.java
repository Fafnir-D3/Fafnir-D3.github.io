package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "FazLogoff", urlPatterns = {"/FazLogoff"})
public class FazLogoff extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session= request.getSession();
        if(session!=null){
            if(session.getAttribute("usuario")!=null){
                session.removeAttribute("usuario");//excluo tambem o atributo para garantir
            }
            session.invalidate();//exclui a sessão?
            response.sendRedirect("index.jsp");//volta para a pagina inicial de login
        }
        else{response.sendRedirect("Erro.jsp");}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session= request.getSession();
        if(session!=null){
            if(session.getAttribute("usuario")!=null){
                session.removeAttribute("usuario");//excluo tambem o atributo para garantir
            }
            session.invalidate();//exclui a sessão?
            response.sendRedirect("index.jsp");//volta para a pagina inicial de login
        }else{response.sendRedirect("Erro.jsp");}
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
