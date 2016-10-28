/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.fornecedor.Fornecedor;
import com.lucas.graca.domain.entity.planoatendimento.Responsavel;
import com.lucas.graca.domain.service.fornecedor.FornecedorService;
import com.lucas.graca.test.domain.AbstractIntegrationTests;


/**
 * @author lucas
 *
 */
public class FornecedorServiceIntegrationTests extends AbstractIntegrationTests
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private FornecedorService fornecedorService;
	
	/*-------------------------------------------------------------------
	 *				 		     TESTS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml"
	})
	public void insertFornecedorMustPass()
	{
		Fornecedor fornecedor = new Fornecedor();
		
		fornecedor.setRazaoSocial( "Fornecedor 1" );
		fornecedor.setCnpj( "123123123" );
		fornecedor.setResponsavel( new Responsavel(9999L) );
		
		fornecedor = this.fornecedorService.insertFornecedor( fornecedor );
		
		Assert.assertNotNull( fornecedor );
	}
	
	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml"
	})
	public void insertFornecedorMustFailWithoutMandatoryFields()
	{
		Fornecedor fornecedor = new Fornecedor();
		
		fornecedor.setRazaoSocial( null );
		
		fornecedor = this.fornecedorService.insertFornecedor( fornecedor );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test(expected = DataIntegrityViolationException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml"
	})
	public void insertFornecedorMustFailWithDuplicatedRazaoSocial()
	{
		Fornecedor fornecedor = new Fornecedor();
		
		fornecedor.setRazaoSocial( "fornecedor 1" );
		fornecedor.setCnpj( "8239892338" );
		fornecedor.setResponsavel( new Responsavel(1000L) );
		
		fornecedor = this.fornecedorService.insertFornecedor( fornecedor );
	}
	
	/**
	 * 
	 */
	@Test(expected = DataIntegrityViolationException.class)
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
	})
	public void insertFornecedorMustFailWithDuplicatedCNPJ()
	{
		Fornecedor fornecedor = new Fornecedor();
		
		fornecedor.setRazaoSocial( "Fornecedor 2" );
		fornecedor.setCnpj( "123456" );
		fornecedor.setResponsavel( new Responsavel(1000L) );
		
		fornecedor = this.fornecedorService.insertFornecedor( fornecedor );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/casalar/ResponsavelDataSet.xml" ,
			"/dataset/redeapoio/redeApoioDataSet.xml", 
			"/dataset/account/UserDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
	})
	public void listFornecedoresMustPass()
	{
		List<Fornecedor> fornecedores = this.fornecedorService.listFornecedoresByFilters( null, true, null).getContent();
		
		Assert.assertNotNull( fornecedores );
		Assert.assertTrue( !fornecedores.isEmpty() );
		
		Assert.assertTrue( fornecedores.size() > 0 );
	}
	
}
