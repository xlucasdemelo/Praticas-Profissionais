/**
 * 
 */
package com.lucas.graca.domain.service.aquisicaoproduto;

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
import com.lucas.graca.domain.entity.aquisicaoCompra.ProdutoAdquirido;
import com.lucas.graca.domain.entity.aquisicaoCompra.StatusAquisicao;
import com.lucas.graca.domain.entity.produto.Produto;
import com.lucas.graca.domain.repository.aquisicaoCompra.IAquisicaoProdutoRepository;
import com.lucas.graca.domain.repository.aquisicaoCompra.IProdutoAdquiridoRepository;
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
		
		
		
		return this.aquisicaoProdutoRepository.save( aquisicaoProduto );
	}
	
	/**
	 * 
	 */
	public void removeAquisicaoProduto(long id)
	{
		AquisicaoProduto aquisicaoProduto = this.aquisicaoProdutoRepository.findOne( id );
		Assert.notNull( aquisicaoProduto, "Registro não encontrado" );
		
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
	 * @param id
	 * @return
	 */
	public AquisicaoProduto changeToEmAberto(long id)
	{
		AquisicaoProduto aquisicaoProduto = this.aquisicaoProdutoRepository.findOne( id );
		Assert.notNull( aquisicaoProduto, "Registro não encontrado" );
		
		
		aquisicaoProduto.changeToEmAberto();
		
		return this.aquisicaoProdutoRepository.save( aquisicaoProduto );
	}
	
	/**
	 * 
	 * @return
	 */
	public AquisicaoProduto changeToConcluido(long id)
	{
		AquisicaoProduto aquisicaoProduto = this.aquisicaoProdutoRepository.findOne( id );
		Assert.notNull( aquisicaoProduto, "Registro não encontrado" );
		
		Assert.isTrue( aquisicaoProduto.getStatus() == StatusAquisicao.CONCLUIDO );
		
		Assert.notNull( aquisicaoProduto.getVezesPagamento(), "Informe a quantidade de parcelas" );
		aquisicaoProduto.changeToConcluido();
		
		this.changeQuantidadeProdutoEmEstoque( id );
		
		return this.aquisicaoProdutoRepository.save( aquisicaoProduto );
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public AquisicaoProduto changeToRecusado(long id)
	{
		AquisicaoProduto aquisicaoProduto = this.aquisicaoProdutoRepository.findOne( id );
		Assert.notNull( aquisicaoProduto, "Registro não encontrado" );
		
		aquisicaoProduto.changeToRecusado();
		
		return this.aquisicaoProdutoRepository.save( aquisicaoProduto );
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
	}
	
	/**
	 * 
	 * @param fornecedorAquisicaoId
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<ProdutoAdquirido> listProdutosByFornecedorAquisicao(long aquisicaoProdutoId, PageRequest pageable)
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

