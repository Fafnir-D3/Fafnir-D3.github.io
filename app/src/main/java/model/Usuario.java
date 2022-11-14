package model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/* Ideia:
    Uso a classe usuario para representar tanto os usuarios como os administradores,
ja que ambos tem muitas colunas em comum. Uso a variavel suspenso para distinguir
os administradores dos usuarios comuns.
*/
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Usuario {

  private int id;
  private String nome;
  private String cpf;
  private String senha;
  private String suspenso; // S = suspenso , N = nao suspenso, A = administrador

  // facilita para saber se esta suspenso
  public boolean isSuspenso() {
    return (suspenso.equals("S"));
  }
}
