/**
 * 
 */
package com.digows.blank.domain.entity.integrantefamiliar;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.digows.blank.domain.entity.documento.TipoDocumento;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "DocumentoIntegranteFamiliar")
public class DocumentoIntegranteFamiliar extends AbstractEntity implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6114163017959935420L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private TipoDocumento tipoDocumento;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "integrante_familiar_id")
	private IntegranteFamiliar integranteFamiliar;
	
	/**
	 * 
	 */
	@NotNull
	private String numeroDocumento;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTOR
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param tipoDocumento
	 * @param integranteFamiliar
	 * @param numeroDocumento
	 */
	public DocumentoIntegranteFamiliar( Long id, TipoDocumento tipoDocumento, IntegranteFamiliar integranteFamiliar, String numeroDocumento )
	{
		super(id);
		this.tipoDocumento = tipoDocumento;
		this.integranteFamiliar = integranteFamiliar;
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * 
	 */
	public DocumentoIntegranteFamiliar()
	{
		super();
	}

	/**
	 * @param id
	 */
	public DocumentoIntegranteFamiliar( Long id )
	{
		super( id );
	}

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	public void mergeObject( DocumentoIntegranteFamiliar documentoIntegranteFamiliar )
	{
		this.numeroDocumento = documentoIntegranteFamiliar.numeroDocumento;
	}
	
	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the tipoDocumento
	 */
	public TipoDocumento getTipoDocumento()
	{
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento( TipoDocumento tipoDocumento )
	{
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the integranteFamiliar
	 */
	public IntegranteFamiliar getIntegranteFamiliar()
	{
		return integranteFamiliar;
	}

	/**
	 * @param integranteFamiliar the integranteFamiliar to set
	 */
	public void setIntegranteFamiliar( IntegranteFamiliar integranteFamiliar )
	{
		this.integranteFamiliar = integranteFamiliar;
	}

	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento()
	{
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento( String numeroDocumento )
	{
		this.numeroDocumento = numeroDocumento;
	}
}


















