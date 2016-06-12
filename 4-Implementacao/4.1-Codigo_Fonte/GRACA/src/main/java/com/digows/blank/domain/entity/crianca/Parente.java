/**
 * 
 */
package com.digows.blank.domain.entity.crianca;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.digows.blank.domain.entity.integrantefamiliar.IntegranteFamiliar;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
public class Parente extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3967244488258402003L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "crianca_id")
	private Crianca crianca;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "integrante_familiar_id")
	private IntegranteFamiliar integranteFamiliar;
	
	/**
	 * 
	 */
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private GrauParentesco grauParentesco;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param crianca
	 * @param integranteFamiliar
	 * @param grauParentesco
	 */
	public Parente(Long id, Crianca crianca, IntegranteFamiliar integranteFamiliar, GrauParentesco grauParentesco )
	{
		super(id);
		this.crianca = crianca;
		this.integranteFamiliar = integranteFamiliar;
		this.grauParentesco = grauParentesco;
	}

	/**
	 * 
	 */
	public Parente()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Parente( Long id )
	{
		super( id );
	}

	/*-------------------------------------------------------------------
	 *				 		  GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the crianca
	 */
	public Crianca getCrianca()
	{
		return crianca;
	}

	/**
	 * @param crianca the crianca to set
	 */
	public void setCrianca( Crianca crianca )
	{
		this.crianca = crianca;
	}

	/**
	 * @return the integranteFamiliar
	 */
	public IntegranteFamiliar getIntegranteFamiliar()
	{
		return integranteFamiliar;
	}

	/**
	 * @param integranteFamiliar the integranteFamiliar to set
	 */
	public void setIntegranteFamiliar( IntegranteFamiliar integranteFamiliar )
	{
		this.integranteFamiliar = integranteFamiliar;
	}

	/**
	 * @return the grauParentesco
	 */
	public GrauParentesco getGrauParentesco()
	{
		return grauParentesco;
	}

	/**
	 * @param grauParentesco the grauParentesco to set
	 */
	public void setGrauParentesco( GrauParentesco grauParentesco )
	{
		this.grauParentesco = grauParentesco;
	}
}
