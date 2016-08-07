/**
 * 
 */
package com.lucas.graca.domain.entity.entidadeAcolhimento;

import java.io.Serializable;

import javax.persistence.Column;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
public class EntidadeAcolhimento extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7945222487046924653L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Column
	private String nome;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param nome
	 */
	public EntidadeAcolhimento( Long id, String nome )
	{
		super(id);
		this.nome = nome;
	}

	/**
	 * 
	 */
	public EntidadeAcolhimento()
	{
		super();
	}

	/**
	 * @param id
	 */
	public EntidadeAcolhimento( Long id )
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
