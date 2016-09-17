/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.avaliacaoIndividual.AvaliacaoIndividual;
import com.lucas.graca.domain.entity.avaliacaoIndividual.ConfiguracaoAvaliacaoIndividual;
import com.lucas.graca.domain.entity.avaliacaoIndividual.Resposta;
import com.lucas.graca.domain.entity.avaliacaoIndividual.StatusAvaliacaoIndividual;
import com.lucas.graca.domain.entity.crianca.Crianca;
import com.lucas.graca.domain.entity.questionario.Questionario;
import com.lucas.graca.domain.service.avaliacao.AvaliacaoService;
import com.lucas.graca.test.domain.AbstractIntegrationTests;



/**
 * @author lucas
 *
 */
public class AvaliacaoServiceIntegrationTests extends AbstractIntegrationTests
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private AvaliacaoService avaliacaoService;
	
	/*-------------------------------------------------------------------
	 *				 		SERVICES AVALIACAO
	 *-------------------------------------------------------------------*/
	
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml"
	})
	public void insertAvaliacaoMustPass()
	{
		AvaliacaoIndividual avaliacaoIndividual = new AvaliacaoIndividual();
		avaliacaoIndividual.setCrianca( new Crianca(100L) );
		avaliacaoIndividual.setQuestionario( new Questionario(9999L) );
		
		avaliacaoIndividual = this.avaliacaoService.insertAvaliacaoIndividual( avaliacaoIndividual );
		
		Assert.assertNotNull( avaliacaoIndividual );
		Assert.assertTrue( avaliacaoIndividual.getStatus() == StatusAvaliacaoIndividual.RASCUNHO );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml",
			"/dataset/redeapoio/redeApoioDataSet.xml",
			"/dataset/account/UserDataSet.xml",
			"/dataset/questionario/QuestionarioDataSet.xml",
			"/dataset/questionario/VersaoDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml"
	})
	public void insertAvaliacaoMustFailWithouCrianca()
	{
		AvaliacaoIndividual avaliacaoIndividual = new AvaliacaoIndividual();
		avaliacaoIndividual.setCrianca( null);
		avaliacaoIndividual.setQuestionario( new Questionario(9999L) );
		
		avaliacaoIndividual = this.avaliacaoService.insertAvaliacaoIndividual( avaliacaoIndividual );
		
		Assert.fail();
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void listAvaliacoesMustPass()
	{
		List<AvaliacaoIndividual> avaliacaoIndividual = this.avaliacaoService.listAvaliacoesByFilters( null, null ).getContent();
		
		Assert.assertFalse( avaliacaoIndividual.isEmpty() );
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
	})
	public void listAvaliacoesMustPassEmptyList()
	{
		List<AvaliacaoIndividual> avaliacaoIndividual = this.avaliacaoService.listAvaliacoesByFilters( null, null ).getContent();
		
		Assert.assertTrue( avaliacaoIndividual.isEmpty() );
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void disableAvaliacaoIndividualMustPass()
	{
		AvaliacaoIndividual avaliacaoIndividual = this.avaliacaoService.findAvaliacaoById( 9999L );
		
		Assert.assertNotNull( avaliacaoIndividual );
		Assert.assertTrue( avaliacaoIndividual.getEnabled() );
		
		this.avaliacaoService.disableAvaliacaoIndividual( 9999L );
		
		this.avaliacaoService.findAvaliacaoById( 9999L );
		
		Assert.assertNotNull( avaliacaoIndividual );
		Assert.assertTrue( avaliacaoIndividual.getEnabled() );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml",
			"/dataset/redeapoio/redeApoioDataSet.xml",
			"/dataset/account/UserDataSet.xml",
			"/dataset/questionario/QuestionarioDataSet.xml",
			"/dataset/questionario/VersaoDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void disableAvaliacaoIndividualMustFail()
	{
		this.avaliacaoService.disableAvaliacaoIndividual( 8888L );
		
		Assert.fail();
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void insertConfiguracaoMustPass()
	{
		ConfiguracaoAvaliacaoIndividual configuracaoAvaliacaoIndividual = new ConfiguracaoAvaliacaoIndividual();
		configuracaoAvaliacaoIndividual.setQuestionario( new Questionario(9999L) );;
		
		configuracaoAvaliacaoIndividual = this.avaliacaoService.insertConfiguracao( configuracaoAvaliacaoIndividual );
		
		Assert.assertNotNull( configuracaoAvaliacaoIndividual );
		Assert.assertTrue( configuracaoAvaliacaoIndividual.getId() == 1L );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml",
			"/dataset/redeapoio/redeApoioDataSet.xml",
			"/dataset/account/UserDataSet.xml",
			"/dataset/questionario/QuestionarioDataSet.xml",
			"/dataset/questionario/VersaoDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void insertConfiguracaoMustFailWithoutQuestionario()
	{
		ConfiguracaoAvaliacaoIndividual configuracaoAvaliacaoIndividual = new ConfiguracaoAvaliacaoIndividual();
		configuracaoAvaliacaoIndividual.setQuestionario( null );
		
		configuracaoAvaliacaoIndividual = this.avaliacaoService.insertConfiguracao( configuracaoAvaliacaoIndividual );
		
		Assert.fail();
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void sendAvaliacaoIndividuaToAvaliacaoMustPass()
	{
		AvaliacaoIndividual avaliacaoIndividual = this.avaliacaoService.findAvaliacaoById( 9999L );
		Assert.assertNotNull( avaliacaoIndividual );
		Assert.assertTrue( avaliacaoIndividual.getStatus() == StatusAvaliacaoIndividual.RASCUNHO );
		
		this.avaliacaoService.enviarParaAvaliacao( 9999L );
		
		avaliacaoIndividual = this.avaliacaoService.findAvaliacaoById( 9999L );
		Assert.assertNotNull( avaliacaoIndividual );
		Assert.assertTrue( avaliacaoIndividual.getStatus() == StatusAvaliacaoIndividual.ENVIADO_PARA_AVALIACAO );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml",
			"/dataset/redeapoio/redeApoioDataSet.xml",
			"/dataset/account/UserDataSet.xml",
			"/dataset/questionario/QuestionarioDataSet.xml",
			"/dataset/questionario/VersaoDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void sendAvaliacaoIndividuaToAvaliacaoMustFail()
	{
		this.avaliacaoService.enviarParaAvaliacao( 8888L );
		
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void aceitarAvaliacaoMustPassWithAdminisntrator()
	{
		AvaliacaoIndividual avaliacaoIndividual = this.avaliacaoService.findAvaliacaoById(1000);
		Assert.assertNotNull( avaliacaoIndividual );
		Assert.assertTrue( avaliacaoIndividual.getStatus() == StatusAvaliacaoIndividual.ENVIADO_PARA_AVALIACAO );
		
		this.avaliacaoService.aceitarAvaliacao( 1000L ) ;
		
		avaliacaoIndividual = this.avaliacaoService.findAvaliacaoById( 1000L );
		Assert.assertNotNull( avaliacaoIndividual );
		Assert.assertTrue( avaliacaoIndividual.getStatus() == StatusAvaliacaoIndividual.ACEITO );
	}
	
	/**
	 * 
	 */
	@Test(expected=AccessDeniedException.class)
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml",
			"/dataset/redeapoio/redeApoioDataSet.xml",
			"/dataset/account/UserDataSet.xml",
			"/dataset/questionario/QuestionarioDataSet.xml",
			"/dataset/questionario/VersaoDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void aceitarAvaliacaoMustFailWithRoleNotAdministrator()
	{
		this.avaliacaoService.aceitarAvaliacao( 1000L ) ;
		
		Assert.fail();
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void aceitarAvaliacaoNotInAguardandoAprovacao()
	{
		this.avaliacaoService.aceitarAvaliacao( 9999L ) ;
		
		Assert.fail();
	}
	
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml",
			"/dataset/redeapoio/redeApoioDataSet.xml",
			"/dataset/account/UserDataSet.xml",
			"/dataset/questionario/QuestionarioDataSet.xml",
			"/dataset/questionario/VersaoDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void rejeitarAvaliacaoMustPassWithAdminisntrator()
	{
		AvaliacaoIndividual avaliacaoIndividual = this.avaliacaoService.findAvaliacaoById(1000);
		Assert.assertNotNull( avaliacaoIndividual );
		Assert.assertTrue( avaliacaoIndividual.getStatus() == StatusAvaliacaoIndividual.ENVIADO_PARA_AVALIACAO );
		
		this.avaliacaoService.rejeitarAvaliacao( 1000L ) ;
		
		avaliacaoIndividual = this.avaliacaoService.findAvaliacaoById( 1000L );
		Assert.assertNotNull( avaliacaoIndividual );
		Assert.assertTrue( avaliacaoIndividual.getStatus() == StatusAvaliacaoIndividual.REJEITADO );
	}
	
	/**
	 * 
	 */
	@Test(expected=AccessDeniedException.class)
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml",
			"/dataset/redeapoio/redeApoioDataSet.xml",
			"/dataset/account/UserDataSet.xml",
			"/dataset/questionario/QuestionarioDataSet.xml",
			"/dataset/questionario/VersaoDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void rejeitarAvaliacaoMustFailWithRoleNotAdministrator()
	{
		this.avaliacaoService.rejeitarAvaliacao( 1000L ) ;
		
		Assert.fail();
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml"
	})
	public void rejeitarAvaliacaoNotInAguardandoAprovacao()
	{
		this.avaliacaoService.rejeitarAvaliacao( 9999L ) ;
		
		Assert.fail();
	}
	
	/*-------------------------------------------------------------------
	 *				 		SERVICES RESPOSTA
	 *-------------------------------------------------------------------*/
	
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml",
			"/dataset/avaliacaoindividual/RespostaDataSet.xml"
	})
	public void awnserRespostaMustPass()
	{
		Resposta resposta = this.avaliacaoService.findRespostaById( 9999L );
		
		Assert.assertNotNull( resposta );
		
		resposta.setResposta( "37 anos" );
		
		resposta = this.avaliacaoService.updateResposta( resposta );
		Assert.assertNotNull( resposta );
		Assert.assertTrue( resposta.getResposta().equals( "37 anos" ) );
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml",
			"/dataset/avaliacaoindividual/RespostaDataSet.xml"
	})
	public void awnserRespostaMustUpdateOnlyResposta()
	{
		Resposta resposta = this.avaliacaoService.findRespostaById( 9999L );
		
		Assert.assertNotNull( resposta );
		Assert.assertTrue( resposta.getAvaliacao().getId() == 9999L );
		
		resposta.setResposta( "37 anos" );
		resposta.setAvaliacao( new AvaliacaoIndividual(1000L) );
		
		resposta = this.avaliacaoService.updateResposta( resposta );
		Assert.assertNotNull( resposta );
		Assert.assertTrue( resposta.getResposta().equals( "37 anos" ) );
		Assert.assertTrue( resposta.getAvaliacao().getId() == 9999L );
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/crianca/CriancaDataSet.xml",
			"/dataset/avaliacaoindividual/COnfiguracaoDataSet.xml",
			"/dataset/avaliacaoindividual/AvaliacaoIndividualDataSet.xml",
			"/dataset/avaliacaoindividual/RespostaDataSet.xml"
	})
	public void listRespostasByAvaliacaoMustPass()
	{
		List<Resposta> respostas = this.avaliacaoService.listRespostasByAvaliacao( 9999L, null ).getContent();
		
		Assert.assertNotNull( respostas );
		Assert.assertFalse( respostas.isEmpty() );
	}
}
