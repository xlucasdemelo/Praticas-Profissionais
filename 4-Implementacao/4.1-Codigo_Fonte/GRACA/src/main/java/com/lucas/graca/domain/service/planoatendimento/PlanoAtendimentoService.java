
package com.lucas.graca.domain.service.planoatendimento;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.planoatendimento.Encaminhamento;
import com.lucas.graca.domain.entity.planoatendimento.StatusPlanoAtendimento;
import com.lucas.graca.domain.entity.planoatendimentofamiliar.PlanoAtendimentoFamiliar;
import com.lucas.graca.domain.repository.planoatendimento.IEncaminhamentoRepository;
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
	
	/**
	 * 
	 */
	@Autowired
	private IEncaminhamentoRepository encaminhamentoRepository;
	
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
		Assert.isTrue( planoAtendimentoFamiliar.getStatus() == StatusPlanoAtendimento.RASCUNHO, "Para alterar precisa estar em rascunho" );
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
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 */
	public Page<PlanoAtendimentoFamiliar> listByFilters( String filter, PageRequest pageable )
	{
		return this.planoAtendimentoFamiliarRepository.listByFilters( filter, pageable );
	}
	
	/*-------------------------------------------------------------------
	 *				SERVICES ENCAMINHAMENTO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param encaminhamento
	 * @return
	 */
	public Encaminhamento insertEncaminhamento( Encaminhamento encaminhamento )
	{
		Assert.notNull( encaminhamento );
		Assert.isNull( encaminhamento.getId() );
		
		return this.encaminhamentoRepository.save( encaminhamento );
	}
	
	/**
	 * 
	 * @param encaminhamento
	 * @return
	 */
	public Encaminhamento updateEncaminhamento( Encaminhamento encaminhamento )
	{
		Assert.notNull( encaminhamento );
		Assert.notNull( encaminhamento.getId() );
		
		Encaminhamento encaminhamentoDB = this.encaminhamentoRepository.findOne( encaminhamento.getId() );
		
		encaminhamentoDB.mergeToUpdate( encaminhamento );
		
		return this.encaminhamentoRepository.save( encaminhamentoDB );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Encaminhamento cancelEncaminhamento( long id )
	{
		Encaminhamento encaminhamento = this.encaminhamentoRepository.findOne( id );
		
		encaminhamento.changeToCancelado();
		
		return this.encaminhamentoRepository.save( encaminhamento );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Encaminhamento concluirEncaminhamento( long id )
	{
		Encaminhamento encaminhamento = this.encaminhamentoRepository.findOne( id );
		
		encaminhamento.changeToConcluido();
		
		return this.encaminhamentoRepository.save( encaminhamento );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	public Page<Encaminhamento> listEncaminhamentosByFilter( long idPlanoAtendimento, String filter, PageRequest pageable )
	{
		return this.encaminhamentoRepository.listByPlanoAtendimentoAndFilters( idPlanoAtendimento, filter, pageable );
	}
}
