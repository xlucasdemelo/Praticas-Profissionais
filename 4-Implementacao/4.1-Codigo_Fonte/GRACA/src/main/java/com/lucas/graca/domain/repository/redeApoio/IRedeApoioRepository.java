/**
 * 
 */
package com.lucas.graca.domain.repository.redeApoio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.redeapoio.RedeApoio;

/**
 * @author lucas
 *
 */
public interface IRedeApoioRepository extends JpaRepository<RedeApoio, Long>
{
	
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Query(value="SELECT new RedeApoio( redeApoio.id, redeApoio.nome, redeApoio.telefone, endereco, responsavel, redeApoio.enabled ) " +
			   " FROM RedeApoio redeApoio " +
			   " LEFT OUTER JOIN redeApoio.endereco endereco " +
			   " LEFT OUTER JOIN redeApoio.responsavel responsavel " +
			  "WHERE ( redeApoio.enabled = :enabled AND "
			   		+ " ( FILTER(redeApoio.nome, :filter) = TRUE ) "
			  	 + ")"
			)
		public Page<RedeApoio> listByFilters( @Param("filter")String filters, @Param("enabled") Boolean enabled, Pageable pageable );
	
}
