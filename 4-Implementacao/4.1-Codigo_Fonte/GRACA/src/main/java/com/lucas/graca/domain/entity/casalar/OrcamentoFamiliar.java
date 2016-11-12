/**
 * 
 */
package com.lucas.graca.domain.entity.casalar;

import java.math.BigDecimal;
import java.time.YearMonth;

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

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */

@Entity
@Audited
@DataTransferObject(javascript = "OrcamentoFamiliar")
public class OrcamentoFamiliar extends AbstractEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3381959095294894316L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Basic
	private YearMonth periodo;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private BigDecimal rendaPerCapitaAlimentacao;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private BigDecimal rendaPerCapitaHigiene;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private StatusOrcamentoFamiliar status;

	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER, optional = false)
	private CasaLar casaLar;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param mesPeriodo
	 * @param anoPeriodo
	 * @param rendaPerCapitaAlimentacao
	 * @param rendaPerCapitaHigiene
	 * @param enabled
	 * @param status
	 */
	public OrcamentoFamiliar( Long id, YearMonth periodo, BigDecimal rendaPerCapitaAlimentacao, 
			BigDecimal rendaPerCapitaHigiene, StatusOrcamentoFamiliar status, CasaLar casaLar )
	{
		super(id);
		this.periodo = periodo;
		this.rendaPerCapitaAlimentacao = rendaPerCapitaAlimentacao;
		this.rendaPerCapitaHigiene = rendaPerCapitaHigiene;
		this.status = status;
		this.casaLar = casaLar;
	}

	/**
	 * 
	 */
	public OrcamentoFamiliar()
	{
		super();
	}

	/**
	 * @param id
	 */
	public OrcamentoFamiliar( Long id )
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
		result = prime * result + ( ( periodo == null ) ? 0 : periodo.hashCode() );
		result = prime * result + ( ( rendaPerCapitaAlimentacao == null ) ? 0 : rendaPerCapitaAlimentacao.hashCode() );
		result = prime * result + ( ( rendaPerCapitaHigiene == null ) ? 0 : rendaPerCapitaHigiene.hashCode() );
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
		OrcamentoFamiliar other = ( OrcamentoFamiliar ) obj;
		if ( casaLar == null )
		{
			if ( other.casaLar != null ) return false;
		}
		else if ( !casaLar.equals( other.casaLar ) ) return false;
		if ( periodo == null )
		{
			if ( other.periodo != null ) return false;
		}
		else if ( !periodo.equals( other.periodo ) ) return false;
		if ( rendaPerCapitaAlimentacao == null )
		{
			if ( other.rendaPerCapitaAlimentacao != null ) return false;
		}
		else if ( !rendaPerCapitaAlimentacao.equals( other.rendaPerCapitaAlimentacao ) ) return false;
		if ( rendaPerCapitaHigiene == null )
		{
			if ( other.rendaPerCapitaHigiene != null ) return false;
		}
		else if ( !rendaPerCapitaHigiene.equals( other.rendaPerCapitaHigiene ) ) return false;
		if ( status != other.status ) return false;
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public Boolean isRascunho()
	{
		return this.status == StatusOrcamentoFamiliar.RASCUNHO;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void changeToRascunho()
	{
		this.status = StatusOrcamentoFamiliar.RASCUNHO;
	}
	
	/**
	 * 
	 */
	public void changeToExpirado()
	{
		this.status = StatusOrcamentoFamiliar.EXPIRADO;
	}
	
	/**
	 * 
	 */
	public void changeToVigente()
	{
		Assert.isTrue( this.status == StatusOrcamentoFamiliar.RASCUNHO, "O status deve ser rascunho " );
		
		Assert.isTrue( this.periodo.equals( YearMonth.now()), "Apenas o orçamento do mês atual pode se tornar vigente" );
		
		this.status = StatusOrcamentoFamiliar.VIGENTE;
	}
	
	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	

	/**
	 * @return the rendaPerCapitaAlimentacao
	 */
	public BigDecimal getRendaPerCapitaAlimentacao()
	{
		return rendaPerCapitaAlimentacao;
	}

	/**
	 * @param rendaPerCapitaAlimentacao the rendaPerCapitaAlimentacao to set
	 */
	public void setRendaPerCapitaAlimentacao( BigDecimal rendaPerCapitaAlimentacao )
	{
		this.rendaPerCapitaAlimentacao = rendaPerCapitaAlimentacao;
	}

	/**
	 * @return the rendaPerCapitaHigiene
	 */
	public BigDecimal getRendaPerCapitaHigiene()
	{
		return rendaPerCapitaHigiene;
	}

	/**
	 * @param rendaPerCapitaHigiene the rendaPerCapitaHigiene to set
	 */
	public void setRendaPerCapitaHigiene( BigDecimal rendaPerCapitaHigiene )
	{
		this.rendaPerCapitaHigiene = rendaPerCapitaHigiene;
	}

	/**
	 * @return the status
	 */
	public StatusOrcamentoFamiliar getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( StatusOrcamentoFamiliar status )
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

	/**
	 * @return the periodo
	 */
	public YearMonth getPeriodo()
	{
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo( YearMonth periodo )
	{
		this.periodo = periodo;
	}
	
	
}











