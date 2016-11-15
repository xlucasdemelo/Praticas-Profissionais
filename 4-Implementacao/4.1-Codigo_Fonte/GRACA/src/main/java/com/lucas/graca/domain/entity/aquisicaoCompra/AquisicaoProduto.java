/**
 * 
 */
package com.lucas.graca.domain.entity.aquisicaoCompra;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.casalar.RequisicaoCompra;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "AquisicaoProduto")
public class AquisicaoProduto extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8463963238305917405L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private StatusAquisicao status;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private TipoAquisicao tipoAquisicao;
	
	/**
	 * 
	 */
	private TipoPagamento tipoPagamento;
	
	/**
	 * 
	 */
	@Basic
	private Integer vezesPagamento;
	
	/**
	 * 
	 */
	@ManyToOne(optional=true)
	private RequisicaoCompra requisicaoCompra;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param status
	 * @param tipoAquisicao
	 * @param tipoPagamento
	 * @param vezesPagamento
	 * @param requisicaoCompra
	 * @param casaLar
	 */
	public AquisicaoProduto( Long id, StatusAquisicao status, TipoAquisicao tipoAquisicao, TipoPagamento tipoPagamento, Integer vezesPagamento, 
			RequisicaoCompra requisicaoCompra )
	{
		super(id);
		this.status = status;
		this.tipoAquisicao = tipoAquisicao;
		this.tipoPagamento = tipoPagamento;
		this.vezesPagamento = vezesPagamento;
		this.requisicaoCompra = requisicaoCompra;
	}

	/**
	 * 
	 */
	public AquisicaoProduto()
	{
		super();
	}

	
	/**
	 * @param id
	 */
	public AquisicaoProduto( Long id )
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
		result = prime * result + ( ( requisicaoCompra == null ) ? 0 : requisicaoCompra.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
		result = prime * result + ( ( tipoAquisicao == null ) ? 0 : tipoAquisicao.hashCode() );
		result = prime * result + ( ( tipoPagamento == null ) ? 0 : tipoPagamento.hashCode() );
		result = prime * result + ( ( vezesPagamento == null ) ? 0 : vezesPagamento.hashCode() );
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
		AquisicaoProduto other = ( AquisicaoProduto ) obj;
		if ( requisicaoCompra == null )
		{
			if ( other.requisicaoCompra != null ) return false;
		}
		else if ( !requisicaoCompra.equals( other.requisicaoCompra ) ) return false;
		if ( status != other.status ) return false;
		if ( tipoAquisicao != other.tipoAquisicao ) return false;
		if ( tipoPagamento != other.tipoPagamento ) return false;
		if ( vezesPagamento == null )
		{
			if ( other.vezesPagamento != null ) return false;
		}
		else if ( !vezesPagamento.equals( other.vezesPagamento ) ) return false;
		return true;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void changeToRascunho()
	{
		this.status = StatusAquisicao.RASCUNHO;
	}
	
	/**
	 * 
	 */
	public void changeToConcluido()
	{
		Assert.isTrue( this.status == StatusAquisicao.ABERTO, "Status precisa ser ABERTO" );
		this.status = StatusAquisicao.CONCLUIDO;
	}
	
	/**
	 * 
	 */
	public void changeToRecusado()
	{
		Assert.isTrue( this.status == StatusAquisicao.RASCUNHO, "Status precisa ser rascunho" );
		this.status = StatusAquisicao.RECUSADO;
	}

	public void changeToEmAberto()
	{
		Assert.isTrue( this.status == StatusAquisicao.RASCUNHO, "Status precisa ser rascunho" );
		this.status = StatusAquisicao.ABERTO;
	}
	
	/*-------------------------------------------------------------------
	 *				 		  GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the status
	 */
	public StatusAquisicao getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( StatusAquisicao status )
	{
		this.status = status;
	}

	/**
	 * @return the tipoAquisicao
	 */
	public TipoAquisicao getTipoAquisicao()
	{
		return tipoAquisicao;
	}

	/**
	 * @param tipoAquisicao the tipoAquisicao to set
	 */
	public void setTipoAquisicao( TipoAquisicao tipoAquisicao )
	{
		this.tipoAquisicao = tipoAquisicao;
	}

	/**
	 * @return the tipoPagamento
	 */
	public TipoPagamento getTipoPagamento()
	{
		return tipoPagamento;
	}

	/**
	 * @param tipoPagamento the tipoPagamento to set
	 */
	public void setTipoPagamento( TipoPagamento tipoPagamento )
	{
		this.tipoPagamento = tipoPagamento;
	}

	/**
	 * @return the vezesPagamento
	 */
	public Integer getVezesPagamento()
	{
		return vezesPagamento;
	}

	/**
	 * @param vezesPagamento the vezesPagamento to set
	 */
	public void setVezesPagamento( Integer vezesPagamento )
	{
		this.vezesPagamento = vezesPagamento;
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
