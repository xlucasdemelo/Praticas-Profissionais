/**
 * 
 */
package com.lucas.graca.domain.repository.endereco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.endereco.Estado;

/**
 * @author lucas
 *
 */
public interface IEstadoRepository extends JpaRepository<Estado, Long>
{
	@Query(value="SELECT new Estado(estado.id, estado.nome, pais) " +
			   "FROM Estado estado " +
			   " LEFT OUTER JOIN estado.pais pais " +
			  "WHERE ( (FILTER(estado.nome, :filter) = TRUE) " +
			   		" AND pais.id = :paisId  " +
			  ")"
			)
	public Page<Estado> listEstadosByFilters( @Param("filter") String filters, @Param("paisId")Long paisId, Pageable pageable );

}
