package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public int getId(String descricao) {

    try {
      Statement stmt = conexao.createStatement();
      ResultSet rs =
          stmt.executeQuery("SELECT id FROM categorias where descricao='" + descricao + "';");

      if (rs.next()) {
        return (rs.getInt("id"));
      }
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return 0;
  }

  // retorna todas as categorias cadastradas no BD
  public ArrayList<String> getLista() {

    ArrayList<String> retorno = new ArrayList<>();

    try {
      Statement stmt = conexao.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT descricao FROM categorias;");

      while (rs.next()) {
        String aux = rs.getString("descricao");
        retorno.add(aux);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retorno;
  }

  public boolean insere(String descricao) {
    try {
      Statement stmt = conexao.createStatement();
      stmt.executeUpdate("insert into categorias (descricao) values ('" + descricao + "');");
      return true;
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public boolean edita(String descricao, String antigo) {
    try {
      Statement stmt = conexao.createStatement();
      if (getId(antigo) > 0) {
        stmt.executeUpdate(
            "update categorias set descricao='"
                + descricao
                + "' where id='"
                + getId(antigo)
                + "';");
        return true;
      }
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
    try {
      Statement stmt = conexao.createStatement();
      Statement stmt2 = conexao.createStatement();
      int idAntigo = getId(antigo);
      int idNovo = 0;
      if (idAntigo > 0) {
        ResultSet rs =
            stmt.executeQuery(
                "SELECT EXISTS(SELECT id FROM categorias where descricao='Sem Categoria') as existe;");
        if (rs.next()) {
          if (rs.getInt("existe") == 0) {
            stmt.executeUpdate("insert into categorias (descricao) values ('Sem Categoria');");
          }
        }
        rs = stmt.executeQuery("SELECT id FROM categorias where descricao='Sem Categoria'");
        if (rs.next()) {
          idNovo = rs.getInt("id");
        }
        rs = stmt.executeQuery("select id from lancamentos where id_categoria=" + idAntigo + ";");
        while (rs.next()) {
          stmt2.executeUpdate(
              "update lancamentos set id_categoria="
                  + idNovo
                  + " where id='"
                  + rs.getInt("id")
                  + "';");
        }
        stmt.executeUpdate("delete from categorias where id='" + idAntigo + "';");
        return true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }
}
