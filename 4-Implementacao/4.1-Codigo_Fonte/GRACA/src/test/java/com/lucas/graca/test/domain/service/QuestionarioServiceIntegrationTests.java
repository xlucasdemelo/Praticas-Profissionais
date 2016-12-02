/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.util.List;

import org.directwebremoting.io.FileTransfer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.questionario.Questao;
import com.lucas.graca.domain.entity.questionario.Questionario;
import com.lucas.graca.domain.entity.questionario.QuestionarioResposta;
import com.lucas.graca.domain.entity.questionario.Resposta;
import com.lucas.graca.domain.entity.questionario.StatusQuestionarioResposta;
import com.lucas.graca.domain.entity.questionario.StatusVersaoQuestionario;
import com.lucas.graca.domain.entity.questionario.TipoQuestao;
import com.lucas.graca.domain.entity.questionario.VersaoQuestionario;
import com.lucas.graca.domain.service.questionario.QuestionarioService;
import com.lucas.graca.test.domain.AbstractIntegrationTests;


/**
 * @author lucas
 *
 */
public class QuestionarioServiceIntegrationTests extends AbstractIntegrationTests
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private QuestionarioService questionarioService;
	
	/*-------------------------------------------------------------------
	 *				 		    TESTS SERVICE 
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void insertQuestioNarioMustPass()
	{
		Questionario questionario = new Questionario();
		
		questionario.setNome( "Avaliacao individual" );
		questionario.setDescricao( "utilizaod para bla bla" );
		
		questionario = this.questionarioService.insertQuestionario( questionario );
		
		Assert.assertNotNull( questionario );
		Assert.assertNotNull( questionario.getId() );
		
		VersaoQuestionario versao = this.questionarioService.findLastVersaoByQuestionario( questionario.getId() );
		Assert.assertNotNull( versao );
		Assert.assertTrue( versao.getNumeroVersao() == 1 );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
	})
	public void insertQuestioNarioMustFail()
	{
		Questionario questionario = new Questionario();
		
		questionario.setNome( null );
		questionario.setDescricao( "utilizaod para bla bla" );
		
		questionario = this.questionarioService.insertQuestionario( questionario );
		
		Assert.fail();
	}
	
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void updateQuestionarioMustPass()
	{
		Questionario questionario = this.questionarioService.findQuestionarioById( 9999L );
		
		questionario.setNome( "Lixo 123" );
		questionario.setDescricao( "utilizaod para bla bla" );
		
		questionario = this.questionarioService.updateQuestionario( questionario );
		
		Assert.assertNotNull( questionario );
		Assert.assertNotNull( questionario.getId() );
		Assert.assertTrue( questionario.getNome().equals( "Lixo 123" ) );
		
		VersaoQuestionario versao = this.questionarioService.findLastVersaoByQuestionario( questionario.getId() );
		Assert.assertNotNull( versao );
		Assert.assertTrue( versao.getNumeroVersao() == 1 );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void updateQuestionarioMustFailStatusNotRascunho()
	{
		Questionario questionario = this.questionarioService.findQuestionarioById( 1000L );
		
		questionario.setNome( "Lixo 123wqe" );
		questionario.setDescricao( "utilizaod para bla bla" );
		
		questionario = this.questionarioService.updateQuestionario( questionario );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void listQuestionariosByUsersAndFilters()
	{
		List<Questionario> questionarios = this.questionarioService.listQuestionariosByUserAndFilters( null, null ).getContent();
		
		Assert.assertFalse( questionarios.isEmpty() );
		
		for ( Questionario questionario : questionarios )
		{
			Assert.assertTrue( questionario.getUsuarioCriador().getId() == 9999L );
		}
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void listQuestionariosByUsersAndFiltersMustReturnEmptyList()
	{
		List<Questionario> questionarios = this.questionarioService.listQuestionariosByUserAndFilters( null, null ).getContent();
		
		Assert.assertTrue( questionarios.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void listAllQuestionariosByFilters()
	{
		List<Questionario> questionarios = this.questionarioService.listQuestionariosByFilters( null, null ) .getContent();
		
		Assert.assertFalse( questionarios.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test(expected=AccessDeniedException.class)
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void listAllQuestionariosByFiltersMustFailWithUserRolerNotAtendente()
	{
		this.questionarioService.listQuestionariosByFilters( null, null );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void disableQuestionarioMustPass()
	{
		
		this.questionarioService.disableQuestionario( 9999L );
		
		Questionario questionario = this.questionarioService.findQuestionarioById( 9999L );
		Assert.assertTrue( questionario.getEnabled() == false );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml"
	})
	public void enviarVersaoParaAvaliacaoMustPass()
	{
		this.questionarioService.enviarVersaoParaAprovacao( 9999L );
		
		VersaoQuestionario versao = this.questionarioService.findLastVersaoByQuestionario( 9999L );
		
		Assert.assertNotNull( versao );
		Assert.assertTrue( versao.getStatus() == StatusVersaoQuestionario.AGUARDANDO_APROVACAO );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void enviarVersaoParaAvaliacaoMustFailVersaoConcluida()
	{
		this.questionarioService.enviarVersaoParaAprovacao( 1000L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml"
	})
	public void aprovarVersaoMustPass()
	{
		this.questionarioService.enviarVersaoParaAprovacao( 9999L );
		
		VersaoQuestionario versao = this.questionarioService.findLastVersaoByQuestionario( 9999L );
		
		Assert.assertNotNull( versao );
		Assert.assertTrue( versao.getStatus() == StatusVersaoQuestionario.AGUARDANDO_APROVACAO );
		
		this.questionarioService.aprovarVersao( 9999L );
		
		versao = this.questionarioService.findLastVersaoByQuestionario( 9999L );
		
		Assert.assertNotNull( versao );
		Assert.assertTrue( versao.getStatus() == StatusVersaoQuestionario.APROVADO );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void aprovarVersaoMustFailWrongStatus()
	{
		this.questionarioService.aprovarVersao( 9999L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
	})
	public void rejeitarVersaoMustPass()
	{
		this.questionarioService.enviarVersaoParaAprovacao( 9999L );
		
		VersaoQuestionario versao = this.questionarioService.findLastVersaoByQuestionario( 9999L );
		
		Assert.assertNotNull( versao );
		Assert.assertTrue( versao.getStatus() == StatusVersaoQuestionario.AGUARDANDO_APROVACAO );
		
		this.questionarioService.rejeitarVersao( 9999L );
		
		versao = this.questionarioService.findLastVersaoByQuestionario( 9999L );
		
		Assert.assertNotNull( versao );
		Assert.assertTrue( versao.getStatus() == StatusVersaoQuestionario.REJEITADO );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void rejeitarVersaoMustFailWrongStatus()
	{
		this.questionarioService.aprovarVersao( 9999L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void aumentarVersaoMustPass()
	{
		VersaoQuestionario versao = this.questionarioService.findLastVersaoByQuestionario( 1000L );
		
		Assert.assertNotNull( versao );
		Assert.assertTrue( versao.getStatus() == StatusVersaoQuestionario.APROVADO || versao.getStatus() == StatusVersaoQuestionario.REJEITADO);
		Assert.assertTrue( versao.getNumeroVersao() == 1 );
		
		this.questionarioService.aumentarVersao( 1000L );
		
		versao = this.questionarioService.findLastVersaoByQuestionario( 1000L );
		
		Assert.assertNotNull( versao );
		Assert.assertTrue( versao.getStatus() == StatusVersaoQuestionario.RASCUNHO );
		Assert.assertTrue( versao.getNumeroVersao() == 2 );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void aumentarVersaoMustWithStatusRascunho()
	{
		VersaoQuestionario versao = this.questionarioService.findLastVersaoByQuestionario( 9999L );
		
		Assert.assertNotNull( versao );
		Assert.assertTrue( versao.getStatus() == StatusVersaoQuestionario.RASCUNHO);
		Assert.assertTrue( versao.getNumeroVersao() == 1 );
		
		this.questionarioService.aumentarVersao( 9999L );
		
		Assert.fail();
	}
	
	/*-------------------------------------------------------------------
	 *				 		    TESTS QUESTÕES 
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void insertQuestaoMustPass()
	{
		Questao questao = new Questao();
		questao.setDescricao( "bla bla" );
		questao.setTipoQuestao( TipoQuestao.TEXTO );
		questao.setVersaoQuestionario( new VersaoQuestionario(9999L) );
		questao.getVersaoQuestionario().setQuestionario( new Questionario(9999L) );
		
		questao = this.questionarioService.insertQuestao( questao );
		
		Assert.assertNotNull( questao );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml"
	})
	public void insertQuestaoMustFailWithVersaoAprovada()
	{
		Questao questao = new Questao();
		questao.setDescricao( "bla bla" );
		questao.setTipoQuestao( TipoQuestao.TEXTO );
		questao.setVersaoQuestionario( new VersaoQuestionario(1000L) );
		questao.getVersaoQuestionario().setQuestionario( new Questionario(1000L) );
		
		questao = this.questionarioService.insertQuestao( questao );
		
		Assert.assertNotNull( questao );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
	})
	public void removeQuestaoMustPass()
	{
		this.questionarioService.disableQuestao( 9999L );
		
		Questao questao = this.questionarioService.findQuestaoById( 9999L );
		Assert.assertNull( questao );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
	})
	public void removeQuestaoMustFailNotRascunho()
	{
		this.questionarioService.disableQuestao( 1000L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
	})
	public void listQuestoesByVersao()
	{
		List<Questao> questoes = this.questionarioService.listQuestoesByVersao( 9999L, null ).getContent();
		
		Assert.assertFalse( questoes.isEmpty() );
		
		for ( Questao questao : questoes )
		{
			Assert.assertTrue( questao.getVersaoQuestionario().getId() == 9999L );
		}
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
	})
	public void listQuestoesByVersaoMustReturnEmptyList()
	{
		List<Questao> questoes = this.questionarioService.listQuestoesByVersao( 234234L, null ).getContent();
		
		Assert.assertTrue( questoes.isEmpty() );
	}
	
	/*-------------------------------------------------------------------
	 *				 	TESTS QUESTÕES RESPONDIDAS 
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
	})
	public void insertQuestionarioRespostaMustPass()
	{
		QuestionarioResposta questionarioResposta = new QuestionarioResposta();
		
		questionarioResposta.setVersao( new VersaoQuestionario(1000L) );
		
		questionarioResposta = this.questionarioService.insertQuestionarioResposta(questionarioResposta);
		
		Assert.assertNotNull(questionarioResposta);
		Assert.assertNotNull(questionarioResposta.getId());
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
	})
	public void insertQuestionarioRespostaMustFail()
	{
		QuestionarioResposta questionarioResposta = new QuestionarioResposta();
		
		questionarioResposta.setVersao( null );
		
		questionarioResposta = this.questionarioService.insertQuestionarioResposta(questionarioResposta);
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
		"/dataset/questionario/QuestionarioRespostaDataSet.xml"
	})
	public void listQuestionariosRespostaByUsuarioMustPass()
	{
		List<QuestionarioResposta> questionariosResposta = this.questionarioService.listQuestionariosRespostaByUserAndFilters(null, null).getContent();
		
		Assert.assertNotNull(questionariosResposta);
		Assert.assertFalse(questionariosResposta.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
		"/dataset/questionario/QuestionarioRespostaDataSet.xml"
	})
	public void listQuestionariosRespostaByUsuarioMustReturnNone()
	{
		List<QuestionarioResposta> questionariosResposta = this.questionarioService.listQuestionariosRespostaByUserAndFilters(null, null).getContent();
		
		Assert.assertNotNull(questionariosResposta);
		Assert.assertTrue(questionariosResposta.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
		"/dataset/questionario/QuestionarioRespostaDataSet.xml",
		"/dataset/questionario/RespostaDataSet.xml"
	})
	public void listRespostasByQuestionarioRespostaMustPass()
	{
		List<Resposta> respotas = this.questionarioService.listRespostasByQuestionarioResposta(9999L, null).getContent();
		
		Assert.assertNotNull(respotas);
		Assert.assertFalse(respotas.isEmpty());
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
		"/dataset/questionario/QuestionarioRespostaDataSet.xml",
		"/dataset/questionario/RespostaDataSet.xml"
	})
	public void responderRespostaMustPass()
	{
		List<Resposta> respostas = this.questionarioService.listRespostasByQuestionarioResposta(9999L, null).getContent();
		
		respostas.get(0).setRespostaTexto("LALA");
		respostas.get(1).setRespostaTexto("PAPAPA");
		
		respostas = this.questionarioService.responderResposta(respostas);
		
		Assert.assertNotNull(respostas);
		Assert.assertFalse(respostas.isEmpty());
		
		for (Resposta resposta : respostas) 
		{
			Assert.assertTrue(resposta.getRespostaTexto() != null || resposta.getRespostaBoolean() != null);
		}
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
		"/dataset/questionario/QuestionarioRespostaDataSet.xml",
		"/dataset/questionario/RespostaDataSet.xml"
	})
	public void changeQuestionarioRespostaToFinalizado()
	{
		this.questionarioService.changeQuestionarioRespostaToFinalizado(9999L);
		
		Assert.assertTrue( this.questionarioService.findQuestionarioRespostaById(9999L).getStatus() == StatusQuestionarioResposta.FINALIZADO );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml",
		"/dataset/redeapoio/redeApoioDataSet.xml",
		"/dataset/account/UserDataSet.xml",
		"/dataset/questionario/QuestionarioDataSet.xml",
		"/dataset/questionario/VersaoDataSet.xml",
		"/dataset/questionario/QuestaoDataSet.xml",
		"/dataset/questionario/QuestionarioRespostaDataSet.xml",
		"/dataset/questionario/RespostaDataSet.xml"
	})
	public void imprimirQuestionarioMustPass()
	{
		FileTransfer file = this.questionarioService.imprimirQuestionario(1000L);
		
		Assert.assertNotNull(file);
	}
	
}
