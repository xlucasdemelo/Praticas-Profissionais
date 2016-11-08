/**
 * 
 */
package com.lucas.graca.domain.entity.caixa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author eits
 *
 */

@Entity
@Audited
@DataTransferObject(javascript = "Banco")
public class Banco extends AbstractEntity 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2545688423305746105L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private String nome;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean enabled;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param nome
	 * @param enabled
	 */
	public Banco( Long id, String nome, Boolean enabled )
	{
		super(id);
		this.nome = nome;
		this.enabled = enabled;
	}

	/**
	 * 
	 */
	public Banco()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Banco( Long id )
	{
		super( id );
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
		result = prime * result + ( ( enabled == null ) ? 0 : enabled.hashCode() );
		result = prime * result + ( ( nome == null ) ? 0 : nome.hashCode() );
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
		Banco other = ( Banco ) obj;
		if ( enabled == null )
		{
			if ( other.enabled != null ) return false;
		}
		else if ( !enabled.equals( other.enabled ) ) return false;
		if ( nome == null )
		{
			if ( other.nome != null ) return false;
		}
		else if ( !nome.equals( other.nome ) ) return false;
		return true;
	}
	
	/**
	 * 
	 */
	public void disableBanco()
	{
		this.enabled = false;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void enableBanco()
	{
		this.enabled = true;
	}

	/*-------------------------------------------------------------------
	 *				 		 GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the nome
	 */
	public String getNome()
	{
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome( String nome )
	{
		this.nome = nome;
	}

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
	
	
}
