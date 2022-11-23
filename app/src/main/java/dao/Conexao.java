package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.http.HttpServlet;

/*  Necessaria para o funcionamento dos demais DAO
 */
public class Conexao extends HttpServlet {

  private static Connection conexao = null;
  private static Properties properties = new Properties();

  public static Connection criaConexao() throws SQLException, IOException, ClassNotFoundException {

    if (conexao == null) {
      InputStream is = Conexao.class.getClassLoader().getResourceAsStream("application.properties");
      properties.load(is);
      Class.forName(properties.getProperty("db.driver"));
      conexao =
          DriverManager.getConnection(
              properties.getProperty("db.url"),
              properties.getProperty("db.user"),
              properties.getProperty("db.password"));
    }
    return conexao;
  }
}
