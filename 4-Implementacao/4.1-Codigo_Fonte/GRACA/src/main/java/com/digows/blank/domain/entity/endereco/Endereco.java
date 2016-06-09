/**
 * 
 */
package com.digows.blank.domain.entity.endereco;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "Endereco")
public class Endereco extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1383197097809701746L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	private String bairro;
	
	/**
	 * 
	 */
	private String rua;
	
	/**
	 * 
	 */
	private Integer numero;
	
	/**
	 * 
	 */
	private String complemento;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cidade_id")
	private Cidade cidade;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param bairro
	 * @param rua
	 * @param numero
	 * @param complemento
	 */
	public Endereco( Long id, String bairro, String rua, Integer numero, String complemento, Cidade cidade )
	{
		super(id);
		this.bairro = bairro;
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
		this.cidade = cidade;
	}

	/**
	 * 
	 */
	public Endereco()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Endereco( Long id )
	{
		super( id );
	}

	/*-------------------------------------------------------------------
	 *				 		    BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	public void mergeObject( Endereco endereco )
	{
		this.bairro = endereco.getBairro();
		this.rua = endereco.getRua();
		
		this.cidade = endereco.getCidade();
	}
	
	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the bairro
	 */
	public String getBairro()
	{
		return bairro;
	}

	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro( String bairro )
	{
		this.bairro = bairro;
	}

	/**
	 * @return the rua
	 */
	public String getRua()
	{
		return rua;
	}

	/**
	 * @param rua the rua to set
	 */
	public void setRua( String rua )
	{
		this.rua = rua;
	}

	/**
	 * @return the numero
	 */
	public Integer getNumero()
	{
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero( Integer numero )
	{
		this.numero = numero;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento()
	{
		return complemento;
	}

	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento( String complemento )
	{
		this.complemento = complemento;
	}

	/**
	 * @return the cidade
	 */
	public Cidade getCidade()
	{
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade( Cidade cidade )
	{
		this.cidade = cidade;
	}
}
