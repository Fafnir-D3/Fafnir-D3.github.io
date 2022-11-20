package controller;

import dao.ContasDAO;
import dao.LancamentosDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;

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
        request.setAttribute("lancamentos", DAO.getBalancete(usuario.getCpf(), id, data));
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
