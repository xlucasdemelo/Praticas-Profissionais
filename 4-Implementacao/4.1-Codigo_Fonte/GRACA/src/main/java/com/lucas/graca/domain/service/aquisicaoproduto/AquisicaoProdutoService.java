/**
 * 
 */
package com.lucas.graca.domain.service.aquisicaoproduto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.aquisicaoCompra.AquisicaoProduto;
import com.lucas.graca.domain.entity.aquisicaoCompra.CondicaoPagamento;
import com.lucas.graca.domain.entity.aquisicaoCompra.FormaPagamento;
import com.lucas.graca.domain.entity.aquisicaoCompra.ProdutoAdquirido;
import com.lucas.graca.domain.entity.aquisicaoCompra.StatusAquisicao;
import com.lucas.graca.domain.entity.caixa.Movimentacao;
import com.lucas.graca.domain.entity.caixa.NaturezaGastos;
import com.lucas.graca.domain.entity.caixa.StatusMovimentacao;
import com.lucas.graca.domain.entity.caixa.TipoMovimentacao;
import com.lucas.graca.domain.entity.produto.Produto;
import com.lucas.graca.domain.repository.aquisicaoCompra.IAquisicaoProdutoRepository;
import com.lucas.graca.domain.repository.aquisicaoCompra.IProdutoAdquiridoRepository;
import com.lucas.graca.domain.repository.caixa.IMovimentacaoRepository;
import com.lucas.graca.domain.repository.produto.IProdutoRepository;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
@PreAuthorize("hasAuthority('"+UserRole.OPERADOR_ADMINISTRATIVO_VALUE+"') ")
public class AquisicaoProdutoService 
{
	
	/**
	 * 
	 */
	@Autowired
	private IAquisicaoProdutoRepository aquisicaoProdutoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IProdutoAdquiridoRepository produtoAdquiridoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IProdutoRepository produtoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IMovimentacaoRepository movimentacaoRepository;
	
	/*-------------------------------------------------------------------
	 *				 SERVICES AQUISIÇÃO DE PRODUTO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param aquisicaoProduto
	 * @return
	 */
	public AquisicaoProduto insertAquisicaoProduto(AquisicaoProduto aquisicaoProduto)
	{
		Assert.notNull( aquisicaoProduto, "Aquisição é obrigatória" );
		Assert.notNull( aquisicaoProduto.getCondicaoPagamento(), "Condição de pagamento é obrigatória" );
		Assert.notNull(aquisicaoProduto.getFormaPagamento(), "Forma de pagamento é obrigatório");
		Assert.notNull(aquisicaoProduto.getFornecedor(), "Fornecedor é obrigatório");
		
		if (aquisicaoProduto.getCondicaoPagamento() == CondicaoPagamento.A_PRAZO)
		{
			Assert.notNull(aquisicaoProduto.getVezesPagamento(), "Informe em quantas vezes será a compra");
			Assert.notNull(aquisicaoProduto.getDiaVencimento(), "Informe em que dia vence a conta");
		} 
		else
		{
			aquisicaoProduto.setVezesPagamento(1);
		}
		
		return this.aquisicaoProdutoRepository.save( aquisicaoProduto );
	}
	
