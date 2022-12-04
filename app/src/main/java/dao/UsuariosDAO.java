package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import model.Conta;
import model.Usuario;

@Slf4j
public class UsuariosDAO {

  private Connection conexao;
  private ContasDAO contasDAO;

  public UsuariosDAO() {
    try {
      conexao = Conexao.criaConexao();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    this.contasDAO = new ContasDAO();
  }

  public UsuariosDAO(ContasDAO contasDAO) {
    try {
      conexao = Conexao.criaConexao();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    this.contasDAO = contasDAO;
  }

  // retorna todos os usuarios cadastrados no BD
  public ArrayList<Usuario> getLista() {
    ArrayList<Usuario> retorno = new ArrayList<>();

    try (PreparedStatement preparedStatement = conexao.prepareStatement("select * from usuarios;");
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        retorno.add(
            new Usuario(
                resultSet.getInt("id"),
                resultSet.getString("nome"),
                resultSet.getString("cpf"),
                resultSet.getString("senha"),
                resultSet.getString("suspenso")));
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return retorno;
  }

  // retorna o usuario de cpf dado ou null
  public Usuario getUsuario(String cpf) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("select * from usuarios where cpf=?;")) {
      preparedStatement.setString(1, cpf);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return new Usuario(
              resultSet.getInt("id"),
              resultSet.getString("nome"),
              resultSet.getString("cpf"),
              resultSet.getString("senha"),
              resultSet.getString("suspenso"));
        } else {
          try (PreparedStatement secondStatement =
              conexao.prepareStatement("select * from administradores where cpf=?;")) {
            secondStatement.setString(1, cpf);
            try (ResultSet secondResult = secondStatement.executeQuery()) {
              if (secondResult.next()) {
                return new Usuario(
                    secondResult.getInt("id"),
                    secondResult.getString("nome"),
                    secondResult.getString("cpf"),
                    secondResult.getString("senha"),
                    "A");
              }
            }
          }
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return null;
  }

  // checa se o cpf e senha dados batem com os de um usuario cadastrado no BD
  public boolean valida(String cpf, String senha) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("select cpf, senha, suspenso from usuarios where cpf=?;")) {
      preparedStatement.setString(1, cpf);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          if (senha.equals(resultSet.getString("senha"))
              && resultSet.getString("suspenso").equals("N")) return true;
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean insere(Usuario usuario) {
    if (getUsuario(usuario.getCpf()) != null) return false; // checa se o cpf ja existe
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement(
            "insert into usuarios (nome,cpf,senha,suspenso) " + "values (?,?,?,?);")) {
      preparedStatement.setString(1, usuario.getNome());
      preparedStatement.setString(2, usuario.getCpf());
      preparedStatement.setString(3, usuario.getSenha());
      preparedStatement.setString(4, usuario.getSuspenso());
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  // para excluir um usuario eu preciso excluir antes tudo que esta vinculado a ele
  public boolean exclui(String cpf) {
    // excluindo todas as contas, e cada conta ja exclui todos os lancamentos vinculados a ela
    ArrayList<Conta> contas = contasDAO.getLista(cpf);
    for (Conta conta : contas) {
      contasDAO.exclui(conta.getConta_corrente());
    }
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("delete from usuarios where id=?;")) {
      preparedStatement.setInt(1, getUsuario(cpf).getId());
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean edita(Usuario usuario, int id) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("update usuarios set nome=? ,senha=? ,suspenso=? where id=?;")) {
      preparedStatement.setString(1, usuario.getNome());
      preparedStatement.setString(2, usuario.getSenha());
      preparedStatement.setString(3, usuario.getSuspenso());
      preparedStatement.setInt(4, id);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean suspende(String cpf) {
    Usuario usuario = getUsuario(cpf);
    String suspenso = usuario.getSuspenso().equals("S") ? "N" : "S";
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("update usuarios set suspenso=? where id=?;")) {
      preparedStatement.setString(1, suspenso);
      preparedStatement.setInt(2, usuario.getId());
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }
}
