/**
 * 
 */
package com.lucas.graca.domain.entity.endereco;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@DataTransferObject(javascript = "Estado")
public class Estado extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6167860630481352397L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	private String nome;

	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pais_id")
	private Pais pais;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param nome
	 * @param pais
	 */
	public Estado( Long id, String nome, Pais pais )
	{
		super(id);
		this.nome = nome;
		this.pais = pais;
	}

	/**
	 * 
	 */
	public Estado()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Estado( Long id )
	{
		super( id );
	}

	/*-------------------------------------------------------------------
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
	 * @return the pais
	 */
	public Pais getPais()
	{
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais( Pais pais )
	{
		this.pais = pais;
	} 
	
	
}
