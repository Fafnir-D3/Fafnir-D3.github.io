package model;

/* Ideia:
    Uso a classe usuario para representar tanto os usuarios como os administradores,
ja que ambos tem muitas colunas em comum. Uso a variavel suspenso para distinguir
os administradores dos usuarios comuns.
*/
public class Usuario {

  private int id;
  private String nome;
  private String cpf;
  private String senha;
  private String suspenso; // S = suspenso , N = nao suspenso, A = administrador

  // caso esteja inserindo um usuario novo o id será 0, porem não uso o 0 ao passar
  // para o BD, uso o indice automatico do BD.
  public Usuario(int i, String n, String c, String se, String su) {
    id = i;
    nome = n;
    cpf = c;
    senha = se;
    suspenso = su;
  }

  // facilita para saber se está suspenso
  public boolean isSuspenso() {
    return (suspenso.equals("S"));
  }

  // gets e sets
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getSuspenso() {
    return suspenso;
  }

  public void setSuspenso(String suspenso) {
    this.suspenso = suspenso;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
