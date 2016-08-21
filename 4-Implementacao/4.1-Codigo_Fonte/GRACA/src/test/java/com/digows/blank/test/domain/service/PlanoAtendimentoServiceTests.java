/**
 * 
 */
package com.digows.blank.test.domain.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
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
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/planoatendimento/PlanoAtendimentoFamiliarDataSet.xml"
	})
	public void listPlanoAtendimentoFamiliarByFiltersMustPass()
	{
		List<PlanoAtendimentoFamiliar> planos = this.planoAtendimentoService.listByFilters( null, null ).getContent();
		
		Assert.assertNotNull( planos );
		Assert.assertFalse( planos.isEmpty() );
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
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
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
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
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
	
	
}
