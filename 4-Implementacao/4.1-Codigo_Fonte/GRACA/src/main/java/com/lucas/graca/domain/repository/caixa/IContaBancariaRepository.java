/**
 * 
 */
package com.lucas.graca.domain.repository.caixa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.caixa.ContaBancaria;

/**
 * @author eits
 *
 */
public interface IContaBancariaRepository extends JpaRepository<ContaBancaria, Long>
{
	
	@Query(value="SELECT new ContaBancaria( contaBancaria.id, contaBancaria.numero, contaBancaria.agencia, contaBancaria.enabled, contaBancaria.banco ) " +
			   "FROM ContaBancaria contaBancaria " +
			  "WHERE ( contaBancaria.enabled = :ativo AND "
			  	 	+ "( (FILTER(contaBancaria.numero, :filter) = TRUE)  ) "
			  	 	+ " OR (FILTER(contaBancaria.agencia, :filter) = TRUE) " 
			  	 + ")"
			)
	public Page<ContaBancaria> listByFilters( @Param("filter") String filters, @Param("ativo") Boolean ativo, Pageable pageable );
	
}
