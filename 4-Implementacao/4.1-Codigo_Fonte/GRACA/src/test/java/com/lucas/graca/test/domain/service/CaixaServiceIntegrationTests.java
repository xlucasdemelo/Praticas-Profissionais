/**
 * 
 */
package com.lucas.graca.test.domain.service;

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
			"/dataset/caixa/BancoDataSet.xml"
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
	
}
