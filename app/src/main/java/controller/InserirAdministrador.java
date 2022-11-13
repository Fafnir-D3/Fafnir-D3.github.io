package controller;

import dao.AdministradoresDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;

@WebServlet(
    name = "InserirAdministrador",
    urlPatterns = {"/InserirAdministrador"})
public class InserirAdministrador extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.sendRedirect("Erro.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    AdministradoresDAO DAO = new AdministradoresDAO();
    HttpSession session = request.getSession();
    if (session != null) { // checa login
      if (session.getAttribute("usuario") != null) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario.getSuspenso().equals("A")) { // admin
          // uso 0 ao inserir para representar que é um dado novo, mas
          // o 0 não é mandado para o BD, uso o indice automatico.
          Usuario aux =
              new Usuario(
                  0,
                  (String) request.getParameter("nome"),
                  (String) request.getParameter("cpf"),
                  (String) request.getParameter("senha"),
                  "A");
          DAO.insere(aux);
          response.sendRedirect("MostrarAdministradores");
        } else {
          response.sendRedirect("Erro.jsp");
        }
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
