/**
 * 
 */
package com.lucas.graca.domain.entity.questionario;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "Questao")
public class Questao extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8042529762982523785L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	private String descricao;
	
	/**
	 * 
	 */
	@NotNull
	private TipoQuestao tipoQuestao;
	
	/**
	 * 
	 */
	@ManyToOne( fetch=FetchType.EAGER )
	private VersaoQuestionario versaoQuestionario;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param descricao
	 * @param tipoQuestao
	 * @param versaoQuestionario
	 */
	public Questao( Long id, String descricao, TipoQuestao tipoQuestao, VersaoQuestionario versaoQuestionario )
	{
		super(id);
		this.descricao = descricao;
		this.tipoQuestao = tipoQuestao;
		this.versaoQuestionario = versaoQuestionario;
	}

	/**
	 * 
	 */
	public Questao()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Questao( Long id )
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
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( tipoQuestao == null ) ? 0 : tipoQuestao.hashCode() );
		result = prime * result + ( ( versaoQuestionario == null ) ? 0 : versaoQuestionario.hashCode() );
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
		Questao other = ( Questao ) obj;
		if ( descricao == null )
		{
			if ( other.descricao != null ) return false;
		}
		else if ( !descricao.equals( other.descricao ) ) return false;
		if ( tipoQuestao != other.tipoQuestao ) return false;
		if ( versaoQuestionario == null )
		{
			if ( other.versaoQuestionario != null ) return false;
		}
		else if ( !versaoQuestionario.equals( other.versaoQuestionario ) ) return false;
		return true;
	}
	
	/**
	 * 
	 */
	public void isRascunho()
	{
		Assert.isTrue( this.getVersaoQuestionario().getStatus() == StatusVersaoQuestionario.RASCUNHO, "O questionário precisa estar em rascunho" );
	}
	
	/**
	 * 
	 */
	public Questao clone()
	{
		Questao novaQuestao = new Questao();
		
		novaQuestao.setDescricao( this.descricao ) ;
		novaQuestao.setTipoQuestao( this.tipoQuestao );
		
		return novaQuestao;
	}
	
	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the descricao
	 */
	public String getDescricao()
	{
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao( String descricao )
	{
		this.descricao = descricao;
	}

	/**
	 * @return the tipoQuestao
	 */
	public TipoQuestao getTipoQuestao()
	{
		return tipoQuestao;
	}

	/**
	 * @param tipoQuestao the tipoQuestao to set
	 */
	public void setTipoQuestao( TipoQuestao tipoQuestao )
	{
		this.tipoQuestao = tipoQuestao;
	}

	/**
	 * @return the versaoQuestionario
	 */
	public VersaoQuestionario getVersaoQuestionario()
	{
		return versaoQuestionario;
	}

	/**
	 * @param versaoQuestionario the versaoQuestionario to set
	 */
	public void setVersaoQuestionario( VersaoQuestionario versaoQuestionario )
	{
		this.versaoQuestionario = versaoQuestionario;
	}
	
	
}
