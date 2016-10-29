/**
 * 
 */
package com.lucas.graca.domain.repository.produto;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.produto.Marca;

/**
 * @author eits
 *
 */
public interface IMarcaRepository extends JpaRepository<Marca, Long>
{
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new Marca( marca.id, marca.nome ) " +
			   "FROM Marca marca " +
			  "WHERE (  (FILTER(marca.nome, :filter) = TRUE)  )"
			)
	public Page<Marca> listByFilters( @Param("filter") String filters, Pageable pageable );
	
}
