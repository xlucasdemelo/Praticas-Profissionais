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
import com.lucas.graca.domain.entity.aquisicaoCompra.AquisicaoProduto;
import com.lucas.graca.domain.entity.aquisicaoCompra.CondicaoPagamento;
import com.lucas.graca.domain.entity.aquisicaoCompra.FormaPagamento;
import com.lucas.graca.domain.entity.aquisicaoCompra.ProdutoAdquirido;
import com.lucas.graca.domain.entity.aquisicaoCompra.StatusAquisicao;
import com.lucas.graca.domain.entity.caixa.Movimentacao;
import com.lucas.graca.domain.entity.caixa.StatusMovimentacao;
import com.lucas.graca.domain.entity.fornecedor.Fornecedor;
import com.lucas.graca.domain.entity.produto.Produto;
import com.lucas.graca.domain.service.aquisicaoproduto.AquisicaoProdutoService;
import com.lucas.graca.domain.service.caixa.CaixaService;
import com.lucas.graca.domain.service.produto.ProdutoService;
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
	
	/**
	 * 
	 */
	@Autowired
	private ProdutoService produtoService;
	
	/**
	 * 
	 */
	@Autowired
	private CaixaService caixaService;
	
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
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
	})
	public void removeAquisicaoRascunhoMustPass()
	{
		this.aquisicaoProdutoService.removeAquisicaoProduto(9999L);
		
		Assert.assertNull( this.aquisicaoProdutoService.findAquisicaoProdutoById(9999) );
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
		"/dataset/fornecedor/FornecedorDataSet.xml",
		"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml",
		"/dataset/aquisicaoproduto/ProdutoAdquiridoDataSet.xml",
		"/dataset/caixa/NaturezaGastosDataSet.xml"
	})
	public void changeAquisicaoAVistaToConcluidoMustPass()
	{
		this.aquisicaoProdutoService.changeToConcluido(9999L);
		
		AquisicaoProduto aquisicao = this.aquisicaoProdutoService.findAquisicaoProdutoById(9999L);
		
		Assert.assertTrue( aquisicao.getStatus() == StatusAquisicao.CONCLUIDO );
		
		Produto produto = this.produtoService.findProdutoById(9999L);
		Assert.assertTrue(produto.getQuantidade() == 30);
		
		List<Movimentacao> movimentacoes = this.caixaService.listMovimentacoesByAquisicao(9999L, null).getContent();
		
		Assert.assertFalse(movimentacoes.isEmpty());
		Assert.assertTrue(movimentacoes.size() == 1);
		
		for (Movimentacao movimentacao : movimentacoes) 
		{
			Assert.assertTrue(movimentacao.getStatus() == StatusMovimentacao.ABERTO);
			Assert.assertTrue(movimentacao.getValorEmissao().compareTo(new BigDecimal("4000")) == 0 );
		}
	}
	
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
		"/dataset/produto/ProdutoDataSet.xml",
		"/dataset/aquisicaoproduto/ProdutoAdquiridoDataSet.xml",
		"/dataset/caixa/NaturezaGastosDataSet.xml"
	})
	public void changeAquisicaoAPrazoToConcluidoMustPass()
	{
		this.aquisicaoProdutoService.changeToConcluido(1001L);
		
		AquisicaoProduto aquisicao = this.aquisicaoProdutoService.findAquisicaoProdutoById(1001L);
		
		Assert.assertTrue( aquisicao.getStatus() == StatusAquisicao.CONCLUIDO );
		
		Produto produto = this.produtoService.findProdutoById(9999L);
		Assert.assertTrue(produto.getQuantidade() == 34);
		
		List<Movimentacao> movimentacoes = this.caixaService.listMovimentacoesByAquisicao(1001L, null).getContent();
		
		Assert.assertFalse(movimentacoes.isEmpty());
		Assert.assertTrue(movimentacoes.size() == 2);
		
		for (Movimentacao movimentacao : movimentacoes) 
		{
			Assert.assertTrue(movimentacao.getStatus() == StatusMovimentacao.ABERTO);
			Assert.assertTrue(movimentacao.getValorEmissao().compareTo(new BigDecimal("0")) == 0 );
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
		"/dataset/fornecedor/FornecedorDataSet.xml",
		"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml",
		"/dataset/aquisicaoproduto/ProdutoAdquiridoDataSet.xml",
	})
	public void changeAquisicaoToRecusadoMustPass()
	{
		this.aquisicaoProdutoService.changeToRecusado(9999L);
		
		AquisicaoProduto aquisicao = this.aquisicaoProdutoService.findAquisicaoProdutoById(9999L);
		
		Assert.assertTrue( aquisicao.getStatus() == StatusAquisicao.RECUSADO );
		Produto produto = this.produtoService.findProdutoById(9999L);
		Assert.assertTrue(produto.getQuantidade() == 10);
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
		"/dataset/fornecedor/FornecedorDataSet.xml",
		"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml",
		"/dataset/aquisicaoproduto/ProdutoAdquiridoDataSet.xml",
		"/dataset/caixa/NaturezaGastosDataSet.xml"
	})
	public void getValorTotalAquisicaoMustPass()
	{
		this.aquisicaoProdutoService.changeToConcluido(9999L);
		
		AquisicaoProduto aquisicao = this.aquisicaoProdutoService.findAquisicaoProdutoById(9999L);
		
		Assert.assertTrue( aquisicao.getStatus() == StatusAquisicao.CONCLUIDO );
		
		Produto produto = this.produtoService.findProdutoById(9999L);
		Assert.assertTrue(produto.getQuantidade() == 30);
		
		BigDecimal valorTotal = this.aquisicaoProdutoService.getValorTotalAquisicao(9999L);
		Assert.assertNotNull(valorTotal);
		Assert.assertTrue(valorTotal.compareTo( new BigDecimal("4000") ) == 0);
	}
	
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
	public void removeProdutoAdquiridoMustFailStatusConcluido()
	{
		this.aquisicaoProdutoService.removeProdutoAdquirido(1000L);
		
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
		"/dataset/fornecedor/FornecedorDataSet.xml",
		"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml",
		"/dataset/aquisicaoproduto/ProdutoAdquiridoDataSet.xml",
	})
	public void listProdutosAdquiridosByAquisicaoProdutoMustPass()
	{
		List<ProdutoAdquirido> produtosAdquiridos = this.aquisicaoProdutoService.listProdutosByAquisicao(9999L, null).getContent();
		
		Assert.assertNotNull(produtosAdquiridos);
		Assert.assertFalse(produtosAdquiridos.isEmpty());
	}
	
}
