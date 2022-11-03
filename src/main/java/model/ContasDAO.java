package model;

import aplicacao.Conta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContasDAO {
    
    private Connection conexao;
    public ContasDAO(){
        try {
            conexao=Conexao.criaConexao();
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //retorna todas as contas cadastradas no BD
    public ArrayList<Conta> getLista(){
        
        ArrayList<Conta> retorno=new ArrayList<>();
        
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contas");
            
            while(rs.next()){
                Conta aux = new Conta(rs.getInt("id"),rs.getString("nome_conta"),rs.getString("banco"),rs.getString("agencia"),rs.getString("conta_corrente"));
                retorno.add(aux);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return retorno;
    }
    
    //retorna todas as contas que pertencem ao usuario de cpf dado
    public ArrayList<Conta> getLista(String cpf){
        
        ArrayList<Conta> retorno=new ArrayList<>();
        
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select * from contas \n" +
"inner join usuarios\n" +
"on contas.id_usuario=usuarios.id\n" +
"where usuarios.cpf='"+cpf+"';");
            
            while(rs.next()){
                Conta aux = new Conta(rs.getInt("id"),rs.getString("nome_conta"),rs.getString("banco"),rs.getString("agencia"),rs.getString("conta_corrente"));
                retorno.add(aux);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return retorno;
    }
    
    //retorna a conta com o numero de conta-corrente dado
    public Conta getConta(String conta_corrente){
        Conta conta=null;
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select * from contas where conta_corrente='"+conta_corrente+"';");
            if(rs.next()){
                conta = new Conta(rs.getInt("id"),rs.getString("nome_conta"),rs.getString("banco"),rs.getString("agencia"),rs.getString("conta_corrente"));
            }
        }catch (SQLException ex) {Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);}
        return conta;
    }
    
    //retorna o id da conta de nome_conta dado
    public int idPorNome(String nome_conta){
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select id from contas where nome_conta='"+nome_conta+"';");
            if(rs.next()){
                return rs.getInt("id");
            }
        }catch (SQLException ex) {Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);}
        return 0;
    }
    
    
    public boolean insere(Conta conta,int id_usuario){
            try{
                Statement stmt = conexao.createStatement();
                stmt.executeUpdate("insert into contas (id_usuario,nome_conta,banco,agencia,conta_corrente) values ("+id_usuario+",'"+conta.getNome_conta()+"','"+conta.getBanco()+"','"+conta.getAgencia()+"','"+conta.getConta_corrente()+"');");
                return true;
            }catch (SQLException ex) {
                Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }
    
    public boolean edita(Conta conta){
            try{
                Statement stmt = conexao.createStatement();
                if(conta.getId()>0){
                stmt.executeUpdate("update contas set nome_conta='"+conta.getNome_conta()+"',banco='"+conta.getBanco()+"',agencia='"+conta.getAgencia()+"',conta_corrente='"+conta.getConta_corrente()+"' where id="+conta.getId()+";");
                return true;}
            }catch (SQLException ex) {
                Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }
    
    //para excluir a conta preciso excluir antes todos os lançamentos vinculados a ela
    public boolean exclui(String conta_corrente){
            try{
                Statement stmt = conexao.createStatement();
                int id=(getConta(conta_corrente)).getId();
                LancamentosDAO lDAO=new LancamentosDAO();
                lDAO.excluiPorIdConta(id); //preciso excluir os lancamentos vinculados a essa conta antes de excluir a conta
                stmt.executeUpdate("delete from contas where id="+id+";");
                return true;
            }catch (SQLException ex) {
                Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }
    
}
