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
import com.lucas.graca.domain.entity.caixa.Banco;
import com.lucas.graca.domain.entity.caixa.ContaBancaria;
import com.lucas.graca.domain.service.caixa.CaixaService;
import com.lucas.graca.test.domain.AbstractIntegrationTests;

/**
 * @author lucas
 *
 */
public class CaixaServiceIntegrationTests extends AbstractIntegrationTests
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private CaixaService caixaService;
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICES CAIXA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
		})
	public void insertContaBancariaMustPass()
	{
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setAgencia( "1234" );
		contaBancaria.setBanco( new Banco(9999L) );
		contaBancaria.setNumero( "09876" );
		
		contaBancaria = this.caixaService.insertContaBancaria( contaBancaria );
		
		Assert.assertNotNull( contaBancaria );
		Assert.assertNotNull( contaBancaria.getId() );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml"
		})
	public void insertContaBancariaMustFail()
	{
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setAgencia( "1234" );
		contaBancaria.setBanco( new Banco(9999L) );
		contaBancaria.setNumero( null );
		
		contaBancaria = this.caixaService.insertContaBancaria( contaBancaria );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
			"/dataset/caixa/ContaBancariaDataset.xml"
		})
	public void insertContaBancariaMustFailDuplicatedNumeroAndAgencia()
	{
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setAgencia( "01238" );
		contaBancaria.setBanco( new Banco(9999L) );
		contaBancaria.setNumero( "23331" );
		
		contaBancaria = this.caixaService.insertContaBancaria( contaBancaria );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
			"/dataset/caixa/ContaBancariaDataset.xml"
		})
	public void insertContaBancariaMustPassDuplicatedNumeroAndAgenciaOfDisabledRegister()
	{
		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setAgencia( "01231238" );
		contaBancaria.setBanco( new Banco(9999L) );
		contaBancaria.setNumero( "44423" );
		
		contaBancaria = this.caixaService.insertContaBancaria( contaBancaria );
		
		Assert.assertNotNull( contaBancaria );
		Assert.assertNotNull( contaBancaria.getId() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
			"/dataset/caixa/ContaBancariaDataset.xml",
		})
	public void disableContaBancarMustPass()
	{
		this.caixaService.disableContaBancaria( 9999L );
		
		Assert.assertFalse( this.caixaService.findContaBancariaById( 9999L ).getEnabled() );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
			"/dataset/caixa/ContaBancariaDataset.xml",
		})
	public void disableContaBancarMustFail()
	{
		this.caixaService.disableContaBancaria( 100L );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
			"/dataset/caixa/ContaBancariaDataset.xml",
		})
	public void enableContaBancarMustPass()
	{
		this.caixaService.enableContaBancaria( 1000L );
		
		Assert.assertTrue( this.caixaService.findContaBancariaById( 1000L ).getEnabled() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
			"/dataset/caixa/ContaBancariaDataset.xml",
		})
	public void listContasByFilters()
	{
		List<ContaBancaria> contas = this.caixaService.listContaBancariaByFilters( null, true, null ).getContent();
		
		Assert.assertNotNull( contas );
		Assert.assertFalse( contas.isEmpty());
	}
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICES BANCO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
		})
	public void insertBancoMustPass()
	{
		Banco banco = new Banco();
		banco.setNome( "Santander" );

		banco = this.caixaService.insertBanco( banco );
		
		Assert.assertNotNull( banco );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
		})
	public void insertBancoMustFailDuplicatedName()
	{
		Banco banco = new Banco();
		banco.setNome( "Bradesco" );

		banco = this.caixaService.insertBanco( banco );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
		})
	public void insertBancoMustpassDuplicatedNameAndDisabled()
	{
		Banco banco = new Banco();
		banco.setNome( "Itau" );

		banco = this.caixaService.insertBanco( banco );
		
		Assert.assertNotNull( banco );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("chefe_adm@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/caixa/BancoDataSet.xml",
		})
	public void listBancosMustPass()
	{
		List<Banco> bancos = this.caixaService.listBancosByFilters( "Bradesco", true, null ).getContent();
		
		Assert.assertNotNull( bancos );
		Assert.assertFalse( bancos.isEmpty());
	}
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICES BANCO
	 *-------------------------------------------------------------------*/
}
