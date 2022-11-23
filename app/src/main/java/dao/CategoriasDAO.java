package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;

@WebServlet(
    name = "categoriasDAO",
    urlPatterns = {"/categoriasDAO"})
public class CategoriasDAO {

  private Connection conexao;

  public CategoriasDAO() {
    try {
      conexao = Conexao.criaConexao();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int getId(String descricao) {

    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("SELECT id FROM categorias where descricao=?;")) {
      preparedStatement.setString(1, descricao);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return (resultSet.getInt("id"));
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return 0;
  }

  // retorna todas as categorias cadastradas no BD
  public ArrayList<String> getLista() {

    ArrayList<String> retorno = new ArrayList<>();

    try (PreparedStatement preparedStatement =
            conexao.prepareStatement("SELECT descricao FROM categorias;");
        ResultSet resultSet = preparedStatement.executeQuery()) {
      while (resultSet.next()) {
        String aux = resultSet.getString("descricao");
        retorno.add(aux);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retorno;
  }

  public boolean insere(String descricao) {
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("insert into categorias (descricao) values (?);")) {
      preparedStatement.setString(1, descricao);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public boolean edita(String descricao, String antigo) {
    if (getId(antigo) <= 0) return false;
    try (PreparedStatement preparedStatement =
        conexao.prepareStatement("update categorias set descricao=? where id=?;")) {
      preparedStatement.setString(1, descricao);
      preparedStatement.setString(2, antigo);
      return true;
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  // Para excluir uma categoria que tem lançamentos vinculados a ela, crio uma
  // nova Categoria, "Sem Categoria" e adiciono os lançamentos a ela. Só depois
  // que a categoria que será excluida não tiver mais nenhum lançamento vinculado
  // eu posso excluir ela do BD
  public boolean exclui(String antigo) {
    int idAntigo = getId(antigo);
    if (idAntigo <= 0) return false;
    try (PreparedStatement preparedStatement =
            conexao.prepareStatement(
                "SELECT EXISTS(SELECT id FROM categorias where descricao='Sem Categoria') as existe;");
        ResultSet resultSet = preparedStatement.executeQuery(); ) {
      int idNovo = 0;
      if (resultSet.next() && resultSet.getInt("existe") == 0) {
        try (PreparedStatement segundoStatement =
            conexao.prepareStatement(
                "insert into categorias (descricao) values ('Sem Categoria');")) {
          segundoStatement.executeUpdate();
        }
      }
      try (PreparedStatement terceiroStatement =
              conexao.prepareStatement(
                  "SELECT id FROM categorias where descricao='Sem Categoria'");
          ResultSet segundoResult = terceiroStatement.executeQuery()) {
        if (segundoResult.next()) {
          idNovo = segundoResult.getInt("id");
        }
        try (PreparedStatement quartoStatement =
            conexao.prepareStatement("select id from lancamentos where id_categoria=?;")) {
          quartoStatement.setInt(1, idAntigo);
          try (ResultSet terceiroResult = quartoStatement.executeQuery();
              PreparedStatement quintoStatement =
                  conexao.prepareStatement("update lancamentos set id_categoria=? where id=?;")) {
            while (terceiroResult.next()) {
              quintoStatement.setInt(1, idNovo);
              quintoStatement.setInt(2, terceiroResult.getInt("id"));
              quintoStatement.executeUpdate();
            }
          }
        }
        try (PreparedStatement sextoStatement =
            conexao.prepareStatement("delete from categorias where id=?;")) {
          sextoStatement.setInt(1, idAntigo);
          sextoStatement.executeUpdate();
          return true;
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }
}
