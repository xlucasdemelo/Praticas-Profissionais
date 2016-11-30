/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.caixa.Conta;
import com.lucas.graca.domain.entity.caixa.Movimentacao;
import com.lucas.graca.domain.entity.caixa.NaturezaGastos;
import com.lucas.graca.domain.entity.caixa.StatusMovimentacao;
import com.lucas.graca.domain.entity.caixa.TipoMovimentacao;
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
	 *				 		     SERVICES CONTA
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
		conta.setSaldo(new BigDecimal("987"));
		
		conta = this.caixaService.insertConta( conta );
		
		Assert.assertNotNull( conta );
		Assert.assertNotNull( conta.getId() );
		Assert.assertTrue(conta.getSaldo().compareTo(new BigDecimal("987")) == 0 );
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
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICES MOVIMENTAÇÃO
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
			"/dataset/caixa/ContaDataset.xml",
			"/dataset/caixa/NaturezaGastosDataSet.xml"
		})
	public void insertMovimentacaoEntradaMustPass()
	{
		Movimentacao movimentacao = new Movimentacao();
		
		movimentacao.setTipoMovimentacao( TipoMovimentacao.ENTRADA );
		movimentacao.setValorEmissao( new BigDecimal("1000") );
		movimentacao.setContaOrigem( new Conta(9999L) );
		movimentacao.setContaDestino( new Conta(1000L) );
		movimentacao.setNaturezaGastos( new NaturezaGastos(1L) );
		movimentacao.setDataPagamento( Calendar.getInstance() );
		
		movimentacao = this.caixaService.insertMovimentacao(movimentacao);
		
		Assert.assertNotNull(movimentacao);
		Assert.assertTrue(movimentacao.getStatus() == StatusMovimentacao.RASCUNHO);
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
			"/dataset/caixa/NaturezaGastosDataSet.xml"
		})
	public void insertMovimentacaoEntradaMustFail()
	{
		Movimentacao movimentacao = new Movimentacao();
		
		movimentacao.setTipoMovimentacao( TipoMovimentacao.ENTRADA );
		movimentacao.setValorEmissao( null);
		movimentacao.setContaOrigem( new Conta(9999L) );
		movimentacao.setContaDestino( new Conta(1000L) );
		movimentacao.setNaturezaGastos( new NaturezaGastos(1L) );
		movimentacao.setDataPagamento( Calendar.getInstance() );
		
		movimentacao = this.caixaService.insertMovimentacao(movimentacao);
		
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml",
		})
	public void removeMovimentacaoMustPass()
	{
		this.caixaService.removeMovimentacao(9999L);
		
		Assert.assertNull( this.caixaService.findMovimentacaoById(9999L) );
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml",
		})
	public void removeMovimentacaoMustFailStatusNotRascunho()
	{
		this.caixaService.removeMovimentacao(1000L);
		
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml"
		})
	public void listMovimentacoesByFilter()
	{
		List<Movimentacao> movimentacoes = this.caixaService.listMovimentacoesByFilters("pagamento", null).getContent();
		
		Assert.assertFalse(movimentacoes.isEmpty());
		Assert.assertTrue(movimentacoes.get(0).getId() == 9999L);
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml",
	})
	public void listMovimentacoesByAquisicaoMustPass()
	{
		List<Movimentacao> movimentacoes = this.caixaService.listMovimentacoesByAquisicao(9999L, null).getContent();
		
		Assert.assertFalse(movimentacoes.isEmpty());
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml",
	})
	public void changetToRecusadoMustPass()
	{
		this.caixaService.changeToRecusado(9999L);
		
		Assert.assertTrue(this.caixaService.findMovimentacaoById(9999L).getStatus() == StatusMovimentacao.RECUSADO );;
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml",
	})
	public void changetToRecusadoMustFailStatusNotRascunho()
	{
		this.caixaService.changeToRecusado(1000L);
		
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml",
	})
	public void changeToemAbertoMustPass()
	{
		this.caixaService.changeToAberto(9999L);
		
		Assert.assertTrue( this.caixaService.findMovimentacaoById(9999L).getStatus() == StatusMovimentacao.ABERTO );
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml",
	})
	public void changeToemAbertoMustFailStatusNotRascunho()
	{
		this.caixaService.changeToAberto(1000L);
		
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml",
	})
	public void changeToConcluidoMustPass()
	{
		Conta contaOrigem = this.caixaService.findContaById(9999L);
		contaOrigem.setSaldo(new BigDecimal("99999999"));
		
		Conta contaDestino = this.caixaService.findContaById(1001L);
		contaDestino.setSaldo(new BigDecimal("0"));
		
		this.caixaService.updateConta(contaOrigem);
		this.caixaService.updateConta(contaDestino);
		
		this.caixaService.changeToAberto(9999L);
		this.caixaService.changeToConcluido(9999L);
		
		Movimentacao movimentacao = this.caixaService.findMovimentacaoById(9999L);
		
		Assert.assertNotNull(movimentacao);
		Assert.assertTrue(movimentacao.getStatus() == StatusMovimentacao.CONCLUIDO);
		
		Assert.assertTrue(this.caixaService.findContaById(9999L).getSaldo().compareTo( new BigDecimal("209") ) == 0);
		Assert.assertTrue(this.caixaService.findContaById(1001L).getSaldo().compareTo( new BigDecimal("1000")) == 0);
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml",
	})
	public void changeToConcluidoMustPassAtraso()
	{
		this.caixaService.changeToAberto(1001L);
		this.caixaService.changeToConcluido(1001L);
		
		Movimentacao movimentacao = this.caixaService.findMovimentacaoById(1001L);
		
		Assert.assertNotNull(movimentacao);
		Assert.assertTrue(movimentacao.getStatus() == StatusMovimentacao.CONCLUIDO);
		
		Assert.assertTrue(this.caixaService.findContaById(1002L).getSaldo().compareTo( new BigDecimal("1") ) == 0);
		Assert.assertTrue(this.caixaService.findContaById(1003L).getSaldo().compareTo( new BigDecimal("1500")) == 0);
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
			"/dataset/caixa/NaturezaGastosDataSet.xml",
			"/dataset/fornecedor/FornecedorDataSet.xml",
			"/dataset/aquisicaoproduto/AquisicaoProdutoDataSet.xml",
			"/dataset/caixa/MovimentacaoDataSet.xml",
	})
	public void changeToConcluidoMustFailValorMaiorSaldo()
	{
		Movimentacao movimentacao = this.caixaService.findMovimentacaoById(9999L);
		movimentacao.setValorEmissao(new BigDecimal("5000"));
		
		this.caixaService.insertMovimentacao(movimentacao);
		
		this.caixaService.changeToAberto(1001L);
		this.caixaService.changeToConcluido(1001L);
		
		Assert.fail();
	}
}
