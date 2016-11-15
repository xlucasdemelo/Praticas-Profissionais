/**
 * 
 */
package com.lucas.graca.domain.entity.aquisicaoCompra;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.fornecedor.Fornecedor;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */

@Entity
@Audited
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"fornecedor_id", "aquisicao_produto_id"}))
@DataTransferObject(javascript = "FornecedorAquisicao")
public class FornecedorAquisicao extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5962704325441977993L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Fornecedor fornecedor;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private AquisicaoProduto aquisicaoProduto;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param fornecedor
	 * @param aquisicaoProduto
	 */
	public FornecedorAquisicao( Long id, Fornecedor fornecedor, AquisicaoProduto aquisicaoProduto )
	{
		super(id);
		this.fornecedor = fornecedor;
		this.aquisicaoProduto = aquisicaoProduto;
	}

	/**
	 * 
	 */
	public FornecedorAquisicao()
	{
		super();
	}

	/**
	 * @param id
	 */
	public FornecedorAquisicao( Long id )
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
		result = prime * result + ( ( fornecedor == null ) ? 0 : fornecedor.hashCode() );
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
		FornecedorAquisicao other = ( FornecedorAquisicao ) obj;
		if ( aquisicaoProduto == null )
		{
			if ( other.aquisicaoProduto != null ) return false;
		}
		else if ( !aquisicaoProduto.equals( other.aquisicaoProduto ) ) return false;
		if ( fornecedor == null )
		{
			if ( other.fornecedor != null ) return false;
		}
		else if ( !fornecedor.equals( other.fornecedor ) ) return false;
		return true;
	}

	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
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






