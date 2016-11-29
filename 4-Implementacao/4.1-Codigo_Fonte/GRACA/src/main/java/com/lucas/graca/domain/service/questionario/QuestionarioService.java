/**
 * 
 */
package com.lucas.graca.domain.service.questionario;

import java.util.ArrayList;
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

import com.lucas.graca.application.security.ContextHolder;
import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.questionario.Questao;
import com.lucas.graca.domain.entity.questionario.Questionario;
import com.lucas.graca.domain.entity.questionario.QuestionarioResposta;
import com.lucas.graca.domain.entity.questionario.Resposta;
import com.lucas.graca.domain.entity.questionario.StatusQuestionarioResposta;
import com.lucas.graca.domain.entity.questionario.StatusVersaoQuestionario;
import com.lucas.graca.domain.entity.questionario.TipoQuestao;
import com.lucas.graca.domain.entity.questionario.VersaoQuestionario;
import com.lucas.graca.domain.repository.questionario.IQuestaoRepository;
import com.lucas.graca.domain.repository.questionario.IQuestionarioReposiotry;
import com.lucas.graca.domain.repository.questionario.IQuestionarioRespostaRepository;
import com.lucas.graca.domain.repository.questionario.IRespostaRepository;
import com.lucas.graca.domain.repository.questionario.IVersaoQuestionario;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"') || hasAuthority('"+UserRole.OPERADOR_ATENDIMENTOS_VALUE+"') ")
public class QuestionarioService
{

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private IQuestaoRepository questaoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IQuestionarioReposiotry questionarioRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IQuestionarioRespostaRepository questionarioRespostaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IRespostaRepository respostaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IVersaoQuestionario versaoQuestionarioRepository;
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param questionario
	 * @return
	 */
	public Questionario insertQuestionario( Questionario questionario )
	{
		Assert.notNull( questionario );
		Assert.isNull( questionario.getId(), "Id precisa ser nulo" );
		Assert.notNull( questionario.getNome(), "Nome é obrigatório" );
		
		questionario.setUsuarioCriador( (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal() );
		
		VersaoQuestionario versao = new VersaoQuestionario();
		versao.setNumeroVersao( 1 );
		versao.setQuestionario( questionario );
		
		questionario = this.questionarioRepository.saveAndFlush( questionario );
		
		this.versaoQuestionarioRepository.save( versao );
		
		return questionario;
	}
	
	/**
	 * 
	 * @param questionario
	 * @return
	 */
	public Questionario updateQuestionario( Questionario questionario )
	{
		Assert.notNull( questionario );
		Assert.notNull( questionario.getId(), "Id não pode ser nulo" );
		Assert.notNull( questionario.getNome(), "Nome é obrigatório" );
		
		Questionario questionarioBd = this.questionarioRepository.findOne( questionario.getId() );
		
		questionarioBd.setDescricao( questionario.getDescricao() );
		
		VersaoQuestionario maiorVersao = this.versaoQuestionarioRepository.findTopByQuestionarioIdOrderByNumeroVersaoDesc( questionario.getId() );
		maiorVersao.isRascunho();
		
		return this.questionarioRepository.save( questionario );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Questionario> listQuestionariosByUserAndFilters(String filter, long userId, PageRequest pageable)
	{
		return this.questionarioRepository.listQuestionariosByUserAndFilters( filter, userId, pageable );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"')")
	public Page<Questionario> listQuestionariosByFilters(String filter, PageRequest pageable)
	{
		Page<Questionario> questionarios = this.questionarioRepository.listQuestionariosByFilters( filter, pageable );
		
		for ( Questionario questionario : questionarios )
		{
			VersaoQuestionario maiorVersao = this.versaoQuestionarioRepository.findTopByQuestionarioIdOrderByNumeroVersaoDesc( questionario.getId() );
			questionario.setStatusVersao( maiorVersao.getStatus() );
		}
		
		return questionarios;
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"')")
	public List<Questionario> listQuestionariosAprovados(String filter, PageRequest pageable)
	{
		List<Questionario> questionarios = this.questionarioRepository.listQuestionariosByFilters( filter, pageable ).getContent();
		
		List<Questionario> questionariosAprovados = new ArrayList<Questionario>();
		
		for ( Questionario questionario : questionarios )
		{
			VersaoQuestionario maiorVersao = this.versaoQuestionarioRepository.findTopByQuestionarioIdOrderByNumeroVersaoDesc( questionario.getId() );
			
			if (maiorVersao.getStatus() == StatusVersaoQuestionario.APROVADO)
			{
				questionariosAprovados.add( questionario );
			}
		}
		
		return questionariosAprovados;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Questionario findQuestionarioById( long id )
	{
		return this.questionarioRepository.findOne( id );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void disableQuestionario( long id )
	{
		Questionario questionario = this.questionarioRepository.findOne( id );
		
		questionario.disableQuestionario();
		this.questionarioRepository.save( questionario );
	}
	
	/**
	 * 
	 * @return
	 */
	public VersaoQuestionario enviarVersaoParaAprovacao( long questionarioId )
	{
		VersaoQuestionario maiorVersao = this.versaoQuestionarioRepository.findTopByQuestionarioIdOrderByNumeroVersaoDesc( questionarioId );
		maiorVersao.isRascunho();
		
		List<Questao> questoes = this.questaoRepository.findByVersaoQuestionarioId(maiorVersao.getId(), null).getContent();
		Assert.notEmpty(questoes, "Insira ao menos uma questão");
		
		maiorVersao.changeToAguardandoAprovacao();
		
		return this.versaoQuestionarioRepository.save( maiorVersao );
	}
	
	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"')")
	public VersaoQuestionario aprovarVersao( long questionarioId )
	{
		VersaoQuestionario maiorVersao = this.versaoQuestionarioRepository.findTopByQuestionarioIdOrderByNumeroVersaoDesc( questionarioId );
		
		maiorVersao.isAguardandoaprovacao();
		maiorVersao.changeToAprovado();
		
		return this.versaoQuestionarioRepository.save( maiorVersao );
	}
	
	/**
	 * 
	 * @param versaoId
	 * @return
	 */
	@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"')")
	public VersaoQuestionario rejeitarVersao( long questionarioId )
	{
		VersaoQuestionario maiorVersao = this.versaoQuestionarioRepository.findTopByQuestionarioIdOrderByNumeroVersaoDesc( questionarioId );
		
		maiorVersao.changeToRejeitado();
		
		return this.versaoQuestionarioRepository.save( maiorVersao );
	}
	
	/**
	 * 
	 * @return
	 */
	public VersaoQuestionario aumentarVersao( long questionarioId )
	{
		
		VersaoQuestionario maiorVersao = this.versaoQuestionarioRepository.findTopByQuestionarioIdOrderByNumeroVersaoDesc( questionarioId );
		maiorVersao.isAprovado();
		
		Integer numeroVersao = maiorVersao.getNumeroVersao() + 1;
		
		List<Questao> questoes = this.questaoRepository.listQuestoesByQuestionario( maiorVersao.getQuestionario().getId(), null ).getContent();
		
		VersaoQuestionario novaVersao = new VersaoQuestionario();
		novaVersao.setNumeroVersao( numeroVersao );
		novaVersao.setQuestionario( maiorVersao.getQuestionario() );
		
		this.versaoQuestionarioRepository.saveAndFlush( novaVersao );
		
		for ( Questao questao : questoes )
		{
			Questao novaQuestao = questao.clone();
			novaQuestao.setVersaoQuestionario( novaVersao );
			
			this.questaoRepository.save( questao );
		}
		
		return novaVersao;
	}
	
	/**
	 * 
	 * @param questionarioId
	 * @return
	 */
	@Transactional(readOnly=true)
	public VersaoQuestionario findLastVersaoByQuestionario( long questionarioId )
	{
		
		return this.versaoQuestionarioRepository.findTopByQuestionarioIdOrderByNumeroVersaoDesc( questionarioId );
		
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public TipoQuestao[] listAllTiposQuestao()
	{
		return TipoQuestao.values();
	}
	
	/*-------------------------------------------------------------------
	 *				 		SERVICES QUESTAO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param questao
	 */
	public Questao insertQuestao( Questao questao )
	{
		Assert.notNull( questao );
		Assert.isNull( questao.getId(), "Id precisa ser nulo" );
		Assert.notNull( questao.getDescricao(), "descricao não pode ser nula" );
		Assert.notNull( questao.getTipoQuestao(), "Tipo não pode ser nulo" );
		Assert.notNull( questao.getVersaoQuestionario(), "Versão não pode ser nula");
		
		VersaoQuestionario versao = this.versaoQuestionarioRepository.findTopByQuestionarioIdOrderByNumeroVersaoDesc( questao.getVersaoQuestionario().getQuestionario().getId() );
		Assert.isTrue( versao.getStatus() == StatusVersaoQuestionario.RASCUNHO, "Status precisa ser rascunho" );
		
		questao.setVersaoQuestionario( versao );
		
		return this.questaoRepository.save( questao );
	}
	
	/**
	 * 
	 * @param questao
	 * @return
	 */
	public Questao updateQuestao( Questao questao )
	{
		Assert.notNull( questao );
		Assert.notNull( questao.getId(), "Id não pode sert nulo" );
		Assert.notNull( questao.getDescricao(), "descricao não pode ser nula" );
		Assert.notNull( questao.getTipoQuestao(), "Tipo não pode ser nulo" );
		
		questao.isRascunho();
		
		Questao questaoBd = this.questaoRepository.findOne( questao.getId() );
		questaoBd.setDescricao( questao.getDescricao() );
		questaoBd.setTipoQuestao( questao.getTipoQuestao() );
		
		return this.questaoRepository.save( questao );
	}
	
	/**
	 * 
	 */
	public void removeQuestao( long id )
	{
		Questao questao = this.questaoRepository.findOne( id );
		questao.isRascunho();
		
		this.questaoRepository.delete( questao );
	}
	
	/**
	 * 
	 * @param versaoId
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Questao> listQuestoesByVersao(long versaoId, PageRequest pageable)
	{
		return this.questaoRepository.findByVersaoQuestionarioId( versaoId, pageable );
	}
	
	/**
	 * 
	 * @param versaoId
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Questao> listQuestoesByQuestionario(long questionarioId, PageRequest pageable)
	{
		return this.questaoRepository.listQuestoesByQuestionario( questionarioId, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Questao findQuestaoById( long id )
	{
		return this.questaoRepository.findOne( id );
	}
	
	/*-------------------------------------------------------------------
	 *					SERVICES QUESTIONARIO RESPOSTA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param questionarioResposta
	 * @return
	 */
	public QuestionarioResposta insertQuestionarioResposta( QuestionarioResposta questionarioResposta )
	{
		Assert.notNull( questionarioResposta );
		
		VersaoQuestionario maiorVersao = this.versaoQuestionarioRepository.findTopByQuestionarioIdAndStatusOrderByNumeroVersaoDesc(questionarioResposta.getVersao().getQuestionario().getId(), StatusVersaoQuestionario.APROVADO );
		questionarioResposta.setVersao(maiorVersao);
		
		Assert.notNull( questionarioResposta.getVersao(), "Versão é obrigatório" );
		
		questionarioResposta.setUsuario( new User(ContextHolder.getAuthenticatedUser().getId() ) );
		
		questionarioResposta = this.questionarioRespostaRepository.saveAndFlush(questionarioResposta);
		
		this.removeQuestoesOfQuestionarioResposta(questionarioResposta);
		this.copyQuestoesToQuestionarioResposta(questionarioResposta);
		
		return questionarioResposta;
	}
	
	public VersaoQuestionario findLastVersaoAprovadaByQuestionarrio( long questionarioId )
	{
		return this.versaoQuestionarioRepository.findTopByQuestionarioIdAndStatusOrderByNumeroVersaoDesc(questionarioId, StatusVersaoQuestionario.APROVADO );
	}
	
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<QuestionarioResposta> listQuestionariosRespostaByUserAndFilters(String filters, PageRequest pageable)
	{
		return this.questionarioRespostaRepository.findByUsuarioId(ContextHolder.getAuthenticatedUser().getId() , pageable);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public QuestionarioResposta changeQuestionarioRespostaToFinalizado(long id)
	{
		QuestionarioResposta questionarioResposta = this.questionarioRespostaRepository.findOne(id);
		
		Assert.notNull(questionarioResposta, "Registro não encontrado");
		
		questionarioResposta.changeToFinalizado();
		
		return this.questionarioRespostaRepository.save(questionarioResposta);
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removeQuestionarioResposta( long id )
	{
		QuestionarioResposta questionarioResposta = this.questionarioRespostaRepository.findOne(id);
		
		Assert.notNull(questionarioResposta, "Registro não encontrado");
		
		Assert.isTrue(questionarioResposta.getStatus() == StatusQuestionarioResposta.RASCUNHO, "Status deve ser rascunho");
		
		this.removeQuestoesOfQuestionarioResposta(questionarioResposta);
		
		this.questionarioRespostaRepository.delete(questionarioResposta);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public QuestionarioResposta findQuestionarioRespostaById(long id)
	{
		return this.questionarioRespostaRepository.findOne(id);
	}
	
	/**
	 * 
	 * @param versaoId
	 */
	private void copyQuestoesToQuestionarioResposta(QuestionarioResposta questionarioResposta)
	{
		VersaoQuestionario maiorVersao = this.versaoQuestionarioRepository.findTopByQuestionarioIdAndStatusOrderByNumeroVersaoDesc(questionarioResposta.getVersao().getQuestionario().getId(), StatusVersaoQuestionario.APROVADO );
		
		List<Questao> questoes = this.questaoRepository.findByVersaoQuestionarioId(maiorVersao.getId(), null).getContent();
		
		for (Questao questao : questoes) 
		{
			Resposta resposta = new Resposta();
			resposta.setQuestao(questao);
			resposta.setQuestionarioResposta(questionarioResposta);
			
			this.respostaRepository.save(resposta);
		}
	}
	
	private void removeQuestoesOfQuestionarioResposta(QuestionarioResposta questionarioResposta)
	{
		List<Resposta> respostas = this.respostaRepository.findByQuestionarioRespostaId(questionarioResposta.getId(), null).getContent();
		
		for (Resposta resposta : respostas) 
		{
			this.respostaRepository.delete(resposta);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Resposta> responderResposta(List<Resposta> respostas)
	{
		Assert.notNull(respostas, "Resposta não pode ser nula");
		
		for (Resposta resposta : respostas) 
		{
			if (resposta.getQuestao().getTipoQuestao() == TipoQuestao.TEXTO)
				Assert.notNull(resposta.getRespostaTexto(), "Informe a resposta");
			else
				Assert.notNull(resposta.getRespostaBoolean(), "Informe a resposta");
			
			this.respostaRepository.save(resposta);
		}
		
		return respostas;
	}
	
	/**
	 * 
	 * @param questionarioRespostaId
	 * @param pageable
	 * @return
	 */
	public Page<Resposta> listRespostasByQuestionarioResposta(long questionarioRespostaId, PageRequest pageable)
	{
		return this.respostaRepository.findByQuestionarioRespostaId(questionarioRespostaId, pageable);
	}
	
}
