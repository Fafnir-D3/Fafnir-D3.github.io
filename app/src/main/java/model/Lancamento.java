package model;

/* Ideia:
    Tentei deixar o lancamento intuitivo tanto para o usuario quanto para o programador
usando nome_conta e categoria, invez dos respectivos IDs. Faço a conversão necessária
no LancamentosDAO.
*/
public class Lancamento {
  private int id;
  private String nome_conta;
  private String categoria;
  private double valor;
  private String operacao;
  private String data;
  private String descricao;

  public Lancamento(int i, String n, String c, double v, String o, String da, String de) {
    id = i;
    nome_conta = n;
    categoria = c;
    valor = v;
    operacao = o;
    data = da;
    descricao = de;
  }

  // gets e sets
  public String getNome_conta() {
    return nome_conta;
  }

  public void setNome_conta(String nome_conta) {
    this.nome_conta = nome_conta;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public double getValor() {
    return valor;
  }

  public void setValor(double valor) {
    this.valor = valor;
  }

  public String getOperacao() {
    return operacao;
  }

  public void setOperacao(String operacao) {
    this.operacao = operacao;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
