/**
 * 
 */
package com.lucas.graca.domain.repository.caixa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.caixa.Banco;

/**
 * @author eits
 *
 */
public interface IBancoRepository extends JpaRepository<Banco, Long>
{ 
	
	@Query(value="SELECT new Banco( banco.id, banco.nome, banco.enabled ) " +
			   "FROM Banco banco " +
			  "WHERE ( banco.enabled = :ativo AND "
			  	 	+ "( (FILTER(banco.nome, :filter) = TRUE)  ) "
			  	 + ")"
			)
	public Page<Banco> listByFilters( @Param("filter") String filters, @Param("ativo") Boolean ativo, Pageable pageable );
	
	/**
	 * 
	 * @param nome
	 * @return
	 */
	public Banco findByNomeAndEnabled(String nome, Boolean enabled);
	
}
