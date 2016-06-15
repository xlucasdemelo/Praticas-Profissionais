/**
 * 
 */
package com.digows.blank.test.domain.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.TransactionSystemException;

import com.digows.blank.test.domain.AbstractIntegrationTests;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lucas.graca.domain.entity.documento.TipoDocumento;
import com.lucas.graca.domain.entity.endereco.Endereco;
import com.lucas.graca.domain.entity.familia.Familia;
import com.lucas.graca.domain.entity.familia.Sexo;
import com.lucas.graca.domain.entity.integrantefamiliar.DocumentoIntegranteFamiliar;
import com.lucas.graca.domain.entity.integrantefamiliar.GrauEscolaridade;
import com.lucas.graca.domain.entity.integrantefamiliar.IntegranteFamiliar;
import com.lucas.graca.domain.service.integranteFamiliar.IntegranteFamiliarService;

/**
 * @author lucas
 *
 */
public class IntegranteFamiliarIntegrationTests extends AbstractIntegrationTests 
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private IntegranteFamiliarService integranteFamiliarService;
	
	/*-------------------------------------------------------------------
	 						TESTS INTEGRANTE FAMILIAR
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertIntegranteFamiliarMustPass()
	{
		IntegranteFamiliar integranteFamiliar = new IntegranteFamiliar( null, "lucas", Calendar.getInstance(), "DEV", new BigDecimal( 9000 ), "Paia 23", "9999-9999", Sexo.MASCULINO, new Endereco(100L), new Boolean( true ), new Familia(100L), GrauEscolaridade.FUNDAMENTAL_COMPLETO );
	
		integranteFamiliar = this.integranteFamiliarService.insertIntegranteFamiliar( integranteFamiliar );
		Assert.assertNotNull( integranteFamiliar );
	}
	
	/**
	 * 
	 */
	@Test(expected= TransactionSystemException.class)
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml" 
	})
	public void insertIntegranteFamiliarMustFailWithoutMandatoryFields()
	{
		IntegranteFamiliar integranteFamiliar = new IntegranteFamiliar( null, null, Calendar.getInstance(), "DEV", new BigDecimal( 9000 ), "Juliana Penayo de Melo", "9999-9999", Sexo.MASCULINO, new Endereco(100L), new Boolean( true ), new Familia(100L), null );
	
		integranteFamiliar = this.integranteFamiliarService.insertIntegranteFamiliar( integranteFamiliar );
		Assert.fail();
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml"  
	})
	public void listIntegrantesByFamiliaMustPass()
	{
		Page<IntegranteFamiliar> integrantes = this.integranteFamiliarService.listIntegrantesByfamilia( 100L, null );
		
		Assert.assertNotNull( integrantes );
		Assert.assertFalse( integrantes.getContent().isEmpty() );
	}
	
	/**
	 * 
	 */
	@Test
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", "/dataset/endereco/EstadoDataSet.xml", "/dataset/endereco/CidadeDataSet.xml", "/dataset/endereco/EnderecoDataSet.xml", "/dataset/familia/FamiliaDataSet.xml", "/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml"  
	})
	public void listIntegrantesByFamiliaMustReturnEmptyList()
	{
		Page<IntegranteFamiliar> integrantes = this.integranteFamiliarService.listIntegrantesByfamilia( 101L, null );
		
		Assert.assertNotNull( integrantes );
		Assert.assertTrue( integrantes.getContent().isEmpty() );
	}
	
	/*-------------------------------------------------------------------
	 *                 TESTS DOCUMENTO INTEGRANTE FAMILIAR
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
			"/dataset/familia/FamiliaDataSet.xml", 
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml", 
			"/dataset/integrantefamiliar/DocumentoIntegranteFamiliarDataSet.xml"  
		})
	public void insertDocumentoIntegranteFamiliarMustPass()
	{
		DocumentoIntegranteFamiliar documentoIntegranteFamiliar = new DocumentoIntegranteFamiliar(null);
		documentoIntegranteFamiliar.setIntegranteFamiliar( new IntegranteFamiliar(100L) );
		documentoIntegranteFamiliar.setNumeroDocumento( "8888888888" );
		documentoIntegranteFamiliar.setTipoDocumento( TipoDocumento.CPF );
		
		documentoIntegranteFamiliar = this.integranteFamiliarService.insertDocumentoIntegranteFamiliar( documentoIntegranteFamiliar );
		Assert.assertNotNull( documentoIntegranteFamiliar );
		Assert.assertTrue( documentoIntegranteFamiliar.getNumeroDocumento().equals( "8888888888" ) );
	}
	
	/**
	 * 
	 */
	@Test( expected=ValidationException.class )
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml", 
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml", 
			"/dataset/integrantefamiliar/DocumentoIntegranteFamiliarDataSet.xml"  
		})
	public void insertDocumentoIntegranteFamiliarMustFailWithoutMandatoryFields()
	{
		DocumentoIntegranteFamiliar documentoIntegranteFamiliar = new DocumentoIntegranteFamiliar(null);
		documentoIntegranteFamiliar.setIntegranteFamiliar( null );
		documentoIntegranteFamiliar.setNumeroDocumento( null );
		documentoIntegranteFamiliar.setTipoDocumento( null );
		
		documentoIntegranteFamiliar = this.integranteFamiliarService.insertDocumentoIntegranteFamiliar( documentoIntegranteFamiliar );
		
		Assert.fail();
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
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml", 
			"/dataset/integrantefamiliar/DocumentoIntegranteFamiliarDataSet.xml"  
		})
	public void updateDocumentoIntegranteFamiliarMustPass()
	{
		DocumentoIntegranteFamiliar documentoIntegranteFamiliar = this.integranteFamiliarService.findDocumentoIntegranteFamiliarById( 100L );
		Assert.assertTrue( documentoIntegranteFamiliar.getNumeroDocumento().equals( "99999999" ) );
		
		documentoIntegranteFamiliar.setNumeroDocumento( "989898989898" );
		
		documentoIntegranteFamiliar = this.integranteFamiliarService.updateDocumentoIntegranteFamiliar( documentoIntegranteFamiliar );
		Assert.assertNotNull( documentoIntegranteFamiliar );
		Assert.assertFalse( documentoIntegranteFamiliar.getNumeroDocumento().equals( "99999999" ) );
		Assert.assertTrue( documentoIntegranteFamiliar.getNumeroDocumento().equals( "989898989898" ) );
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
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml", 
			"/dataset/integrantefamiliar/DocumentoIntegranteFamiliarDataSet.xml"  
		})
	public void updateDocumentoIntegranteOnlyEditableFieldsMustPass()
	{
		DocumentoIntegranteFamiliar documentoIntegranteFamiliar = this.integranteFamiliarService.findDocumentoIntegranteFamiliarById( 100L );
		Assert.assertTrue( documentoIntegranteFamiliar.getNumeroDocumento().equals( "99999999" ) );
		Assert.assertTrue( documentoIntegranteFamiliar.getIntegranteFamiliar().getId() == 100L );
		
		documentoIntegranteFamiliar.setNumeroDocumento( "9898989898");
		documentoIntegranteFamiliar.setIntegranteFamiliar( new IntegranteFamiliar(101L) );
		
		documentoIntegranteFamiliar = this.integranteFamiliarService.updateDocumentoIntegranteFamiliar( documentoIntegranteFamiliar );
		
		Assert.assertNotNull( documentoIntegranteFamiliar );
		Assert.assertTrue( documentoIntegranteFamiliar.getNumeroDocumento().equals( "9898989898" ) );
		Assert.assertTrue( documentoIntegranteFamiliar.getIntegranteFamiliar().getId() == 100L );
	}
	
	/**
	 * 
	 */
	@Test( expected=TransactionSystemException.class )
	@DatabaseSetup(type = DatabaseOperation.INSERT, value = {
			"/dataset/endereco/PaisDataSet.xml", 
			"/dataset/endereco/EstadoDataSet.xml", 
			"/dataset/endereco/CidadeDataSet.xml", 
			"/dataset/endereco/EnderecoDataSet.xml", 
			"/dataset/familia/FamiliaDataSet.xml", 
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml", 
			"/dataset/integrantefamiliar/DocumentoIntegranteFamiliarDataSet.xml"  
		})
	public void updateDocumentoIntegranteFamiliarMustFailWithouMandatoryFieds()
	{
		DocumentoIntegranteFamiliar documentoIntegranteFamiliar = this.integranteFamiliarService.findDocumentoIntegranteFamiliarById( 100L );
		Assert.assertTrue( documentoIntegranteFamiliar.getNumeroDocumento().equals( "99999999" ) );
		
		documentoIntegranteFamiliar.setNumeroDocumento( null );
		
		documentoIntegranteFamiliar = this.integranteFamiliarService.updateDocumentoIntegranteFamiliar( documentoIntegranteFamiliar );
		Assert.fail();
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
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml", 
			"/dataset/integrantefamiliar/DocumentoIntegranteFamiliarDataSet.xml"  
		})
	public void removeDocumentoIntegranteFamiliarMustPass()
	{
		DocumentoIntegranteFamiliar documentoIntegranteFamiliar = this.integranteFamiliarService.findDocumentoIntegranteFamiliarById( 100L );
		
		this.integranteFamiliarService.removeDocumentoIntegranteFamiliar( documentoIntegranteFamiliar );
		
		documentoIntegranteFamiliar = this.integranteFamiliarService.findDocumentoIntegranteFamiliarById( 100L );
		Assert.assertNull( documentoIntegranteFamiliar );
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
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml", 
			"/dataset/integrantefamiliar/DocumentoIntegranteFamiliarDataSet.xml"  
		})
	public void listDocumentosByIntegranteFamiliarMustPass()
	{
		List<DocumentoIntegranteFamiliar> documentos = this.integranteFamiliarService.listDocumentosByIntegranteFamiliar( 100L ).getContent();
		
		Assert.assertFalse( documentos.isEmpty() );
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
			"/dataset/integrantefamiliar/IntegranteFamiliarDataSet.xml", 
			"/dataset/integrantefamiliar/DocumentoIntegranteFamiliarDataSet.xml"  
		})
	public void listDocumentosByIntegranteFamiliarMustReturnEmptyList()
	{
		List<DocumentoIntegranteFamiliar> documentos = this.integranteFamiliarService.listDocumentosByIntegranteFamiliar( 9999L ).getContent();
		
		Assert.assertTrue( documentos.isEmpty() );
	}
}


























