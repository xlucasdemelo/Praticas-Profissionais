/**
 * 
 */
package com.lucas.graca.domain.entity.caixa;

import java.io.Serializable;

import javax.persistence.Basic;
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
@DataTransferObject(javascript = "NaturezaGastos")
public class NaturezaGastos extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7661443537903422937L;
	
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
	@Basic
	private String descricao;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean enabled;

	/*-------------------------------------------------------------------
	 *				 		     CONTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param nome
	 * @param descricao
	 * @param enabled
	 */
	public NaturezaGastos( Long id, String nome, String descricao, Boolean enabled )
	{
		super(id);
		this.nome = nome;
		this.descricao = descricao;
		this.enabled = enabled;
	}

	/**
	 * 
	 */
	public NaturezaGastos()
	{
		super();
	}

	/**
	 * @param id
	 */
	public NaturezaGastos( Long id )
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
		NaturezaGastos other = ( NaturezaGastos ) obj;
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
	 *				 		GETTERS AND SETTERS
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
