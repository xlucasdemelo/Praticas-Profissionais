/**
 * 
 */
package com.lucas.graca.domain.service.crianca;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.crianca.Crianca;
import com.lucas.graca.domain.entity.crianca.DocumentoCrianca;
import com.lucas.graca.domain.entity.crianca.Etnia;
import com.lucas.graca.domain.entity.crianca.GrauParentesco;
import com.lucas.graca.domain.entity.crianca.Parente;
import com.lucas.graca.domain.entity.integrantefamiliar.IntegranteFamiliar;
import com.lucas.graca.domain.repository.crianca.ICriancaRepository;
import com.lucas.graca.domain.repository.crianca.IDocumentoCriancaRepository;
import com.lucas.graca.domain.repository.crianca.IParenteRepository;
import com.lucas.graca.domain.repository.endereco.IEnderecoRepository;
import com.lucas.graca.domain.repository.integranteFamiliar.IIntegranteFamiliarRepository;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
public class CriancaService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private ICriancaRepository criancaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IIntegranteFamiliarRepository integranteFamiliarRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IParenteRepository parenteRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IDocumentoCriancaRepository documentoCriancaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IEnderecoRepository enderecoRepository;
	/*-------------------------------------------------------------------
	 *				 		     SERVICES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param crianca
	 * @return
	 */
	public Crianca insertCrianca( Crianca crianca )
	{
		Assert.notNull( crianca );
		
		if (crianca.getEndereco() != null)
			this.enderecoRepository.save( crianca.getEndereco() );
		
		return this.criancaRepository.save( crianca );
	}
	
	/**
	 * 
	 * @param crianca
	 * @return
	 */
	public Crianca updateCrianca( Crianca crianca )
	{
		Assert.notNull( crianca );
		return this.criancaRepository.save( crianca );
	}
	
	/**
	 * 
	 * @return
	 */
	public Crianca findCriancaById(Long id)
	{
		return this.criancaRepository.findOne( id );
	}
	
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	public Page<Crianca> listCriancasByFilters( String filters, PageRequest pageable )
	{
		return this.criancaRepository.listCriancasByFilters( filters, pageable );
	}
	
	/**
	 * 
	 * @param ativo
	 * @param inativo
	 * @param pageable
	 * @return
	 */
	public Page<Crianca> listCriancasByMoreFilters( Boolean ativo, Boolean inativo, PageRequest pageable )
	{
		return this.criancaRepository.listCriancasByMoreFilters( ativo, inativo, pageable );
	}
	
	/**
	 * 
	 * @param crianca
	 * @return
	 */
	public Crianca disableCrianca( Crianca crianca )
	{
		crianca.disableIntegranteFamiliar();
		
		return this.criancaRepository.save( crianca );
	}
	
	/**
	 * 
	 * @return
	 */
	public Crianca enableCrianca( Crianca crianca )
	{
		crianca.enableIntegranteFamiliar();
		
		return this.criancaRepository.save( crianca );
	}
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICES PARENTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param crianca
	 * @param pageable
	 * @return
	 */
	public List<Parente> associateFamiliaToCrianca( Crianca crianca, PageRequest pageable )
	{
		this.removeAllParentes( crianca.getId() );
		this.criancaRepository.save( crianca );
		
		List<Parente> parentes = new ArrayList<Parente>();
		
		List <IntegranteFamiliar> integrantes = this.integranteFamiliarRepository.listIntegrantesByfamiliaToCrianca( crianca.getFamilia().getId(), null ).getContent();
		
		for ( IntegranteFamiliar integranteFamiliar : integrantes )
		{
			Parente parente = new Parente();
			parente.setCrianca( crianca );
			parente.setIntegranteFamiliar( integranteFamiliar );
			parente.setGrauParentesco( GrauParentesco.OUTRO );
			
			parentes.add( parente );
			this.parenteRepository.save( parente );
		}
		
		return parentes;
	}
	
	/**
	 * 
	 * @param crianca
	 * @param pageable
	 * @return
	 */
	private void removeAllParentes( Long criancaId )
	{
		List<Parente> parentes = this.parenteRepository.listParentesByCrianca( criancaId, null ).getContent();
		
		for ( Parente parente : parentes )
		{
			this.parenteRepository.delete( parente );
		}
	}
	
	/**
	 * 
	 * @param criancaId
	 * @param pageable
	 * @return
	 */
	public Page<Parente> listParentesByCrianca( Long criancaId, PageRequest pageable )
	{
		return this.parenteRepository.listParentesByCrianca( criancaId, pageable );
	}
	
	/**
	 * 
	 * @param parente
	 * @return
	 */
	public Parente insertParente (Parente parente)
	{
		Assert.notNull( parente );
		return this.parenteRepository.save( parente );
	}
	
	/**
	 * 
	 * @param parente
	 * @return
	 */
	public Parente updateParente (Parente parente)
	{
		Assert.notNull( parente );
		return this.parenteRepository.save( parente );
	}
	
	/**
	 * 
	 * @return
	 */
	public Parente updateGrauParentesco( Parente parente )
	{
		Parente updatedParente = this.parenteRepository.findOne( parente.getId() );
		
		updatedParente.setGrauParentesco( parente.getGrauParentesco() );
		
		return this.parenteRepository.save( updatedParente );
	}
	
	/**
	 * 
	 * @return
	 */
	public GrauParentesco[] listAllGrausParentesco()
	{
		return GrauParentesco.values();
	}
	
	/**
	 * 
	 * @return
	 */
	public Etnia[] listAllEtnias()
	{
		return Etnia.values();
	}
	
	/*-------------------------------------------------------------------
	 						SERVICES DOCUMENTOS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param documentoCrianca
	 * @return
	 */
	public DocumentoCrianca insertDocumentoCrianca(DocumentoCrianca documentoCrianca)
	{
		Assert.notNull( documentoCrianca );
		
		return this.documentoCriancaRepository.save( documentoCrianca );
	}
	
	/**
	 * 
	 * @param documentoCrianca
	 * @return
	 */
	public DocumentoCrianca updateDocumentoCrianca(DocumentoCrianca documentoCrianca)
	{
		Assert.notNull( documentoCrianca );
		
		return this.documentoCriancaRepository.save( documentoCrianca );
	}
	
	/**
	 * 
	 * @param documentoCrianca
	 */
	public void removeDocumentoCrianca( DocumentoCrianca documentoCrianca )
	{
		this.documentoCriancaRepository.delete( documentoCrianca );
	}
	
	/**
	 * 
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<DocumentoCrianca> listDocumentosByCrianca( Long id, PageRequest pageable )
	{
		return this.documentoCriancaRepository.listDocumentosByCrianca( id, pageable );
	}
}



