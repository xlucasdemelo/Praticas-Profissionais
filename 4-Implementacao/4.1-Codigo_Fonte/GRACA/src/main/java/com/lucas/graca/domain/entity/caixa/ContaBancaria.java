/**
 * 
 */
package com.lucas.graca.domain.entity.caixa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
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
@Table( name = "conta_bancaria")
@DataTransferObject(javascript = "ContaBancaria")
public class ContaBancaria extends AbstractEntity 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5372953763563247046L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private String numero;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private String agencia;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean enabled;
	
	/**
	 * 
	 */
	@ManyToOne( optional = false, fetch = FetchType.EAGER)
	private Banco banco;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param numero
	 * @param agencia
	 * @param enabled
	 * @param banco
	 */
	public ContaBancaria( Long id, String numero, String agencia, Boolean enabled, Banco banco )
	{
		super(id);
		this.numero = numero;
		this.agencia = agencia;
		this.enabled = enabled;
		this.banco = banco;
	}

	/**
	 * 
	 */
	public ContaBancaria()
	{
		super();
	}

	/**
	 * @param id
	 */
	public ContaBancaria( Long id )
	{
		super( id );
	}

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*
	 *
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( agencia == null ) ? 0 : agencia.hashCode() );
		result = prime * result + ( ( banco == null ) ? 0 : banco.hashCode() );
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
		ContaBancaria other = ( ContaBancaria ) obj;
		if ( agencia == null )
		{
			if ( other.agencia != null ) return false;
		}
		else if ( !agencia.equals( other.agencia ) ) return false;
		if ( banco == null )
		{
			if ( other.banco != null ) return false;
		}
		else if ( !banco.equals( other.banco ) ) return false;
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
	public void disableFornecedor()
	{
		this.enabled = false;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void enableFornecedor()
	{
		this.enabled = true;
	}
	
	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*
	 *
	/**
	 * @return the agencia
	 */
	public String getAgencia()
	{
		return agencia;
	}

	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia( String agencia )
	{
		this.agencia = agencia;
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

	/**
	 * @return the banco
	 */
	public Banco getBanco()
	{
		return banco;
	}

	/**
	 * @param banco the banco to set
	 */
	public void setBanco( Banco banco )
	{
		this.banco = banco;
	}
	
	
}
