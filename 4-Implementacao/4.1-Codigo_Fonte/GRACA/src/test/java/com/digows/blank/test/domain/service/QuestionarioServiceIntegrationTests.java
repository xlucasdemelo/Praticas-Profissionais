/**
 * 
 */
package com.digows.blank.test.domain.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;

import com.digows.blank.test.domain.AbstractIntegrationTests;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.questionario.Questao;
import com.lucas.graca.domain.entity.questionario.Questionario;
import com.lucas.graca.domain.entity.questionario.StatusVersaoQuestionario;
import com.lucas.graca.domain.entity.questionario.TipoQuestao;
import com.lucas.graca.domain.entity.questionario.VersaoQuestionario;
import com.lucas.graca.domain.service.questionario.QuestionarioService;


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
		List<Questionario> questionarios = this.questionarioService.listQuestionariosByUserAndFilters( null, 9999L, null ).getContent();
		
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
		List<Questionario> questionarios = this.questionarioService.listQuestionariosByUserAndFilters( null, 192L, null ).getContent();
		
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
		"/dataset/questionario/VersaoDataSet.xml"
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
		"/dataset/questionario/VersaoDataSet.xml"
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
		"/dataset/questionario/VersaoDataSet.xml"
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
		this.questionarioService.removeQuestao( 9999L );
		
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
		this.questionarioService.removeQuestao( 1000L );
		
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
}
