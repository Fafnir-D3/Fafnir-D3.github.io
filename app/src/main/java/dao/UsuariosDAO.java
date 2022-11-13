package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Conta;
import model.Usuario;

public class UsuariosDAO {

  private Connection conexao;

  public UsuariosDAO() {
    try {
      conexao = Conexao.criaConexao();
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  // retorna todos os usuarios cadastrados no BD
  public ArrayList<Usuario> getLista() {

    ArrayList<Usuario> retorno = new ArrayList<>();

    try {
      Statement stmt = conexao.createStatement();
      ResultSet rs = stmt.executeQuery("select * from usuarios;");

      while (rs.next()) {
        Usuario aux =
            new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("senha"),
                rs.getString("suspenso"));
        retorno.add(aux);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retorno;
  }

  // retorna o usuario de cpf dado
  public Usuario getUsuario(String cpf) {

    try {
      Statement stmt = conexao.createStatement();
      ResultSet rs = stmt.executeQuery("select * from usuarios where cpf='" + cpf + "';");
      if (rs.next()) {
        Usuario aux =
            new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("senha"),
                rs.getString("suspenso"));
        return aux;
      } else {
        rs = stmt.executeQuery("select * from administradores where cpf='" + cpf + "';");
        if (rs.next()) {
          Usuario aux =
              new Usuario(
                  rs.getInt("id"),
                  rs.getString("nome"),
                  rs.getString("cpf"),
                  rs.getString("senha"),
                  "A");
          return aux;
        }
      }

    } catch (SQLException ex) {
      Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  // checa se o cpf e senha dados batem com os de um usuario cadastrado no BD
  public boolean valida(String cpf, String senha) {
    Statement stmt;
    try {
      stmt = conexao.createStatement();
      ResultSet rs =
          stmt.executeQuery("select cpf, senha, suspenso from usuarios where cpf='" + cpf + "';");
      if (rs.next()) {
        if (cpf.equals(rs.getString("cpf"))
            && senha.equals(rs.getString("senha"))
            && rs.getString("suspenso").equals("N")) return true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  public boolean insere(Usuario usuario) {

    try {
      if (getUsuario(usuario.getCpf()) == null) { // checa se o cpf ja existe
        Statement stmt = conexao.createStatement();
        stmt.executeUpdate(
            "insert into usuarios (nome,cpf,senha,suspenso)values ('"
                + usuario.getNome()
                + "','"
                + usuario.getCpf()
                + "','"
                + usuario.getSenha()
                + "','"
                + usuario.getSuspenso()
                + "');");
        return true;
      } else return false;
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  // para excluir um usuario eu preciso excluir antes tudo que esta vinculado a ele
  public boolean exclui(String cpf) {

    try {
      ContasDAO DAO = new ContasDAO();
      ArrayList<Conta> contas = DAO.getLista(cpf);
      for (Conta aux : contas) { // excluindo todas as contas vinculadas
        DAO.exclui(
            aux.getConta_corrente()); // cada conta ja exclui todos os lancamentos vinculados a ela
        // antes
      }
      Statement stmt = conexao.createStatement();
      stmt.executeUpdate("delete from usuarios where id='" + getUsuario(cpf).getId() + "';");
      return true;
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public boolean edita(Usuario usuario, int id) {

    try {
      Statement stmt = conexao.createStatement();
      stmt.executeUpdate(
          "update usuarios set nome='"
              + usuario.getNome()
              + "',cpf='"
              + usuario.getCpf()
              + "',senha='"
              + usuario.getSenha()
              + "',Suspenso='"
              + usuario.getSuspenso()
              + "' where id='"
              + id
              + "';");
      return true;
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public boolean suspende(String cpf) {

    Usuario aux = getUsuario(cpf);
    String suspenso;
    if (aux.getSuspenso().equals("S")) {
      suspenso = "N";
    } else {
      suspenso = "S";
    }
    try {
      Statement stmt = conexao.createStatement();
      stmt.executeUpdate(
          "update usuarios set suspenso='" + suspenso + "' where id='" + aux.getId() + "';");
      return true;
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }
}
