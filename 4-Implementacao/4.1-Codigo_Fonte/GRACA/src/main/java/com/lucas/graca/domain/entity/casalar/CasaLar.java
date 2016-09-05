/**
 * 
 */
package com.lucas.graca.domain.entity.casalar;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.planoatendimento.Responsavel;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "CasaLar")
public class CasaLar extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5440662163624581803L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	private Boolean enabled; 
	
	/**
	 * 
	 */
	@NotNull
	private Integer numero;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	private Responsavel cuidadoraResidente;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	private Responsavel cuidadoraApoiadora;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	public CasaLar()
	{
		super();
	}

	/**
	 * @param id
	 */
	public CasaLar( Long id )
	{
		super( id );
	}

	/**
	 * @param enabled
	 * @param numero
	 * @param cuidadoraResidente
	 * @param cuidadoraApoiadora
	 */
	public CasaLar( Long id, Boolean enabled, Integer numero, Responsavel cuidadoraResidente, Responsavel cuidadoraApoiadora )
	{
		super(id);
		this.enabled = enabled;
		this.numero = numero;
		this.cuidadoraResidente = cuidadoraResidente;
		this.cuidadoraApoiadora = cuidadoraApoiadora;
	}

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( cuidadoraApoiadora == null ) ? 0 : cuidadoraApoiadora.hashCode() );
		result = prime * result + ( ( cuidadoraResidente == null ) ? 0 : cuidadoraResidente.hashCode() );
		result = prime * result + ( ( enabled == null ) ? 0 : enabled.hashCode() );
		result = prime * result + ( ( numero == null ) ? 0 : numero.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj ) return true;
		if ( !super.equals( obj ) ) return false;
		if ( getClass() != obj.getClass() ) return false;
		CasaLar other = ( CasaLar ) obj;
		if ( cuidadoraApoiadora == null )
		{
			if ( other.cuidadoraApoiadora != null ) return false;
		}
		else if ( !cuidadoraApoiadora.equals( other.cuidadoraApoiadora ) ) return false;
		if ( cuidadoraResidente == null )
		{
			if ( other.cuidadoraResidente != null ) return false;
		}
		else if ( !cuidadoraResidente.equals( other.cuidadoraResidente ) ) return false;
		if ( enabled == null )
		{
			if ( other.enabled != null ) return false;
		}
		else if ( !enabled.equals( other.enabled ) ) return false;
		if ( numero == null )
		{
			if ( other.numero != null ) return false;
		}
		else if ( !numero.equals( other.numero ) ) return false;
		return true;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void enableCasaLar()
	{
		this.enabled = true;
	}
	
	/**
	 * 
	 */
	public void disableCasaLar()
	{
		this.enabled = false;
	}

	/*-------------------------------------------------------------------
	 *				 		 GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled()
	{
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled( Boolean enabled )
	{
		this.enabled = enabled;
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
	 * @return the cuidadoraResidente
	 */
	public Responsavel getCuidadoraResidente()
	{
		return cuidadoraResidente;
	}

	/**
	 * @param cuidadoraResidente the cuidadoraResidente to set
	 */
	public void setCuidadoraResidente( Responsavel cuidadoraResidente )
	{
		this.cuidadoraResidente = cuidadoraResidente;
	}

	/**
	 * @return the cuidadoraApoiadora
	 */
	public Responsavel getCuidadoraApoiadora()
	{
		return cuidadoraApoiadora;
	}

	/**
	 * @param cuidadoraApoiadora the cuidadoraApoiadora to set
	 */
	public void setCuidadoraApoiadora( Responsavel cuidadoraApoiadora )
	{
		this.cuidadoraApoiadora = cuidadoraApoiadora;
	}
}
