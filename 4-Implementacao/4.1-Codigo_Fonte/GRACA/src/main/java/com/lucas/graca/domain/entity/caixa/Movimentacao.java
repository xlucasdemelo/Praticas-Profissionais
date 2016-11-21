/**
 * 
 */
package com.lucas.graca.domain.entity.caixa;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

import com.lucas.graca.domain.entity.aquisicaoCompra.AquisicaoProduto;

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
	@NotNull
	@Column(nullable=false)
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
	private Calendar dataEfetivada;
	
	/**
	 * 
	 */
	@Basic
	private String descricao;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private BigDecimal valorEmissao;
	
	/**
	 * 
	 */
	@Basic
	private BigDecimal valorEfetivado;
	
	/**
	 * 
	 */
	@Basic
	private Float porcentagemDiferenca;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private StatusMovimentacao status;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private TipoMovimentacao tipoMovimentacao;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER, optional= true)
	private Conta contaDestino;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER, optional= true)
	private Conta contaOrigem;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER, optional= true)
	private AquisicaoProduto aquisicaoProduto;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER, optional= false)
	private NaturezaGastos naturezaGastos;
	
	/*-------------------------------------------------------------------
	 *				 		  CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param id
	 * @param dataEmissao
	 * @param dataPagamento
	 * @param dataEfetivada
	 * @param descricao
	 * @param valor
	 * @param porcentagemDiferenca
	 * @param status
	 * @param tipoMovimentacao
	 * @param contaDestino
	 * @param contaOrigem
	 * @param aquisicaoProduto
	 * @param naturezaGastos
	 */
	public Movimentacao( Long id, Calendar dataEmissao, Calendar dataPagamento, Calendar dataEfetivada, String descricao,
			BigDecimal valorEmissao, BigDecimal valorEfetivado, Float porcentagemDiferenca, StatusMovimentacao status, TipoMovimentacao tipoMovimentacao,
			Conta contaDestino, Conta contaOrigem, AquisicaoProduto aquisicaoProduto, NaturezaGastos naturezaGastos) 
	{
		super(id);
		this.dataEmissao = dataEmissao;
		this.dataPagamento = dataPagamento;
		this.dataEfetivada = dataEfetivada;
		this.descricao = descricao;
		this.valorEmissao = valorEmissao;
		this.valorEfetivado = valorEfetivado;
		this.porcentagemDiferenca = porcentagemDiferenca;
		this.status = status;
		this.tipoMovimentacao = tipoMovimentacao;
		this.contaDestino = contaDestino;
		this.contaOrigem = contaOrigem;
		this.aquisicaoProduto = aquisicaoProduto;
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
	 * 
	 * @param id
	 */
	public Movimentacao(Long id) 
	{
		super(id);
	}

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((aquisicaoProduto == null) ? 0 : aquisicaoProduto.hashCode());
		result = prime * result + ((contaDestino == null) ? 0 : contaDestino.hashCode());
		result = prime * result + ((contaOrigem == null) ? 0 : contaOrigem.hashCode());
		result = prime * result + ((dataEfetivada == null) ? 0 : dataEfetivada.hashCode());
		result = prime * result + ((dataEmissao == null) ? 0 : dataEmissao.hashCode());
		result = prime * result + ((dataPagamento == null) ? 0 : dataPagamento.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((naturezaGastos == null) ? 0 : naturezaGastos.hashCode());
		result = prime * result + ((porcentagemDiferenca == null) ? 0 : porcentagemDiferenca.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tipoMovimentacao == null) ? 0 : tipoMovimentacao.hashCode());
		result = prime * result + ((valorEfetivado == null) ? 0 : valorEfetivado.hashCode());
		result = prime * result + ((valorEmissao == null) ? 0 : valorEmissao.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movimentacao other = (Movimentacao) obj;
		if (aquisicaoProduto == null) {
			if (other.aquisicaoProduto != null)
				return false;
		} else if (!aquisicaoProduto.equals(other.aquisicaoProduto))
			return false;
		if (contaDestino == null) {
			if (other.contaDestino != null)
				return false;
		} else if (!contaDestino.equals(other.contaDestino))
			return false;
		if (contaOrigem == null) {
			if (other.contaOrigem != null)
				return false;
		} else if (!contaOrigem.equals(other.contaOrigem))
			return false;
		if (dataEfetivada == null) {
			if (other.dataEfetivada != null)
				return false;
		} else if (!dataEfetivada.equals(other.dataEfetivada))
			return false;
		if (dataEmissao == null) {
			if (other.dataEmissao != null)
				return false;
		} else if (!dataEmissao.equals(other.dataEmissao))
			return false;
		if (dataPagamento == null) {
			if (other.dataPagamento != null)
				return false;
		} else if (!dataPagamento.equals(other.dataPagamento))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (naturezaGastos == null) {
			if (other.naturezaGastos != null)
				return false;
		} else if (!naturezaGastos.equals(other.naturezaGastos))
			return false;
		if (porcentagemDiferenca == null) {
			if (other.porcentagemDiferenca != null)
				return false;
		} else if (!porcentagemDiferenca.equals(other.porcentagemDiferenca))
			return false;
		if (status != other.status)
			return false;
		if (tipoMovimentacao != other.tipoMovimentacao)
			return false;
		if (valorEfetivado == null) {
			if (other.valorEfetivado != null)
				return false;
		} else if (!valorEfetivado.equals(other.valorEfetivado))
			return false;
		if (valorEmissao == null) {
			if (other.valorEmissao != null)
				return false;
		} else if (!valorEmissao.equals(other.valorEmissao))
			return false;
		return true;
	}
	
	/**
	 * 
	 */
	@PrePersist
	private void changeToRascunho()
	{
		this.status = StatusMovimentacao.RASCUNHO;
	}
	
	/**
	 * 
	 */
	public void changeToEmAberto()
	{
		Assert.isTrue(this.status == StatusMovimentacao.RASCUNHO, "Status precisa ser rascunho");
		this.status = StatusMovimentacao.ABERTO;
	}
	
	/**
	 * 
	 */
	public void changeToRecusado()
	{
		Assert.isTrue(this.status == StatusMovimentacao.RASCUNHO, "Status precisa ser rascunho");
		this.status = StatusMovimentacao.RECUSADO;
	}
	
	/**
	 * 
	 */
	public void changeToConcluido()
	{
		Assert.isTrue(this.status == StatusMovimentacao.ABERTO, "Status precisa ser aberto");
		this.status = StatusMovimentacao.CONCLUIDO;
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getValorAjustado()
	{
		Date dataEfetivada = this.getDataEfetivada().getTime(); 
		Date dataEmissao= this.getDataPagamento().getTime();
		
		long diferencaDias = dataEfetivada.getTime() - dataEmissao.getTime();
		
		diferencaDias = TimeUnit.DAYS.convert(diferencaDias, TimeUnit.MILLISECONDS);
		
		BigDecimal reajuste = this.getValorEmissao().multiply( new BigDecimal( this.getPorcentagemDiferenca() / 100).setScale(2, BigDecimal.ROUND_HALF_EVEN) ).multiply( new BigDecimal(diferencaDias) );
		
		return this.valorEmissao.add(reajuste);
	}
	
	/*-------------------------------------------------------------------
	 *				 		 GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the dataEmissao
	 */
	public Calendar getDataEmissao() {
		return dataEmissao;
	}

	/**
	 * @param dataEmissao the dataEmissao to set
	 */
	public void setDataEmissao(Calendar dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	/**
	 * @return the dataPagamento
	 */
	public Calendar getDataPagamento() {
		return dataPagamento;
	}

	/**
	 * @param dataPagamento the dataPagamento to set
	 */
	public void setDataPagamento(Calendar dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	/**
	 * @return the dataEfetivada
	 */
	public Calendar getDataEfetivada() {
		return dataEfetivada;
	}

	/**
	 * @param dataEfetivada the dataEfetivada to set
	 */
	public void setDataEfetivada(Calendar dataEfetivada) {
		
		dataEfetivada.set(Calendar.HOUR_OF_DAY, 0);
		dataEfetivada.set(Calendar.MINUTE, 0);
		dataEfetivada.set(Calendar.SECOND, 0);
		dataEfetivada.set(Calendar.MILLISECOND, 0);
		
		this.dataEfetivada = dataEfetivada;
		
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the porcentagemDiferenca
	 */
	public Float getPorcentagemDiferenca() {
		return porcentagemDiferenca;
	}

	/**
	 * @param porcentagemDiferenca the porcentagemDiferenca to set
	 */
	public void setPorcentagemDiferenca(Float porcentagemDiferenca) {
		this.porcentagemDiferenca = porcentagemDiferenca;
	}

	/**
	 * @return the status
	 */
	public StatusMovimentacao getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusMovimentacao status) {
		this.status = status;
	}

	/**
	 * @return the tipoMovimentacao
	 */
	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	/**
	 * @param tipoMovimentacao the tipoMovimentacao to set
	 */
	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	/**
	 * @return the contaDestino
	 */
	public Conta getContaDestino() {
		return contaDestino;
	}

	/**
	 * @param contaDestino the contaDestino to set
	 */
	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}

	/**
	 * @return the contaOrigem
	 */
	public Conta getContaOrigem() {
		return contaOrigem;
	}

	/**
	 * @param contaOrigem the contaOrigem to set
	 */
	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	/**
	 * @return the aquisicaoProduto
	 */
	public AquisicaoProduto getAquisicaoProduto() {
		return aquisicaoProduto;
	}

	/**
	 * @param aquisicaoProduto the aquisicaoProduto to set
	 */
	public void setAquisicaoProduto(AquisicaoProduto aquisicaoProduto) {
		this.aquisicaoProduto = aquisicaoProduto;
	}

	/**
	 * @return the naturezaGastos
	 */
	public NaturezaGastos getNaturezaGastos() {
		return naturezaGastos;
	}

	/**
	 * @param naturezaGastos the naturezaGastos to set
	 */
	public void setNaturezaGastos(NaturezaGastos naturezaGastos) {
		this.naturezaGastos = naturezaGastos;
	}

	/**
	 * @return the valorEmissao
	 */
	public BigDecimal getValorEmissao() {
		return valorEmissao;
	}

	/**
	 * @param valorEmissao the valorEmissao to set
	 */
	public void setValorEmissao(BigDecimal valorEmissao) {
		this.valorEmissao = valorEmissao;
	}

	/**
	 * @return the valorEfetivado
	 */
	public BigDecimal getValorEfetivado() {
		return valorEfetivado;
	}

	/**
	 * @param valorEfetivado the valorEfetivado to set
	 */
	public void setValorEfetivado(BigDecimal valorEfetivado) {
		this.valorEfetivado = valorEfetivado;
	}
	 
	
}
