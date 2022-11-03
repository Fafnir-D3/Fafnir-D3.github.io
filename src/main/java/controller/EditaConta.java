package controller;

import aplicacao.Conta;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ContasDAO;

@WebServlet(
    name = "EditaConta",
    urlPatterns = {"/EditaConta"})
public class EditaConta extends HttpServlet {

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
    HttpSession session = request.getSession();
    if (session != null) { // checa login
      if (session.getAttribute("usuario") != null) {

        int id = Integer.parseInt((String) request.getParameter("id"));
        Conta conta =
            new Conta(
                id,
                (String) request.getParameter("nome_conta"),
                (String) request.getParameter("banco"),
                (String) request.getParameter("agencia"),
                (String) request.getParameter("conta_corrente"));
        DAO.edita(conta);
        response.sendRedirect("MostrarContas");
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
