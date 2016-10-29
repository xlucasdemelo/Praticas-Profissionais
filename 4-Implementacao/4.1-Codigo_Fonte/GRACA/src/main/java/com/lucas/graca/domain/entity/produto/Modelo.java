/**
 * 
 */
package com.lucas.graca.domain.entity.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */

@Entity
@Audited
@Table( name = "modelo")
@DataTransferObject(javascript = "Marca")
public class Modelo extends AbstractEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2355343986853995373L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false, unique = true)
	private String nome;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private Marca marca;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param nome
	 * @param marca
	 */
	public Modelo( Long id, String nome, Marca marca )
	{
		super(id);
		this.nome = nome;
		this.marca = marca;
	}

	/**
	 * 
	 */
	public Modelo()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Modelo( Long id )
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
		Modelo other = ( Modelo ) obj;
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
	
	/*-------------------------------------------------------------------
	 *				 		  GETTERS AND SETTERS
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







