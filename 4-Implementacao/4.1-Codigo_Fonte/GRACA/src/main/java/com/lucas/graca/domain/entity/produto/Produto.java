/**
 * 
 */
package com.lucas.graca.domain.entity.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author Lucas
 *
 */

@Entity
@Audited
@Table( name = "produto")
@DataTransferObject(javascript = "Produto")
public class Produto extends AbstractEntity 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4099791925955815624L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private String nome;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean ativo;
	
	/**
	 * 
	 */
	@ManyToOne( optional = false, fetch = FetchType.EAGER )
	private Categoria categoria;
	
	/**
	 * 
	 */
	@ManyToOne( optional = false, fetch = FetchType.EAGER )
	private Marca marca;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param nome
	 * @param ativo
	 * @param categoria
	 * @param marca
	 */
	public Produto( Long id, String nome, Boolean ativo, Categoria categoria, Marca marca )
	{
		super(id);
		this.nome = nome;
		this.ativo = ativo;
		this.categoria = categoria;
		this.marca = marca;
	}

	/**
	 * 
	 */
	public Produto()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Produto( Long id )
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
		result = prime * result + ( ( ativo == null ) ? 0 : ativo.hashCode() );
		result = prime * result + ( ( categoria == null ) ? 0 : categoria.hashCode() );
		result = prime * result + ( ( marca == null ) ? 0 : marca.hashCode() );
		result = prime * result + ( ( nome == null ) ? 0 : nome.hashCode() );
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
		Produto other = ( Produto ) obj;
		if ( ativo == null )
		{
			if ( other.ativo != null ) return false;
		}
		else if ( !ativo.equals( other.ativo ) ) return false;
		if ( categoria == null )
		{
			if ( other.categoria != null ) return false;
		}
		else if ( !categoria.equals( other.categoria ) ) return false;
		if ( marca == null )
		{
			if ( other.marca != null ) return false;
		}
		else if ( !marca.equals( other.marca ) ) return false;
		if ( nome == null )
		{
			if ( other.nome != null ) return false;
		}
		else if ( !nome.equals( other.nome ) ) return false;
		return true;
	}
	
	/**
	 * 
	 */
	public void disableProduto()
	{
		this.ativo = false;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void enableFornecedoproduto()
	{
		this.ativo = true;
	}
	
	/*------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	 
	/**
	 * @return the nome
	 */
	public String getNome()
	{
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome( String nome )
	{
		this.nome = nome;
	}

	/**
	 * @return the ativo
	 */
	public Boolean getAtivo()
	{
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo( Boolean ativo )
	{
		this.ativo = ativo;
	}

	/**
	 * @return the categoria
	 */
	public Categoria getCategoria()
	{
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria( Categoria categoria )
	{
		this.categoria = categoria;
	}

	/**
	 * @return the marca
	 */
	public Marca getMarca()
	{
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca( Marca marca )
	{
		this.marca = marca;
	}
}
