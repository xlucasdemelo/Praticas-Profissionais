/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.familia.Familia;
import com.lucas.graca.domain.entity.planoatendimento.Encaminhamento;
import com.lucas.graca.domain.entity.planoatendimento.StatusEncaminhamento;
import com.lucas.graca.domain.entity.planoatendimentofamiliar.PlanoAtendimentoFamiliar;
import com.lucas.graca.domain.service.planoatendimento.PlanoAtendimentoService;
import com.lucas.graca.test.domain.AbstractIntegrationTests;
import com.lucas.graca.infrastructure.report.FamiliaReportRepository;

/**
 * @author lucas
 *
 */
public class PlanoAtendimentoServiceTests extends AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
	 *							ATRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private PlanoAtendimentoService planoAtendimentoService;
	
	/**
	 * 
	 */
//	@Autowired
//	private FamiliaReportRepository familiaReportRepository;
	
	/**
	 * 
	 */
	@Autowired
	private FamiliaReportRepository familiaReportRepository;
	
	/*-------------------------------------------------------------------
	 *				TESTS PLANO DE ATENDIMENTO FAMILIAR
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml", 
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertPlanoAtendimentoFamiliarMustPass()
	{
		
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = new PlanoAtendimentoFamiliar( null, null, null, new Familia(100L), null );
		
		planoAtendimentoFamiliar = this.planoAtendimentoService.insertPlanoAtendimentoFamiliar( planoAtendimentoFamiliar );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
	}
	
	/**
	 * Deve falhar, pois não é permitido ter duas famílias em execução ao mesmo tempo
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml", 
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void insertPlanoAtendimentoFamiliarMustFailWithPLanoALreadyInExecution()
	{
		
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = new PlanoAtendimentoFamiliar( null, null, null, new Familia(101L), null );
		
		planoAtendimentoFamiliar = this.planoAtendimentoService.insertPlanoAtendimentoFamiliar( planoAtendimentoFamiliar );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 */
	@Test(expected = AccessDeniedException.class)
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml", 
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertPlanoAtendimentoFamiliarMustFailWithoutOperadotAtendimento()
	{
		
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = new PlanoAtendimentoFamiliar( null, null, null, new Familia(100L), null );
		
		planoAtendimentoFamiliar = this.planoAtendimentoService.insertPlanoAtendimentoFamiliar( planoAtendimentoFamiliar );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml", 
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertPlanoAtendimentoFamiliarMustFailWIthId()
	{
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = new PlanoAtendimentoFamiliar( 9999L, null, null, new Familia(100L), null );
		
		planoAtendimentoFamiliar = this.planoAtendimentoService.insertPlanoAtendimentoFamiliar( planoAtendimentoFamiliar );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 */
	@Test(expected=NullPointerException.class)
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertPlanoAtendimentoFamiliarMustFailWIthouMandatoryFields()
	{
		
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = new PlanoAtendimentoFamiliar( null, null, null, null, null);
		
		planoAtendimentoFamiliar = this.planoAtendimentoService.insertPlanoAtendimentoFamiliar( planoAtendimentoFamiliar );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml", 
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPassWithOperadorAtendimento()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listPlanoAtendimentoFamiliarByFilters( null, true, null ).getContent();
		
		Assert.assertNotNull( planos );
		Assert.assertFalse( planos.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("colaborador_externo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml", 
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPassWithOperadorColaboradorExterno()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listPlanoAtendimentoFamiliarByFilters( null, true, null ).getContent();
		
		Assert.assertNotNull( planos );
		Assert.assertFalse( planos.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPassFilterName()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listPlanoAtendimentoFamiliarByFilters( "Paia", true, null ).getContent();
		
		Assert.assertNotNull( planos );
		Assert.assertFalse( planos.isEmpty() );
		Assert.assertTrue( planos.size() == 1 );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPassFilterNomeMae()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listPlanoAtendimentoFamiliarByFilters( "Marlene Paia", true, null ).getContent();
		
		Assert.assertNotNull( planos );
		Assert.assertFalse( planos.isEmpty() );
		Assert.assertTrue( planos.size() == 1 );
		Assert.assertTrue( planos.get(0).getFamilia().getNomeMae().equals( "Marlene Paia" ) );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPassFilterMustRetuirnEmptyList()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listPlanoAtendimentoFamiliarByFilters( "Nenhum registro", true, null ).getContent();
		
		Assert.assertNotNull( planos );
		Assert.assertTrue( planos.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void findPlanoAtendimentoFamiliarById()
	{
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoService.findPlanoAtendimentoFamiliarById( 9999L );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void disablePlanoAtendimentoFamiliarMustPass()
	{
		this.planoAtendimentoService.disablePlanoAtendimentoFamiliar( 9999L );
		
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoService.findPlanoAtendimentoFamiliarById(9999L);
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
		Assert.assertTrue( planoAtendimentoFamiliar.getAtivo() == false );
	}
	
	/**
	 * 
	 */
	@Test(expected= AccessDeniedException.class)
	@WithUserDetails("colaborador_externo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void disablePlanoAtendimentoFamiliarMustFailWithoutOperadorAtendimento()
	{
		this.planoAtendimentoService.disablePlanoAtendimentoFamiliar( 9999L );
		
		Assert.fail("Deveria falhar pois o ator nao tem permissao de acesso a esse metodo");
	}
	
	/**
	 * 
	 */
	@Test(expected= IllegalArgumentException.class)
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void disablePlanoAtendimentoFamiliarMustFailTryngToDisablePlanoEmExecucao()
	{
		this.planoAtendimentoService.disablePlanoAtendimentoFamiliar( 1002L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void enablePlanoAtendimentoFamiliarMustPass()
	{
		this.planoAtendimentoService.enablePlanoAtendimentoFamiliar( 9999L );

		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoService.findPlanoAtendimentoFamiliarById( 9999L );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
		Assert.assertTrue( planoAtendimentoFamiliar.getAtivo() == true );
	}
	
	/**
	 * 
	 */
	@Test(expected=AccessDeniedException.class)
	@WithUserDetails("colaborador_externo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void enablePlanoAtendimentoFamiliarMustFailWithUserWithoutPermission()
	{
		this.planoAtendimentoService.enablePlanoAtendimentoFamiliar( 9999L );

		Assert.fail();
	}
	
	/*-------------------------------------------------------------------
	 *				TESTS ENCAMINHAMENTO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
//	@Test
//	@WithUserDetails("operador_atendimento@email.com")
//	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
//			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
//			"/dataset/endereco/PaisDataSet.xml", 
//			"/dataset/endereco/EstadoDataSet.xml", 
//			"/dataset/endereco/CidadeDataSet.xml", 
//			"/dataset/endereco/EnderecoDataSet.xml", 
//			"/dataset/familia/FamiliaDataSet.xml",
//			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
//			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
//	})
//	public void insertEncaminhamentoMustPass()
//	{
//		Encaminhamento encaminhamento = new Encaminhamento( null, "Descrição pa", null, null, new PlanoAtendimentoFamiliar(9999L), null, null, Calendar.getInstance(), null, null );
//		
//		encaminhamento.setTipo( TipoEncaminhamento.SAUDE );
//		encaminhamento = this.planoAtendimentoService.insertEncaminhamento( encaminhamento, 9999L );
//		
//		Assert.assertNotNull( encaminhamento );
//		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
//	}
	
	/**
	 * 
	 */
	@Test(expected=AccessDeniedException.class)
	@WithUserDetails("colaborador_externo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void insertEncaminhamentoMustFailWithUserWithoutAccess()
	{
		Encaminhamento encaminhamento = new Encaminhamento( null, "Descrição pa", null, null, new PlanoAtendimentoFamiliar(9999L), null, null, Calendar.getInstance(), null, null);
		
		encaminhamento = this.planoAtendimentoService.insertEncaminhamento( encaminhamento, 9999L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void insertEncaminhamentoMustFailIthId()
	{
		Encaminhamento encaminhamento = new Encaminhamento( 9999L, "Descrição pa", null, null, new PlanoAtendimentoFamiliar(9999L), null, null, Calendar.getInstance(), null, null);
		
		encaminhamento = this.planoAtendimentoService.insertEncaminhamento( encaminhamento, 9999L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void updateEncaminhamentoMustPass()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 9999L );
		
		Assert.assertFalse( encaminhamento.getDescricao().equals( "Nova descrição" ) );
		
		encaminhamento.setDescricao( "Nova descrição" );
		
		encaminhamento = this.planoAtendimentoService.updateEncaminhamento( encaminhamento );
		
		Assert.assertNotNull( encaminhamento );
		Assert.assertTrue( encaminhamento.getDescricao().equals( "Nova descrição" ) );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void updateEncaminhamentoMustAllowOnlyEDescription()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 9999L );
		
		Assert.assertFalse( encaminhamento.getDescricao().equals( "Nova descrição" ) );
		Assert.assertFalse( encaminhamento.getStatus() == StatusEncaminhamento.CONCLUIDO );
		
		encaminhamento.setDescricao( "Nova descrição" );
		encaminhamento.setStatus( StatusEncaminhamento.CONCLUIDO );
		
		encaminhamento = this.planoAtendimentoService.updateEncaminhamento( encaminhamento );
		
		Assert.assertNotNull( encaminhamento );
		Assert.assertTrue( encaminhamento.getDescricao().equals( "Nova descrição" ) );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO);
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void updateEncaminhamentoMustFailWithIdNull()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 9999L );
		
		encaminhamento.setId( null );
		
		encaminhamento = this.planoAtendimentoService.updateEncaminhamento( encaminhamento );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void listEncaminhamentosByPlanoAtendimentoMustPass()
	{
		this.planoAtendimentoService.listEncaminhamentosByFilter( 9999L, null, null, null ).getContent();

	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("colaborador_externo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void listEncaminhamentosByPlanoAtendimentoMustPassWithColaboradorExterno()
	{
		List<Encaminhamento> encaminhamentos = this.planoAtendimentoService.listEncaminhamentosByFilter( 9999L, null, null, null ).getContent();
		
		Assert.assertNotNull( encaminhamentos );
	}
	
	/**
	 * 
	 */
	@Test(expected=AccessDeniedException.class)
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void listEncaminhamentosByPlanoAtendimentoMustFailWithUserWithoutPermission()
	{
		this.planoAtendimentoService.listEncaminhamentosByFilter( 9999L, null, null, null ).getContent();
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void listEncaminhamentosByPlanoAtendimentoMustReturnOne()
	{
		this.planoAtendimentoService.listEncaminhamentosByFilter( 9999L, "BOLOLO", null, null ).getContent();
		
		
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void listEncaminhamentosByPlanoAtendimentoMustReturnEmptyList()
	{
		List<Encaminhamento> encaminhamentos = this.planoAtendimentoService.listEncaminhamentosByFilter( 9999L, "Nenhum registro", null, null ).getContent();
		
		Assert.assertNotNull( encaminhamentos );
		Assert.assertTrue( encaminhamentos.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void changeEncaminhamentoToConcluidoMustPassWithAdmin()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 9999L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
		
		encaminhamento = this.planoAtendimentoService.concluirEncaminhamento( 9999L, "bla" );
		
		Assert.assertNotNull( encaminhamento );
		Assert.assertTrue( encaminhamento.getStatus() ==StatusEncaminhamento.CONCLUIDO );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void changeEncaminhamentoToConcluidoMustPassWithTryingToConcludeTheUsersOwnEncaminhamento()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 1002L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
		
		encaminhamento = this.planoAtendimentoService.concluirEncaminhamento( 1002L, "Ble" );
		
		Assert.assertNotNull( encaminhamento );
		Assert.assertTrue( encaminhamento.getStatus() ==StatusEncaminhamento.CONCLUIDO );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void changeEncaminhamentoToConcluidoMustFailWithTryingToConcludeTheOtherUserEncaminhamento()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 9999L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
		
		encaminhamento = this.planoAtendimentoService.concluirEncaminhamento( 9999L, "bla" );
		
		Assert.assertNotNull( encaminhamento );
		Assert.assertTrue( encaminhamento.getStatus() ==StatusEncaminhamento.CONCLUIDO );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void changeEncaminhamentoToConcluidoMustFailStatusAlreadyDone()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 1000L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.CONCLUIDO );
		
		encaminhamento = this.planoAtendimentoService.concluirEncaminhamento( 1000L, "dsdsd" );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void cancelEncaminhamentoMustPassWithAdmin()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 9999L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
		
		encaminhamento = this.planoAtendimentoService.cancelEncaminhamento( 9999L );
		
		Assert.assertNotNull( encaminhamento );
		Assert.assertTrue( encaminhamento.getStatus() ==StatusEncaminhamento.CANCELADO );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void cancelEncaminhamentoMustPassWitPermission()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 1003L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
		
		encaminhamento = this.planoAtendimentoService.cancelEncaminhamento( 1003L );
		
		Assert.assertNotNull( encaminhamento );
		Assert.assertTrue( encaminhamento.getStatus() ==StatusEncaminhamento.CANCELADO );
	}
	
	/**
	 * 
	 */
	@Test(expected=AccessDeniedException.class)
	@WithUserDetails("colaborador_externo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void cancelEncaminhamentoMustFailWithoutPermission()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 1003L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
		
		encaminhamento = this.planoAtendimentoService.cancelEncaminhamento( 1003L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void cancelEncaminhamentoMustFailWithStatusConcluido()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 1000L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.CONCLUIDO );
		
		encaminhamento = this.planoAtendimentoService.cancelEncaminhamento( 1000L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void cancelEncaminhamentoMustFailWithUserNotCreatorAndNotAdministrator()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 9999L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
		
		encaminhamento = this.planoAtendimentoService.cancelEncaminhamento( 9999L );
		
		Assert.fail();
	}
	
	/**
	 * GOAL : FAIL
	 * Usuário comum, tenta cancelar o encaminhamento de outro usuário 
	 */
	@Test(expected=AccessDeniedException.class)
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void cancelEncaminhamentoMustFailWithNormalUser()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 9999L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
		
		encaminhamento = this.planoAtendimentoService.cancelEncaminhamento( 9999L );
		
		Assert.fail();
	}
	
	/**
	 * Um usuário administrador tenta cancelar um encaminhamento em execução de outro funcionário, 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void cancelEncaminhamentoMustPassAdministratorCancelingOhterEncaminhamento()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 1001L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
		Assert.assertTrue( encaminhamento.getUsuario().getId() == 1002 );
		
		encaminhamento = this.planoAtendimentoService.cancelEncaminhamento( 1001L );
		
		Assert.assertNotNull( encaminhamento );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.CANCELADO );
	}
	
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml", "/dataset/redeapoio/redeApoioDataSet.xml",  "/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void gerarRelatorioFamiliasAtendidas() throws FileNotFoundException
	{
		
//		Calendar inicio = new GregorianCalendar(2013,1,1);
//		Calendar termino = Calendar.getInstance();
//		
//		List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
//		List<Produto> produtos = new ArrayList<Produto>();
		
		ByteArrayOutputStream report = this.familiaReportRepository.gerarRelatorioFamiliasAtendidas(null, null, null);
		
		FileOutputStream fos = new FileOutputStream("/tmp/teste.pdf");
		try
		{
			fos.write( report.toByteArray() );
			fos.close();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}
	
	
}
