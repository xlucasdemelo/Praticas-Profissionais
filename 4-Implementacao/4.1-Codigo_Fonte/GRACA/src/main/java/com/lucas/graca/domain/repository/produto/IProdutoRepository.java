/**
 * 
 */
package com.lucas.graca.domain.repository.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.fornecedor.Fornecedor;
import com.lucas.graca.domain.entity.produto.Produto;

/**
 * @author eits
 *
 */
public interface IProdutoRepository extends JpaRepository<Produto, Long>
{
	
	@Query(value="SELECT new Produto( produto.id, produto.nome, produto.ativo, produto.categoria, produto.marca ) " +
			   "FROM Produto produto " +
			  "WHERE ( produto.ativo = :ativo AND "
			  	 + "( (FILTER(produto.nome, :filter) = TRUE)  ) )"
			)
	public Page<Fornecedor> listByFilters( @Param("filter") String filters, @Param("ativo") Boolean ativo, Pageable pageable );
	
}
