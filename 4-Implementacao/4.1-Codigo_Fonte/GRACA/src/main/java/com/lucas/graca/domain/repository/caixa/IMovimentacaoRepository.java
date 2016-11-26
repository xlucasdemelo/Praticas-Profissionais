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

/**
 * @author eits
 *
 */
public interface IMovimentacaoRepository extends JpaRepository<Movimentacao, Long>
{
	
	@Query(value="SELECT new Movimentacao( movimentacao.id, movimentacao.dataEmissao, movimentacao.dataPagamento, movimentacao.dataEfetivada, movimentacao.descricao, "
			+ " movimentacao.valorEmissao, movimentacao.valorEfetivado, movimentacao.porcentagemDiferenca, movimentacao.status, movimentacao.tipoMovimentacao, "
			+ " contaDestino, contaOrigem, aquisicaoProduto, movimentacao.naturezaGastos ) " +
			   "FROM Movimentacao movimentacao "
			+ " LEFT OUTER JOIN movimentacao.aquisicaoProduto aquisicaoProduto "
			+ " LEFT OUTER JOIN movimentacao.contaOrigem contaOrigem "
			+ " LEFT OUTER JOIN movimentacao.contaDestino contaDestino "
			+  "WHERE ( (FILTER(movimentacao.descricao, :filter) = TRUE) )"
			)
	public Page<Movimentacao> listByFilters( @Param("filter") String filters, Pageable pageable );
	
	/**
	 * 
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<Movimentacao> findByAquisicaoProdutoId( Long id, Pageable pageable );
	
}
