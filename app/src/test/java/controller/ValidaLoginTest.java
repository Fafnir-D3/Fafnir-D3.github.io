package controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dao.AdministradoresDAO;
import dao.UsuariosDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;
import org.junit.jupiter.api.Test;

public class ValidaLoginTest {

  ValidaLogin controlador;

  @Test
  public void testDoPost_quandoValido_entaoRetornaUsuario() throws ServletException, IOException {
    UsuariosDAO usuariosDAO = mock(UsuariosDAO.class);
    AdministradoresDAO adminDAO = mock(AdministradoresDAO.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    controlador = new ValidaLogin(usuariosDAO, adminDAO);

    when(request.getParameter("cpf")).thenReturn("123.456.789-00");
    when(request.getParameter("senha")).thenReturn("123");
    when(request.getSession()).thenReturn(mock(HttpSession.class));
    when(usuariosDAO.valida("123.456.789-00", "123")).thenReturn(true);
    when(usuariosDAO.getUsuario("123.456.789-00"))
        .thenReturn(new Usuario(1, "Usuario Teste", "123.456.789-00", "123", "N"));

    controlador.doPost(request, response);
    verify(response).sendRedirect("MostrarCadastro");
  }
}
