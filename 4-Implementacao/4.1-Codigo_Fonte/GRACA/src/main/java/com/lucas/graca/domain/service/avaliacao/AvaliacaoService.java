/**
 * 
 */
package com.lucas.graca.domain.service.avaliacao;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.avaliacaoIndividual.AvaliacaoIndividual;
import com.lucas.graca.domain.entity.avaliacaoIndividual.ConfiguracaoAvaliacaoIndividual;
import com.lucas.graca.domain.entity.avaliacaoIndividual.Resposta;
import com.lucas.graca.domain.entity.questionario.Questao;
import com.lucas.graca.domain.entity.questionario.VersaoQuestionario;
import com.lucas.graca.domain.repository.avaliacao.IAvaliacaoRepository;
import com.lucas.graca.domain.repository.avaliacao.IConfiguracaoAvaliacaorepository;
import com.lucas.graca.domain.repository.avaliacao.IRespostaRepository;
import com.lucas.graca.domain.repository.questionario.IQuestaoRepository;
import com.lucas.graca.domain.repository.questionario.IVersaoQuestionario;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"')")
public class AvaliacaoService
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private IAvaliacaoRepository avaliacaoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IRespostaRepository respostaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IConfiguracaoAvaliacaorepository configuracaoAvaliacaorepository;
	
	/**
	 * 
	 */
	@Autowired
	private IQuestaoRepository questaoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IVersaoQuestionario versaoQuestionarioRepository;
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICES AVALIACAO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param avaliacaoIndividual
	 * @return
	 */
	public AvaliacaoIndividual insertAvaliacaoIndividual( AvaliacaoIndividual avaliacaoIndividual )
	{
		Assert.notNull( avaliacaoIndividual );
		Assert.isNull( avaliacaoIndividual.getId(), "Id precisa ser nulo" );
		Assert.notNull( avaliacaoIndividual.getCrianca(), "Criança é obrigatória" );
		
		ConfiguracaoAvaliacaoIndividual configuracaoAvaliacaoIndividual = this.configuracaoAvaliacaorepository.findTopByIdOrderByIdDesc();
		
		avaliacaoIndividual.setQuestionario( configuracaoAvaliacaoIndividual.getQuestionario() );
		avaliacaoIndividual.setAvaliador( (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal() );
		
		avaliacaoIndividual = this.avaliacaoRepository.save( avaliacaoIndividual );
		
		VersaoQuestionario versao = this.versaoQuestionarioRepository.findTopByQuestionarioIdOrderByNumeroVersaoDesc( configuracaoAvaliacaoIndividual.getQuestionario().getId() );
		
		List<Questao> questoes = this.questaoRepository.findByVersaoQuestionarioId( versao.getId(), null ).getContent();
		
		for ( Questao questao : questoes )
		{
			Resposta resposta = new Resposta();
			resposta.setAvaliacao( avaliacaoIndividual );
			resposta.setQuestao( questao );
			
			this.insertResposta( resposta );
		}
		
		return avaliacaoIndividual;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void disableAvaliacaoIndividual( long id )
	{
		AvaliacaoIndividual avaliacaoIndividual = this.avaliacaoRepository.findOne( id );
		
		Assert.notNull( avaliacaoIndividual, "não pode ser nulo" );
		
		avaliacaoIndividual.disableAvaliacao();
		this.avaliacaoRepository.saveAndFlush( avaliacaoIndividual );
	}
	
	/**
	 * 
	 * @return
	 */
	public AvaliacaoIndividual enviarParaAvaliacao( long id )
	{
		AvaliacaoIndividual avaliacaoIndividual = this.avaliacaoRepository.findOne( id );
		
		Assert.notNull( avaliacaoIndividual, "não pode ser nulo" );
		
		avaliacaoIndividual.changeToEnviadoParaAvaliacao();
		return this.avaliacaoRepository.saveAndFlush( avaliacaoIndividual );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('"+UserRole.ADMINISTRATOR_VALUE+"')")
	public AvaliacaoIndividual publicAceitarAvaliacao( long id )
	{
		AvaliacaoIndividual avaliacaoIndividual = this.avaliacaoRepository.findOne( id );
		
		Assert.notNull( avaliacaoIndividual, "não pode ser nulo" );
		avaliacaoIndividual.changeToAceito();
		
		return this.avaliacaoRepository.save( avaliacaoIndividual );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('"+UserRole.ADMINISTRATOR_VALUE+"')")
	public AvaliacaoIndividual publicRejeitarAvaliacao( long id )
	{
		AvaliacaoIndividual avaliacaoIndividual = this.avaliacaoRepository.findOne( id );
		
		Assert.notNull( avaliacaoIndividual, "não pode ser nulo" );
		avaliacaoIndividual.changeToRejeitado();;
		
		return this.avaliacaoRepository.save( avaliacaoIndividual );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<AvaliacaoIndividual> listAvaliacoesByFilters (String filter, PageRequest pageable)
	{
		return this.avaliacaoRepository.listAvaliacoesFilters( filter, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public AvaliacaoIndividual findAvaliacaoById( long id )
	{
		return this.avaliacaoRepository.findOne( id );
	}
	
	/**
	 * 
	 * @param configuracaoAvaliacaoIndividual
	 * @return
	 */
	public ConfiguracaoAvaliacaoIndividual insertConfiguração( ConfiguracaoAvaliacaoIndividual configuracaoAvaliacaoIndividual)
	{
		return this.configuracaoAvaliacaorepository.save( configuracaoAvaliacaoIndividual );
	}
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @return
	 */
	public Resposta insertResposta(Resposta resposta)
	{
		Assert.notNull( resposta );
		Assert.notNull( resposta.getQuestao() );
		
		return this.respostaRepository.save( resposta );
	}
	
	/**
	 * 
	 * @param resposta
	 * @return
	 */
	public Resposta updateResposta(Resposta resposta)
	{
		Assert.notNull( resposta );
		Assert.notNull( resposta.getId() );
		Assert.notNull( resposta.getQuestao() );
		Assert.notNull( resposta.getResposta() );
		
		return this.respostaRepository.save( resposta );
	}
	
	/**
	 * 
	 * @param avaliacaoId
	 * @return
	 */
	public Page<Resposta> listRespostasByAvaliacao( long avaliacaoId, PageRequest pageable )
	{
		return this.respostaRepository.findByAvaliacaoId( avaliacaoId, pageable );
	}
}
