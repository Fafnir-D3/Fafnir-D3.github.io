package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import model.Usuario;

@Slf4j
public class AdministradoresDAO {

  private Connection conexao;

  public AdministradoresDAO() {
    try {
      conexao = Conexao.criaConexao();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  // retorna todos os admins do BD
  public ArrayList<Usuario> getLista() {
    ArrayList<Usuario> retorno = new ArrayList<>();
    try (PreparedStatement preparedStatement =
            conexao.prepareStatement("select * from administradores;");
        ResultSet rs = preparedStatement.executeQuery(); ) {
      while (rs.next()) {
        retorno.add(
            new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("senha"),
                "A"));
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return retorno;
  }

  // retorna o admin com o cpf dado
  public Usuario getAdmin(String cpf) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("select * from administradores where cpf=?;")) {
      preparedStatement.setString(1, cpf);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        return rs.next()
            ? new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("senha"),
                "A")
            : null;
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return null;
  }

  // checa se o cpf e senha dados batem com um admin cadastrado no sistema
  public boolean valida(String cpf, String senha) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("select cpf, senha from administradores where cpf=?;"); ) {
      preparedStatement.setString(1, cpf);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          if (cpf.equals(rs.getString("cpf")) && senha.equals(rs.getString("senha"))) return true;
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }

    return false;
  }

  // exclui o admin de cpf dado
  public boolean exclui(String cpf) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("delete from administradores where id=?;")) {
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  // insere um novo admin no sistema. Ele é tratado como classe Usuario na função,
  // porem no SQL é tratado como admin
  public boolean insere(Usuario usuario) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("insert into administradores (nome,cpf,senha) values (?,?,?);")) {
      if (getAdmin(usuario.getCpf()) == null) { // checa se o cpf do admin ja existe
        preparedStatement.setString(1, usuario.getNome());
        preparedStatement.setString(2, usuario.getCpf());
        preparedStatement.setString(3, usuario.getSenha());
        preparedStatement.executeUpdate();
        return true;
      } else return false;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  // edita os dados de um admin no sistema. Ele é tratado como classe Usuario
  // na função, porem no SQL é tratado como admin
  public boolean edita(Usuario usuario, int id) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("update administradores set nome=?,cpf=?,senha=? where id=?;")) {
      if (usuario.getSuspenso().equals("A")) { // checa se é admin
        preparedStatement.setString(1, usuario.getNome());
        preparedStatement.setString(2, usuario.getCpf());
        preparedStatement.setString(3, usuario.getSenha());
        preparedStatement.setInt(4, id);
        preparedStatement.executeUpdate();
        return true;
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }
}
