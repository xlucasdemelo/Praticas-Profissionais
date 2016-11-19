/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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
		})
	public void insertContaMustPass()
	{
		Conta conta = new Conta();
		
		conta.setNome( "conta 1" );
		conta.setDescricao("001 323 1233 222");
		
		conta = this.caixaService.insertConta( conta );
		
		Assert.assertNotNull( conta );
		Assert.assertNotNull( conta.getId() );
		Assert.assertTrue(conta.getSaldo().compareTo(new BigDecimal("0")) == 0 );
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
		})
	public void insertContaMustFail()
	{
		Conta conta = new Conta();
		conta.setNome( null );
		conta.setDescricao("001 323 1233 222");
		
		conta = this.caixaService.insertConta( conta );
		
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
			"/dataset/caixa/ContaDataset.xml"
		})
	public void insertContaMustFailDuplicatedNome()
	{
		Conta conta = new Conta();
		conta.setNome( "conta 1" );
		conta.setDescricao("001 323 1233 222");
		
		conta = this.caixaService.insertConta( conta );
		
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
			"/dataset/caixa/ContaDataset.xml"
		})
	public void insertContaMustPassDuplicatedNomeDisabledRegister()
	{
		Conta conta = new Conta();
		conta.setNome( "conta 2" );
		conta.setDescricao("001 323 1233 222");
		
		conta = this.caixaService.insertConta( conta );
		
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
			"/dataset/caixa/ContaDataset.xml",
		})
	public void disableContaBancarMustPass()
	{
		this.caixaService.disableConta( 9999L );
		
		Assert.assertFalse( this.caixaService.findContaById( 9999L ).getEnabled() );
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
			"/dataset/caixa/ContaDataset.xml",
		})
	public void disableContaBancarMustFail()
	{
		this.caixaService.disableConta( 100L );
		
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
			"/dataset/caixa/ContaDataset.xml",
		})
	public void enableContaBancarMustPass()
	{
		this.caixaService.enableConta( 1000L );
		
		Assert.assertTrue( this.caixaService.findContaById( 1000L ).getEnabled() );
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
			"/dataset/caixa/ContaDataset.xml",
		})
	public void listContasByFilters()
	{
		List<Conta> contas = this.caixaService.listContaByFilters( null, true, null ).getContent();
		
		Assert.assertNotNull( contas );
		Assert.assertFalse( contas.isEmpty());
	}
//	
//	/*-------------------------------------------------------------------
//	 *				 		     SERVICES BANCO
//	 *-------------------------------------------------------------------*/
//	
//	/**
//	 * 
//	 */
//	@Test
//	@WithUserDetails("chefe_adm@email.com")
//	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
//			"/dataset/casalar/ResponsavelDataSet.xml" ,
//			"/dataset/redeapoio/redeApoioDataSet.xml", 
//			"/dataset/account/UserDataSet.xml",
//			"/dataset/caixa/BancoDataSet.xml",
//		})
//	public void insertBancoMustPass()
//	{
//		Banco banco = new Banco();
//		banco.setNome( "Santander" );
//
//		banco = this.caixaService.insertBanco( banco );
//		
//		Assert.assertNotNull( banco );
//	}
//	
//	/**
//	 * 
//	 */
//	@Test(expected=IllegalArgumentException.class)
//	@WithUserDetails("chefe_adm@email.com")
//	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
//			"/dataset/casalar/ResponsavelDataSet.xml" ,
//			"/dataset/redeapoio/redeApoioDataSet.xml", 
//			"/dataset/account/UserDataSet.xml",
//			"/dataset/caixa/BancoDataSet.xml",
//		})
//	public void insertBancoMustFailDuplicatedName()
//	{
//		Banco banco = new Banco();
//		banco.setNome( "Bradesco" );
//
//		banco = this.caixaService.insertBanco( banco );
//		
//		Assert.fail();
//	}
//	
//	/**
//	 * 
//	 */
//	@Test
//	@WithUserDetails("chefe_adm@email.com")
//	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
//			"/dataset/casalar/ResponsavelDataSet.xml" ,
//			"/dataset/redeapoio/redeApoioDataSet.xml", 
//			"/dataset/account/UserDataSet.xml",
//			"/dataset/caixa/BancoDataSet.xml",
//		})
//	public void insertBancoMustpassDuplicatedNameAndDisabled()
//	{
//		Banco banco = new Banco();
//		banco.setNome( "Itau" );
//
//		banco = this.caixaService.insertBanco( banco );
//		
//		Assert.assertNotNull( banco );
//	}
//	
//	/**
//	 * 
//	 */
//	@Test
//	@WithUserDetails("chefe_adm@email.com")
//	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
//			"/dataset/casalar/ResponsavelDataSet.xml" ,
//			"/dataset/redeapoio/redeApoioDataSet.xml", 
//			"/dataset/account/UserDataSet.xml",
//			"/dataset/caixa/BancoDataSet.xml",
//		})
//	public void listBancosMustPass()
//	{
//		List<Banco> bancos = this.caixaService.listBancosByFilters( "Bradesco", true, null ).getContent();
//		
//		Assert.assertNotNull( bancos );
//		Assert.assertFalse( bancos.isEmpty());
//	}
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICES BANCO
	 *-------------------------------------------------------------------*/
}
