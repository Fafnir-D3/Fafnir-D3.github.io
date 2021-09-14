package controller;

import aplicacao.Lancamento;
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
import model.LancamentosDAO;

/*  Ideia:
    Tambem atende admin e usuarios, mas no caso de usuarios é possível pedir os
lançamentos de todas as contas do BD, ou apenas de uma conta específica. Diferencio
esses casos pelo parametro conta
 */
@WebServlet(name = "MostrarLancamentos", urlPatterns = {"/MostrarLancamentos"})
public class MostrarLancamentos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LancamentosDAO DAO = new LancamentosDAO();
        ArrayList<Lancamento> aux= new ArrayList<>();
        int contas=0;
        HttpSession session= request.getSession();
        if(session!=null){//checa login
            if(session.getAttribute("usuario")!=null){

                Usuario usuario=(Usuario)session.getAttribute("usuario");
                String link;
                if(usuario.getSuspenso().equals("A")){//admin
                    aux= DAO.getLista();
                    link="LancamentosAdmin.jsp";
                }else{//usuario
                    if(request.getParameter("conta")==null){//todas as contas
                    aux= DAO.getLista(usuario.getCpf());
                    }else{//conta especifica
                        aux= DAO.getLista(usuario.getCpf(),Integer.parseInt(request.getParameter("conta")));
                        contas=1;
                    }
                    link="Lancamentos.jsp";
                }

                request.setAttribute("lancamentos", aux);
                request.setAttribute("contas", contas);
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
