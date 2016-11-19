/**
 * 
 */
package com.lucas.graca.domain.repository.caixa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.caixa.Movimentacao;
import com.lucas.graca.domain.entity.fornecedor.Fornecedor;

/**
 * @author eits
 *
 */
public interface IMovimentacaoRepository extends JpaRepository<Movimentacao, Long>
{
	
	@Query(value="SELECT new Movimentacao( movimentacao.id, movimentacao.dataEmissao, movimentacao.dataPagamento, movimentacao.dataEfetivada, movimentacao.descricao, "
			+ " movimentacao.valor, movimentacao.porcentagemDiferenca, movimentacao.status, movimentacao.tipoMovimentacao, "
			+ " movimentacao.contaDestino, movimentacao.contaOrigem ) " +
			   "FROM Movimentacao movimentacao " +
			  "WHERE ( (FILTER(movimentacao.descricao, :filter) = TRUE) )"
			)
	public Page<Fornecedor> listByFilters( @Param("filter") String filters, Pageable pageable );
	
}
