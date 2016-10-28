/**
 * 
 */
package com.lucas.graca.domain.service.produto;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.produto.Produto;
import com.lucas.graca.domain.repository.produto.IProdutoRepository;

/**
 * @author eits
 *
 */
@Service
@RemoteProxy
@Transactional
@PreAuthorize( "hasAuthority('" + UserRole.OPERADOR_ADMINISTRATIVO_VALUE + "')" )
public class ProdutoService 
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private IProdutoRepository produtoRepository;
	
	/*-------------------------------------------------------------------
	 *				 		 SERVICES PRODUTO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param produto
	 * @return
	 */
	public Produto insertProduto(Produto produto)
	{
		Assert.notNull( produto, "Produto não pode ser nulo" );
		Assert.notNull( produto.getNome(), "Nome não pode ser nulo" );
		Assert.notNull( produto.getCategoria(), "Nome não pode ser nulo" );
		Assert.notNull( produto.getMarca(), "Marca não pode ser nulo" );
		
		return this.produtoRepository.save( produto );
	}
	
	/**
	 * 
	 * @param produto
	 * @return
	 */
	public Produto updateProduto ( Produto produto)
	{
		Assert.notNull( produto, "Produto não pode ser nulo" );
		Assert.notNull( produto.getId(), "id não pode ser nulo" );
		
		Produto produtoBd = this.produtoRepository.findOne( produto.getId() );
		Assert.notNull( produtoBd, "Produto não existe" );
		
		Assert.notNull( produto.getNome(), "Nome não pode ser nulo" );
		Assert.notNull( produto.getCategoria(), "Nome não pode ser nulo" );
		Assert.notNull( produto.getMarca(), "Marca não pode ser nulo" );
		
		return this.produtoRepository.save( produto );
	}
	
	public Page<Produto> listProdutosByFilters()
	{
		return null; //TODO AQUII
	}
}
