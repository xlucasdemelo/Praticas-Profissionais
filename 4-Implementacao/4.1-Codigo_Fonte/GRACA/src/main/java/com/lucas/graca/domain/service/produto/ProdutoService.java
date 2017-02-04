/**
 * 
 */
package com.lucas.graca.domain.service.produto;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.io.FileTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.casalar.CasaLar;
import com.lucas.graca.domain.entity.fornecedor.Fornecedor;
import com.lucas.graca.domain.entity.produto.Categoria;
import com.lucas.graca.domain.entity.produto.Marca;
import com.lucas.graca.domain.entity.produto.Modelo;
import com.lucas.graca.domain.entity.produto.Produto;
import com.lucas.graca.domain.entity.produto.ProdutoRepassado;
import com.lucas.graca.domain.entity.produto.Repasse;
import com.lucas.graca.domain.entity.produto.StatusRepasse;
import com.lucas.graca.domain.repository.produto.ICategoriaRepository;
import com.lucas.graca.domain.repository.produto.IMarcaRepository;
import com.lucas.graca.domain.repository.produto.IModeloRepository;
import com.lucas.graca.domain.repository.produto.IProdutoRepassadoRepository;
import com.lucas.graca.domain.repository.produto.IProdutoReportRepository;
import com.lucas.graca.domain.repository.produto.IProdutoRepository;
import com.lucas.graca.domain.repository.produto.IRepasseRepository;

