/**
 * 
 */
package com.lucas.graca.domain.service.produto;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.produto.Categoria;
import com.lucas.graca.domain.entity.produto.Marca;
import com.lucas.graca.domain.entity.produto.Modelo;
import com.lucas.graca.domain.entity.produto.Produto;
import com.lucas.graca.domain.repository.produto.ICategoriaRepository;
import com.lucas.graca.domain.repository.produto.IMarcaRepository;
import com.lucas.graca.domain.repository.produto.IModeloRepository;
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
	
	/**
	 * 
	 */
	@Autowired
	private IMarcaRepository marcaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IModeloRepository modeloRepository;
	
	/**
	 * 
	 */
	@Autowired
	private ICategoriaRepository categoriaRepository;
	
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
		Assert.notNull( produto.getModelo(), "Modelo não pode ser nulo" );
		
		Modelo modelo = this.modeloRepository.findOne( produto.getModelo().getId() );
		Assert.isTrue( modelo.getMarca().getId().equals( produto.getMarca().getId() ), 
				"Modelo deve pertencer a marca escolhida" );
		
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
		
		Modelo modelo = this.modeloRepository.findOne( produto.getModelo().getId() );
		Assert.isTrue( modelo.getMarca().getId().equals( produto.getMarca().getId() ), 
				"Modelo deve pertencer a marca escolhida" );
		
		return this.produtoRepository.save( produto );
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Produto> listProdutosByFilters( String filter, boolean ativo, PageRequest pageable )
	{
		return this.produtoRepository.listByFilters( filter, ativo, pageable );
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public Produto findProdutoById( long id )
	{
		return this.produtoRepository.findOne( id );	
	}
	
	/**
	 * 
	 */
	public void disableProduto( long id )
	{
		Produto produto = this.produtoRepository.findOne( id );
		
		Assert.notNull( produto, "Produto não encontrado" );
		produto.disableProduto();
		
		this.produtoRepository.save( produto );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void enableProduto( long id )
	{
		Produto produto = this.produtoRepository.findOne( id );
		
		Assert.notNull( produto, "Produto não encontrado" );
		produto.enableProduto();
		
		this.produtoRepository.save( produto );
	}
	
	/*-------------------------------------------------------------------
	 *				 		 SERVICES MARCA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param marca
	 * @return
	 */
	public Marca insertMarca(Marca marca)
	{
		Assert.notNull( marca, "Marca não pode ser nula" );
		Assert.notNull( marca.getNome(), "Nome não pode ser nulo" );
		
		return this.marcaRepository.save( marca );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	public Page<Marca> listMarcasByFilter(String filter, PageRequest pageable)
	{
		return this.marcaRepository.listByFilters( filter, pageable );
	}
	
	/**
	 * 
	 */
	public void removeMarca( long id )
	{
		Marca marca = this.marcaRepository.findOne( id );
		Assert.notNull( marca, "Marca não encontrada" );
		
		this.marcaRepository.delete( marca );
	}
	
	/**
	 * 
	 * @return
	 */
	public Marca findMarcaById(long id)
	{
		return this.marcaRepository.findOne( id );
	}
	
	/*-------------------------------------------------------------------
	 *				 		 SERVICES MODELO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param modelo
	 * @return
	 */
	public Modelo insertModelo(Modelo modelo)
	{
		Assert.notNull( modelo, "Modelo não pode ser nula" );
		Assert.notNull( modelo.getNome(), "Nome não pode ser nulo" );
		Assert.notNull( modelo.getMarca(), "Marca não pode ser nula" );
		
		return this.modeloRepository.save( modelo );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	public Page<Modelo> listModelosByMarcaAndFilters(long marcaId, String filter, PageRequest pageable)
	{
		return this.modeloRepository.listByFilters( marcaId, filter, pageable );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removeModelo( long id )
	{
		Modelo modelo = this.modeloRepository.findOne( id );
		Assert.notNull( modelo, "Modelo não encontrada" );
		
		this.modeloRepository.delete( modelo );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Modelo findModeloById( long id )
	{
		return this.modeloRepository.findOne( id );
	}
	
	/*-------------------------------------------------------------------
	 *				 		 SERVICES CATEGORIA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param categoria
	 * @return
	 */
	public Categoria insertCategoria(Categoria categoria)
	{
		Assert.notNull( categoria, "Categoria não pode ser nula" );
		Assert.notNull( categoria.getNome(), "Nome não pode ser nulo" );
		
		return this.categoriaRepository.save( categoria );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	public Page<Categoria> listCategoriasByFilter(String filter, PageRequest pageable)
	{
		return this.categoriaRepository.listByFilters( filter, pageable );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removeCategoria( long id )
	{
		Categoria categoria = this.categoriaRepository.findOne( id );
		Assert.notNull( categoria, "categoria não encontrada" );
		
		this.categoriaRepository.delete( categoria );
	}
}