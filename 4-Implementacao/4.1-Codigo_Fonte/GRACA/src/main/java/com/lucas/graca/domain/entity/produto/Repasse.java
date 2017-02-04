package com.lucas.graca.domain.entity.produto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
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
	private StatusRepasse status;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private CasaLar casaLar;

	/**
	 * 
	 */
	@Transient
	private Integer quantidadeProdutos;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param id
	 * @param status
	 * @param casaLar
	 */
	public Repasse(Long id, StatusRepasse status, CasaLar casaLar) 
	{
		super(id);
		this.status = status;
		this.casaLar = casaLar;
	}

	/**
	 * 
	 */
	public Repasse() 
	{
		super();
	}

	/**
	 * @param id
	 */
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
	
	/*-------------------------------------------------------------------
	 *				 		  GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
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

	public Integer getQuantidadeProdutos() {
		return quantidadeProdutos;
	}

	public void setQuantidadeProdutos(Integer quantidadeProdutos) {
		this.quantidadeProdutos = quantidadeProdutos;
	}

}
