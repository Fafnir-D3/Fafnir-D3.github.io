package controller;

import dao.LancamentosDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Lancamento;

@WebServlet(
    name = "InserirLancamento",
    urlPatterns = {"/InserirLancamento"})
public class InserirLancamento extends HttpServlet {

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
    HttpSession session = request.getSession();
    if (session != null) { // checa login
      if (session.getAttribute("usuario") != null) {

        double valor = Double.parseDouble((String) request.getParameter("valor"));
        // uso 0 ao inserir para representar que é um dado novo, mas
        // o 0 não é mandado para o BD, uso o indice automatico.
        Lancamento aux =
            new Lancamento(
                0,
                (String) request.getParameter("nome_conta"),
                (String) request.getParameter("categoria"),
                valor,
                (String) request.getParameter("operacao"),
                (String) request.getParameter("data"),
                (String) request.getParameter("descricao"));
        DAO.insere(aux);
        response.sendRedirect("MostrarLancamentos");
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
