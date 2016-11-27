package com.lucas.graca.domain.entity.produto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;

@Entity
@Audited
@DataTransferObject(javascript = "ProdutoRepassado")
public class ProdutoRepassado extends AbstractEntity implements Serializable
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
	@ManyToOne(fetch=FetchType.EAGER)
	private Produto produto;
	
	/**
	 * 
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private Repasse repasse;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param id
	 * @param quantidade
	 * @param produto
	 * @param repasse
	 */
	public ProdutoRepassado(Long id, Integer quantidade, Produto produto, Repasse repasse) 
	{
		super(id);
		this.quantidade = quantidade;
		this.produto = produto;
		this.repasse = repasse;
	}

	/**
	 * 
	 */
	public ProdutoRepassado() 
	{
		super();
	}

	/**
	 * 
	 * @param id
	 */
	public ProdutoRepassado(Long id) 
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
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((repasse == null) ? 0 : repasse.hashCode());
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
		ProdutoRepassado other = (ProdutoRepassado) obj;
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
		if (repasse == null) {
			if (other.repasse != null)
				return false;
		} else if (!repasse.equals(other.repasse))
			return false;
		return true;
	}

	/*-------------------------------------------------------------------
	 *				 		 GETTERS AND SETTERS
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
	 * @return the repasse
	 */
	public Repasse getRepasse() {
		return repasse;
	}

	/**
	 * @param repasse the repasse to set
	 */
	public void setRepasse(Repasse repasse) {
		this.repasse = repasse;
	}

		
}
