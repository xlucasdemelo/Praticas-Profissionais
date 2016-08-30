
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
import com.lucas.graca.domain.entity.planoatendimento.Parecer;
import com.lucas.graca.domain.entity.planoatendimento.Responsavel;
import com.lucas.graca.domain.entity.planoatendimento.StatusPlanoAtendimento;
import com.lucas.graca.domain.entity.planoatendimento.TipoEncaminhamento;
import com.lucas.graca.domain.entity.planoatendimentofamiliar.PlanoAtendimentoFamiliar;
import com.lucas.graca.domain.repository.planoatendimento.IEncaminhamentoRepository;
import com.lucas.graca.domain.repository.planoatendimento.IParecerRepository;
import com.lucas.graca.domain.repository.planoatendimento.IPlanoAtendimentoFamiliarRepository;
import com.lucas.graca.domain.repository.planoatendimento.IResponsavelRepository;

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
	
	/**
	 * 
	 */
	@Autowired
	private IResponsavelRepository responsavelRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IParecerRepository parecerRepository;
	
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
	 */
	@PreAuthorize("hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') ")
	public PlanoAtendimentoFamiliar associateFamiliaToPlano( PlanoAtendimentoFamiliar plano )
	{
		Assert.notNull( plano );
		Assert.notNull( plano.getFamilia() );
		
		PlanoAtendimentoFamiliar planoBD = this.planoAtendimentoFamiliarRepository.findOne( plano.getId() );
		
		Assert.isTrue( planoBD.getStatus() == 
				StatusPlanoAtendimento.RASCUNHO, "Para alterar a familia, somente no status rascunho" );
		
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoFamiliarRepository.findByFamiliaId( plano.getFamilia().getId() );
		
		for ( PlanoAtendimentoFamiliar planoAtendimentoFamiliarDB : planos )
		{
			planoAtendimentoFamiliarDB.validateUnicity();
		}
		
		return this.planoAtendimentoFamiliarRepository.save( plano );
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
	public PlanoAtendimentoFamiliar changetToFinalizado ( PlanoAtendimentoFamiliar planoAtendimentoFamiliar )
	{
		Assert.notNull( planoAtendimentoFamiliar );
		Assert.notNull( planoAtendimentoFamiliar.getMotivoFinalizacao(), "Prrencha o motivo pela finalização" );
		
		PlanoAtendimentoFamiliar planoAtendimentoFamiliarDB = this.planoAtendimentoFamiliarRepository.findOne( planoAtendimentoFamiliar.getId() );
		
		planoAtendimentoFamiliarDB.setMotivoFinalizacao( planoAtendimentoFamiliar.getMotivoFinalizacao() );
		
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
	public Encaminhamento insertEncaminhamento( Encaminhamento encaminhamento, long planoId )
	{
		Assert.notNull( encaminhamento );
		Assert.isNull( encaminhamento.getId() );
		
		encaminhamento.setPlanoAtendimento( new PlanoAtendimentoFamiliar(planoId) );
		
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
	public Encaminhamento concluirEncaminhamento( long id, String observacao )
	{
		Encaminhamento encaminhamento = this.encaminhamentoRepository.findOne( id );
		
		Assert.notNull( observacao, "A observação é obrigatória para concluir" );
		
		encaminhamento.setObservacao(observacao);
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
	public Page<Encaminhamento> listEncaminhamentosByFilter( long idPlanoAtendimento, String filter, TipoEncaminhamento tipo, PageRequest pageable )
	{
		return this.encaminhamentoRepository.listByPlanoAtendimentoAndFilters( idPlanoAtendimento, filter, tipo, pageable );
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
	
	/**
	 * 
	 * @return
	 */
	public TipoEncaminhamento[] listAllTIposEncaminhamento()
	{
		return TipoEncaminhamento.values();
	}
	
	/**
	 * 
	 * @param filters
	 * @return
	 */
	public List<Responsavel> listResponsaveisByFilters( String filters )
	{
		return this.responsavelRepository.listByFilters( filters );
	}
	
	/**
	 * 
	 * @param responsavel
	 * @return
	 */
	public Responsavel isnertResposnavel( Responsavel responsavel )
	{
		Assert.notNull( responsavel );
		Assert.isNull( responsavel.getId() );
		
		return this.responsavelRepository.save(responsavel);
	}
	
	/*-------------------------------------------------------------------
	 *				SERVICES PARECER
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param parecer
	 * @return
	 */
	public Parecer insertParecer( Parecer parecer )
	{
		Assert.notNull( parecer );
		
		parecer.setUsuario();
		
		return this.parecerRepository.save( parecer );
	}
	
	/**
	 * 
	 * @param idPlano
	 * @param tipoEncaminhamento
	 * @return
	 */
	public List<Parecer> listPareceresByPlanoAndTipo( long idPlano, TipoEncaminhamento tipoEncaminhamento, PageRequest pageable )
	{
		Assert.notNull(  tipoEncaminhamento );
		
		return this.parecerRepository.listByPlanoAtendimentoAndFilters( idPlano, tipoEncaminhamento, pageable );
	}
	
}
