package model;

import aplicacao.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministradoresDAO {

  private Connection conexao;

  public AdministradoresDAO() {
    try {
      conexao = Conexao.criaConexao();
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  // retorna todos os admins do BD
  public ArrayList<Usuario> getLista() {

    ArrayList<Usuario> retorno = new ArrayList<>();

    try {
      Statement stmt = conexao.createStatement();
      ResultSet rs = stmt.executeQuery("select * from administradores;");

      while (rs.next()) {
        Usuario aux =
            new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("senha"),
                "A");
        retorno.add(aux);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retorno;
  }

  // retorna o admin com o cpf dado
  public Usuario getAdmin(String cpf) {

    try {
      Statement stmt = conexao.createStatement();
      ResultSet rs = stmt.executeQuery("select * from administradores where cpf='" + cpf + "';");
      if (rs.next()) {
        Usuario aux =
            new Usuario(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("senha"),
                "A");
        return aux;
      } else {
        return null;
      }
    } catch (SQLException ex) {
      Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  // checa se o cpf e senha dados batem com um admin cadastrado no sistema
  public boolean valida(String cpf, String senha) {
    Statement stmt;
    try {
      stmt = conexao.createStatement();
      ResultSet rs =
          stmt.executeQuery("select cpf, senha from administradores where cpf='" + cpf + "';");
      if (rs.next()) {
        if (cpf.equals(rs.getString("cpf")) && senha.equals(rs.getString("senha"))) return true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  // exclui o admin de cpf dado
  public boolean exclui(String cpf) {

    try {
      Statement stmt = conexao.createStatement();
      stmt.executeUpdate("delete from administradores where id='" + getAdmin(cpf).getId() + "';");
      return true;
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  // insere um novo admin no sistema. Ele é tratado como classe Usuario na função,
  // porem no SQL é tratado como admin
  public boolean insere(Usuario usuario) {

    try {
      if (getAdmin(usuario.getCpf()) == null) { // checa se o cpf do admin ja existe
        Statement stmt = conexao.createStatement();
        stmt.executeUpdate(
            "insert into administradores (nome,cpf,senha)values ('"
                + usuario.getNome()
                + "','"
                + usuario.getCpf()
                + "','"
                + usuario.getSenha()
                + "');");
        return true;
      } else return false;
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  // edita os dados de um admin no sistema. Ele é tratado como classe Usuario
  // na função, porem no SQL é tratado como admin
  public boolean edita(Usuario usuario, int id) {

    try {
      Statement stmt = conexao.createStatement();
      if (usuario.getSuspenso().equals("A")) { // checa se é admin
        stmt.executeUpdate(
            "update administradores set nome='"
                + usuario.getNome()
                + "',cpf='"
                + usuario.getCpf()
                + "',senha='"
                + usuario.getSenha()
                + "' where id='"
                + id
                + "';");
        return true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }
}
