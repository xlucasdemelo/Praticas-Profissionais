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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.casalar.Beneficiado;
import com.lucas.graca.domain.entity.produto.Produto;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */

@Entity
@Audited
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"produto_id", "fornecedor_aquisicao_id"}))
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
	private FornecedorAquisicao fornecedorAquisicao;

	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Beneficiado beneficiado;
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param valor
	 * @param produto
	 * @param fornecedorAquisicao
	 */
	public ProdutoAdquirido( Long id, BigDecimal valor, Produto produto, FornecedorAquisicao fornecedorAquisicao, 
			Beneficiado beneficiado, Integer quantidade )
	{
		super(id);
		this.valor = valor;
		this.produto = produto;
		this.fornecedorAquisicao = fornecedorAquisicao;
		this.beneficiado = beneficiado;
		this.quantidade = quantidade;
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
		result = prime * result + ( ( beneficiado == null ) ? 0 : beneficiado.hashCode() );
		result = prime * result + ( ( fornecedorAquisicao == null ) ? 0 : fornecedorAquisicao.hashCode() );
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
		if ( beneficiado == null )
		{
			if ( other.beneficiado != null ) return false;
		}
		else if ( !beneficiado.equals( other.beneficiado ) ) return false;
		if ( fornecedorAquisicao == null )
		{
			if ( other.fornecedorAquisicao != null ) return false;
		}
		else if ( !fornecedorAquisicao.equals( other.fornecedorAquisicao ) ) return false;
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
	 * @return the fornecedorAquisicao
	 */
	public FornecedorAquisicao getFornecedorAquisicao()
	{
		return fornecedorAquisicao;
	}

	/**
	 * @param fornecedorAquisicao the fornecedorAquisicao to set
	 */
	public void setFornecedorAquisicao( FornecedorAquisicao fornecedorAquisicao )
	{
		this.fornecedorAquisicao = fornecedorAquisicao;
	}

	/**
	 * @return the beneficiado
	 */
	public Beneficiado getBeneficiado()
	{
		return beneficiado;
	}

	/**
	 * @param beneficiado the beneficiado to set
	 */
	public void setBeneficiado( Beneficiado beneficiado )
	{
		this.beneficiado = beneficiado;
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
	
}
