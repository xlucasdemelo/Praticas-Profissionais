/**
 * 
 */
package com.digows.blank.domain.service.crianca;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.digows.blank.domain.entity.crianca.Crianca;
import com.digows.blank.domain.entity.crianca.GrauParentesco;
import com.digows.blank.domain.entity.crianca.Parente;
import com.digows.blank.domain.entity.integrantefamiliar.IntegranteFamiliar;
import com.digows.blank.domain.repository.crianca.ICriancaRepository;
import com.digows.blank.domain.repository.crianca.IParenteRepository;
import com.digows.blank.domain.repository.integranteFamiliar.IIntegranteFamiliarRepository;

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
		this.criancaRepository.save( crianca );
		
		List<Parente> parentes = new ArrayList<Parente>();
		
		List <IntegranteFamiliar> integrantes = this.integranteFamiliarRepository.listIntegrantesByfamilia( crianca.getFamilia().getId(), null ).getContent();
		
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
	
}



