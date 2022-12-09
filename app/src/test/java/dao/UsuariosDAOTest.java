package dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Conta;
import model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class UsuariosDAOTest {

  static UsuariosDAO dao;
  UsuariosDAO mockDao;

  @BeforeAll
  public static void setup() {
    dao = new UsuariosDAO();
  }

  @Test
  public void testConstrutor_quandoException_entaoLoga()
      throws SQLException, IOException, ClassNotFoundException {
    MockedStatic<Conexao> mockedStatic = mockStatic(Conexao.class);
    given(Conexao.criaConexao()).willThrow(new SQLException());
    assertDoesNotThrow(() -> mockDao = new UsuariosDAO());
    mockedStatic.close();
  }

  @Test
  public void testOutroConstrutor_quandoException_entaoLoga()
      throws SQLException, IOException, ClassNotFoundException {
    MockedStatic<Conexao> mockedStatic = mockStatic(Conexao.class);
    given(Conexao.criaConexao()).willThrow(new SQLException());
    assertDoesNotThrow(() -> mockDao = new UsuariosDAO(null));
    mockedStatic.close();
  }

  @Test
  public void testGetLista_quandoExistemUsuarios_entaoRetornaUsuarios() throws SQLException {
    List<Usuario> retorno = dao.getLista();
    assertTrue(retorno.size() >= 2);
  }

  @Test
  public void testGetUsuario_quandoExiste_entaoRetornaUsuario() throws SQLException {
    Usuario retorno = dao.getUsuario("870.304.050-05");
    assertEquals(1, retorno.getId());
  }

  @Test
  public void testGetUsuario_quandoAdmin_entaoRetornaAdmin() throws SQLException {
    Usuario retorno = dao.getUsuario("249.252.810-38");
    assertEquals(1, retorno.getId());
  }

  @Test
  public void testGetUsuario_quandoNaoExiste_entaoNull() throws SQLException {
    Usuario retorno = dao.getUsuario("101.202.303-40");
    assertNull(retorno);
  }

  @Test
  public void testValida_quandoValido_entaoTrue() throws SQLException {
    assertTrue(dao.valida("870.304.050-05", "111"));
  }

  @Test
  public void testValida_quandoSenhaErrada_entaoFalse() throws SQLException {
    assertFalse(dao.valida("870.304.050-05", "123"));
  }

  @Test
  public void testValida_quandoSuspenso_entaoFalse() throws SQLException {
    dao.suspende("870.304.050-05");
    assertFalse(dao.valida("870.304.050-05", "111"));
    dao.suspende("870.304.050-05");
  }

  @Test
  public void testValida_quandoNaoExiste_entaoFalse() throws SQLException {
    assertFalse(dao.valida("nao existe", "111"));
  }

  @Test
  public void testInsere_quandoValido_entaoRetornaIdNovo() throws SQLException {
    dao.insere(new Usuario(0, "Teste", "123.456.789-00", "123", "N"));
    Usuario retorno = dao.getUsuario("123.456.789-00");
    assertEquals("Teste", retorno.getNome());
    assertTrue(retorno.getId() > 0);
  }

  @Test
  public void testInsere_quandoJaExiste_entaoFalse() throws SQLException {
    assertFalse(dao.insere(new Usuario(0, "Teste", "870.304.050-05", "123", "N")));
  }

  @Test
  public void testExclui_quandoExiste_entaoExclui() throws SQLException {
    dao.insere(new Usuario(0, "Teste", "987.654.321-00", "123", "N"));
    assertTrue(dao.getUsuario("987.654.321-00").getId() >= 0);
    dao.exclui("987.654.321-00");
    assertNull(dao.getUsuario("987.654.321-00"));
  }

  @Test
  public void testExclui_quandoContaVinculada_entaoExcluiTambem() throws SQLException {
    dao.insere(new Usuario(0, "Teste", "987.654.321-01", "123", "N"));
    int id = dao.getUsuario("987.654.321-01").getId();
    assertTrue(id >= 0);
    ContasDAO contasDAO = new ContasDAO();
    contasDAO.insere(new Conta(0, "nome", "ban", "agenci", "contac"), id);
    dao.exclui("987.654.321-01");
    assertNull(dao.getUsuario("987.654.321-01"));
    assertNull(contasDAO.getConta("contac"));
  }

  @Test
  public void testEdita_quandoExiste_entaoEdita() throws SQLException {
    // CPF original: 167.740.300-41, quando editado o CPF continua o mesmo, mas os outros dados sao
    // alterados
    dao.edita(new Usuario(2, "Editado", "123.987.465-90", "999", "S"), 2);
    assertNull(dao.getUsuario("123.987.465-90"));
    assertEquals("Editado", dao.getUsuario("167.740.300-41").getNome());
  }

  @Test
  public void testSuspende_quandoQualquer_entaoTroca() throws SQLException {
    String primeiro = dao.getUsuario("167.740.300-41").getSuspenso();
    dao.suspende("167.740.300-41");
    String novo = dao.getUsuario("167.740.300-41").getSuspenso();
    // O suspenso anterior eh diferente do atual
    assertNotEquals(primeiro, novo);
    String segundo = novo;
    // Suspende mais uma vez e os resultados continuam diferentes
    dao.suspende("167.740.300-41");
    novo = dao.getUsuario("167.740.300-41").getSuspenso();
    assertNotEquals(segundo, novo);
    // O novo resultado volta a ser igual ao primeiro
    assertEquals(primeiro, novo);
  }

  @Test
  public void testConstrutor_quandoNull_entaoConstroi() {
    mockDao = new UsuariosDAO(null);
    assertNotNull(mockDao);
  }

  @Test
  public void testGetUsuario_quandoPrepareStatementException_entaoThrow()
      throws SQLException, IOException, ClassNotFoundException {
    MockedStatic<Conexao> mockedStatic = mockStatic(Conexao.class);
    Connection mock = mock(Connection.class);
    given(Conexao.criaConexao()).willReturn(mock);
    when(mock.prepareStatement("select * from usuarios where cpf=?;"))
        .thenThrow(new SQLException());
    mockDao = new UsuariosDAO();

    assertThrows(SQLException.class, () -> mockDao.getUsuario("cpf"));
    mockedStatic.close();
  }

  @Test
  public void testGetUsuario_quandoExecuteQueryException_entaoThrow()
      throws SQLException, IOException, ClassNotFoundException {
    MockedStatic<Conexao> mockedStatic = mockStatic(Conexao.class);
    Connection mock = mock(Connection.class);
    given(Conexao.criaConexao()).willReturn(mock);
    PreparedStatement statement = mock(PreparedStatement.class);
    when(mock.prepareStatement(any())).thenReturn(statement);
    when(statement.executeQuery()).thenThrow(new SQLException());
    mockDao = new UsuariosDAO();

    assertThrows(SQLException.class, () -> mockDao.getUsuario("cpf"));
    mockedStatic.close();
  }

  @Test
  public void testGetUsuario_quandoAdminPrepareStatementException_entaThrow()
      throws SQLException, IOException, ClassNotFoundException {
    MockedStatic<Conexao> mockedStatic = mockStatic(Conexao.class);
    Connection mock = mock(Connection.class);
    given(Conexao.criaConexao()).willReturn(mock);
    PreparedStatement statement = mock(PreparedStatement.class);
    when(mock.prepareStatement(any())).thenReturn(statement).thenThrow(new SQLException());
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(false);
    when(statement.executeQuery()).thenReturn(resultSet);
    mockDao = new UsuariosDAO();

    assertThrows(SQLException.class, () -> mockDao.getUsuario("cpf"));
    mockedStatic.close();
  }

  @Test
  public void testGetUsuario_quandoAdminExecuteQueryException_entaoThrow()
      throws SQLException, IOException, ClassNotFoundException {
    MockedStatic<Conexao> mockedStatic = mockStatic(Conexao.class);
    Connection mock = mock(Connection.class);
    given(Conexao.criaConexao()).willReturn(mock);
    PreparedStatement statement = mock(PreparedStatement.class);
    when(mock.prepareStatement(any())).thenReturn(statement);
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(false);
    when(statement.executeQuery()).thenReturn(resultSet).thenThrow(new SQLException());
    mockDao = new UsuariosDAO();

    assertThrows(SQLException.class, () -> mockDao.getUsuario("cpf"));
    mockedStatic.close();
  }
}
