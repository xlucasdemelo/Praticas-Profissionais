/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.aquisicaoCompra.AquisicaoProduto;
import com.lucas.graca.domain.entity.aquisicaoCompra.CondicaoPagamento;
import com.lucas.graca.domain.entity.aquisicaoCompra.FormaPagamento;
import com.lucas.graca.domain.entity.aquisicaoCompra.ProdutoAdquirido;
import com.lucas.graca.domain.entity.aquisicaoCompra.StatusAquisicao;
import com.lucas.graca.domain.entity.fornecedor.Fornecedor;
import com.lucas.graca.domain.entity.produto.Produto;
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
		"/dataset/fornecedor/FornecedorDataSet.xml"
	})
	public void insertAquisicaoProdutoAVistaMustPass()
	{
		AquisicaoProduto aquisicaoProduto = new AquisicaoProduto();
		
		aquisicaoProduto.setCondicaoPagamento(CondicaoPagamento.A_VISTA);
		aquisicaoProduto.setFormaPagamento(FormaPagamento.PAGAMENTO_ONLINE);
		aquisicaoProduto.setFornecedor(new Fornecedor(9999L));
		
		aquisicaoProduto = this.aquisicaoProdutoService.insertAquisicaoProduto( aquisicaoProduto );
		
		Assert.assertNotNull( aquisicaoProduto );
		Assert.assertTrue(aquisicaoProduto.getStatus() == StatusAquisicao.RASCUNHO);
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
	public void insertAquisicaoProdutoAVistaMustFail()
	{
		AquisicaoProduto aquisicaoProduto = new AquisicaoProduto();
		
		aquisicaoProduto.setCondicaoPagamento(CondicaoPagamento.A_VISTA);
		aquisicaoProduto.setFormaPagamento(FormaPagamento.PAGAMENTO_ONLINE);
		aquisicaoProduto.setFornecedor(null);
		
		aquisicaoProduto = this.aquisicaoProdutoService.insertAquisicaoProduto( aquisicaoProduto );
		
		Assert.fail();
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
		"/dataset/fornecedor/FornecedorDataSet.xml"
	})
	public void insertAquisicaoProdutoMustPass()
	{
		AquisicaoProduto aquisicaoProduto = new AquisicaoProduto();
		
		aquisicaoProduto.setCondicaoPagamento(CondicaoPagamento.A_PRAZO);
		aquisicaoProduto.setFormaPagamento(FormaPagamento.PAGAMENTO_ONLINE);
		aquisicaoProduto.setFornecedor(new Fornecedor(9999L));
		aquisicaoProduto.setVezesPagamento(10);;
		aquisicaoProduto.setDiaVencimento(29);
		
		aquisicaoProduto = this.aquisicaoProdutoService.insertAquisicaoProduto( aquisicaoProduto );
		
		Assert.assertNotNull( aquisicaoProduto );
		Assert.assertTrue(aquisicaoProduto.getStatus() == StatusAquisicao.RASCUNHO);
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
		"/dataset/fornecedor/FornecedorDataSet.xml"
	})
	public void insertAquisicaoProdutoMustFail()
	{
		AquisicaoProduto aquisicaoProduto = new AquisicaoProduto();
		
		aquisicaoProduto.setCondicaoPagamento(CondicaoPagamento.A_PRAZO);
		aquisicaoProduto.setFormaPagamento(FormaPagamento.PAGAMENTO_ONLINE);
		aquisicaoProduto.setFornecedor(new Fornecedor(9999L));
		
		aquisicaoProduto = this.aquisicaoProdutoService.insertAquisicaoProduto( aquisicaoProduto );
		
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
		"/dataset/fornecedor/FornecedorDataSet.xml",
		"/dataset/aquisicaoProduto/AquisicaoProdutoDataSet.xml"
	})
	public void removeAquisicaoRascunhoMustPass()
	{
		this.aquisicaoProdutoService.removeAquisicaoProduto(9999L);
		
		Assert.assertNull( this.aquisicaoProdutoService.findAquisicaoProdutoById(9999) );
		Assert.fail();
	}
	
	//TODO testes de mudança de status 
	
	/*-------------------------------------------------------------------
	 *				 TESTS PRODUTO ADQUIRIDO
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
		"/dataset/fornecedor/FornecedorDataSet.xml",
		"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml"
	})
	public void insertProdutoAdquiridoMustPass()
	{
		ProdutoAdquirido produtoAdquirido = new ProdutoAdquirido();
		
		produtoAdquirido.setAquisicaoProduto(new AquisicaoProduto(9999L));
		produtoAdquirido.setProduto(new Produto(9999L));
		produtoAdquirido.setQuantidade(10);
		produtoAdquirido.setValor(new BigDecimal("1000"));
		
		produtoAdquirido = this.aquisicaoProdutoService.insertProdutoAdquirido(produtoAdquirido);
		
		Assert.assertNotNull(produtoAdquirido);
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
		"/dataset/fornecedor/FornecedorDataSet.xml",
		"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml"
	})
	public void insertProdutoAdquiridoMustAquisicaoconcluida()
	{
		ProdutoAdquirido produtoAdquirido = new ProdutoAdquirido();
		
		produtoAdquirido.setAquisicaoProduto(new AquisicaoProduto(1000L));
		produtoAdquirido.setProduto(new Produto(9999L));
		produtoAdquirido.setQuantidade(10);
		produtoAdquirido.setValor(new BigDecimal("1000"));
		
		produtoAdquirido = this.aquisicaoProdutoService.insertProdutoAdquirido(produtoAdquirido);
		
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
		"/dataset/fornecedor/FornecedorDataSet.xml",
		"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml",
		"/dataset/aquisicaoproduto/ProdutoAdquiridoDataSet.xml",
	})
	public void removeProdutoAdquiridoMustPass()
	{
		this.aquisicaoProdutoService.removeProdutoAdquirido(9999L);
		
		this.aquisicaoProdutoService.findProdutoAdquiridoById(9999L);
	}
}
