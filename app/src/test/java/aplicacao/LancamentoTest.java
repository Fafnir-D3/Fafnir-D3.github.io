package aplicacao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LancamentoTest {

  Lancamento lancamento;

  @Test
  public void testConstrutor_quandoValido_entaoConstroi() {
    lancamento = new Lancamento(1, "nome", "categoria", 10, "operacao", "data", "descricao");
    assertEquals(lancamento.getId(), 1);
  }
}
