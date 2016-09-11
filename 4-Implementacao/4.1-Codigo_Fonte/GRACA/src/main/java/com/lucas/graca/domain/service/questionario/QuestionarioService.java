/**
 * 
 */
package com.lucas.graca.domain.service.questionario;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.questionario.Questao;
import com.lucas.graca.domain.entity.questionario.Questionario;
import com.lucas.graca.domain.entity.questionario.StatusVersaoQuestionario;
import com.lucas.graca.domain.entity.questionario.VersaoQuestionario;
import com.lucas.graca.domain.repository.questionario.IQuestaoRepository;
import com.lucas.graca.domain.repository.questionario.IQuestionarioReposiotry;
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
		
		VersaoQuestionario versao = new VersaoQuestionario();
		versao.setNumeroVersao( 1 );
		
		this.versaoQuestionarioRepository.save( versao );
		
		return this.questionarioRepository.save( questionario );
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
		
		return this.questionarioRepository.save( questionario );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Questionario> listQuestionariosByFilters(String filter, PageRequest pageable)
	{
		return this.questionarioRepository.listQuestionariosByFilters( filter, pageable );
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
		
		Assert.isTrue( questao.getVersaoQuestionario().getStatus() == StatusVersaoQuestionario.RASCUNHO, "Para alterar o nome da questão, o status deve ser 'RASCUNHO'" );
		
		Questao questaoBd = this.questaoRepository.findOne( questao.getId() );
		return this.questaoRepository.save( questao )
	}
}
