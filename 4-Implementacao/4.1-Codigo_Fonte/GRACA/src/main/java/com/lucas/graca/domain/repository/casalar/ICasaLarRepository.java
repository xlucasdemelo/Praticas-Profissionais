/**
 * 
 */
package com.lucas.graca.domain.repository.casalar;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.casalar.CasaLar;

/**
 * @author lucas
 *
 */
public interface ICasaLarRepository extends JpaRepository<CasaLar, Long>
{
	
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new CasaLar( casaLar.id, casaLar.enabled, casaLar.numero, casaLar.cuidadoraResidente, casaLar.cuidadoraApoiadora ) " +
		   "FROM CasaLar casaLar " +
		  "WHERE (  "
		  	 + " (FILTER(casaLar.numero, :filter) = TRUE)  "
		  	 + "OR ( FILTER(casaLar.cuidadoraApoiadora.nome, :filter) = TRUE )  "
		  	 + "OR ( FILTER(casaLar.cuidadoraResidente.nome, :filter) = TRUE )  "
		  	 + ")"
		)
	public Page<CasaLar> listByFilters( @Param("filter")String filters, Pageable pageable );
	
}
