/**
 * 
 */
package com.lucas.graca.domain.entity.crianca;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.lucas.graca.domain.entity.documento.TipoDocumento;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
public class DocumentoCrianca extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 333803514562239922L;
	
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
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "crianca_id")
	private Crianca crianca;
	
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
	 * @param crianca
	 * @param numeroDocumento
	 */
	public DocumentoCrianca(Long id, TipoDocumento tipoDocumento, com.lucas.graca.domain.entity.crianca.Crianca crianca, String numeroDocumento )
	{
		super(id);
		this.tipoDocumento = tipoDocumento;
		this.crianca = crianca;
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * 
	 */
	public DocumentoCrianca()
	{
		super();
	}

	/**
	 * @param id
	 */
	public DocumentoCrianca( Long id )
	{
		super( id );
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
	 * @return the crianca
	 */
	public Crianca getCrianca()
	{
		return crianca;
	}

	/**
	 * @param crianca the crianca to set
	 */
	public void setCrianca( Crianca crianca )
	{
		this.crianca = crianca;
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