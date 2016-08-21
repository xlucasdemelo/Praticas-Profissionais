/**
 * 
 */
package com.lucas.graca.domain.entity.planoatendimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.abstractEntity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DataTransferObject(javascript = "PlanoAtendimento")
public abstract class PlanoAtendimento extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6419174811021071714L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	private Boolean ativo;
	
	/**
	 * 
	 */
	@NotNull
	private StatusPlanoAtendimento status;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param ativo
	 * @param redeApoio
	 * @param status
	 */
	public PlanoAtendimento( Long id, Boolean ativo, StatusPlanoAtendimento status )
	{
		super(id);
		this.ativo = ativo;
		this.status = status;
	}

	/**
	 * 
	 */
	public PlanoAtendimento()
	{
		super();
	}

	/**
	 * @param id
	 */
	public PlanoAtendimento( Long id )
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
		result = prime * result + ( ( ativo == null ) ? 0 : ativo.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
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
		PlanoAtendimento other = ( PlanoAtendimento ) obj;
		if ( ativo == null )
		{
			if ( other.ativo != null ) return false;
		}
		else if ( !ativo.equals( other.ativo ) ) return false;
		if ( status != other.status ) return false;
		return true;
	}
	
	/**
	 * 
	 */
	public void enablePlanoAtendimento()
	{
		this.ativo = true;
	}
	
	/**
	 * 
	 */
	public void disablePlanoAtendimento()
	{
		this.ativo = false;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void initializeToPersist()
	{
		this.enablePlanoAtendimento();
		this.changeToRascunho();
	}
	
	/**
	 * 
	 */
	public void changeToRascunho()
	{
		this.status = StatusPlanoAtendimento.RASCUNHO;
	}
	
	/*-------------------------------------------------------------------
	 *				 		  GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the ativo
	 */
	public Boolean getAtivo()
	{
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo( Boolean ativo )
	{
		this.ativo = ativo;
	}

	/**
	 * @return the status
	 */
	public StatusPlanoAtendimento getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( StatusPlanoAtendimento status )
	{
		this.status = status;
	}
}
