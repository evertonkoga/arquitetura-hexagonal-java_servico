package teste.integracao;

import br.com.conta.sistema.dominio.modelo.NegocioException;
import br.com.conta.sistema.porta.ContaRepositorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Serviço de banco de dados - Conta")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class TesteContaRepositorio {
    @Inject
    ContaRepositorio repositorio;

    @Test
    @DisplayName("pesquisa conta com número nulo")
    void teste1() {
        try {
            var conta = repositorio.get(null);
            assertTrue(conta == null, "Conta deve ser nula");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta nula.");
        }
    }
    @Test
    @DisplayName("pesquisa conta com número inexistente")
    void teste2() {
        try {
            var conta = repositorio.get(8547);
            assertTrue(conta == null, "Conta deve ser nula");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta nula.");
        }
    }
    @Test
    @DisplayName("pesquisa conta com número existente")
    void teste3() {
        try {
            var conta = repositorio.get(50);
            assertTrue(conta != null, "Conta deve estar preenchida");
            System.out.println(conta);
        } catch (NegocioException e) {
            fail("Deve carregar uma conta.");
        }
    }
    @Test
    @DisplayName("alterar conta como nulo")
    void teste4() {
        try {
            repositorio.alterar(null);
            fail("Não deve alterar conta nula");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta é obrigatório.");
            System.out.println(e.getMessage());
        }
    }

}
