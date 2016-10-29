/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.produto.Categoria;
import com.lucas.graca.domain.entity.produto.Marca;
import com.lucas.graca.domain.entity.produto.Modelo;
import com.lucas.graca.domain.entity.produto.Produto;
import com.lucas.graca.domain.service.produto.ProdutoService;
import com.lucas.graca.test.domain.AbstractIntegrationTests;

/**
 * @author lucas
 *
 */
public class ProdutoServiceIntegrationTests extends AbstractIntegrationTests
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private ProdutoService produtoService;
	
	/*-------------------------------------------------------------------
	 *				 		     TESTS PRODUTO
	 *-------------------------------------------------------------------*/
		
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml"
	})
	public void insertProdutoMustPass()
	{
		Produto produto = new Produto();
		
		produto.setNome( "Carne" );
		produto.setMarca( new Marca(9999L) );
		produto.setModelo( new Modelo(9999L) );
		produto.setCategoria( new Categoria(9999L) );
		
		produto = this.produtoService.insertProduto( produto );
		
		Assert.assertNotNull( produto );
		Assert.assertNotNull( produto.getId() );
		Assert.assertTrue( produto.getAtivo() );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml"
	})
	public void insertProdutoMustFailWithouMandatoryFields()
	{
		Produto produto = new Produto();
		
		produto.setNome( null );
		produto.setMarca( new Marca(9999L) );
		produto.setModelo( new Modelo(9999L) );
		produto.setCategoria( new Categoria(9999L) );
		
		produto = this.produtoService.insertProduto( produto );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml"
	})
	public void updateProdutoMustPass()
	{
		Produto produto = this.produtoService.findProdutoById( 9999L );
		
		produto.setNome( "Escova de dente" );
		
		produto = this.produtoService.insertProduto( produto );
		
		Assert.assertNotNull( produto );
		Assert.assertNotNull( produto.getId() );
		Assert.assertTrue( produto.getAtivo() );
		Assert.assertTrue( produto.getNome().equals( "Escova de dente" ) );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml"
	})
	public void updateProdutoMustFail()
	{
		Produto produto = this.produtoService.findProdutoById( 9999L );
		
		produto.setNome( null);
		
		produto = this.produtoService.insertProduto( produto );
		
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml"
	})
	public void listProdutosByFilterMustPassListAll()
	{
		List<Produto> produtos = this.produtoService.listProdutosByFilters( null, true, null ).getContent();
		
		Assert.assertNotNull( produtos );
		Assert.assertTrue( !produtos.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml"
	})
	public void listProdutosByFilterMustPassListOne()
	{
		List<Produto> produtos = this.produtoService.listProdutosByFilters( "Pasta", true, null ).getContent();
		
		Assert.assertNotNull( produtos );
		Assert.assertTrue( !produtos.isEmpty() );
		Assert.assertTrue( produtos.size() == 1 );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml"
	})
	public void disableProdutoMustPass()
	{
		Produto produto = this.produtoService.findProdutoById( 9999L );
		Assert.assertTrue( produto.getAtivo() );
		
		this.produtoService.disableProduto( 9999L );
		
		produto = this.produtoService.findProdutoById( 9999L );
		Assert.assertTrue( ! produto.getAtivo() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
		"/dataset/produto/CategoriaDataSet.xml",
		"/dataset/produto/ProdutoDataSet.xml"
	})
	public void enableProdutoMustPass()
	{
		this.produtoService.disableProduto( 9999L );
		
		Produto produto = this.produtoService.findProdutoById( 9999L );
		Assert.assertTrue( !produto.getAtivo() );
		
		this.produtoService.enableProduto( 9999L );
		
		produto = this.produtoService.findProdutoById( 9999L );
		Assert.assertTrue( produto.getAtivo() );
	}
	
	/*-------------------------------------------------------------------
	 *				 		     TESTS MARCA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
	})
	public void listAllMarcasByfiltersMustPass()
	{
		List<Marca> marcas = this.produtoService.listMarcasByFilter( null, null ).getContent();
		
		Assert.assertNotNull( marcas );
		Assert.assertTrue( !marcas.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
	})
	public void listOneMarcasByfiltersMustPass()
	{
		List<Marca> marcas = this.produtoService.listMarcasByFilter( "Colga", null ).getContent();
		
		Assert.assertNotNull( marcas );
		Assert.assertTrue( !marcas.isEmpty() );
		Assert.assertTrue( marcas.get( 0 ).getNome().equals( "Colgate" ) );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
	})
	public void inserMarcaMustPass()
	{
		Marca marca = new Marca();
		marca.setNome( "Teste" );
		
		marca = this.produtoService.insertMarca( marca );
		Assert.assertNotNull( marca );
	}
	
	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
	})
	public void inserMarcaMustFail()
	{
		Marca marca = new Marca();
		marca.setNome( null );
		
		marca = this.produtoService.insertMarca( marca );
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
	})
	public void removeMarcaMustPass()
	{
		Marca marca = this.produtoService.findMarcaById( 9999L );
		Assert.assertNotNull( marca );
		
		this.produtoService.removeMarca( marca.getId() );
		
		marca = this.produtoService.findMarcaById( 9999L );
		Assert.assertNull( marca );
	}
	
	/*-------------------------------------------------------------------
	 *				 		     TESTS MARCA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml"
	})
	public void listAllModelosByfiltersMustPass()
	{
		List<Modelo> modelos = this.produtoService.listModelosByMarcaAndFilters( 9999L, null, null ).getContent();
		
		Assert.assertNotNull( modelos );
		Assert.assertTrue( !modelos.isEmpty() );
	}
	
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
	})
	public void inserModeloMustPass()
	{
		Modelo modelo = new Modelo();
		modelo.setMarca( new Marca(9999L) );
		modelo.setNome( "Teste" );
		
		modelo = this.produtoService.insertModelo( modelo );
		Assert.assertNotNull( modelo );
	}

	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("operador_administrativo@email.com")
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/casalar/ResponsavelDataSet.xml" ,
		"/dataset/redeapoio/redeApoioDataSet.xml", 
		"/dataset/account/UserDataSet.xml",
		"/dataset/produto/MarcaDataSet.xml",
		"/dataset/produto/ModeloDataSet.xml",
	})
	public void removeModeloMustPass()
	{
		Modelo modelo = this.produtoService.findModeloById( 9999L );
		Assert.assertNotNull( modelo );
		
		this.produtoService.removeModelo( 9999L );
		
		modelo = this.produtoService.findModeloById( 9999L );
		Assert.assertNull( modelo );
	}
}



