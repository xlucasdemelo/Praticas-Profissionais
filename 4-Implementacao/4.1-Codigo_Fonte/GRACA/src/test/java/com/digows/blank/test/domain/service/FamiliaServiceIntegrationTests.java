/**
 * 
 */
package com.digows.blank.test.domain.service;

import javax.validation.ValidationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import com.digows.blank.domain.entity.endereco.Endereco;
import com.digows.blank.domain.entity.familia.Familia;
import com.digows.blank.domain.entity.familia.TipoImovel;
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
		Familia familia = new Familia( 1L, "Silva", "99999", 3, "Boa", "Basica", TipoImovel.CASA, null, true, "Maria Silva");
		familia.setEndereco( new Endereco(100L) );
		
		familia = this.familiaService.insertFamilia( familia );
		
		Assert.notNull( familia );
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
		Familia familia = new Familia( 1L, null, "99999", 3, "Boa", "Basica", TipoImovel.CASA, null, true, "Marlene Silva");
		familia.setEndereco( new Endereco(100L) );
		
		familia = this.familiaService.insertFamilia( familia );
		
		Assert.notNull( familia );
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
		Familia familia = new Familia( 1L, "Silva", "99999", 3, "Boa", "Basica", TipoImovel.CASA, null, true, "Marlene Silva");
		familia.setEndereco( new Endereco(100L) );
		
		familia = this.familiaService.insertFamilia( familia );
		
		Assert.notNull( familia );
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
		Assert.notNull( familia );
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
		
		Assert.isTrue( !familia.getContent().isEmpty() );
		Assert.isTrue( familia.getContent().get( 0 ).getNome() == "Silva" );
	}
}
