package model;

public class Conta {
  private int id;
  private String nome_conta;
  private String banco;
  private String agencia;
  private String conta_corrente;

  public Conta(int i, String n, String b, String a, String c) {
    id = i;
    nome_conta = n;
    banco = b;
    agencia = a;
    conta_corrente = c;
  }

  // gets e sets
  public String getNome_conta() {
    return nome_conta;
  }

  public void setNome_conta(String nome_conta) {
    this.nome_conta = nome_conta;
  }

  public String getBanco() {
    return banco;
  }

  public void setBanco(String banco) {
    this.banco = banco;
  }

  public String getAgencia() {
    return agencia;
  }

  public void setAgencia(String agencia) {
    this.agencia = agencia;
  }

  public String getConta_corrente() {
    return conta_corrente;
  }

  public void setConta_corrente(String conta_corrente) {
    this.conta_corrente = conta_corrente;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
