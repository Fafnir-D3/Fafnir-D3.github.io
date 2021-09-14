package controller;

import aplicacao.Conta;
import aplicacao.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ContasDAO;

@WebServlet(name = "InserirConta", urlPatterns = {"/InserirConta"})
public class InserirConta extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Erro.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        request.setCharacterEncoding("UTF-8");
        ContasDAO DAO = new ContasDAO();
        HttpSession session= request.getSession();
        if(session!=null){//checa login
            if(session.getAttribute("usuario")!=null){

                Usuario usuario=(Usuario)session.getAttribute("usuario");
                //uso 0 ao inserir para representar que é um dado novo, mas
                //o 0 não é mandado para o BD, uso o indice automatico.
                Conta aux=new Conta(0,(String)request.getParameter("nome_conta"),(String)request.getParameter("banco"),(String)request.getParameter("agencia"),(String)request.getParameter("conta_corrente"));
                DAO.insere(aux, usuario.getId());
                response.sendRedirect("MostrarContas");
            }else{
                response.sendRedirect("Erro.jsp");
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