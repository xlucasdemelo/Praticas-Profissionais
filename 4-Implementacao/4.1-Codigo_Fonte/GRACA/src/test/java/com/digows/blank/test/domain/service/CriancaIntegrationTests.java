/**
 * 
 */
package com.digows.blank.test.domain.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import com.digows.blank.domain.entity.crianca.Crianca;
import com.digows.blank.domain.entity.crianca.Etnia;
import com.digows.blank.domain.entity.endereco.Endereco;
import com.digows.blank.domain.entity.familia.Familia;
import com.digows.blank.domain.entity.familia.Sexo;
import com.digows.blank.domain.entity.integrantefamiliar.GrauEscolaridade;
import com.digows.blank.domain.service.crianca.CriancaService;
import com.digows.blank.test.domain.AbstractIntegrationTests;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;



/**
 * @author lucas
 *
 */
public class CriancaIntegrationTests extends AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private CriancaService criancaService;
	
	/*-------------------------------------------------------------------
	 						TESTS CRIANCA
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertCriancaMustPass()
	{
		Crianca crianca = new Crianca( null, "lucas", Calendar.getInstance(), "DEV", new BigDecimal( 9000 ),
				"Paia 23", "9999-9999", Sexo.MASCULINO, new Endereco(100L), new Boolean( true ), new Familia(100L), 
				GrauEscolaridade.FUNDAMENTAL_COMPLETO, "100", "Sofre violencia", "123456789", 
				"1,92", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Etnia.BRANCO );
	
		crianca = this.criancaService.insertCrianca( crianca );
		Assert.assertNotNull( crianca );
	}
	
	/**
	 * 
	 */
	@Test(expected=TransactionSystemException.class)
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertCriancaMustFail()
	{
		Crianca crianca = new Crianca( null, null, Calendar.getInstance(), "DEV", new BigDecimal( 9000 ),
				"Paia 23", "9999-9999", Sexo.MASCULINO, new Endereco(100L), new Boolean( true ), null, 
				GrauEscolaridade.FUNDAMENTAL_COMPLETO, "100", "Sofre violencia", "123456789", 
				"1,92", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Etnia.BRANCO );
	
		crianca = this.criancaService.insertCrianca( crianca );
		Assert.assertNotNull( crianca );
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/crianca/CriancaDataSet.xml"  
	})
	public void updateCriancaMustPass()
	{
		Crianca crianca = this.criancaService.findCriancaById( 100L );
		crianca.setEtnia( Etnia.NEGRO );
		
		crianca = this.criancaService.updateCrianca( crianca );
		Assert.assertNotNull( crianca );
		Assert.assertTrue( crianca.getEtnia() == Etnia.NEGRO );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/crianca/CriancaDataSet.xml"  
	})
	public void updateCriancaMustFail()
	{
		Crianca crianca = this.criancaService.findCriancaById( 100L );
		crianca.setEtnia( Etnia.NEGRO );
		
		crianca = this.criancaService.updateCrianca( crianca );
		Assert.assertNotNull( crianca );
		Assert.assertTrue( crianca.getEtnia() == Etnia.NEGRO );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/crianca/CriancaDataSet.xml"  
	})
	public void listCriancasByFiltersNullMustPass()
	{
		List<Crianca> criancas = this.criancaService.listCriancasByFilters( null, null ).getContent();
		
		Assert.assertTrue( !criancas.isEmpty() );
		
		for ( Crianca crianca : criancas )
		{
			Assert.assertTrue( crianca.getAtivo() );
		}
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/crianca/CriancaDataSet.xml"  
	})
	public void listCriancasByFiltersMustPass()
	{
		List<Crianca> criancas = this.criancaService.listCriancasByFilters( "Maynard", null ).getContent();
		
		Assert.assertTrue( !criancas.isEmpty() );
		Assert.assertTrue( criancas.size() == 1 );
		Assert.assertTrue( criancas.get( 0 ).getNome().equals( "Maynard James Keenan" ) );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/crianca/CriancaDataSet.xml"  
	})
	public void listCriancasByFiltersMustReturnEmptyList()
	{
		List<Crianca> criancas = this.criancaService.listCriancasByFilters( "Nenhum item" , null ).getContent();
		
		Assert.assertTrue( criancas.isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/crianca/CriancaDataSet.xml"  
	})
	public void disableCriancaMustPass()
	{
		Crianca crianca = this.criancaService.findCriancaById( 100L );
		
		Assert.assertNotNull( crianca );
		Assert.assertTrue( crianca.getAtivo() );
		
		crianca = this.criancaService.disableCrianca( crianca );
		Assert.assertNotNull( crianca );
		Assert.assertFalse( crianca.getAtivo() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/crianca/CriancaDataSet.xml"  
	})
	public void enableCriancaMustPass()
	{
		Crianca crianca = this.criancaService.findCriancaById( 100L );
		
		Assert.assertNotNull( crianca );
		Assert.assertTrue( crianca.getAtivo() );
		
		crianca = this.criancaService.enableCrianca( crianca );
		Assert.assertNotNull( crianca );
		Assert.assertTrue( crianca.getAtivo() );
	}
}
