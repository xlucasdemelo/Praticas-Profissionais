/**
 * 
 */
package com.digows.blank.test.domain.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;

import com.digows.blank.test.domain.AbstractIntegrationTests;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.familia.Familia;
import com.lucas.graca.domain.entity.planoatendimento.Encaminhamento;
import com.lucas.graca.domain.entity.planoatendimento.StatusEncaminhamento;
import com.lucas.graca.domain.entity.planoatendimentofamiliar.PlanoAtendimentoFamiliar;
import com.lucas.graca.domain.service.planoatendimento.PlanoAtendimentoService;

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
	
	/*-------------------------------------------------------------------
	 *				TESTS PLANO DE ATENDIMENTO FAMILIAR
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("atendente@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml", 
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertPlanoAtendimentoFamiliarMustPass()
	{
		
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = new PlanoAtendimentoFamiliar( null, null, null, new Familia(100L) );
		
		planoAtendimentoFamiliar = this.planoAtendimentoService.insertPlanoAtendimentoFamiliar( planoAtendimentoFamiliar );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 */
	@Test(expected = AccessDeniedException.class)
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml", 
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertPlanoAtendimentoFamiliarMustFailWithoutOperadotAtendimento()
	{
		
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = new PlanoAtendimentoFamiliar( null, null, null, new Familia(100L) );
		
		planoAtendimentoFamiliar = this.planoAtendimentoService.insertPlanoAtendimentoFamiliar( planoAtendimentoFamiliar );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertPlanoAtendimentoFamiliarMustFailWIthId()
	{
		
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = new PlanoAtendimentoFamiliar( 9999L, null, null, new Familia(100L) );
		
		planoAtendimentoFamiliar = this.planoAtendimentoService.insertPlanoAtendimentoFamiliar( planoAtendimentoFamiliar );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 */
	@Test(expected=DataIntegrityViolationException.class)
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertPlanoAtendimentoFamiliarMustFailWIthouMandatoryFields()
	{
		
		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = new PlanoAtendimentoFamiliar( null, null, null, null );
		
		planoAtendimentoFamiliar = this.planoAtendimentoService.insertPlanoAtendimentoFamiliar( planoAtendimentoFamiliar );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml", 
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPassWithOperadorAtendimento()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listByFilters( null, null ).getContent();
		
		Assert.assertNotNull( planos );
		Assert.assertFalse( planos.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("colaborador_externo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml", 
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPassWithOperadorColaboradorExterno()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listByFilters( null, null ).getContent();
		
		Assert.assertNotNull( planos );
		Assert.assertFalse( planos.isEmpty() );
	}
	
	//TODO CONTINUAR AJUSTANDO TUDO KRAI
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_atendimento@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPassFilterName()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listByFilters( "Paia", null ).getContent();
		
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
			"/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPassFilterNomeMae()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listByFilters( "Marlene Paia", null ).getContent();
		
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
			"/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPassFilterMustRetuirnEmptyList()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listByFilters( "Nenhum registro", null ).getContent();
		
		Assert.assertNotNull( planos );
		Assert.assertTrue( planos.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
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
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void disablePlanoAtendimentoFamiliar()
	{
		this.planoAtendimentoService.disablePlanoAtendimentoFamiliar( 9999L );

		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoService.findPlanoAtendimentoFamiliarById( 9999L );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
		Assert.assertTrue( planoAtendimentoFamiliar.getAtivo() == false );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void enablePlanoAtendimentoFamiliar()
	{
		this.planoAtendimentoService.enablePlanoAtendimentoFamiliar( 9999L );

		PlanoAtendimentoFamiliar planoAtendimentoFamiliar = this.planoAtendimentoService.findPlanoAtendimentoFamiliarById( 9999L );
		
		Assert.assertNotNull( planoAtendimentoFamiliar );
		Assert.assertTrue( planoAtendimentoFamiliar.getAtivo() == true );
	}
	
	/*-------------------------------------------------------------------
	 *				TESTS ENCAMINHAMENTO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void insertEncaminhamentoMustPass()
	{
		Encaminhamento encaminhamento = new Encaminhamento( null, "Descrição pa", null, null, new PlanoAtendimentoFamiliar(9999L), null, null);
		
		encaminhamento = this.planoAtendimentoService.insertEncaminhamento( encaminhamento );
		
		Assert.assertNotNull( encaminhamento );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
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
		Encaminhamento encaminhamento = new Encaminhamento( 9999L, "Descrição pa", null, null, new PlanoAtendimentoFamiliar(9999L), null, null);
		
		encaminhamento = this.planoAtendimentoService.insertEncaminhamento( encaminhamento );
		
		Assert.assertNotNull( encaminhamento );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
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
			"/dataset/account/UserDataSet.xml",
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
			"/dataset/account/UserDataSet.xml",
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
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
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
		List<Encaminhamento> encaminhamentos = this.planoAtendimentoService.listEncaminhamentosByFilter( 9999L, null, null ).getContent();
		
		Assert.assertNotNull( encaminhamentos );
		Assert.assertFalse( encaminhamentos.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
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
		List<Encaminhamento> encaminhamentos = this.planoAtendimentoService.listEncaminhamentosByFilter( 9999L, "BOLOLO", null ).getContent();
		
		Assert.assertNotNull( encaminhamentos );
		Assert.assertFalse( encaminhamentos.isEmpty() );
		Assert.assertTrue( encaminhamentos.size() == 1 );
		Assert.assertTrue( encaminhamentos.get(0).getDescricao().equals( "BOLOLO" ) );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
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
		List<Encaminhamento> encaminhamentos = this.planoAtendimentoService.listEncaminhamentosByFilter( 9999L, "Nenhum registro", null ).getContent();
		
		Assert.assertNotNull( encaminhamentos );
		Assert.assertTrue( encaminhamentos.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void changeEncaminhamentoToConcluidoMustPass()
	{
		Encaminhamento encaminhamento = this.planoAtendimentoService.findEncaminhamentoById( 9999L );
		Assert.assertTrue( encaminhamento.getStatus() == StatusEncaminhamento.EM_EXECUCAO );
		
		encaminhamento = this.planoAtendimentoService.concluirEncaminhamento( 9999L );
		
		Assert.assertNotNull( encaminhamento );
		Assert.assertTrue( encaminhamento.getStatus() ==StatusEncaminhamento.CONCLUIDO );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
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
		
		encaminhamento = this.planoAtendimentoService.concluirEncaminhamento( 1000L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
			"/dataset/endereco/PaisDataSet.xml",
			"/dataset/endereco/EstadoDataSet.xml",
			"/dataset/endereco/CidadeDataSet.xml",
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml",
			"/dataset/planoatendimento/EncaminhamentoDataSet.xml"
	})
	public void cancelEncaminhamentoMustPass()
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
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
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
	@Test(expected= IllegalArgumentException.class)
	@WithUserDetails("xova@testing.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
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
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("xova@testing.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/account/UserDataSet.xml",
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
			"/dataset/account/UserDataSet.xml",
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
	
}