import br.com.eits.common.infrastructure.file.MimeType;

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
	
	/**
	 * 
	 */
	@Autowired
	private IRepasseRepository repasseRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IProdutoRepassadoRepository produtoRepassadoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IProdutoReportRepository produtoReportRepository;
	
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
	public Page<Produto> listProdutosByFilters( String filter, Boolean ativo, PageRequest pageable )
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
	
	/*-------------------------------------------------------------------
	 *				 		 SERVICES REPASSE
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param repasse
	 * @return
	 */
	public Repasse insertRepasse(Repasse repasse)
	{
		Assert.notNull(repasse);
		Assert.notNull(repasse.getCasaLar(), "Informe qual é a casa lar");
		
		return this.repasseRepository.save(repasse);
	}
	
	/**
	 * 
	 * @param repasseId
	 */
	public void removeRepasse(long repasseId)
	{
		Repasse repasse = this.repasseRepository.findOne(repasseId);
		Assert.notNull(repasse, "Registro não existe");
		
		Assert.isTrue( repasse.getStatus() == StatusRepasse.RASCUNHO, "Somente um repasse em rascunho pode ser excluídos" );
		
		List<ProdutoRepassado> produtosRepassados = this.produtoRepassadoRepository.findByRepasseId(repasseId, null).getContent();
		
		for (ProdutoRepassado produtoRepassado : produtosRepassados) 
		{
			this.produtoRepassadoRepository.delete(produtoRepassado);
		}
		
		this.repasseRepository.delete(repasse);
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	public Page<Repasse> listRepassesByFilters( String filter, PageRequest pageable )
	{
		Page<Repasse> repasses = this.repasseRepository.listByFilters(filter, pageable);
		
		for (Repasse repasse : repasses) 
		{
			Repasse repasseDB = this.repasseRepository.findOne(repasse.getId());
			
			List<ProdutoRepassado> produtos = this.produtoRepassadoRepository.
					findByRepasseId(repasse.getId(), null).getContent();
			
			repasse.setQuantidadeProdutos(produtos.size());
			repasse.setCreated(repasseDB.getCreated());
		}
				
		return repasses;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Repasse findRepasseById(long id)
	{
		return this.repasseRepository.findOne(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public Repasse changeToRecusado( long id )
	{
		Repasse repasse = this.repasseRepository.findOne(id);
		Assert.notNull(repasse, "Registro não existe");
		
		repasse.changeToRecusado();
		
		return this.repasseRepository.save(repasse);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public Repasse changeToAprovado( long id )
	{
		Repasse repasse = this.repasseRepository.findOne(id);
		Assert.notNull(repasse, "Registro não existe");
		
		repasse.changeToAprovado();
		
		this.changeQuantidadeProdutoEmEstoque(repasse.getId());
		return this.repasseRepository.saveAndFlush(repasse);
	}
	
	/**
	 * 
	 * @param repasse
	 */
	private void changeQuantidadeProdutoEmEstoque(Long repasseId)
	{
		
		List<ProdutoRepassado> produtosRepassados = this.produtoRepassadoRepository.findByRepasseId(repasseId, null).getContent();
		
		for (ProdutoRepassado produtoRepassado : produtosRepassados) 
		{
			if (produtoRepassado.getProduto().getQuantidade() < 0)
			{
				break;
			}
			
			Produto produto = produtoRepassado.getProduto();
			produto.setQuantidade( produto.getQuantidade() - produtoRepassado.getQuantidade() );
			
			Assert.isTrue(produto.getQuantidade() >= 0, "Existe um produto com quantidade em estoque menor do que se quer repassar");
			
			this.produtoRepository.saveAndFlush(produto);
		}
	}
	
	/*-------------------------------------------------------------------
	 *				     SERVICES PRODUTO REPASSADO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param repasse
	 * @return
	 */
	public ProdutoRepassado insertProdutoRepassado(ProdutoRepassado produtoRepassado)
	{
		Assert.notNull(produtoRepassado, "produto Repassado não pode ser nulo");
		
		Assert.notNull(produtoRepassado.getRepasse(), "Informe o repasse");
		Assert.notNull(produtoRepassado.getProduto(), "Informe o produto");
		Assert.notNull(produtoRepassado.getQuantidade(), "Informe a quantidade");
		
		return this.produtoRepassadoRepository.save(produtoRepassado);
	}
	
	public ProdutoRepassado updateProdutoRepassado(ProdutoRepassado produtoRepassado)
	{
		Assert.notNull(produtoRepassado, "produto Repassado não pode ser nulo");
		
		ProdutoRepassado produtoRepassadoBd = this.produtoRepassadoRepository.findOne(produtoRepassado.getId());
		Assert.notNull(produtoRepassadoBd, "Registro não existe");
		
		Assert.notNull(produtoRepassado.getRepasse(), "Informe o repasse");
		Assert.notNull(produtoRepassado.getProduto(), "Informe o produto");
		Assert.notNull(produtoRepassado.getQuantidade(), "Informe a quantidade");
		
		produtoRepassadoBd.setQuantidade(produtoRepassado.getQuantidade());
		
		return this.produtoRepassadoRepository.save(produtoRepassadoBd);
	}
	
	/**
	 * 
	 * @param produtoRepassadoId
	 */
	public void removeProdutoRepassado(long produtoRepassadoId)
	{
		ProdutoRepassado produtoRepassado = this.produtoRepassadoRepository.findOne(produtoRepassadoId);
		Assert.notNull(produtoRepassado, "Registro não existe");
		
		Assert.isTrue( produtoRepassado.getRepasse().getStatus() == StatusRepasse.RASCUNHO, "Somente produtos de um repasse em rascunho pode ser excluídos" );
		
		this.produtoRepassadoRepository.delete(produtoRepassado);
	}
	
	/**
	 * 
	 * @param repasseId
	 * @param pageable
	 * @return
	 */
	public Page<ProdutoRepassado> listProdutosRepassadosByRepasse(long repasseId, PageRequest pageable)
	{
		return this.produtoRepassadoRepository.findByRepasseId(repasseId, pageable);
	}
	
	/**
	 * 
	 * @param fornecedores
	 * @param produtos
	 * @param dataInicio
	 * @param dataFIm
	 * @return
	 */
	public FileTransfer gerarRelatorioProdutosAdquiridos(List<Fornecedor> fornecedores, List<Produto> produtos,
			Calendar dataInicio, Calendar dataFIm)
	{
		final ByteArrayOutputStream reportOutputStream = this.produtoReportRepository.gerarRelatorioProdutosAdquiridos(fornecedores, produtos, dataInicio, dataFIm);
		
		final String name = String.format( IProdutoReportRepository.PRODUTO_ADQUIRIDO_REPORT, Calendar.getInstance().getTime().toString() );
		return new FileTransfer( name, MimeType.PDF.value, reportOutputStream.toByteArray() );
	}
	
	/**
	 * 
	 * @param casasLares
	 * @param produtos
	 * @param dataInicio
	 * @param dataFIm
	 * @return
	 */
	public FileTransfer gerarRelatorioProdutosRepassados(List<CasaLar> casasLares, List<Produto> produtos,
			Calendar dataInicio, Calendar dataFIm)
	{
		final ByteArrayOutputStream reportOutputStream = this.produtoReportRepository.gerarRelatorioProdutosRepassados(casasLares, produtos, dataInicio, dataFIm);
		
		final String name = String.format( IProdutoReportRepository.PRODUTO_REPASSADO_REPORT, Calendar.getInstance().getTime().toString() );
		return new FileTransfer( name, MimeType.PDF.value, reportOutputStream.toByteArray() );
	}
	
}
