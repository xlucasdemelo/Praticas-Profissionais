/**
 * 
 */
package com.digows.blank.domain.repository.endereco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digows.blank.domain.entity.endereco.Pais;

/**
 * @author lucas
 *
 */
public interface IPaisRepository extends JpaRepository<Pais, Long>
{
	@Query(value="SELECT new Pais(pais.id, pais.nome) " +
			   "FROM Pais pais " +
			  "WHERE ( (FILTER(pais.nome, :filter) = TRUE) )"
			)
	public Page<Pais> listPaisesByFilters( @Param("filter") String filters, Pageable pageable );
	
}
