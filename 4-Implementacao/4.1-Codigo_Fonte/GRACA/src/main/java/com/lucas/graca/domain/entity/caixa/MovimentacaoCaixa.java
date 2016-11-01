/**
 * 
 */
package com.lucas.graca.domain.entity.caixa;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.ibm.icu.math.BigDecimal;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author eits
 *
 */

@Entity
@Audited
@DataTransferObject(javascript = "MovimentacaoCaixa")
public class MovimentacaoCaixa extends AbstractEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 565806327684766250L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Basic
	private BigDecimal valor;
	
	/**
	 * 
	 */
	@ManyToOne( fetch=FetchType.EAGER, optional=false )
	private ContaBancaria contaBancaria;
	
	/**
	 * 
	 */
	@ManyToOne( fetch=FetchType.EAGER, optional=false )
	private Movimentacao movimentacao;

	/**
	 * @param valor
	 * @param contaBancaria
	 * @param movimentacao
	 */
	public MovimentacaoCaixa( Long id, BigDecimal valor, ContaBancaria contaBancaria, Movimentacao movimentacao )
	{
		super(id);
		this.valor = valor;
		this.contaBancaria = contaBancaria;
		this.movimentacao = movimentacao;
	}

	/**
	 * 
	 */
	public MovimentacaoCaixa()
	{
		super();
	}

	/*-------------------------------------------------------------------
	 *				 		   BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param id
	 */
	public MovimentacaoCaixa( Long id )
	{
		super( id );
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( contaBancaria == null ) ? 0 : contaBancaria.hashCode() );
		result = prime * result + ( ( movimentacao == null ) ? 0 : movimentacao.hashCode() );
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
		MovimentacaoCaixa other = ( MovimentacaoCaixa ) obj;
		if ( contaBancaria == null )
		{
			if ( other.contaBancaria != null ) return false;
		}
		else if ( !contaBancaria.equals( other.contaBancaria ) ) return false;
		if ( movimentacao == null )
		{
			if ( other.movimentacao != null ) return false;
		}
		else if ( !movimentacao.equals( other.movimentacao ) ) return false;
		if ( valor == null )
		{
			if ( other.valor != null ) return false;
		}
		else if ( !valor.equals( other.valor ) ) return false;
		return true;
	}

	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
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
	 * @return the contaBancaria
	 */
	public ContaBancaria getContaBancaria()
	{
		return contaBancaria;
	}

	/**
	 * @param contaBancaria the contaBancaria to set
	 */
	public void setContaBancaria( ContaBancaria contaBancaria )
	{
		this.contaBancaria = contaBancaria;
	}

	/**
	 * @return the movimentacao
	 */
	public Movimentacao getMovimentacao()
	{
		return movimentacao;
	}

	/**
	 * @param movimentacao the movimentacao to set
	 */
	public void setMovimentacao( Movimentacao movimentacao )
	{
		this.movimentacao = movimentacao;
	}
	
}
