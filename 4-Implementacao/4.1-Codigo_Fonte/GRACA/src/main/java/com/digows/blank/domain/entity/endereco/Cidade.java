/**
 * 
 */
package com.digows.blank.domain.entity.endereco;

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
@DataTransferObject(javascript = "Cidade")
public class Cidade extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5730904302408952774L;
	
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
	@JoinColumn(name = "estado_id")
	private Estado estado;
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param nome
	 */
	public Cidade( Long id, String nome, Estado estado )
	{
		super(id);
		this.nome = nome;
		this.estado = estado;
	}

	/**
	 * 
	 */
	public Cidade()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Cidade( Long id )
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
	 * @return the estado
	 */
	public Estado getEstado()
	{
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado( Estado estado )
	{
		this.estado = estado;
	}
}
