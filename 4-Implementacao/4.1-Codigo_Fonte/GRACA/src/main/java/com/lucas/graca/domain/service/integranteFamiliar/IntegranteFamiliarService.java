/**
 * 
 */
package com.lucas.graca.domain.service.integranteFamiliar;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.crianca.Crianca;
import com.lucas.graca.domain.entity.crianca.GrauParentesco;
import com.lucas.graca.domain.entity.crianca.Parente;
import com.lucas.graca.domain.entity.endereco.Endereco;
import com.lucas.graca.domain.entity.familia.Sexo;
import com.lucas.graca.domain.entity.integrantefamiliar.DocumentoIntegranteFamiliar;
import com.lucas.graca.domain.entity.integrantefamiliar.GrauEscolaridade;
import com.lucas.graca.domain.entity.integrantefamiliar.IntegranteFamiliar;
import com.lucas.graca.domain.entity.integrantefamiliar.TipoDocumento;
import com.lucas.graca.domain.repository.crianca.ICriancaRepository;
import com.lucas.graca.domain.repository.crianca.IParenteRepository;
import com.lucas.graca.domain.repository.endereco.IEnderecoRepository;
import com.lucas.graca.domain.repository.integranteFamiliar.IDocumentoIntegranteFamiliarRepository;
import com.lucas.graca.domain.repository.integranteFamiliar.IIntegranteFamiliarRepository;

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
	
	/**
	 * 
	 */
	@Autowired
	private ICriancaRepository criancaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IParenteRepository parenteRepository;
	
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
		
		if (integranteFamiliar.getEndereco() != null)
		{
			integranteFamiliar.getEndereco().setId( null );
			
			this.enderecoRepository.save( integranteFamiliar.getEndereco() );
		}
		
		integranteFamiliar = this.integranteFamiliarRepository.saveAndFlush( integranteFamiliar );
		
		List<Crianca> criancasByFamilia = this.criancaRepository.findByFamiliaId( integranteFamiliar.getFamilia().getId(), null ).getContent();
		
		for ( Crianca crianca : criancasByFamilia )
		{
			Parente parente = new Parente();
			parente.setCrianca( crianca );
			parente.setIntegranteFamiliar( integranteFamiliar );
			parente.setGrauParentesco( GrauParentesco.OUTRO );
			
			this.parenteRepository.save( parente );
		}
		
		return integranteFamiliar;
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
		
		if (integranteFamiliarDB.getEndereco() != null)
		{
			integranteFamiliarDB.getEndereco().mergeObject( integranteFamiliar.getEndereco() );
		} else if ( integranteFamiliar.getEndereco() != null )
		{
			integranteFamiliarDB.setEndereco( new Endereco() );
			integranteFamiliarDB.getEndereco().mergeObject( integranteFamiliar.getEndereco() );
			
			this.enderecoRepository.saveAndFlush( integranteFamiliarDB.getEndereco() );
		}
		
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
	 * @param familiaId
	 * @param pageable
	 * @return
	 */
	public Page<IntegranteFamiliar> listIntegrantesByfamiliaAndFilters( Long familiaId, String filter, PageRequest pageable )
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
		integranteFamiliar = this.integranteFamiliarRepository.saveAndFlush( integranteFamiliar );
		
		List<Parente> parentes = this.parenteRepository.findByIntegranteFamiliarId( integranteFamiliar.getId(), null ).getContent();
		
		for ( Parente parente : parentes )
		{
			this.parenteRepository.delete( parente );
		}
		
		return integranteFamiliar ;
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
