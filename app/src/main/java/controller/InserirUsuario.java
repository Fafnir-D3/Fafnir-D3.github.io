package controller;

import aplicacao.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.UsuariosDAO;

@WebServlet(
    name = "InserirUsuario",
    urlPatterns = {"/InserirUsuario"})
public class InserirUsuario extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.sendRedirect("Erro.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    UsuariosDAO DAO = new UsuariosDAO();
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
                  (String) request.getParameter("suspenso"));
          DAO.insere(aux);
          // MostrarCadatro nesse caso me redireciona para Usuarios.jsp
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
  public String getServletInfo() {
    return "Short description";
  } // </editor-fold>
}
