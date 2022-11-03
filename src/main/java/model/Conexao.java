package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;

/*  Necessaria para o funcionamento dos demais DAO
*/
public class Conexao extends HttpServlet {
    
    private static Connection conexao=null;
    
    public static Connection criaConexao() throws SQLException{
        
        if (conexao == null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conexao= DriverManager.getConnection("jdbc:mysql://localhost:3306/financeiro","root","");
            }catch(ClassNotFoundException e){
                System.out.println("erro");
            }
        }
        
        return conexao;
        
    }
}
