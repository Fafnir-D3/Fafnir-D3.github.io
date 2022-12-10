package controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import dao.ContasDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MostrarContasTest {
    MostrarContas mostrarContas;
    HttpServletRequest requestMock;
    HttpServletResponse responseMock;
    HttpSession sessionMock;
    RequestDispatcher requestDispatcherMock;
    ContasDAO DaoAccount;

    @BeforeEach
    void setup() {
        DaoAccount = mock(ContasDAO.class);
        requestMock = mock(HttpServletRequest.class);
        responseMock = mock(HttpServletResponse.class);
        sessionMock = mock(HttpSession.class);
        requestDispatcherMock = mock(RequestDispatcher.class);
        mostrarContas = new MostrarContas();
    }

    @Test
    void testDoGet_seContaNaoLogada_entaoErro() throws IOException, ServletException {
        when(requestMock.getSession()).thenReturn(null);
        mostrarContas.doGet(requestMock, responseMock, DaoAccount);
        verify(responseMock).sendRedirect("Erro.jsp");
    }

    @Test
    void testDoget_seContaLogadaEUsuarioForValido_entaoErro() throws ServletException, IOException {
        when(requestMock.getSession()).thenReturn(sessionMock);
        mostrarContas.doGet(requestMock, responseMock, DaoAccount);
        verify(responseMock).sendRedirect("Erro.jsp");
    }

    @Test
    void testDoget_seContaLogadaEForAdministrador_entaoErro() throws ServletException, IOException {
        Usuario usuario = new Usuario(1, "Usuario Teste", "123.456.789-00", "123", "A");
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);
        assertThrows(
                NullPointerException.class,
                () -> mostrarContas.doGet(requestMock, responseMock, DaoAccount));
    }

    @Test
    void testDoget_seContaLogadaENaoForAdministrador_entaoErro()
            throws ServletException, IOException {
        Usuario usuario = new Usuario(1, "Usuario Teste", "123.456.789-00", "123", "C");
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(usuario);
        assertThrows(
                NullPointerException.class,
                () -> mostrarContas.doGet(requestMock, responseMock, DaoAccount));
    }
}
