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
import com.lucas.graca.domain.entity.caixa.Conta;
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
		Conta conta = new Conta();
		conta.setAgencia( "1234" );
		conta.setBanco( new Banco(9999L) );
		conta.setNumero( "09876" );
		
		conta = this.caixaService.insertContaBancaria( conta );
		
		Assert.assertNotNull( conta );
		Assert.assertNotNull( conta.getId() );
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
		Conta conta = new Conta();
		conta.setAgencia( "1234" );
		conta.setBanco( new Banco(9999L) );
		conta.setNumero( null );
		
		conta = this.caixaService.insertContaBancaria( conta );
		
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
		Conta conta = new Conta();
		conta.setAgencia( "01238" );
		conta.setBanco( new Banco(9999L) );
		conta.setNumero( "23331" );
		
		conta = this.caixaService.insertContaBancaria( conta );
		
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
		Conta conta = new Conta();
		conta.setAgencia( "01231238" );
		conta.setBanco( new Banco(9999L) );
		conta.setNumero( "44423" );
		
		conta = this.caixaService.insertContaBancaria( conta );
		
		Assert.assertNotNull( conta );
		Assert.assertNotNull( conta.getId() );
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
		List<Conta> contas = this.caixaService.listContaBancariaByFilters( null, true, null ).getContent();
		
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
