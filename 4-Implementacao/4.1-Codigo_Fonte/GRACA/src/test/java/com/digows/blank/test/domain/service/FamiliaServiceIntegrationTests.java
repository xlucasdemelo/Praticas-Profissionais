/**
 * 
 */
package com.digows.blank.test.domain.service;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

import com.digows.blank.domain.entity.endereco.Endereco;
import com.digows.blank.domain.entity.familia.Familia;
import com.digows.blank.domain.entity.familia.TipoImovel;
import com.digows.blank.domain.entity.familia.TipoMoradia;
import com.digows.blank.domain.service.familia.FamiliaService;
import com.digows.blank.test.domain.AbstractIntegrationTests;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

/**
 * @author lucas
 *
 */
public class FamiliaServiceIntegrationTests extends AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	@Autowired
	private FamiliaService familiaService;
	
	/*-------------------------------------------------------------------
	 *				 		     SERVICE TESTS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml"
	})
	public void insertFamiliaMustPass()
	{
		Familia familia = new Familia( 1L, "Silva", "99999", 3, "Boa", "Basica", TipoImovel.CASA, TipoMoradia.ALUGADA, new Endereco(100L), true, "Maria Silva", 10);
		familia.setEndereco( new Endereco(100L) );
		
		familia = this.familiaService.insertFamilia( familia );
		
		Assert.assertNotNull( familia );
	}
	
	/**
	 * 
	 */
	@Test(expected = ValidationException.class)
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
		"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml"
	})
	public void insertFamiliaMustFailWithoutMandatoryFields()
	{
		Familia familia = new Familia( 1L, null, "99999", 3, "Boa", "Basica", TipoImovel.CASA, TipoMoradia.ALUGADA, new Endereco(100L), true, "Marlene Silva", 10);
		familia.setEndereco( new Endereco(100L) );
		
		familia = this.familiaService.insertFamilia( familia );
		
		Assert.assertNotNull( familia );
	}
	
	/**
	 * 
	 */
	@Test(expected = DataIntegrityViolationException.class)
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertFamiliaMustFailSameNomeAndNomeMae()
	{
		Familia familia = new Familia( 1L, "Silva", "99999", 3, "Boa", "Basica", TipoImovel.CASA, TipoMoradia.ALUGADA, null, true, "Marlene Silva", 10);
		familia.setEndereco( new Endereco(100L) );
		
		familia = this.familiaService.insertFamilia( familia );
		
		Assert.assertNotNull( familia );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void updateFamiliaMustPass()
	{
		Familia familia = this.familiaService.findFamiliaById( 100L );
		familia.setInfraestrutura( "Loca" );
		
		familia = this.familiaService.updateFamilia( familia );
		Assert.assertNotNull( familia );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void listFamiliaByFiltersMustPass()
	{
		Page<Familia> familia = this.familiaService.listFamiliasByFilters( "Silva", null );
		
		Assert.assertTrue( familia.getContent().size() > 0 );
		Assert.assertTrue( familia.getContent().get( 0 ).getNome().equals( "Silva") );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void listFamiliaByFiltersNenhumResultado()
	{
		Page<Familia> familia = this.familiaService.listFamiliasByFilters( "Nenhum", null );
		
		Assert.assertTrue( familia.getContent().isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void listFamiliaByFiltersMustNotShowDisabled()
	{
		Page<Familia> familiaPage = this.familiaService.listFamiliasByFilters( null, null );
		
		for ( Familia familia : familiaPage )
		{
			Assert.assertTrue( familia.isAtivo() );
		}
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void disableFamiliaMustPass()
	{
		Familia familia = this.familiaService.findFamiliaById( 100L );
		
		familia = this.familiaService.disableFamilia( familia );
		
		Assert.assertNotNull( familia );
		Assert.assertFalse( familia.isAtivo() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void enableFamiliaMustPass()
	{
		Familia familia = this.familiaService.findFamiliaById( 100L );
		
		familia = this.familiaService.enableFamilia( familia );
		
		Assert.assertNotNull( familia );
		Assert.assertTrue( familia.isAtivo() );
	}
}















