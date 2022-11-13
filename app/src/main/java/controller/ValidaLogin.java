package controller;

import dao.AdministradoresDAO;
import dao.UsuariosDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import model.Usuario;

/*  Ideia:
   Usa session para guardar o usuario logado, a sessão dura 5minutos fora do browser
*/
@AllArgsConstructor
@WebServlet(
    name = "ValidaLogin",
    urlPatterns = {"/ValidaLogin"})
public class ValidaLogin extends HttpServlet {

  UsuariosDAO usuariosDAO;
  AdministradoresDAO adminDAO;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.sendRedirect("Erro.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String cpf = request.getParameter("cpf");
    String senha = request.getParameter("senha");
    HttpSession session = request.getSession();
    if (usuariosDAO.valida(cpf, senha)) { // checa se é usuario comum
      Usuario aux = (Usuario) usuariosDAO.getUsuario(cpf);
      if (aux.getSuspenso().equals("N")) { // checa se o usuario esta suspenso ( N = não suspenso)
        session.setAttribute("usuario", aux);
        response.sendRedirect("MostrarCadastro");
      }
    } else if (adminDAO.valida(cpf, senha)) { // checa se e administrador
      Usuario aux = (Usuario) adminDAO.getAdmin(cpf);
      session.setAttribute("usuario", aux);
      response.sendRedirect("MostrarCadastro");
    } else {
      response.sendRedirect("Erro.jsp");
    }
  }

  @Override
  public String getServletInfo() {
    return "Short description";
  } // </editor-fold>
}
