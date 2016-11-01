/**
 * 
 */
package com.lucas.graca.domain.repository.caixa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.caixa.NaturezaGastos;
import com.lucas.graca.domain.entity.fornecedor.Fornecedor;

/**
 * @author eits
 *
 */
public interface INaturezaGastosRepository extends JpaRepository<NaturezaGastos, Long>
{
	
	/**
	 * 
	 * @param filters
	 * @param ativo
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new NaturezaGastos( naturezaGastos.id, naturezaGastos.nome, naturezaGastos.descricao, naturezaGastos.enabled ) " +
			   "FROM NaturezaGastos naturezaGastos " +
			  "WHERE ( naturezaGastos.enabled = :ativo AND "
			  	 + "( (FILTER(naturezaGastos.nome, :filter) = TRUE)  ) )"
			)
	public Page<Fornecedor> listByFilters( @Param("filter") String filters, @Param("ativo") Boolean ativo, Pageable pageable );
	
}
