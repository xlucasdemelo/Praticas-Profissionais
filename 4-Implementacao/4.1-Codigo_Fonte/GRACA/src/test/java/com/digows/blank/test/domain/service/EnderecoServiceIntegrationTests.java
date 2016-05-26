/**
 * 
 */
package com.digows.blank.test.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.digows.blank.domain.entity.endereco.Cidade;
import com.digows.blank.domain.entity.endereco.Estado;
import com.digows.blank.domain.entity.endereco.Pais;
import com.digows.blank.domain.service.endereco.EnderecoService;
import com.digows.blank.test.domain.AbstractIntegrationTests;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

/**
 * @author lucas
 *
 */
public class EnderecoServiceIntegrationTests extends AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	@Autowired
	private EnderecoService enderecoService;
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICE TESTS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void listPaisesByFiltersMustPass()
	{
		Page<Pais> paises = this.enderecoService.listPaisesByFilters( "", null );
		
		Assert.assertNotNull( paises );
		Assert.assertFalse( paises.getContent().isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void listPaisesByFiltersMustFindName()
	{
		Page<Pais> paises = this.enderecoService.listPaisesByFilters( "Irlanda", null );
		
		Assert.assertNotNull( paises );
		Assert.assertTrue( paises.getContent().size() == 1 );
		Assert.assertTrue( paises.getContent().get( 0 ).getNome().equals( "Irlanda"));
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void findPaisByIdMustPass()
	{
		Pais pais = this.enderecoService.findPaisById( 100L );
		
		Assert.assertNotNull( pais );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void listEstadosByFiltersMustPass()
	{
		Page<Estado> estado = this.enderecoService.listEstadosByFIlters( "", 100L, null );
		
		Assert.assertNotNull( estado );
		Assert.assertFalse( estado.getContent().isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void listEstadosByFiltersMustFindName()
	{
		Page<Estado> estado = this.enderecoService.listEstadosByFIlters( "Paraná", 100L, null );
		
		Assert.assertNotNull( estado );
		Assert.assertTrue( estado.getContent().size() == 1 );
		Assert.assertTrue( estado.getContent().get( 0 ).getNome().equals( "Paraná" ) );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void findEstadoById()
	{
		Estado estado = this.enderecoService.findEstadoById( 101L );
		
		Assert.assertNotNull( estado );
		Assert.assertTrue( estado.getNome().equals( "Santa Catarina" ) );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void listCidadesByFiltersMustPass()
	{
		Page<Cidade> cidade = this.enderecoService.listCidadesByFIlters( "", 100L, null );
		
		Assert.assertNotNull( cidade );
		Assert.assertFalse( cidade.getContent().isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void findCidadeById()
	{
		Cidade cidade = this.enderecoService.findCidadeById( 100L );
		Assert.assertNotNull( cidade );
	}
}













