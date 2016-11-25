/**
 * 
 */
package com.lucas.graca.domain.entity.caixa;

import java.io.Serializable;
import java.math.BigDecimal;

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
@DataTransferObject(javascript = "Conta")
public class Conta extends AbstractEntity implements Serializable
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
	private String descricao;
	
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
	private BigDecimal saldo;
	
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
	 * @param descricao
	 * @param nome
	 * @param saldo
	 * @param enabled
	 */
	public Conta( Long id, String descricao, String nome, BigDecimal saldo, Boolean enabled )
	{
		super(id);
		this.descricao = descricao;
		this.nome = nome;
		this.saldo = saldo;
		this.enabled = enabled;
	}
	
	/**
	 * 
	 */
	public Conta()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Conta( Long id )
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
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( enabled == null ) ? 0 : enabled.hashCode() );
		result = prime * result + ( ( nome == null ) ? 0 : nome.hashCode() );
		result = prime * result + ( ( saldo == null ) ? 0 : saldo.hashCode() );
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
		Conta other = ( Conta ) obj;
		if ( descricao == null )
		{
			if ( other.descricao != null ) return false;
		}
		else if ( !descricao.equals( other.descricao ) ) return false;
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
		if ( saldo == null )
		{
			if ( other.saldo != null ) return false;
		}
		else if ( !saldo.equals( other.saldo ) ) return false;
		return true;
	}

	/**
	 * 
	 */
	@PrePersist
	public void enable()
	{
		this.enabled = true;
	}
	
	/**
	 * 
	 */
	public void disable()
	{
		this.enabled = false;
	}
	
	/*-------------------------------------------------------------------
	 *				 		  GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the descricao
	 */
	public String getDescricao()
	{
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao( String descricao )
	{
		this.descricao = descricao;
	}

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
	 * @return the saldo
	 */
	public BigDecimal getSaldo()
	{
		return saldo;
	}

	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo( BigDecimal saldo )
	{
		this.saldo = saldo;
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
