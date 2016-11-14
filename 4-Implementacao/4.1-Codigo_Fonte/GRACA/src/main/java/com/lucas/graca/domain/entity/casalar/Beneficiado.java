/**
 * 
 */
package com.lucas.graca.domain.entity.casalar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.crianca.Crianca;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */

@Entity
@Audited
@DataTransferObject(javascript = "Beneficiado")
public class Beneficiado extends AbstractEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5657560779374010110L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Crianca crianca; 
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private RequisicaoCompra requisicaoCompra;

	//TODO produto adquirido
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param crianca
	 * @param requisicaoCompra
	 */
	public Beneficiado( Long id, Crianca crianca, RequisicaoCompra requisicaoCompra )
	{
		super(id);
		this.crianca = crianca;
		this.requisicaoCompra = requisicaoCompra;
	}

	/**
	 * 
	 */
	public Beneficiado()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Beneficiado( Long id )
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
		result = prime * result + ( ( crianca == null ) ? 0 : crianca.hashCode() );
		result = prime * result + ( ( requisicaoCompra == null ) ? 0 : requisicaoCompra.hashCode() );
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
		Beneficiado other = ( Beneficiado ) obj;
		if ( crianca == null )
		{
			if ( other.crianca != null ) return false;
		}
		else if ( !crianca.equals( other.crianca ) ) return false;
		if ( requisicaoCompra == null )
		{
			if ( other.requisicaoCompra != null ) return false;
		}
		else if ( !requisicaoCompra.equals( other.requisicaoCompra ) ) return false;
		return true;
	}

	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the crianca
	 */
	public Crianca getCrianca()
	{
		return crianca;
	}

	/**
	 * @param crianca the crianca to set
	 */
	public void setCrianca( Crianca crianca )
	{
		this.crianca = crianca;
	}

	/**
	 * @return the requisicaoCompra
	 */
	public RequisicaoCompra getRequisicaoCompra()
	{
		return requisicaoCompra;
	}

	/**
	 * @param requisicaoCompra the requisicaoCompra to set
	 */
	public void setRequisicaoCompra( RequisicaoCompra requisicaoCompra )
	{
		this.requisicaoCompra = requisicaoCompra;
	} 
	
	
	
	
}
