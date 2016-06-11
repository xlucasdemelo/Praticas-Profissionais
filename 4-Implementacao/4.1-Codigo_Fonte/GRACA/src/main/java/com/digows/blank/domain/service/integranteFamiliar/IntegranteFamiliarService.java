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

import com.digows.blank.domain.entity.documento.TipoDocumento;
import com.digows.blank.domain.entity.endereco.Endereco;
import com.digows.blank.domain.entity.familia.Sexo;
import com.digows.blank.domain.entity.integrantefamiliar.DocumentoIntegranteFamiliar;
import com.digows.blank.domain.entity.integrantefamiliar.GrauEscolaridade;
import com.digows.blank.domain.entity.integrantefamiliar.IntegranteFamiliar;
import com.digows.blank.domain.repository.endereco.IEnderecoRepository;
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
	private IEnderecoRepository enderecoRepository;
	
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
		
		integranteFamiliar.getEndereco().setId( null );
		
		this.enderecoRepository.save( integranteFamiliar.getEndereco() );
		
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
		
		IntegranteFamiliar integranteFamiliarDB = this.integranteFamiliarRepository.findOne( integranteFamiliar.getId() );
		
		integranteFamiliarDB.getEndereco().mergeObject( integranteFamiliar.getEndereco() );
		Endereco endereco = integranteFamiliarDB.getEndereco();
		
		integranteFamiliar.setEndereco( endereco );
		
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
		
		return this.integranteFamiliarRepository.listIntegrantesByfamilia( familiaId, pageable );
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
	
	/**
	 * 
	 * @return
	 */
	public GrauEscolaridade[] listAllGrausEscolaridade()
	{
		return GrauEscolaridade.values();
	}
	
	/**
	 * 
	 * @return
	 */
	public Sexo[] listAllSexos()
	{
		return Sexo.values();
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
		
		DocumentoIntegranteFamiliar documentoIntegranteFamiliarDB = this.documentoIntegranteFamiliarRepository.findOne( documentoIntegranteFamiliar.getId() );
		documentoIntegranteFamiliarDB.mergeObject( documentoIntegranteFamiliar );
		
		return this.documentoIntegranteFamiliarRepository.save( documentoIntegranteFamiliarDB );
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
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public DocumentoIntegranteFamiliar findDocumentoIntegranteFamiliarById(Long id)
	{
		return this.documentoIntegranteFamiliarRepository.findOne( id );
	}
	
	/**
	 * 
	 * @return
	 */
	public TipoDocumento[] listAllDocumentos()
	{
		return TipoDocumento.values();
	}
}
