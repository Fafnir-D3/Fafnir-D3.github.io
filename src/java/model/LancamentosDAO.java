package model;

import aplicacao.Lancamento;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*  Ideia:
    Optei por usar o nome da conta e o nome da categoria invez dos respectivos IDs
para ser mais intuitivo para o usuario e pro programador. Por isso preciso acessar
algumas tabelas extras ao lidar com lançamentos.
*/
public class LancamentosDAO {
    
    private Connection conexao;
    public LancamentosDAO(){
        try {
            conexao=Conexao.criaConexao();
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //retorna todos os lançamentos cadastrados no BD.
    public ArrayList<Lancamento> getLista(){
        
        ArrayList<Lancamento> retorno=new ArrayList<>();
        
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select lancamentos.id,contas.nome_conta,categorias.descricao as categorias, valor, operacao, lancamentos.data, lancamentos.descricao from lancamentos\n" +
"inner join contas\n" +
"on lancamentos.id_conta=contas.id\n" +
"inner join categorias\n" +
"on lancamentos.id_categoria=categorias.id\n" +
"order by contas.nome_conta;");
            
            while(rs.next()){
                Lancamento aux = new Lancamento(rs.getInt("id"),rs.getString("nome_conta"),rs.getString("categorias"),rs.getDouble("valor"),rs.getString("operacao"),rs.getString("data"),rs.getString("descricao"));
                retorno.add(aux);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return retorno;
    }
    
    //retorna todos os lançamentos do usuario de cpf dado
    public ArrayList<Lancamento> getLista(String cpf){
        
        ArrayList<Lancamento> retorno=new ArrayList<>();
        
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select lancamentos.id,contas.nome_conta,categorias.descricao as categorias, valor, operacao, lancamentos.data, lancamentos.descricao from lancamentos\n" +
"inner join contas\n" +
"on lancamentos.id_conta=contas.id\n" +
"inner join categorias\n" +
"on lancamentos.id_categoria=categorias.id\n" +
"inner join usuarios\n" +
"on contas.id_usuario=usuarios.id\n" +
"where usuarios.cpf='"+cpf+"';");
            
            while(rs.next()){
                Lancamento aux = new Lancamento(rs.getInt("id"),rs.getString("nome_conta"),rs.getString("categorias"),rs.getDouble("valor"),rs.getString("operacao"),rs.getString("data"),rs.getString("descricao"));
                retorno.add(aux);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return retorno;
    }
    
    //retorna apenas os lançamentos da conta dada.
    public ArrayList<Lancamento> getLista(String cpf,int conta){
        
        ArrayList<Lancamento> retorno=new ArrayList<>();
        
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select lancamentos.id,contas.nome_conta,categorias.descricao as categorias, valor, operacao, lancamentos.data, lancamentos.descricao from lancamentos\n" +
"inner join contas\n" +
"on lancamentos.id_conta=contas.id\n" +
"inner join categorias\n" +
"on lancamentos.id_categoria=categorias.id\n" +
"inner join usuarios\n" +
"on contas.id_usuario=usuarios.id\n" +
"where usuarios.cpf='"+cpf+"' AND lancamentos.id_conta="+conta+";");
            
            while(rs.next()){
                Lancamento aux = new Lancamento(rs.getInt("id"),rs.getString("nome_conta"),rs.getString("categorias"),rs.getDouble("valor"),rs.getString("operacao"),rs.getString("data"),rs.getString("descricao"));
                retorno.add(aux);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    //retorna informações usadas pelo balancete para todos os lançamentos de uma conta
    public ArrayList<Lancamento> getBalancete(String cpf,int conta){
        
        ArrayList<Lancamento> retorno=new ArrayList<>();
        
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select lancamentos.id,categorias.descricao as categorias, sum(valor) as valor, operacao from lancamentos\n" +
"inner join contas\n" +
"on lancamentos.id_conta=contas.id\n" +
"inner join categorias\n" +
"on lancamentos.id_categoria=categorias.id\n" +
"inner join usuarios\n" +
"on contas.id_usuario=usuarios.id\n" +
"where usuarios.cpf='"+cpf+"' and lancamentos.id_conta="+conta+"\n" +
"group by lancamentos.id_categoria;");
            
            while(rs.next()){
                Lancamento aux = new Lancamento(rs.getInt("id"),"",rs.getString("categorias"),rs.getDouble("valor"),rs.getString("operacao"),"","");
                retorno.add(aux);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    //retorna informações usadas para o balancete de uma conta num determinado mes
    public ArrayList<Lancamento> getBalancete(String cpf,int conta,String data){
        
        ArrayList<Lancamento> retorno=new ArrayList<>();
        
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select lancamentos.id,categorias.descricao as categorias, sum(valor) as valor, operacao from lancamentos\n" +
"inner join contas\n" +
"on lancamentos.id_conta=contas.id\n" +
"inner join categorias\n" +
"on lancamentos.id_categoria=categorias.id\n" +
"inner join usuarios\n" +
"on contas.id_usuario=usuarios.id\n" +
"where usuarios.cpf='"+cpf+"' and lancamentos.id_conta="+conta+" and YEAR(lancamentos.data)=YEAR('"+data+"') and MONTH(lancamentos.data)=Month('"+data+"')\n" +
"group by lancamentos.id_categoria;");
            
            while(rs.next()){
                Lancamento aux = new Lancamento(rs.getInt("id"),"",rs.getString("categorias"),rs.getDouble("valor"),rs.getString("operacao"),"","");
                retorno.add(aux);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    //exclui os lançamentos vinculados a uma conta dada. (usado ao excluir uma conta)
    public boolean excluiPorIdConta(int id){
        
        try {
            Statement stmt = conexao.createStatement();
            Statement stmt2 = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("select id from lancamentos where id_conta="+id+";");
            while(rs.next()){
                stmt2.executeUpdate("delete from lancamentos where id="+rs.getInt("id")+";");
            }return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean exclui(int id){
        
        try {
            Statement stmt = conexao.createStatement();
            stmt.executeUpdate("delete from lancamentos where id="+id+";");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean edita(Lancamento lancamento){
        
        try {
            Statement stmt = conexao.createStatement();
            if(lancamento.getId()>0){
                ContasDAO coDAO= new ContasDAO();
                int id_conta=coDAO.idPorNome(lancamento.getNome_conta());
                CategoriasDAO  caDAO=new CategoriasDAO();
                int id_categoria=caDAO.getId(lancamento.getCategoria());
                if(id_conta==0 || id_categoria==0){return false;}
                stmt.executeUpdate("update lancamentos set id_conta="+id_conta+",id_categoria="+id_categoria+",valor="+lancamento.getValor()+",operacao='"+lancamento.getOperacao()+"',data='"+lancamento.getData()+"',descricao='"+lancamento.getDescricao()+"' where id="+lancamento.getId()+";");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean insere(Lancamento lancamento){
        
        try {
            Statement stmt = conexao.createStatement();
            if(lancamento.getId()==0){
                ContasDAO coDAO= new ContasDAO();
                int id_conta=coDAO.idPorNome(lancamento.getNome_conta());
                CategoriasDAO  caDAO=new CategoriasDAO();
                int id_categoria=caDAO.getId(lancamento.getCategoria());
                if(id_conta==0 || id_categoria==0){return false;}
                stmt.executeUpdate("insert into lancamentos (id_conta,id_categoria,valor,operacao,data,descricao) values ("+id_conta+","+id_categoria+","+lancamento.getValor()+",'"+lancamento.getOperacao()+"','"+lancamento.getData()+"','"+lancamento.getDescricao()+"');");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}
