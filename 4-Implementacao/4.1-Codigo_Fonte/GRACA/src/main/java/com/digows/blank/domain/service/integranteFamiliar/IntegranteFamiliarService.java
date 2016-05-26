/**
 * 
 */
package com.digows.blank.domain.service.integranteFamiliar;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.digows.blank.domain.entity.integrantefamiliar.DocumentoIntegranteFamiliar;
import com.digows.blank.domain.entity.integrantefamiliar.IntegranteFamiliar;
import com.digows.blank.domain.repository.integranteFamiliar.IDocumentoIntegranteFamiliarRepository;
import com.digows.blank.domain.repository.integranteFamiliar.IIntegranteFamiliarRepository;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
public class IntegranteFamiliarService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private IIntegranteFamiliarRepository integranteFamiliarRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IDocumentoIntegranteFamiliarRepository documentoIntegranteFamiliarRepository;
	/*-------------------------------------------------------------------
	 *				 		     SERVICES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @param integranteFamiliar
	 * @return
	 */
	public IntegranteFamiliar insertIntegranteFamiliar( IntegranteFamiliar integranteFamiliar )
	{
		Assert.notNull( integranteFamiliar );
		
		return this.integranteFamiliarRepository.save( integranteFamiliar );
	}
	
	/**
	 * 
	 * @param integranteFamiliar
	 * @return
	 */
	public IntegranteFamiliar updateIntegranteFamiliar( IntegranteFamiliar integranteFamiliar )
	{
		Assert.notNull( integranteFamiliar );
		
		return this.integranteFamiliarRepository.save( integranteFamiliar );
	}
	
	/**
	 * 
	 * @param familiaId
	 * @param pageable
	 * @return
	 */
	public Page<IntegranteFamiliar> listIntegrantesByfamilia( Long familiaId, PageRequest pageable )
	{
		Assert.notNull( familiaId );
		
		return this.listIntegrantesByfamilia( familiaId, pageable );
	}
	
	/**
	 * 
	 * @param integranteFamiliar
	 * @return
	 */
	public IntegranteFamiliar disableIntegranteFamiliar( IntegranteFamiliar integranteFamiliar )
	{
		Assert.notNull( integranteFamiliar );
		
		integranteFamiliar.disableIntegranteFamiliar();
		return this.integranteFamiliarRepository.save( integranteFamiliar );
	}
	
	/*-------------------------------------------------------------------
	 *                 SERVICES DOCUMENTO INTEGRANTE FAMILIAR
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param documentoIntegranteFamiliar
	 * @return
	 */
	public DocumentoIntegranteFamiliar insertDocumentoIntegranteFamiliar(DocumentoIntegranteFamiliar documentoIntegranteFamiliar)
	{
		Assert.notNull( documentoIntegranteFamiliar );
		
		return this.documentoIntegranteFamiliarRepository.save( documentoIntegranteFamiliar );
	}
	
	/**
	 * 
	 * @param documentoIntegranteFamiliar
	 * @return
	 */
	public DocumentoIntegranteFamiliar updateDocumentoIntegranteFamiliar(DocumentoIntegranteFamiliar documentoIntegranteFamiliar)
	{
		Assert.notNull( documentoIntegranteFamiliar );
		
		return this.documentoIntegranteFamiliarRepository.save( documentoIntegranteFamiliar );
	}
	
	/**
	 * 
	 * @param documentoIntegranteFamiliar
	 */
	public void removeDocumentoIntegranteFamiliar(DocumentoIntegranteFamiliar documentoIntegranteFamiliar)
	{
		Assert.notNull( documentoIntegranteFamiliar );
		
		this.documentoIntegranteFamiliarRepository.delete( documentoIntegranteFamiliar );
	}
	
	/**
	 * 
	 * @param integranteFamiliarId
	 * @return
	 */
	public Page<DocumentoIntegranteFamiliar> listDocumentosByIntegranteFamiliar (Long integranteFamiliarId)
	{
		return this.documentoIntegranteFamiliarRepository.listDocumentosByIntegranteFamiliar( integranteFamiliarId, null );
	}
}