	/**
	 * 
	 */
	public void removeAquisicaoProduto(long id)
	{
		AquisicaoProduto aquisicaoProduto = this.aquisicaoProdutoRepository.findOne( id );
		Assert.notNull( aquisicaoProduto, "Registro não encontrado" );
		
		Assert.isTrue(aquisicaoProduto.getStatus() == StatusAquisicao.RASCUNHO, "Somente aquisições em rascunho podem ser excluidas");
		
		this.aquisicaoProdutoRepository.delete( aquisicaoProduto );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public AquisicaoProduto findAquisicaoProdutoById( long id )
	{
		AquisicaoProduto aquisicaoProduto = this.aquisicaoProdutoRepository.findOne( id );
		Assert.notNull( aquisicaoProduto, "Registro não encontrado" );
		
		return aquisicaoProduto;
	}
	
	/**
	 * 
	 * @return
	 */
	@PreAuthorize("hasAuthority('"+UserRole.CHEFE_ADMINISTRACAO_VALUE+"') ")
	public AquisicaoProduto changeToConcluido(long id)
	{
		AquisicaoProduto aquisicaoProduto = this.aquisicaoProdutoRepository.findOne( id );
		Assert.notNull( aquisicaoProduto, "Registro não encontrado" );
		
		aquisicaoProduto.changeToConcluido();
		
		Assert.notEmpty( this.produtoAdquiridoRepository.findByAquisicaoProdutoId(id, null).getContent(), "Selecione ao menos um produto" );
		
		this.changeQuantidadeProdutoEmEstoque( id );
		
		if (aquisicaoProduto.getTipoPagamento() == CondicaoPagamento.A_PRAZO)
		{
			Assert.notNull(aquisicaoProduto.getVezesPagamento(), "Informe a quantidade de parcelas");
			Assert.notNull(aquisicaoProduto.getDiaVencimento(), "Informe o dia de vencimento");
			this.insertMovimentacoesPrazo(aquisicaoProduto);
		}
		else
		{
			this.insertMovimentacaoAVista(aquisicaoProduto);
		}
		
		return this.aquisicaoProdutoRepository.save( aquisicaoProduto );
	}
	
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('"+UserRole.CHEFE_ADMINISTRACAO_VALUE+"') ")
	public AquisicaoProduto changeToRecusado(long id)
	{
		AquisicaoProduto aquisicaoProduto = this.aquisicaoProdutoRepository.findOne( id );
		Assert.notNull( aquisicaoProduto, "Registro não encontrado" );
		
		aquisicaoProduto.changeToRecusado();
		
		return this.aquisicaoProdutoRepository.save( aquisicaoProduto );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 */
	@Transactional(readOnly=true)
	public Page<AquisicaoProduto> listAquisicoesByFilters(String filter, PageRequest pageable)
	{
		return this.aquisicaoProdutoRepository.listByFilters(filter, pageable);
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public BigDecimal getValorTotalAquisicao(long aquisicaoProdutoId)
	{
		List<ProdutoAdquirido> produtosAdquiridos = this.produtoAdquiridoRepository.findByAquisicaoProdutoId(aquisicaoProdutoId, null).getContent();
		
		BigDecimal valorTotal = new BigDecimal("0");
		
		for (ProdutoAdquirido produtoAdquirido : produtosAdquiridos) 
		{
			valorTotal = valorTotal.add(produtoAdquirido.getValor().multiply(new BigDecimal( produtoAdquirido.getQuantidade() ) ));
		}
		
		return valorTotal;
	}
	
	/**
	 * 
	 * @return
	 */
	public FormaPagamento[] listAllFormasPagamento()
	{
		return FormaPagamento.values();
	}
	
	/**
	 * 
	 * @return
	 */
	public CondicaoPagamento[] listAllCondicoesPagamento()
	{
		return CondicaoPagamento.values();
	}
	
	/*-------------------------------------------------------------------
	 *				 SERVICES PRODUTO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param aquisicaoId
	 */
	private void changeQuantidadeProdutoEmEstoque(long aquisicaoId)
	{
		List<ProdutoAdquirido> produtosAdquiridos = this.produtoAdquiridoRepository.findByAquisicaoProdutoId( aquisicaoId, null ).getContent();
		
		for ( ProdutoAdquirido produtoAdquirido : produtosAdquiridos )
		{
			Produto produto = this.produtoRepository.findOne( produtoAdquirido.getProduto().getId() );
			produto.setQuantidade( produto.getQuantidade() + produtoAdquirido.getQuantidade() );
			
			this.produtoRepository.saveAndFlush( produto );
		}
	}
	
	/*-------------------------------------------------------------------
	 *				 SERVICES MOVIMENTAÇÃO
	 *-------------------------------------------------------------------*/
	
	/**
	 * Método que gera uma movimentação para cada aquisição de produto
	 * @param aquisicaoProdutoId
	 */
	private void insertMovimentacoesPrazo( AquisicaoProduto aquisicaoProduto)
	{
		for (int i = 0; i < aquisicaoProduto.getVezesPagamento(); i++) 
		{
			Movimentacao movimentacao = new Movimentacao();
			movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
			movimentacao.setNaturezaGastos(new NaturezaGastos(1L));
			
			movimentacao.setAquisicaoProduto(aquisicaoProduto);
			movimentacao.setDescricao("Movimentação referente a aquisição de produto número " + aquisicaoProduto.getId());
			movimentacao.setValorEmissao(new BigDecimal("0"));
			movimentacao.setDataEmissao(Calendar.getInstance());
			movimentacao.setPorcentagemDiferenca(movimentacao.getPorcentagemDiferenca());
			
			Calendar dataPagamento = Calendar.getInstance();
			dataPagamento.add(Calendar.MONTH, 1 );
			dataPagamento.set(Calendar.DAY_OF_WEEK, aquisicaoProduto.getDiaVencimento());
			
			movimentacao.setDataPagamento(dataPagamento);
			
			movimentacao = this.movimentacaoRepository.save(movimentacao);
			movimentacao.setStatus(StatusMovimentacao.ABERTO);
			
			movimentacao = this.movimentacaoRepository.saveAndFlush(movimentacao);
		}
	}
	
	/**
	 * 
	 * @param aquisicaoProduto
	 */
	private void insertMovimentacaoAVista( AquisicaoProduto aquisicaoProduto )
	{
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
		movimentacao.setNaturezaGastos(new NaturezaGastos(1L));
		
		movimentacao.setAquisicaoProduto(aquisicaoProduto);
		movimentacao.setDescricao("Movimentação referente a aquisição de produto número " + aquisicaoProduto.getId());
		movimentacao.setPorcentagemDiferenca(0f);
		
		movimentacao.setValorEmissao(this.getValorTotalAquisicao(aquisicaoProduto.getId()));
		movimentacao.setValorEfetivado(this.getValorTotalAquisicao(aquisicaoProduto.getId()));
		
		movimentacao.setDataEmissao(Calendar.getInstance());
		movimentacao.setDataPagamento(Calendar.getInstance());
		
		movimentacao = this.movimentacaoRepository.save(movimentacao);
		movimentacao.setStatus(StatusMovimentacao.ABERTO);
		
		movimentacao = this.movimentacaoRepository.saveAndFlush(movimentacao);
	}
	
	/*-------------------------------------------------------------------
	 *				 SERVICES PRODUTO ADQUIRIDO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param produtoAdquirido
	 * @return
	 */
	public ProdutoAdquirido insertProdutoAdquirido(ProdutoAdquirido produtoAdquirido)
	{
		Assert.notNull( produtoAdquirido, "Produto adquirido não pode ser nulo" );
		Assert.notNull( produtoAdquirido.getAquisicaoProduto(), "Fornecedor não pode ser nulo" );
		Assert.notNull( produtoAdquirido.getProduto(), "Produto não pode ser nulo" );
		Assert.notNull( produtoAdquirido.getValor(), "Valor não pode ser nulo" );
		Assert.notNull( produtoAdquirido.getQuantidade(), "Informe a quantidade" );
		
		produtoAdquirido.setAquisicaoProduto(this.aquisicaoProdutoRepository.findOne(produtoAdquirido.getAquisicaoProduto().getId()));
		
		Assert.isTrue(produtoAdquirido.getAquisicaoProduto().getStatus() == StatusAquisicao.RASCUNHO, 
				"O status deve ser rascunho");
		
		return this.produtoAdquiridoRepository.save( produtoAdquirido );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removeProdutoAdquirido(long id)
	{
		ProdutoAdquirido produtoAdquirido = this.produtoAdquiridoRepository.findOne( id );
		Assert.notNull( produtoAdquirido, "Registro não encontrado" );
		
		Assert.isTrue( produtoAdquirido.getAquisicaoProduto().getStatus() == StatusAquisicao.RASCUNHO, 
				"Status deve ser em rascunho");
		
		this.produtoAdquiridoRepository.delete(produtoAdquirido);
	}
	
	/**
	 * 
	 * @param fornecedorAquisicaoId
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<ProdutoAdquirido> listProdutosByAquisicao(long aquisicaoProdutoId, PageRequest pageable)
	{
		return this.produtoAdquiridoRepository.findByAquisicaoProdutoId( aquisicaoProdutoId, pageable );
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public ProdutoAdquirido findProdutoAdquiridoById(long id)
	{
		ProdutoAdquirido produtoAdquirido = this.produtoAdquiridoRepository.findOne( id );
		Assert.notNull( produtoAdquirido, "Registro não encontrado" );
		
		return produtoAdquirido;
	}
}

