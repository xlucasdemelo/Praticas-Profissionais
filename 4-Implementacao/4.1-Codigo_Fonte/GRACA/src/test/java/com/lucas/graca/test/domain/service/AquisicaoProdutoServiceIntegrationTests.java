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
import com.lucas.graca.domain.entity.aquisicaoCompra.AquisicaoProduto;
import com.lucas.graca.domain.entity.aquisicaoCompra.TipoAquisicao;
import com.lucas.graca.domain.service.aquisicaoproduto.AquisicaoProdutoService;
import com.lucas.graca.test.domain.AbstractIntegrationTests;

/**
 * @author lucas
 *
 */
public class AquisicaoProdutoServiceIntegrationTests extends AbstractIntegrationTests
{
	
	/**
	 * 
	 */
	@Autowired
	private AquisicaoProdutoService aquisicaoProdutoService;
	
	/*-------------------------------------------------------------------
	 *				 TESTS AQUISIÇÃO DE PRODUTO
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
	public void insertAquisicaoProdutoTipoCompraSedeMustPass()
	{
		AquisicaoProduto aquisicaoProduto = new AquisicaoProduto();
		aquisicaoProduto.setTipoAquisicao( TipoAquisicao.COMPRA_SEDE );
		
		aquisicaoProduto = this.aquisicaoProdutoService.insertAquisicaoProduto( aquisicaoProduto );
		
		Assert.assertNotNull( aquisicaoProduto );
	}
	
}
