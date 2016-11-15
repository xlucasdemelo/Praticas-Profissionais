/**
 * 
 */
package com.lucas.graca.domain.repository.aquisicaoCompra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.aquisicaoCompra.FornecedorAquisicao;

/**
 * @author lucas
 *
 */
public interface IFornecedorAquisicao extends JpaRepository<FornecedorAquisicao, Long>
{
	
	@Query(value="SELECT new FornecedorAquisicao( fornecedorAquisicao.id, fornecedorAquisicao.fornecedor, fornecedorAquisicao.aquisicaoProduto ) " +
			   "FROM FornecedorAquisicao fornecedorAquisicao " +
			  "WHERE ( fornecedorAquisicao.aquisicaoProduto = :aquisicaoId AND "
			  	 + " (FILTER(fornecedorAquisicao.fornecedor.razaoSocial, :filter) = TRUE)  "
			  	 + ")"
			)
		public Page<FornecedorAquisicao> listByAquisicaoAndFilters( @Param("aquisicaoId") Long aquisicaoId, @Param("filter")String filter, Pageable pageable );
	
}
