/**
 * 
 */
package com.lucas.graca.domain.entity.avaliacaoIndividual;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.questionario.Questionario;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "ConfiguracaoAvaliacaoIndividual")
public class ConfiguracaoAvaliacaoIndividual extends AbstractEntity implements Serializable
{

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8744093429353375294L;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne( fetch=FetchType.EAGER )
	private Questionario questionario;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param questionario
	 * @param avaliacaoIndividual
	 */
	public ConfiguracaoAvaliacaoIndividual( Long id, Questionario questionario )
	{
		super(id);
		this.questionario = questionario;
	}

	/**
	 * 
	 */
	public ConfiguracaoAvaliacaoIndividual()
	{
		super();
	}

	/**
	 * @param id
	 */
	public ConfiguracaoAvaliacaoIndividual( Long id )
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
		result = prime * result + ( ( questionario == null ) ? 0 : questionario.hashCode() );
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
		ConfiguracaoAvaliacaoIndividual other = ( ConfiguracaoAvaliacaoIndividual ) obj;
		if ( questionario == null )
		{
			if ( other.questionario != null ) return false;
		}
		else if ( !questionario.equals( other.questionario ) ) return false;
		return true;
	}

	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the questionario
	 */
	public Questionario getQuestionario()
	{
		return questionario;
	}

	/**
	 * @param questionario the questionario to set
	 */
	public void setQuestionario( Questionario questionario )
	{
		this.questionario = questionario;
	}
	
	
}
