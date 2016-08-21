/**
 * 
 */
package com.lucas.graca.domain.entity.planoatendimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.redeapoio.RedeApoio;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "Apoio")
public class Apoio extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7882945403182792447L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="plano_atendimento_id")
	private PlanoAtendimento planoAtendimento;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rede_apoio_id")
	private RedeApoio redeApoio;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param planoAtendimento
	 * @param redeApoio
	 */
	public Apoio( Long id, PlanoAtendimento planoAtendimento, RedeApoio redeApoio )
	{
		super(id);
		this.planoAtendimento = planoAtendimento;
		this.redeApoio = redeApoio;
	}

	/**
	 * 
	 */
	public Apoio()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Apoio( Long id )
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
		result = prime * result + ( ( planoAtendimento == null ) ? 0 : planoAtendimento.hashCode() );
		result = prime * result + ( ( redeApoio == null ) ? 0 : redeApoio.hashCode() );
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
		Apoio other = ( Apoio ) obj;
		if ( planoAtendimento == null )
		{
			if ( other.planoAtendimento != null ) return false;
		}
		else if ( !planoAtendimento.equals( other.planoAtendimento ) ) return false;
		if ( redeApoio == null )
		{
			if ( other.redeApoio != null ) return false;
		}
		else if ( !redeApoio.equals( other.redeApoio ) ) return false;
		return true;
	}
	
	/*-------------------------------------------------------------------
	 *				 		 GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the planoAtendimento
	 */
	public PlanoAtendimento getPlanoAtendimento()
	{
		return planoAtendimento;
	}

	/**
	 * @param planoAtendimento the planoAtendimento to set
	 */
	public void setPlanoAtendimento( PlanoAtendimento planoAtendimento )
	{
		this.planoAtendimento = planoAtendimento;
	}

	/**
	 * @return the redeApoio
	 */
	public RedeApoio getRedeApoio()
	{
		return redeApoio;
	}

	/**
	 * @param redeApoio the redeApoio to set
	 */
	public void setRedeApoio( RedeApoio redeApoio )
	{
		this.redeApoio = redeApoio;
	}
}
