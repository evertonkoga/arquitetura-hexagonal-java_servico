package br.com.conta.servico.repositorio;

import br.com.conta.sistema.dominio.modelo.Conta;
import br.com.conta.sistema.dominio.modelo.NegocioException;
import br.com.conta.sistema.porta.ContaRepositorio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import javax.inject.Inject;
import javax.inject.Named;
import static java.util.Objects.isNull;

// Responsável por implementar a porta de saída (driven) de serviços de banco de dados usando spring jdbc
@Named
public class ContaRepositorioImp implements ContaRepositorio {
    private static final String MESSAGE_ERROR = "Erro inesperado de acesso ao banco. Entre em contato com adminstrador.";
    private JdbcTemplate jdbc;
    @Inject
    public ContaRepositorioImp(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }
    @Override
    public Conta get(Integer numero) {
        if (isNull(numero)) {
            return null;
        }
        var query = "select * from conta where numero = ?";
        var queryParams = new Object[]{numero};
        RowMapper<Conta> rowMapper = (rs, nm) ->
                new Conta(rs.getInt(1), rs.getBigDecimal(2), rs.getString(3));

        try {
            var lista = jdbc.query(query, queryParams, rowMapper);
            if (lista.isEmpty()) {
                return null;
            }
            return lista.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NegocioException(MESSAGE_ERROR);
        }
    }
    @Transactional
    @Override
    public void alterar(Conta conta) {
        if (isNull(conta)) {
            throw new NegocioException("Conta é obrigatório.");
        }
        var query = "update conta set saldo=?, correntista=? where numero=?";
        var queryParams = new Object[]{conta.getSaldo(), conta.getCorrentista(),
                conta.getNumero()};
        try {
            jdbc.update(query, queryParams);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NegocioException(MESSAGE_ERROR);
        }
    }
}
