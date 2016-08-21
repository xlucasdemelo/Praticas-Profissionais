/**
 * 
 */
package com.lucas.graca.domain.entity.planoatendimentofamiliar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.familia.Familia;
import com.lucas.graca.domain.entity.planoatendimento.PlanoAtendimento;
import com.lucas.graca.domain.entity.planoatendimento.StatusPlanoAtendimento;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "PlanoAtendimentoFamiliar")
public class PlanoAtendimentoFamiliar extends PlanoAtendimento
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7144058595965555371L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@ManyToOne (optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name="familia_id")
	private Familia familia;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param id
	 * @param ativo
	 * @param redeApoio
	 * @param status
	 * @param familia
	 */
	public PlanoAtendimentoFamiliar( Long id, Boolean ativo, StatusPlanoAtendimento status, Familia familia )
	{
		super( id, ativo, status );
		this.familia = familia;
	}

	/**
	 * 
	 */
	public PlanoAtendimentoFamiliar()
	{
		super();
	}

	/**
	 * @param id
	 */
	public PlanoAtendimentoFamiliar( Long id )
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
		result = prime * result + ( ( familia == null ) ? 0 : familia.hashCode() );
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
		PlanoAtendimentoFamiliar other = ( PlanoAtendimentoFamiliar ) obj;
		if ( familia == null )
		{
			if ( other.familia != null ) return false;
		}
		else if ( !familia.equals( other.familia ) ) return false;
		return true;
	}

	/*-------------------------------------------------------------------
	 *				 		 GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the familia
	 */
	public Familia getFamilia()
	{
		return familia;
	}

	/**
	 * @param familia the familia to set
	 */
	public void setFamilia( Familia familia )
	{
		this.familia = familia;
	}
	
	
}
