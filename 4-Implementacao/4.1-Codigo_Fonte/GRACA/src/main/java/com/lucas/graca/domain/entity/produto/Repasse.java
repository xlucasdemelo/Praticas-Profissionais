package com.lucas.graca.domain.entity.produto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.casalar.CasaLar;

import br.com.eits.common.domain.entity.AbstractEntity;

@Entity
@Audited
@DataTransferObject(javascript = "Repasse")
public class Repasse extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4564943501782730047L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Integer quantidade; 
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private StatusRepasse status;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Produto produto;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private CasaLar casaLar;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	public Repasse(Long id, Integer quantidade, StatusRepasse status, Produto produto, CasaLar casaLar) 
	{
		super(id);
		this.quantidade = quantidade;
		this.status = status;
		this.produto = produto;
		this.casaLar = casaLar;
	}

	public Repasse() 
	{
		super();
	}

	public Repasse(Long id) 
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
		result = prime * result + ((casaLar == null) ? 0 : casaLar.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Repasse other = (Repasse) obj;
		if (casaLar == null) {
			if (other.casaLar != null)
				return false;
		} else if (!casaLar.equals(other.casaLar))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	/**
	 * 
	 */
	@PrePersist
	private void changeToRascunho()
	{
		this.status = StatusRepasse.RASCUNHO;
	}
	
	/**
	 * 
	 */
	public void changeToAprovado()
	{
		Assert.isTrue(this.status == StatusRepasse.RASCUNHO, "Status deve ser rascunho");
		this.status = StatusRepasse.APROVADO;
	}
	
	/**
	 * 
	 */
	public void changeToRecusado()
	{
		Assert.isTrue(this.status == StatusRepasse.RASCUNHO, "Status deve ser rascunho");
		this.status = StatusRepasse.RECUSADO;
	}

	/*-------------------------------------------------------------------
	 *				 		  GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the quantidade
	 */
	public Integer getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the status
	 */
	public StatusRepasse getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusRepasse status) {
		this.status = status;
	}

	/**
	 * @return the produto
	 */
	public Produto getProduto() {
		return produto;
	}

	/**
	 * @param produto the produto to set
	 */
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	/**
	 * @return the casaLar
	 */
	public CasaLar getCasaLar() {
		return casaLar;
	}

	/**
	 * @param casaLar the casaLar to set
	 */
	public void setCasaLar(CasaLar casaLar) {
		this.casaLar = casaLar;
	}
	
	
}
