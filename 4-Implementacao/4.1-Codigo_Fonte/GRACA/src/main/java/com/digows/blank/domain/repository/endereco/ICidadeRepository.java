/**
 * 
 */
package com.digows.blank.domain.repository.endereco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digows.blank.domain.entity.endereco.Cidade;

/**
 * @author lucas
 *
 */
public interface ICidadeRepository extends JpaRepository<Cidade, Long>
{
	@Query(value="SELECT new Cidade(cidade.id, cidade.nome, cidade.estado) " +
			   "FROM Cidade cidade " +
			  "WHERE ( cidade.estado.id = :estadoId AND (FILTER(cidade.nome, :filter) = TRUE) )" 
			   		
			)
	public Page<Cidade> listEstadosByFilters( @Param("filter") String filters, @Param("estadoId")Long estadoId, Pageable pageable );
}
