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
import model.UsuariosDAO;

/*  Ideia:
    MostrarCadastro é usada para 2 situações, redirecionar para a pagina Usuarios.jsp
para os administradores, e MeuCadastro.jsp para os usuarios comuns.
*/
@WebServlet(
    name = "MostrarCadastro",
    urlPatterns = {"/MostrarCadastro"})
public class MostrarCadastro extends HttpServlet {

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

          ArrayList<Usuario> usuarios = DAO.getLista();
          request.setAttribute("usuarios", usuarios);
          RequestDispatcher rd = request.getRequestDispatcher("Usuarios.jsp");
          rd.forward(request, response);
        } else { // usuario
          request.setAttribute("usuario", usuario);
          RequestDispatcher rd = request.getRequestDispatcher("MeuCadastro.jsp");
          rd.forward(request, response);
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
