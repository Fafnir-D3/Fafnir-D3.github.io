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
import model.CategoriasDAO;

/*  Ideia:
    Tambem faz 2 funções, para admin leva para AdicionaCategoria, e para usuarios
comuns levapara Categorias.jsp. Além disso, precisei implementar um post para funcionar
vindo de uma inserção de nova categoria.
*/
@WebServlet(name = "MostrarCategorias", urlPatterns = {"/MostrarCategorias"})
public class MostrarCategorias extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session= request.getSession();
        if(session!=null){//checa login
            if(session.getAttribute("usuario")!=null){

                Usuario usuario=(Usuario)session.getAttribute("usuario");
                CategoriasDAO categoriasDAO = new CategoriasDAO();
                ArrayList<String> categorias= categoriasDAO.getLista();
                request.setAttribute("categorias", categorias);
                if(usuario.getSuspenso().equals("A")){//admin
                    RequestDispatcher rd=request.getRequestDispatcher("AdicionaCategoria.jsp");
                    rd.forward(request,response);
                }else{//usuario
                    RequestDispatcher rd=request.getRequestDispatcher("Categorias.jsp");
                    rd.forward(request,response);}
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
        request.setCharacterEncoding("UTF-8");
        CategoriasDAO DAO = new CategoriasDAO();
        HttpSession session= request.getSession();
        if(session!=null){//checa login
            if(session.getAttribute("usuario")!=null){

                Usuario usuario=(Usuario)session.getAttribute("usuario");
                if(usuario.getSuspenso().equals("A")){//admin
                    
                    String aux=(String)request.getParameter("categoria");
                    DAO.insere(aux);
                    ArrayList<String> categorias=DAO.getLista();
                    request.setAttribute("categorias", categorias);
                    RequestDispatcher rd=request.getRequestDispatcher("AdicionaCategoria.jsp");
                    rd.forward(request,response);
                }
            }
        }else{
            response.sendRedirect("Erro.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
