package model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/* Ideia:
    Tentei deixar o lancamento intuitivo tanto para o usuario quanto para o programador
usando nome_conta e categoria, invez dos respectivos IDs. Faço a conversão necessária
no LancamentosDAO.
*/
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Lancamento implements Serializable {
  private int id;
  private String nome_conta;
  private String categoria;
  private double valor;
  private String operacao;
  private String data;
  private String descricao;
}
