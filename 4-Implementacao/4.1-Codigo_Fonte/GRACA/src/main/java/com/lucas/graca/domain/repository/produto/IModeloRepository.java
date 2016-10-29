/**
 * 
 */
package com.lucas.graca.domain.repository.produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.produto.Modelo;

/**
 * @author lucas
 *
 */
public interface IModeloRepository extends JpaRepository<Modelo, Long>
{
	
	/**
	 * 
	 * @param filters
	 * @param ativo
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new Modelo(  modelo.id, modelo.nome, modelo.marca ) " +
			   "FROM Modelo modelo " +
			  "WHERE ( modelo.marca.id = :marcaId AND "
			  	 + "( (FILTER(modelo.nome, :filter) = TRUE)  ) )"
			)
	public Page<Modelo> listByFilters( @Param("marcaId") Long marcaId, @Param("filter") String filters, Pageable pageable );

	
}
