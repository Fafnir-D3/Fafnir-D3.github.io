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
import model.ContasDAO;
import model.LancamentosDAO;

@WebServlet(
    name = "MostrarBalancete",
    urlPatterns = {"/MostrarBalancete"})
public class MostrarBalancete extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.sendRedirect("Erro.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    LancamentosDAO DAO = new LancamentosDAO();
    ArrayList<Lancamento> aux = new ArrayList<>();
    int id;
    String data;
    HttpSession session = request.getSession();
    if (session != null) { // checa login
      if (session.getAttribute("usuario") != null) {

        // Preciso acessar multiplos DAO ja que não guardo os IDs em lancamentos
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        ContasDAO cDAO = new ContasDAO();
        id = cDAO.idPorNome((String) request.getParameter("conta"));
        data = (String) request.getParameter("data");
        aux = DAO.getBalancete(usuario.getCpf(), id, data);
        request.setAttribute("lancamentos", aux);
        RequestDispatcher rd = request.getRequestDispatcher("BalanceteMes.jsp");
        rd.forward(request, response);
      } else {
        response.sendRedirect("Erro.jsp");
      }
    } else {
      response.sendRedirect("Erro.jsp");
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  } // </editor-fold>
}
