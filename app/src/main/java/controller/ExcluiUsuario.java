package controller;

import dao.UsuariosDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import model.Usuario;

@Slf4j
@WebServlet(
    name = "ExcluiUsuario",
    urlPatterns = {"/ExcluiUsuario"})
public class ExcluiUsuario extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    UsuariosDAO DAO = new UsuariosDAO();
    HttpSession session = request.getSession();
    if (session != null) { // checa login
      if (session.getAttribute("usuario") != null) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario.getSuspenso().equals("A")) { // admin
          try {
            DAO.exclui(request.getParameter("cpf"));
          } catch (SQLException e) {
            log.error(e.getMessage());
          }
          response.sendRedirect("MostrarCadastro");
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
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.sendRedirect("Erro.jsp");
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  } // </editor-fold>
}
