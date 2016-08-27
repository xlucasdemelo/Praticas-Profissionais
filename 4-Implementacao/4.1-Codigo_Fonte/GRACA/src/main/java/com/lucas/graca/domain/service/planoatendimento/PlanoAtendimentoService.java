
package com.lucas.graca.domain.service.planoatendimento;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
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
@PreAuthorize("hasAuthority('"+UserRole.COLABORADOR_EXTERNO_VALUE+"') || hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') ")
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
	@PreAuthorize("hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') ")
	public PlanoAtendimentoFamiliar insertPlanoAtendimentoFamiliar( PlanoAtendimentoFamiliar planoAtendimentoFamiliar )
	{
		Assert.notNull( planoAtendimentoFamiliar );
		Assert.isNull( planoAtendimentoFamiliar.getId() );
		Assert.notNull( planoAtendimentoFamiliar.getFamilia().getId() );
		
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoFamiliarRepository.findByFamiliaId( planoAtendimentoFamiliar.getFamilia().getId() );
		
		for ( PlanoAtendimentoFamiliar planoAtendimentoFamiliarDB : planos )
		{
			planoAtendimentoFamiliarDB.validateUnicity();
		}
		
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
	@PreAuthorize("hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') ")
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
	@PreAuthorize("hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') ")
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
	@PreAuthorize("hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') || hasAuthority('"+UserRole.COLABORADOR_EXTERNO_VALUE+"') ")
	public Page<PlanoAtendimentoFamiliar> listPlanoAtendimentoFamiliarByFilters( String filter, boolean ativo, PageRequest pageable )
	{
		return this.planoAtendimentoFamiliarRepository.listByFilters( filter, ativo, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PlanoAtendimentoFamiliar changetToEmExecucao ( long id )
	{
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoFamiliarRepository.findOne( id );
		
		planoAtendimentoFamiliar.changeToEmExecucao();
		return this.planoAtendimentoFamiliarRepository.save( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PlanoAtendimentoFamiliar changetToEmDesligamento ( long id )
	{
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoFamiliarRepository.findOne( id );
		
		planoAtendimentoFamiliar.changeToEmProcessoDesligamento();
		return this.planoAtendimentoFamiliarRepository.save( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PlanoAtendimentoFamiliar changetToEmReintegracao ( long id )
	{
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoFamiliarRepository.findOne( id );
		
		planoAtendimentoFamiliar.changeToEmProcessoReintegracao();
		return this.planoAtendimentoFamiliarRepository.save( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PlanoAtendimentoFamiliar changetToEmancipacao ( long id )
	{
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoFamiliarRepository.findOne( id );
		
		planoAtendimentoFamiliar.changeToEmProcessoEmancipacao();
		return this.planoAtendimentoFamiliarRepository.save( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PlanoAtendimentoFamiliar changetToFinalizado ( long id )
	{
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoFamiliarRepository.findOne( id );
		
		planoAtendimentoFamiliar.changeToFinalizado();
		return this.planoAtendimentoFamiliarRepository.save( planoAtendimentoFamiliar );
	} 
	
	/*-------------------------------------------------------------------
	 *				SERVICES ENCAMINHAMENTO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param encaminhamento
	 * @return
	 */
	@PreAuthorize("hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') ")
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
	@PreAuthorize("hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') ")
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
	@PreAuthorize("hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') ")
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
	@PreAuthorize("hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') || hasAuthority('"+UserRole.COLABORADOR_EXTERNO_VALUE+"')")
	public Page<Encaminhamento> listEncaminhamentosByFilter( long idPlanoAtendimento, String filter, PageRequest pageable )
	{
		return this.encaminhamentoRepository.listByPlanoAtendimentoAndFilters( idPlanoAtendimento, filter, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Encaminhamento findEncaminhamentoById( long id )
	{
		return this.encaminhamentoRepository.findOne( id );
	}
}
