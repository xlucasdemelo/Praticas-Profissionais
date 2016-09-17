/**
 * 
 */
package com.lucas.graca.test.domain.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.crianca.Crianca;
import com.lucas.graca.domain.entity.crianca.DocumentoCrianca;
import com.lucas.graca.domain.entity.crianca.Etnia;
import com.lucas.graca.domain.entity.endereco.Cidade;
import com.lucas.graca.domain.entity.endereco.Endereco;
import com.lucas.graca.domain.entity.familia.Familia;
import com.lucas.graca.domain.entity.familia.Sexo;
import com.lucas.graca.domain.entity.integrantefamiliar.GrauEscolaridade;
import com.lucas.graca.domain.entity.integrantefamiliar.TipoDocumento;
import com.lucas.graca.domain.service.crianca.CriancaService;
import com.lucas.graca.test.domain.AbstractIntegrationTests;



/**
 * @author lucas
 *
 */
public class CriancaServiceIntegrationTests extends AbstractIntegrationTests
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml"
	})
	public void insertCriancaMustPass()
	{
		Crianca crianca = new Crianca( null, "lucas", Calendar.getInstance(), "DEV", new BigDecimal( 9000 ),
				"Paia 23", "9999-9999", Sexo.MASCULINO, new Endereco(null, "bairro paia", "rua", null, null, new Cidade(100L)), new Boolean( true ), new Familia(100L), 
				GrauEscolaridade.FUNDAMENTAL_COMPLETO, "100", "Sofre violencia", "123456789", 
				"1,92", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Etnia.BRANCO, "Conselho tutelar", null );
	
		crianca = this.criancaService.insertCrianca( crianca );
		Assert.assertNotNull( crianca );
	}
	
	/**
	 * 
	 */
	
	/**
	 * 
	 */
	@Test(expected=ValidationException.class)
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertCriancaMustFail()
	{
		Crianca crianca = new Crianca( null, null, Calendar.getInstance(), "DEV", new BigDecimal( 9000 ),
				"Paia 23", "9999-9999", Sexo.MASCULINO, new Endereco(100L), new Boolean( true ), null, 
				GrauEscolaridade.FUNDAMENTAL_COMPLETO, "100", "Sofre violencia", "123456789", 
				"1,92", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Etnia.BRANCO, null, null );
	
		crianca = this.criancaService.insertCrianca( crianca );
		Assert.assertNotNull( crianca );
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml" , "/dataset/crianca/CriancaDataSet.xml"  
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
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml" , "/dataset/crianca/CriancaDataSet.xml"  
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
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml" , "/dataset/crianca/CriancaDataSet.xml"  
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
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml" , "/dataset/crianca/CriancaDataSet.xml"  
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
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml" , "/dataset/crianca/CriancaDataSet.xml"  
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
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml",
			"/dataset/familia/FamiliaDataSet.xml",
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml" , "/dataset/crianca/CriancaDataSet.xml"
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
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml" , "/dataset/crianca/CriancaDataSet.xml"  
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
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml" , "/dataset/crianca/CriancaDataSet.xml", "/dataset/crianca/DocumentoCriancaDataSet.xml"  
	})
	public void listDocumentosByCriacaMustPass()
	{
		Crianca crianca = this.criancaService.findCriancaById( 100L );
		
		Assert.assertNotNull( crianca );

		List<DocumentoCrianca> documentos = this.criancaService.listDocumentosByCrianca( 100L, null ).getContent();
		
		Assert.assertNotNull( documentos );
		Assert.assertFalse( documentos.isEmpty() );
	}
	
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml" , "/dataset/crianca/CriancaDataSet.xml"  
	})
	public void insertDocumentoCriancaMustPass()
	{
		Crianca crianca = this.criancaService.findCriancaById( 100L );
		
		Assert.assertNotNull( crianca );

		DocumentoCrianca documentoCrianca = new DocumentoCrianca( null, TipoDocumento.CPF, crianca, "123" );
		
		documentoCrianca = this.criancaService.insertDocumentoCrianca( documentoCrianca );
		Assert.assertNotNull( documentoCrianca );
	}
}
