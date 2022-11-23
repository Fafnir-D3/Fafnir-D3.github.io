package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import model.Lancamento;

/*  Ideia:
    Optei por usar o nome da conta e o nome da categoria invez dos respectivos IDs
para ser mais intuitivo para o usuario e pro programador. Por isso preciso acessar
algumas tabelas extras ao lidar com lançamentos.
*/
@Slf4j
public class LancamentosDAO {

  private Connection conexao;
  ContasDAO contasDAO;
  CategoriasDAO categoriasDAO;

  public LancamentosDAO() {
    try {
      conexao = Conexao.criaConexao();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    contasDAO = new ContasDAO();
    categoriasDAO = new CategoriasDAO();
  }

  public LancamentosDAO(ContasDAO contasDAO, CategoriasDAO categoriasDAO) {
    try {
      conexao = Conexao.criaConexao();
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    this.contasDAO = contasDAO;
    this.categoriasDAO = categoriasDAO;
  }

  // retorna todos os lançamentos cadastrados no BD.
  public ArrayList<Lancamento> getLista() {

    ArrayList<Lancamento> retorno = new ArrayList<>();

    try (PreparedStatement preparedStatement =
            conexao.prepareStatement(
                "select lancamentos.id,contas.nome_conta,categorias.descricao as categorias, "
                    + "valor, operacao, lancamentos.data, lancamentos.descricao from lancamentos "
                    + "inner join contas on lancamentos.id_conta=contas.id "
                    + "inner join categorias on lancamentos.id_categoria=categorias.id "
                    + "order by contas.nome_conta;");
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        retorno.add(
            new Lancamento(
                resultSet.getInt("id"),
                resultSet.getString("nome_conta"),
                resultSet.getString("categorias"),
                resultSet.getDouble("valor"),
                resultSet.getString("operacao"),
                resultSet.getString("data"),
                resultSet.getString("descricao")));
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return retorno;
  }

  // retorna todos os lançamentos do usuario de cpf dado
  public ArrayList<Lancamento> getLista(String cpf) {

    ArrayList<Lancamento> retorno = new ArrayList<>();

    try (PreparedStatement preparedStatement =
        conexao.prepareStatement(
            "select lancamentos.id,contas.nome_conta,categorias.descricao as categorias, "
                + "valor, operacao, lancamentos.data, lancamentos.descricao from lancamentos "
                + "inner join contas on lancamentos.id_conta=contas.id "
                + "inner join categorias on lancamentos.id_categoria=categorias.id "
                + "inner join usuarios on contas.id_usuario=usuarios.id "
                + "where usuarios.cpf=?;")) {
      preparedStatement.setString(1, cpf);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          retorno.add(
              new Lancamento(
                  resultSet.getInt("id"),
                  resultSet.getString("nome_conta"),
                  resultSet.getString("categorias"),
                  resultSet.getDouble("valor"),
                  resultSet.getString("operacao"),
                  resultSet.getString("data"),
                  resultSet.getString("descricao")));
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return retorno;
  }

  // retorna apenas os lançamentos da conta dada.
  public ArrayList<Lancamento> getLista(String cpf, int conta) {

    ArrayList<Lancamento> retorno = new ArrayList<>();

    try (PreparedStatement preparedStatement =
        conexao.prepareStatement(
            "select lancamentos.id,contas.nome_conta,categorias.descricao as categorias, "
                + "valor, operacao, lancamentos.data, lancamentos.descricao from lancamentos "
                + "inner join contas on lancamentos.id_conta=contas.id "
                + "inner join categorias on lancamentos.id_categoria=categorias.id "
                + "inner join usuarios on contas.id_usuario=usuarios.id "
                + "where usuarios.cpf=? AND lancamentos.id_conta=?;")) {
      preparedStatement.setString(1, cpf);
      preparedStatement.setInt(2, conta);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          retorno.add(
              new Lancamento(
                  resultSet.getInt("id"),
                  resultSet.getString("nome_conta"),
                  resultSet.getString("categorias"),
                  resultSet.getDouble("valor"),
                  resultSet.getString("operacao"),
                  resultSet.getString("data"),
                  resultSet.getString("descricao")));
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return retorno;
  }

  // retorna informações usadas pelo balancete para todos os lançamentos de uma conta
  public ArrayList<Lancamento> getBalancete(String cpf, int conta) {

    ArrayList<Lancamento> retorno = new ArrayList<>();

    try (PreparedStatement preparedStatement =
        conexao.prepareStatement(
            "select lancamentos.id,categorias.descricao as categorias, sum(valor) as valor, operacao from lancamentos "
                + "inner join contas on lancamentos.id_conta=contas.id "
                + "inner join categorias on lancamentos.id_categoria=categorias.id "
                + "inner join usuarios on contas.id_usuario=usuarios.id "
                + "where usuarios.cpf=? and lancamentos.id_conta=? "
                + "group by lancamentos.id_categoria;")) {
      preparedStatement.setString(1, cpf);
      preparedStatement.setInt(2, conta);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          retorno.add(
              new Lancamento(
                  resultSet.getInt("id"),
                  "",
                  resultSet.getString("categorias"),
                  resultSet.getDouble("valor"),
                  resultSet.getString("operacao"),
                  "",
                  ""));
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return retorno;
  }

  // retorna informações usadas para o balancete de uma conta num determinado mes
  public ArrayList<Lancamento> getBalancete(String cpf, int conta, String data) {

    ArrayList<Lancamento> retorno = new ArrayList<>();

    try (PreparedStatement preparedStatement =
        conexao.prepareStatement(
            "select lancamentos.id,categorias.descricao as categorias, sum(valor) as valor, operacao from lancamentos "
                + "inner join contas on lancamentos.id_conta=contas.id "
                + "inner join categorias on lancamentos.id_categoria=categorias.id "
                + "inner join usuarios on contas.id_usuario=usuarios.id "
                + "where usuarios.cpf=? and lancamentos.id_conta=? "
                + "and YEAR(lancamentos.data)=YEAR(?) and MONTH(lancamentos.data)=Month(?) "
                + "group by lancamentos.id_categoria;")) {
      preparedStatement.setString(1, cpf);
      preparedStatement.setInt(2, conta);
      preparedStatement.setString(3, data);
      preparedStatement.setString(4, data);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          retorno.add(
              new Lancamento(
                  resultSet.getInt("id"),
                  "",
                  resultSet.getString("categorias"),
                  resultSet.getDouble("valor"),
                  resultSet.getString("operacao"),
                  "",
                  ""));
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return retorno;
  }

  // exclui os lançamentos vinculados a uma conta dada. (usado ao excluir uma conta)
  public boolean excluiPorIdConta(int id) {

    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("select id from lancamentos where id_conta=?;")) {
      preparedStatement.setInt(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          try (PreparedStatement secondStatement =
              conexao.prepareStatement("delete from lancamentos where id=?;")) {
            preparedStatement.setInt(1, resultSet.getInt("id"));
            preparedStatement.executeUpdate();
          }
        }
        return true;
      }
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean exclui(int id) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("delete from lancamentos where id=?;")) {
      preparedStatement.setInt(1, id);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean edita(Lancamento lancamento) {
    if (lancamento.getId() <= 0) return false;
    int id_conta = contasDAO.idPorNome(lancamento.getNome_conta());
    if (id_conta <= 0) return false;
    int id_categoria = categoriasDAO.getId(lancamento.getCategoria());
    if (id_categoria <= 0) return false;
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement(
            "update lancamentos set id_conta=? ,id_categoria=? ,valor=? ,operacao=? "
                + ",data=? ,descricao=? where id=?;")) {
      preparedStatement.setInt(1, id_conta);
      preparedStatement.setInt(2, id_categoria);
      preparedStatement.setDouble(3, lancamento.getValor());
      preparedStatement.setString(4, lancamento.getOperacao());
      preparedStatement.setString(5, lancamento.getData());
      preparedStatement.setString(6, lancamento.getDescricao());
      preparedStatement.setInt(7, lancamento.getId());
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean insere(Lancamento lancamento) {
    if (lancamento.getId() != 0) return false;
    int id_conta = contasDAO.idPorNome(lancamento.getNome_conta());
    if (id_conta <= 0) return false;
    int id_categoria = categoriasDAO.getId(lancamento.getCategoria());
    if (id_categoria <= 0) return false;
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement(
            "insert into lancamentos (id_conta,id_categoria,valor,operacao,data,descricao) "
                + "values (?,?,?,?,?,?);")) {
      preparedStatement.setInt(1, id_conta);
      preparedStatement.setInt(2, id_categoria);
      preparedStatement.setDouble(3, lancamento.getValor());
      preparedStatement.setString(4, lancamento.getOperacao());
      preparedStatement.setString(5, lancamento.getData());
      preparedStatement.setString(6, lancamento.getDescricao());
      return true;
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return false;
  }
}
