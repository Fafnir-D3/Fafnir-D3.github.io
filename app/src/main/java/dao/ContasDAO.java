package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import model.Conta;

@Slf4j
public class ContasDAO {

  private Connection conexao;
  private LancamentosDAO lancamentosDAO;

  public ContasDAO() {
    try {
      conexao = Conexao.criaConexao();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    lancamentosDAO = new LancamentosDAO(this, new CategoriasDAO());
  }

  public ContasDAO(LancamentosDAO lancamentosDAO) {
    try {
      conexao = Conexao.criaConexao();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    this.lancamentosDAO = lancamentosDAO;
  }

  // retorna todas as contas cadastradas no BD
  public ArrayList<Conta> getLista() {

    ArrayList<Conta> retorno = new ArrayList<>();

    try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM contas");
        ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        retorno.add(
            new Conta(
                resultSet.getInt("id"),
                resultSet.getString("nome_conta"),
                resultSet.getString("banco"),
                resultSet.getString("agencia"),
                resultSet.getString("conta_corrente")));
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return retorno;
  }

  // retorna todas as contas que pertencem ao usuario de cpf dado
  public ArrayList<Conta> getLista(String cpf) {

    ArrayList<Conta> retorno = new ArrayList<>();

    try (PreparedStatement preparedStatement =
        conexao.prepareStatement(
            "select * from contas inner join usuarios on contas.id_usuario=usuarios.id where usuarios.cpf=?;")) {
      preparedStatement.setString(1, cpf);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          retorno.add(
              new Conta(
                  resultSet.getInt("id"),
                  resultSet.getString("nome_conta"),
                  resultSet.getString("banco"),
                  resultSet.getString("agencia"),
                  resultSet.getString("conta_corrente")));
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }

    return retorno;
  }

  // retorna a conta com o numero de conta-corrente dado
  public Conta getConta(String conta_corrente) {
    Conta conta = null;
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("select * from contas where conta_corrente=?;")) {
      preparedStatement.setString(1, conta_corrente);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          conta =
              new Conta(
                  resultSet.getInt("id"),
                  resultSet.getString("nome_conta"),
                  resultSet.getString("banco"),
                  resultSet.getString("agencia"),
                  resultSet.getString("conta_corrente"));
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return conta;
  }

  // retorna o id da conta de nome_conta dado
  public int idPorNome(String nome_conta) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("select id from contas where nome_conta=?;")) {
      preparedStatement.setString(1, nome_conta);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getInt("id");
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return 0;
  }

  public boolean insere(Conta conta, int id_usuario) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement(
            "insert into contas (id_usuario,nome_conta,banco,agencia,conta_corrente) values (?,?,?,?,?);")) {
      preparedStatement.setInt(1, id_usuario);
      preparedStatement.setString(2, conta.getNome_conta());
      preparedStatement.setString(3, conta.getBanco());
      preparedStatement.setString(4, conta.getAgencia());
      preparedStatement.setString(5, conta.getConta_corrente());
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean edita(Conta conta) {
    if (conta.getId() <= 0) return false;
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement(
            "update contas set nome_conta=? , banco=? , agencia=? , conta_corrente=? where id=?;")) {
      preparedStatement.setString(1, conta.getNome_conta());
      preparedStatement.setString(2, conta.getBanco());
      preparedStatement.setString(3, conta.getAgencia());
      preparedStatement.setString(4, conta.getConta_corrente());
      preparedStatement.setInt(5, conta.getId());
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  // para excluir a conta preciso excluir antes todos os lançamentos vinculados a ela
  public boolean exclui(String conta_corrente) {
    int id = (getConta(conta_corrente)).getId();
    if (id <= 0) return false;
    // preciso excluir os lancamentos vinculados a essa conta antes de excluir a conta
    lancamentosDAO.excluiPorIdConta(id);
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("delete from contas where id=?;")) {
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }
}
