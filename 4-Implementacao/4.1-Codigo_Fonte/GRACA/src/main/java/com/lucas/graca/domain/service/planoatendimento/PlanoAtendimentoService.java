/**
 * 
 */
package com.lucas.graca.domain.service.planoatendimento;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.planoatendimentofamiliar.PlanoAtendimentoFamiliar;
import com.lucas.graca.domain.repository.planoatendimento.IPlanoAtendimentoFamiliarRepository;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
public class PlanoAtendimentoService
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private IPlanoAtendimentoFamiliarRepository planoAtendimentoFamiliarRepository;
	
	/*-------------------------------------------------------------------
	 *				SERVICES PLANO DE ATENDIMENTO INDIVIDUAL
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param planoAtendimentoFamiliar
	 * @return
	 */
	public PlanoAtendimentoFamiliar insertPlanoAtendimentoFamiliar( PlanoAtendimentoFamiliar planoAtendimentoFamiliar )
	{
		Assert.notNull( planoAtendimentoFamiliar );
		Assert.isNull( planoAtendimentoFamiliar.getId() );
		
		return this.planoAtendimentoFamiliarRepository.save( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 * @param planoAtendimentoFamiliar
	 * @return
	 */
	public PlanoAtendimentoFamiliar updatePlanoAtendimentoFamiliar( PlanoAtendimentoFamiliar planoAtendimentoFamiliar )
	{
		Assert.notNull( planoAtendimentoFamiliar );
		Assert.notNull( planoAtendimentoFamiliar.getId() );
		
		return this.planoAtendimentoFamiliarRepository.save( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PlanoAtendimentoFamiliar findPlanoAtendimentoFamiliarById( long id )
	{
		return this.planoAtendimentoFamiliarRepository.findOne( id );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void disablePlanoAtendimentoFamiliar( long id )
	{
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoFamiliarRepository.findOne( id );
		
		planoAtendimentoFamiliar.disablePlanoAtendimento();
		planoAtendimentoFamiliarRepository.save( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void enablePlanoAtendimentoFamiliar( long id )
	{
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoFamiliarRepository.findOne( id );
		
		planoAtendimentoFamiliar.enablePlanoAtendimento();
		planoAtendimentoFamiliarRepository.save( planoAtendimentoFamiliar );
	}
}
