/**
 * 
 */
package com.lucas.graca.domain.entity.aquisicaoCompra;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.produto.Produto;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */

@Entity
@Audited
@DataTransferObject(javascript = "ProdutoAdquirido")
public class ProdutoAdquirido extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2868545140943393943L; 

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private BigDecimal valor;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private Integer quantidade;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Produto produto;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private AquisicaoProduto aquisicaoProduto;
	
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	

	/**
	 * @param valor
	 * @param quantidade
	 * @param produto
	 * @param aquisicaoProduto
	 */
	public ProdutoAdquirido( Long id, BigDecimal valor, Integer quantidade, Produto produto, AquisicaoProduto aquisicaoProduto )
	{
		super(id);
		this.valor = valor;
		this.quantidade = quantidade;
		this.produto = produto;
		this.aquisicaoProduto = aquisicaoProduto;
	}

	/**
	 * 
	 */
	public ProdutoAdquirido()
	{
		super();
	}

	/**
	 * @param id
	 */
	public ProdutoAdquirido( Long id )
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
		result = prime * result + ( ( aquisicaoProduto == null ) ? 0 : aquisicaoProduto.hashCode() );
		result = prime * result + ( ( produto == null ) ? 0 : produto.hashCode() );
		result = prime * result + ( ( quantidade == null ) ? 0 : quantidade.hashCode() );
		result = prime * result + ( ( valor == null ) ? 0 : valor.hashCode() );
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
		ProdutoAdquirido other = ( ProdutoAdquirido ) obj;
		if ( aquisicaoProduto == null )
		{
			if ( other.aquisicaoProduto != null ) return false;
		}
		else if ( !aquisicaoProduto.equals( other.aquisicaoProduto ) ) return false;
		if ( produto == null )
		{
			if ( other.produto != null ) return false;
		}
		else if ( !produto.equals( other.produto ) ) return false;
		if ( quantidade == null )
		{
			if ( other.quantidade != null ) return false;
		}
		else if ( !quantidade.equals( other.quantidade ) ) return false;
		if ( valor == null )
		{
			if ( other.valor != null ) return false;
		}
		else if ( !valor.equals( other.valor ) ) return false;
		return true;
	}

	/*-------------------------------------------------------------------
	 *				 		  GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the valor
	 */
	public BigDecimal getValor()
	{
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor( BigDecimal valor )
	{
		this.valor = valor;
	}

	/**
	 * @return the produto
	 */
	public Produto getProduto()
	{
		return produto;
	}

	/**
	 * @param produto the produto to set
	 */
	public void setProduto( Produto produto )
	{
		this.produto = produto;
	}

	/**
	 * @return the quantidade
	 */
	public Integer getQuantidade()
	{
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade( Integer quantidade )
	{
		this.quantidade = quantidade;
	}

	/**
	 * @return the aquisicaoProduto
	 */
	public AquisicaoProduto getAquisicaoProduto()
	{
		return aquisicaoProduto;
	}

	/**
	 * @param aquisicaoProduto the aquisicaoProduto to set
	 */
	public void setAquisicaoProduto( AquisicaoProduto aquisicaoProduto )
	{
		this.aquisicaoProduto = aquisicaoProduto;
	}
	
}
