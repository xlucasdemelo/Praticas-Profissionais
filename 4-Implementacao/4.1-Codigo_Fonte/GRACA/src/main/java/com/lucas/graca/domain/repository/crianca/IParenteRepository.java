/**
 * 
 */
package com.lucas.graca.domain.repository.crianca;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.crianca.Parente;

/**
 * @author lucas
 *
 */
public interface IParenteRepository extends JpaRepository<Parente, Long>
{
	
	@Query(value="SELECT new Parente( parente.id, crianca, integranteFamiliar, parente.grauParentesco ) " +
			   "FROM Parente parente " +
			   "LEFT OUTER JOIN parente.integranteFamiliar integranteFamiliar " + 
			   "LEFT OUTER JOIN parente.crianca crianca " +
			  "WHERE ( integranteFamiliar.ativo IS TRUE AND crianca.id = :criancaId " +
			  		")"
			)
	public Page<Parente> listParentesByCrianca( @Param("criancaId") Long criancaId, Pageable pageable );
	
	/**
	 * 
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<Parente> findByIntegranteFamiliarId( Long id, Pageable pageable );
}
