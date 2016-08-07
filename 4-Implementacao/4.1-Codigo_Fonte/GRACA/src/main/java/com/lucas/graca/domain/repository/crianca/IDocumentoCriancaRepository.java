/**
 * 
 */
package com.lucas.graca.domain.repository.crianca;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.crianca.DocumentoCrianca;

/**
 * @author lucas
 *
 */
public interface IDocumentoCriancaRepository extends JpaRepository<DocumentoCrianca, Long>
{
	
	@Query(value="SELECT new DocumentoCrianca( documentoCrianca.id, documentoCrianca.tipoDocumento, crianca, documentoCrianca.numeroDocumento ) " +
			   "FROM DocumentoCrianca documentoCrianca " +
			   "LEFT OUTER JOIN documentoCrianca.crianca crianca " +
			  "WHERE ( crianca.id = :id )"
			)
	public Page<DocumentoCrianca> listDocumentosByCrianca( @Param("id") Long criancaId, Pageable pageable );
	
}
