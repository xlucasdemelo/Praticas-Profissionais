/**
 * 
 */
package com.lucas.graca.domain.entity.caixa;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author eits
 *
 */

@Entity
@Audited
@DataTransferObject(javascript = "Movimentacao")
public class Movimentacao extends AbstractEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6373630682825041548L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Basic
	private String numeroDocumento;
	
	/**
	 * 
	 */
	@Basic
	private Calendar dataEmissao;
	
	/**
	 * 
	 */
	@Basic
	private Calendar dataPagamento;
	
	/**
	 * 
	 */
	@Basic
	private String numeroCheque;
	
	/**
	 * 
	 */
	@Basic
	private StatusMovimentacao status;
	
	/**
	 * 
	 */
	@Basic
	private FormaPagamento formaPagamento;
	
	/**
	 * 
	 */
	@Basic
	private TipoDocumento tipoDocumento;
	
	/**
	 * 
	 */
	@Basic
	private TipoMovimentacao tipoMovimentacao;
	
	/**
	 * 
	 */
	@ManyToOne( fetch=FetchType.EAGER, optional=false )
	private NaturezaGastos naturezaGastos;
	
	//TODO Verificar maneira de fazer o favorecido
	
	/*-------------------------------------------------------------------
	 *				 		   CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param numeroDocumento
	 * @param dataEmissao
	 * @param dataPagamento
	 * @param numeroCheque
	 * @param status
	 * @param formaPagamento
	 * @param tipoDocumento
	 * @param tipoMovimentacao
	 * @param naturezaGastos
	 */
	public Movimentacao( Long id, String numeroDocumento, Calendar dataEmissao, Calendar dataPagamento, 
			String numeroCheque, StatusMovimentacao status, FormaPagamento formaPagamento, TipoDocumento tipoDocumento, 
			TipoMovimentacao tipoMovimentacao, NaturezaGastos naturezaGastos )
	{
		super(id);
		this.numeroDocumento = numeroDocumento;
		this.dataEmissao = dataEmissao;
		this.dataPagamento = dataPagamento;
		this.numeroCheque = numeroCheque;
		this.status = status;
		this.formaPagamento = formaPagamento;
		this.tipoDocumento = tipoDocumento;
		this.tipoMovimentacao = tipoMovimentacao;
		this.naturezaGastos = naturezaGastos;
	}

	/**
	 * 
	 */
	public Movimentacao()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Movimentacao( Long id )
	{
		super( id );
	}
	
	/*-------------------------------------------------------------------
	 *				 		   BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( dataEmissao == null ) ? 0 : dataEmissao.hashCode() );
		result = prime * result + ( ( dataPagamento == null ) ? 0 : dataPagamento.hashCode() );
		result = prime * result + ( ( formaPagamento == null ) ? 0 : formaPagamento.hashCode() );
		result = prime * result + ( ( naturezaGastos == null ) ? 0 : naturezaGastos.hashCode() );
		result = prime * result + ( ( numeroCheque == null ) ? 0 : numeroCheque.hashCode() );
		result = prime * result + ( ( numeroDocumento == null ) ? 0 : numeroDocumento.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
		result = prime * result + ( ( tipoDocumento == null ) ? 0 : tipoDocumento.hashCode() );
		result = prime * result + ( ( tipoMovimentacao == null ) ? 0 : tipoMovimentacao.hashCode() );
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
		Movimentacao other = ( Movimentacao ) obj;
		if ( dataEmissao == null )
		{
			if ( other.dataEmissao != null ) return false;
		}
		else if ( !dataEmissao.equals( other.dataEmissao ) ) return false;
		if ( dataPagamento == null )
		{
			if ( other.dataPagamento != null ) return false;
		}
		else if ( !dataPagamento.equals( other.dataPagamento ) ) return false;
		if ( formaPagamento != other.formaPagamento ) return false;
		if ( naturezaGastos == null )
		{
			if ( other.naturezaGastos != null ) return false;
		}
		else if ( !naturezaGastos.equals( other.naturezaGastos ) ) return false;
		if ( numeroCheque == null )
		{
			if ( other.numeroCheque != null ) return false;
		}
		else if ( !numeroCheque.equals( other.numeroCheque ) ) return false;
		if ( numeroDocumento == null )
		{
			if ( other.numeroDocumento != null ) return false;
		}
		else if ( !numeroDocumento.equals( other.numeroDocumento ) ) return false;
		if ( status != other.status ) return false;
		if ( tipoDocumento != other.tipoDocumento ) return false;
		if ( tipoMovimentacao != other.tipoMovimentacao ) return false;
		return true;
	}
	
	/**
	 * 
	 */
	public void validateFormaPagamento()
	{
		if (this.formaPagamento == FormaPagamento.CHEQUE)
		{
			Assert.notNull( this.numeroCheque, "Número do cheque deve ser informado" );
		}
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void changeToRascunho()
	{
		this.status = StatusMovimentacao.RASCUNHO;
	}
	
	/**
	 * 
	 */
	public void changeToAprovado()
	{
		Assert.isTrue( this.status == StatusMovimentacao.RASCUNHO, "Status precisa ser Rascunho" );
		
		this.status = StatusMovimentacao.APROVADO;
	}
	
	/**
	 * 
	 */
	public void changeToRecusado()
	{
		Assert.isTrue( this.status == StatusMovimentacao.RASCUNHO, "Status precisa ser Rascunho" );
		
		this.status = StatusMovimentacao.RECUSADO;
	}
	
	
	
	/*-------------------------------------------------------------------
	 *				 		   GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento()
	{
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento( String numeroDocumento )
	{
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * @return the dataEmissao
	 */
	public Calendar getDataEmissao()
	{
		return dataEmissao;
	}

	/**
	 * @param dataEmissao the dataEmissao to set
	 */
	public void setDataEmissao( Calendar dataEmissao )
	{
		this.dataEmissao = dataEmissao;
	}

	/**
	 * @return the dataPagamento
	 */
	public Calendar getDataPagamento()
	{
		return dataPagamento;
	}

	/**
	 * @param dataPagamento the dataPagamento to set
	 */
	public void setDataPagamento( Calendar dataPagamento )
	{
		this.dataPagamento = dataPagamento;
	}

	/**
	 * @return the numeroCheque
	 */
	public String getNumeroCheque()
	{
		return numeroCheque;
	}

	/**
	 * @param numeroCheque the numeroCheque to set
	 */
	public void setNumeroCheque( String numeroCheque )
	{
		this.numeroCheque = numeroCheque;
	}

	/**
	 * @return the status
	 */
	public StatusMovimentacao getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( StatusMovimentacao status )
	{
		this.status = status;
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
	 * @return the tipoDocumento
	 */
	public TipoDocumento getTipoDocumento()
	{
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento( TipoDocumento tipoDocumento )
	{
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the tipoMovimentacao
	 */
	public TipoMovimentacao getTipoMovimentacao()
	{
		return tipoMovimentacao;
	}

	/**
	 * @param tipoMovimentacao the tipoMovimentacao to set
	 */
	public void setTipoMovimentacao( TipoMovimentacao tipoMovimentacao )
	{
		this.tipoMovimentacao = tipoMovimentacao;
	}

	/**
	 * @return the naturezaGastos
	 */
	public NaturezaGastos getNaturezaGastos()
	{
		return naturezaGastos;
	}

	/**
	 * @param naturezaGastos the naturezaGastos to set
	 */
	public void setNaturezaGastos( NaturezaGastos naturezaGastos )
	{
		this.naturezaGastos = naturezaGastos;
	}
	
}
