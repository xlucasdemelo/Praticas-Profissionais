/**
 * 
 */
package com.lucas.graca.domain.repository.aquisicaoCompra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.aquisicaoCompra.AquisicaoProduto;

/**
 * @author lucas
 *
 */
public interface IAquisicaoProdutoRepository extends JpaRepository<AquisicaoProduto, Long>
{
	/**
	 * 
	 * @param filters
	 * @param ativo
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new AquisicaoProduto( aquisicaoProduto.id, aquisicaoProduto.status, aquisicaoProduto.condicaoPagamento, aquisicaoProduto.formaPagamento, "
			+ "aquisicaoProduto.vezesPagamento, aquisicaoProduto.fornecedor, aquisicaoProduto.diaVencimento, aquisicaoProduto.porcentagemDiferenca ) " +
			   "FROM AquisicaoProduto aquisicaoProduto " +
			  "WHERE (  (FILTER(aquisicaoProduto.fornecedor.razaoSocial, :filter) = TRUE)  "
			  	 + ")"
			)
	public Page<AquisicaoProduto> listByFilters( @Param("filter") String filters, Pageable pageable );
	
}
