package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
  public void testDoGet_quandoSempre_entaoErro() throws ServletException, IOException {
    HttpServletResponse response = mock(HttpServletResponse.class);
    controlador = new ValidaLogin(null, null);

    controlador.doGet(null, response);

    verify(response).sendRedirect("Erro.jsp");
  }

  @Test
  public void testGetServletInfo_quandoSempre_entaoShortDescription()
      throws ServletException, IOException {
    controlador = new ValidaLogin(null, null);

    String result = controlador.getServletInfo();

    assertEquals("Short description", result);
  }

  @Test
  public void testDoPost_quandoUsuarioValido_entaoRetornaUsuario()
      throws ServletException, IOException {
    // Arrange
    UsuariosDAO usuariosDAO = mock(UsuariosDAO.class);
    AdministradoresDAO adminDAO = mock(AdministradoresDAO.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    controlador = new ValidaLogin(usuariosDAO, adminDAO);
    Usuario usuario = new Usuario(1, "Usuario Teste", "123.456.789-00", "123", "N");

    // Mocks
    when(request.getParameter("cpf")).thenReturn("123.456.789-00");
    when(request.getParameter("senha")).thenReturn("123");
    when(request.getSession()).thenReturn(session);
    when(usuariosDAO.valida("123.456.789-00", "123")).thenReturn(true);
    when(usuariosDAO.getUsuario("123.456.789-00")).thenReturn(usuario);

    // Act
    controlador.doPost(request, response);

    // Assert
    verify(session).setAttribute("usuario", usuario);
    verify(response).sendRedirect("MostrarCadastro");
  }

  @Test
  public void testDoPost_quandoUsuarioSuspenso_entaoErro() throws ServletException, IOException {
    // Arrange
    UsuariosDAO usuariosDAO = mock(UsuariosDAO.class);
    AdministradoresDAO adminDAO = mock(AdministradoresDAO.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    controlador = new ValidaLogin(usuariosDAO, adminDAO);

    // Mocks
    when(request.getParameter("cpf")).thenReturn("123.456.789-00");
    when(request.getParameter("senha")).thenReturn("123");
    when(request.getSession()).thenReturn(mock(HttpSession.class));
    when(usuariosDAO.valida("123.456.789-00", "123")).thenReturn(true);
    when(usuariosDAO.getUsuario("123.456.789-00"))
        .thenReturn(new Usuario(1, "Usuario Teste", "123.456.789-00", "123", "S"));

    // Act
    controlador.doPost(request, response);

    // Assert
    verify(response).sendRedirect("Erro.jsp");
  }

  @Test
  public void testDoPost_quandoAdministradorValido_entaoRetornaAdministrador()
      throws ServletException, IOException {
    // Arrange
    UsuariosDAO usuariosDAO = mock(UsuariosDAO.class);
    AdministradoresDAO adminDAO = mock(AdministradoresDAO.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    controlador = new ValidaLogin(usuariosDAO, adminDAO);
    Usuario usuario = new Usuario(1, "Usuario Teste", "123.456.789-00", "123", "A");

    // Mocks
    when(request.getParameter("cpf")).thenReturn("123.456.789-00");
    when(request.getParameter("senha")).thenReturn("123");
    when(request.getSession()).thenReturn(session);
    when(usuariosDAO.valida("123.456.789-00", "123")).thenReturn(false);
    when(adminDAO.valida("123.456.789-00", "123")).thenReturn(true);
    when(adminDAO.getAdmin("123.456.789-00")).thenReturn(usuario);

    // Act
    controlador.doPost(request, response);

    // Assert
    verify(session).setAttribute("usuario", usuario);
    verify(response).sendRedirect("MostrarCadastro");
  }

  @Test
  public void testDoPost_quandoInvalido_entaoErro() throws ServletException, IOException {
    // Arrange
    UsuariosDAO usuariosDAO = mock(UsuariosDAO.class);
    AdministradoresDAO adminDAO = mock(AdministradoresDAO.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    controlador = new ValidaLogin(usuariosDAO, adminDAO);

    // Mocks
    when(request.getParameter("cpf")).thenReturn("123.456.789-00");
    when(request.getParameter("senha")).thenReturn("123");
    when(request.getSession()).thenReturn(mock(HttpSession.class));
    when(usuariosDAO.valida("123.456.789-00", "123")).thenReturn(false);
    when(adminDAO.valida("123.456.789-00", "123")).thenReturn(false);

    // Act
    controlador.doPost(request, response);

    // Assert
    verify(response).sendRedirect("Erro.jsp");
  }
}
