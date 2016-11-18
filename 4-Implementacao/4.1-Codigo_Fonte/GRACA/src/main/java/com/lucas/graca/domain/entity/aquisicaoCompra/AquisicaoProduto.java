/**
 * 
 */
package com.lucas.graca.domain.entity.aquisicaoCompra;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.fornecedor.Fornecedor;

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
	private CondicaoPagamento condicaoPagamento;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private FormaPagamento formaPagamento;
	
	/**
	 * 
	 */
	@Basic
	private Integer vezesPagamento;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Fornecedor fornecedor;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param status
	 * @param condicaoPagamento
	 * @param formaPagamento
	 * @param vezesPagamento
	 */
	public AquisicaoProduto( Long id, StatusAquisicao status, CondicaoPagamento condicaoPagamento, FormaPagamento formaPagamento, 
			Integer vezesPagamento, Fornecedor fornecedor )
	{
		super(id);
		this.status = status;
		this.condicaoPagamento = condicaoPagamento;
		this.formaPagamento = formaPagamento;
		this.vezesPagamento = vezesPagamento;
		this.fornecedor = fornecedor;
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
		result = prime * result + ( ( condicaoPagamento == null ) ? 0 : condicaoPagamento.hashCode() );
		result = prime * result + ( ( formaPagamento == null ) ? 0 : formaPagamento.hashCode() );
		result = prime * result + ( ( fornecedor == null ) ? 0 : fornecedor.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
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
		if ( condicaoPagamento != other.condicaoPagamento ) return false;
		if ( formaPagamento != other.formaPagamento ) return false;
		if ( fornecedor == null )
		{
			if ( other.fornecedor != null ) return false;
		}
		else if ( !fornecedor.equals( other.fornecedor ) ) return false;
		if ( status != other.status ) return false;
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
	 * @return the condicaoPagamento
	 */
	public CondicaoPagamento getTipoPagamento()
	{
		return condicaoPagamento;
	}

	/**
	 * @param condicaoPagamento the condicaoPagamento to set
	 */
	public void setTipoPagamento( CondicaoPagamento condicaoPagamento )
	{
		this.condicaoPagamento = condicaoPagamento;
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
	 * @return the condicaoPagamento
	 */
	public CondicaoPagamento getCondicaoPagamento()
	{
		return condicaoPagamento;
	}

	/**
	 * @param condicaoPagamento the condicaoPagamento to set
	 */
	public void setCondicaoPagamento( CondicaoPagamento condicaoPagamento )
	{
		this.condicaoPagamento = condicaoPagamento;
	}

	/**
	 * @return the formaPagamento
	 */
	public FormaPagamento getFormaPagamento()
	{
		return formaPagamento;
	}

	/**
	 * @param formaPagamento the formaPagamento to set
	 */
	public void setFormaPagamento( FormaPagamento formaPagamento )
	{
		this.formaPagamento = formaPagamento;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	/**
	 * @return the fornecedor
	 */
	public Fornecedor getFornecedor()
	{
		return fornecedor;
	}

	/**
	 * @param fornecedor the fornecedor to set
	 */
	public void setFornecedor( Fornecedor fornecedor )
	{
		this.fornecedor = fornecedor;
	}

}
