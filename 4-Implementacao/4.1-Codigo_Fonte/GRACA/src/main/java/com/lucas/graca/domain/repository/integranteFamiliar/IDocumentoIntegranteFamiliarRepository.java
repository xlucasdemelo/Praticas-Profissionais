/**
 * 
 */
package com.lucas.graca.domain.repository.integranteFamiliar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.graca.domain.entity.integrantefamiliar.DocumentoIntegranteFamiliar;

/**
 * @author lucas
 *
 */
public interface IDocumentoIntegranteFamiliarRepository extends JpaRepository<DocumentoIntegranteFamiliar, Long>
{
	
	@Query(value="SELECT new DocumentoIntegranteFamiliar( documentoIntegranteFamiliar.id, documentoIntegranteFamiliar.tipoDocumento, integranteFamiliar, documentoIntegranteFamiliar.numeroDocumento ) " +
			   "FROM DocumentoIntegranteFamiliar documentoIntegranteFamiliar " +
			   "LEFT OUTER JOIN documentoIntegranteFamiliar.integranteFamiliar integranteFamiliar " +
			  "WHERE ( integranteFamiliar.id = :id )"
			)
	public Page<DocumentoIntegranteFamiliar> listDocumentosByIntegranteFamiliar( @Param("id") Long integranteFamiliarId, Pageable pageable );
	
}
