/**
 * 
 */
package com.lucas.graca.domain.entity.casalar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */

@Entity
@Audited
@DataTransferObject(javascript = "RequisicaoCompra")
public class RequisicaoCompra extends AbstractEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1973090478238093893L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private String descricao;
		
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private StatusRequisicaoCompra status;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	private CasaLar casaLar;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param descricao
	 * @param valorDisponibilizado
	 * @param status
	 */
	public RequisicaoCompra( Long id, String descricao, StatusRequisicaoCompra status, CasaLar casaLar )
	{
		super(id);
		this.descricao = descricao;
		this.status = status;
		this.casaLar = casaLar;
	}

	/**
	 * 
	 */
	public RequisicaoCompra()
	{
		super();
	}

	/**
	 * @param id
	 */
	public RequisicaoCompra( Long id )
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
		result = prime * result + ( ( casaLar == null ) ? 0 : casaLar.hashCode() );
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
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
		RequisicaoCompra other = ( RequisicaoCompra ) obj;
		if ( casaLar == null )
		{
			if ( other.casaLar != null ) return false;
		}
		else if ( !casaLar.equals( other.casaLar ) ) return false;
		if ( descricao == null )
		{
			if ( other.descricao != null ) return false;
		}
		else if ( !descricao.equals( other.descricao ) ) return false;
		if ( status != other.status ) return false;
		return true;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void changeToRascunho()
	{
		this.status = StatusRequisicaoCompra.RASCUNHO;
	}
	
	/**
	 * 
	 */
	public void changeToEmAberto()
	{
		Assert.isTrue( this.status == StatusRequisicaoCompra.RASCUNHO, "Status precisa ser rascunho" );
		
		this.status = StatusRequisicaoCompra.EM_ABERTO;
	}
	
	/**
	 * 
	 */
	public void changeToConcluido()
	{
		Assert.isTrue( this.status == StatusRequisicaoCompra.EM_ABERTO, "Status precisa ser em aberto" );
		
		this.status = StatusRequisicaoCompra.CONCLUIDA;
	}
	
	/**
	 * 
	 */
	public void changeToRecusado()
	{
		Assert.isTrue( this.status == StatusRequisicaoCompra.RASCUNHO, "Status precisa ser em aberto" );
		
		this.status = StatusRequisicaoCompra.RECUSADO;
	}
	/*-------------------------------------------------------------------
	 *				 		GETTERS AND SETTERS
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
	 * @return the status
	 */
	public StatusRequisicaoCompra getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( StatusRequisicaoCompra status )
	{
		this.status = status;
	}

	/**
	 * @return the casaLar
	 */
	public CasaLar getCasaLar()
	{
		return casaLar;
	}

	/**
	 * @param casaLar the casaLar to set
	 */
	public void setCasaLar( CasaLar casaLar )
	{
		this.casaLar = casaLar;
	}
}
