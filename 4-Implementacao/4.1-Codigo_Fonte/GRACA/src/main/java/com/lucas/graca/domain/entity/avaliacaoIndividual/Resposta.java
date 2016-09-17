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

import com.lucas.graca.domain.entity.questionario.Questao;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "Resposta")
public class Resposta extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3516840853281829902L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	private String resposta;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne( fetch=FetchType.EAGER )
	private Questao questao;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne( fetch=FetchType.EAGER )
	private AvaliacaoIndividual avaliacao;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCOTRS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param resposta
	 * @param questao
	 * @param avaliacao
	 */
	public Resposta( Long id, String resposta, Questao questao, AvaliacaoIndividual avaliacao )
	{
		super( id );
		this.resposta = resposta;
		this.questao = questao;
		this.avaliacao = avaliacao;
	}

	/**
	 * 
	 */
	public Resposta()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Resposta( Long id )
	{
		super( id );
	}
	
	public void mergeToUpdate( Resposta resposta )
	{
		this.resposta = resposta.getResposta();
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
		result = prime * result + ( ( resposta == null ) ? 0 : resposta.hashCode() );
		result = prime * result + ( ( avaliacao == null ) ? 0 : avaliacao.hashCode() );
		result = prime * result + ( ( questao == null ) ? 0 : questao.hashCode() );
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
		Resposta other = ( Resposta ) obj;
		if ( resposta == null )
		{
			if ( other.resposta != null ) return false;
		}
		else if ( !resposta.equals( other.resposta ) ) return false;
		if ( avaliacao == null )
		{
			if ( other.avaliacao != null ) return false;
		}
		else if ( !avaliacao.equals( other.avaliacao ) ) return false;
		if ( questao == null )
		{
			if ( other.questao != null ) return false;
		}
		else if ( !questao.equals( other.questao ) ) return false;
		return true;
	}

	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the resposta
	 */
	public String getResposta()
	{
		return resposta;
	}

	/**
	 * @param resposta the resposta to set
	 */
	public void setResposta( String resposta )
	{
		this.resposta = resposta;
	}

	/**
	 * @return the questao
	 */
	public Questao getQuestao()
	{
		return questao;
	}

	/**
	 * @param questao the questao to set
	 */
	public void setQuestao( Questao questao )
	{
		this.questao = questao;
	}

	/**
	 * @return the avaliacao
	 */
	public AvaliacaoIndividual getAvaliacao()
	{
		return avaliacao;
	}

	/**
	 * @param avaliacao the avaliacao to set
	 */
	public void setAvaliacao( AvaliacaoIndividual avaliacao )
	{
		this.avaliacao = avaliacao;
	}
}
