/**
 * 
 */
package com.digows.blank.domain.entity.endereco;

import java.io.Serializable;

import javax.persistence.Entity;
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
@DataTransferObject(javascript = "Pais")
public class Pais extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2996668483500800475L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	private String nome;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param nome
	 */
	public Pais( Long id, String nome )
	{
		super(id);
		this.nome = nome;
	}

	/**
	 * 
	 */
	public Pais()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Pais( Long id )
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
	
	
}
