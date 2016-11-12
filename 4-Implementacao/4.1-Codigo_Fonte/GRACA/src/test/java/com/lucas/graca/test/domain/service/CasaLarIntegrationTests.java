/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.casalar.CasaLar;
import com.lucas.graca.domain.entity.casalar.OrcamentoFamiliar;
import com.lucas.graca.domain.entity.casalar.StatusOrcamentoFamiliar;
import com.lucas.graca.domain.entity.planoatendimento.Responsavel;
import com.lucas.graca.domain.service.casalar.CasaLarService;
import com.lucas.graca.test.domain.AbstractIntegrationTests;

/**
 * @author lucas
 *
 */
public class CasaLarIntegrationTests extends AbstractIntegrationTests
{
	
	/**
	 * 
	 */
	@Autowired
	private CasaLarService casaLarService;
	
	/*-------------------------------------------------------------------
	 *				 		  TESTS CASA LAR
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
	})
	public void insertCasalarMustPass()
	{
		CasaLar casaLar = new CasaLar();
		casaLar.setNumero( 2 );
		casaLar.setCuidadoraApoiadora( new Responsavel() );
		casaLar.getCuidadoraApoiadora().setNome( "work" );
		casaLar.setCuidadoraResidente( new Responsavel() );
		casaLar.getCuidadoraResidente().setNome( "work" );
		
		casaLar = this.casaLarService.insertCasaLar( casaLar );
		
		Assert.assertNotNull( casaLar );
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
	public void insertCasalarMustFailWithoutMandaToryFields()
	{
		CasaLar casaLar = new CasaLar();
		casaLar.setNumero( null );
		casaLar.setCuidadoraApoiadora( new Responsavel() );
		casaLar.getCuidadoraApoiadora().setNome( "work" );
		casaLar.setCuidadoraResidente( new Responsavel() );
		casaLar.getCuidadoraResidente().setNome( "work" );
		
		casaLar = this.casaLarService.insertCasaLar( casaLar );
		
		Assert.assertNotNull( casaLar );
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
			"/dataset/casalar/CasaLarDataSet.xml",
	})
	public void updateCasaLarMustPass()
	{
		CasaLar casaLar = this.casaLarService.findCasaLarById( 9999L );
		
		casaLar.setNumero( 5 );
		casaLar = this.casaLarService.updateCasaLar( casaLar );
		
		Assert.assertNotNull( casaLar );
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
	})
	public void updateCasaLarMustFail()
	{
		CasaLar casaLar = this.casaLarService.findCasaLarById( 233L );
		
		Assert.assertNull( casaLar );
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
			"/dataset/casalar/CasaLarDataSet.xml"
	})
	public void removeCasaLarMustPass()
	{
		CasaLar casaLar = this.casaLarService.findCasaLarById( 9999L );
		Assert.assertNotNull( casaLar );
		
		this.casaLarService.removeCasaLar( 9999L );
		
		casaLar = this.casaLarService.findCasaLarById( 9999L );
		Assert.assertNull( casaLar );
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
			"/dataset/casalar/CasaLarDataSet.xml",
	})
	public void listCasaLarByIdMustPass()
	{
		List<CasaLar> casas = this.casaLarService.listByFilters( null, null ).getContent();
		
		Assert.assertNotNull( casas );
		Assert.assertTrue( !casas.isEmpty() );
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
			"/dataset/casalar/CasaLarDataSet.xml",
	})
	public void listCasaLarByIdMustReturnOne()
	{
		List<CasaLar> casas = this.casaLarService.listByFilters( "1", null ).getContent();
		
		Assert.assertNotNull( casas );
		Assert.assertTrue( !casas.isEmpty() );
		Assert.assertTrue( casas.size() == 1 );
		Assert.assertTrue( casas.get( 0 ).getNumero() == 1 );
	}
	
	/*-------------------------------------------------------------------
	 *				 	TESTS ORÇAMENTO FAMILIAR
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
			"/dataset/casalar/CasaLarDataSet.xml"
	})
	public void insertOrcamentoFamiliarMustPass()
	{
		OrcamentoFamiliar orcamentoFamiliar = new OrcamentoFamiliar();
		
		orcamentoFamiliar.setPeriodo( YearMonth.now() );
		orcamentoFamiliar.setRendaPerCapitaAlimentacao( new BigDecimal( "100" ) );
		orcamentoFamiliar.setRendaPerCapitaHigiene( new BigDecimal( "200" ) );
		orcamentoFamiliar.setCasaLar( new CasaLar(9999L) );
		
		orcamentoFamiliar = this.casaLarService.insertOrcamentoFamiliar( orcamentoFamiliar );
		
		Assert.assertNotNull( orcamentoFamiliar );
		Assert.assertNotNull( orcamentoFamiliar.getId() );
	}
	
	/**
	 * 
	 */
	@Test(expected= IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml",
			"/dataset/redeapoio/redeApoioDataSet.xml",
			"/dataset/account/UserDataSet.xml",
			"/dataset/casalar/CasaLarDataSet.xml"
	})
	public void insertOrcamentoFamiliarMustFail()
	{
		OrcamentoFamiliar orcamentoFamiliar = new OrcamentoFamiliar();
		
		orcamentoFamiliar.setPeriodo( YearMonth.now() );
		orcamentoFamiliar.setRendaPerCapitaAlimentacao( new BigDecimal( "100" ) );
		orcamentoFamiliar.setRendaPerCapitaHigiene( new BigDecimal( "200" ) );
		orcamentoFamiliar.setCasaLar( null );
		
		orcamentoFamiliar = this.casaLarService.insertOrcamentoFamiliar( orcamentoFamiliar );
		
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
			"/dataset/casalar/CasaLarDataSet.xml",
			"/dataset/casalar/OrcamentoFamiliarDataSet.xml"
	})
	public void removeOrcamentoFamiliarMustPass()
	{
		this.casaLarService.removeOrcamentoFamiliar( 9999L );
		
		Assert.assertNull( this.casaLarService.findOrcamentoFamiliarById( 9999L ));
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
			"/dataset/casalar/CasaLarDataSet.xml",
			"/dataset/casalar/OrcamentoFamiliarDataSet.xml"
	})
	public void removeOrcamentoFamiliarMustFailStatusNotRascunho()
	{
		this.casaLarService.removeOrcamentoFamiliar( 1000L );
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
			"/dataset/casalar/CasaLarDataSet.xml",
			"/dataset/casalar/OrcamentoFamiliarDataSet.xml"
	})
	public void listOrcamentosFamiliaresByCasaLarFiltersMustPass()
	{
		final List<OrcamentoFamiliar> orcamentos = this.casaLarService.listOrcamentosFamiliaresByCasaLarAndFilters( 9999L, null )
				.getContent();
		
		Assert.assertNotNull( orcamentos );
		Assert.assertFalse( orcamentos.isEmpty() );
		
		for ( OrcamentoFamiliar orcamentoFamiliar : orcamentos )
		{
			Assert.assertTrue( orcamentoFamiliar.getCasaLar().getId() == 9999L );
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
			"/dataset/casalar/CasaLarDataSet.xml",
			"/dataset/casalar/OrcamentoFamiliarDataSet.xml"
	})
	public void changeOrcamentoFamiliarToVigenteMustPass()
	{
		OrcamentoFamiliar orcamentoFamiliar = new OrcamentoFamiliar();
		
		orcamentoFamiliar.setPeriodo( YearMonth.now() );
		orcamentoFamiliar.setRendaPerCapitaAlimentacao( new BigDecimal( "100" ) );
		orcamentoFamiliar.setRendaPerCapitaHigiene( new BigDecimal( "200" ) );
		orcamentoFamiliar.setCasaLar( new CasaLar(9999L) );
		
		orcamentoFamiliar = this.casaLarService.insertOrcamentoFamiliar( orcamentoFamiliar );
		
		orcamentoFamiliar = this.casaLarService.changeOrcamentoFamiliarToVigente( orcamentoFamiliar.getId() );
		
		Assert.assertNotNull( orcamentoFamiliar );
		Assert.assertTrue( orcamentoFamiliar.getStatus() == StatusOrcamentoFamiliar.VIGENTE );
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
			"/dataset/casalar/CasaLarDataSet.xml",
			"/dataset/casalar/OrcamentoFamiliarDataSet.xml"
	})
	public void changeOrcamentoFamiliarToVigenteMustFailOrcamentoFromAnotherPeriod()
	{
		OrcamentoFamiliar orcamentoFamiliar = new OrcamentoFamiliar();
		
		orcamentoFamiliar.setPeriodo( YearMonth.of(2016,12) );
		orcamentoFamiliar.setRendaPerCapitaAlimentacao( new BigDecimal( "100" ) );
		orcamentoFamiliar.setRendaPerCapitaHigiene( new BigDecimal( "200" ) );
		orcamentoFamiliar.setCasaLar( new CasaLar(9999L) );
		
		orcamentoFamiliar = this.casaLarService.insertOrcamentoFamiliar( orcamentoFamiliar );
		
		orcamentoFamiliar = this.casaLarService.changeOrcamentoFamiliarToVigente( orcamentoFamiliar.getId() );
		
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
			"/dataset/casalar/CasaLarDataSet.xml",
			"/dataset/casalar/OrcamentoFamiliarDataSet.xml"
	})
	public void changeOrcamentoFamiliarToVigenteMustFailAlreadyHasOneVigente()
	{
		OrcamentoFamiliar orcamentoFamiliar = new OrcamentoFamiliar();
		
		orcamentoFamiliar.setPeriodo( YearMonth.now() );
		orcamentoFamiliar.setRendaPerCapitaAlimentacao( new BigDecimal( "100" ) );
		orcamentoFamiliar.setRendaPerCapitaHigiene( new BigDecimal( "200" ) );
		orcamentoFamiliar.setCasaLar( new CasaLar(9999L) );
		
		orcamentoFamiliar = this.casaLarService.insertOrcamentoFamiliar( orcamentoFamiliar );
		
		orcamentoFamiliar = this.casaLarService.changeOrcamentoFamiliarToVigente( orcamentoFamiliar.getId() );
		
		OrcamentoFamiliar orcamentoFamiliar2 = new OrcamentoFamiliar();
		
		orcamentoFamiliar2.setPeriodo( YearMonth.now() );
		orcamentoFamiliar2.setRendaPerCapitaAlimentacao( new BigDecimal( "100" ) );
		orcamentoFamiliar2.setRendaPerCapitaHigiene( new BigDecimal( "200" ) );
		orcamentoFamiliar2.setCasaLar( new CasaLar(9999L) );
		
		orcamentoFamiliar2 = this.casaLarService.insertOrcamentoFamiliar( orcamentoFamiliar2 );
		
		orcamentoFamiliar2 = this.casaLarService.changeOrcamentoFamiliarToVigente( orcamentoFamiliar2.getId() );
		
		Assert.fail();
	}
}
