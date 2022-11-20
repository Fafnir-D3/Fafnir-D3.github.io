package model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Conta implements Serializable {
  private int id;
  private String nome_conta;
  private String banco;
  private String agencia;
  private String conta_corrente;
}
