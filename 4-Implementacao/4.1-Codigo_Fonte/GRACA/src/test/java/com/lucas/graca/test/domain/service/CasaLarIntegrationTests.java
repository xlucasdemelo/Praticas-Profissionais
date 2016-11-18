/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.casalar.CasaLar;
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
	

}
