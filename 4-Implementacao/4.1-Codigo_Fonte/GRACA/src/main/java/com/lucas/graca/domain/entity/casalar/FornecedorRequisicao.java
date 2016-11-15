/**
 * 
 */
package com.lucas.graca.domain.entity.casalar;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.aquisicaoCompra.AquisicaoProduto;
import com.lucas.graca.domain.entity.fornecedor.Fornecedor;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */

@Entity
@Audited
@DataTransferObject(javascript = "FornecedorRequisicao")
public class FornecedorRequisicao extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2527134768993704438L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private RequisicaoCompra requisicaoCompra;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Fornecedor fornecedor;

	/**
	 * 
	 */
	@NotNull
	@Column
	private BigDecimal valorDisponibilizado;
	
	/**
	 * 
	 */
	@Basic
	private AquisicaoProduto aquisicaoProduto;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param casaLar
	 * @param fornecedor
	 */
	public FornecedorRequisicao( Long id, RequisicaoCompra requisicaoCompra, Fornecedor fornecedor, BigDecimal valorDisponibilizado,
			AquisicaoProduto aquisicaoProduto)
	{
		super(id);
		this.requisicaoCompra = requisicaoCompra;
		this.fornecedor = fornecedor;
		this.valorDisponibilizado = valorDisponibilizado;
		this.aquisicaoProduto = aquisicaoProduto;
	}

	/**
	 * 
	 */
	public FornecedorRequisicao()
	{
		super();
	}

	/**
	 * @param id
	 */
	public FornecedorRequisicao( Long id )
	{
		super( id );
	}

	/*-------------------------------------------------------------------
	 *				 		 GETTERS AND SETTERS
	
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
	 * @return the valorDisponibilizado
	 */
	public BigDecimal getValorDisponibilizado()
	{
		return valorDisponibilizado;
	}

	/**
	 * @param valorDisponibilizado the valorDisponibilizado to set
	 */
	public void setValorDisponibilizado( BigDecimal valorDisponibilizado )
	{
		this.valorDisponibilizado = valorDisponibilizado;
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