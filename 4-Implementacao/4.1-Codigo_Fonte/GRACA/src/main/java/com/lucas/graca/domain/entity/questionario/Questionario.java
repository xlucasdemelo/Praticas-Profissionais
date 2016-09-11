/**
 * 
 */
package com.lucas.graca.domain.entity.questionario;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.account.User;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "Questionario")
public class Questionario extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6783696424113040806L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	private String nome;
	
	/**
	 * 
	 */
	@Basic
	private String descricao;
	
	/**
	 * 
	 */
	@ManyToOne( fetch=FetchType.EAGER )
	private User usuarioCriador;
	
	/**
	 * 
	 */
	@NotNull
	private Boolean enabled;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param nome
	 * @param descricao
	 * @param usuarioCriador
	 */
	public Questionario( Long id, String nome, String descricao, User usuarioCriador, Boolean enabled )
	{
		super(id);
		this.nome = nome;
		this.descricao = descricao;
		this.usuarioCriador = usuarioCriador;
		this.enabled = enabled;
	}

	/**
	 * 
	 */
	public Questionario()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Questionario( Long id )
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
		result = prime * result + ( ( usuarioCriador == null ) ? 0 : usuarioCriador.hashCode() );
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
		Questionario other = ( Questionario ) obj;
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
		if ( usuarioCriador == null )
		{
			if ( other.usuarioCriador != null ) return false;
		}
		else if ( !usuarioCriador.equals( other.usuarioCriador ) ) return false;
		return true;
	}

	/**
	 * 
	 */
	@PrePersist
	public void enableQuestionario()
	{
		this.enabled = true;
	}
	
	/**
	 * 
	 */
	public void disableQuestionario()
	{
		this.enabled = false;
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
	 * @return the usuarioCriador
	 */
	public User getUsuarioCriador()
	{
		return usuarioCriador;
	}

	/**
	 * @param usuarioCriador the usuarioCriador to set
	 */
	public void setUsuarioCriador( User usuarioCriador )
	{
		this.usuarioCriador = usuarioCriador;
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
