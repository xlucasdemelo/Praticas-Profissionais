/**
 * 
 */
package com.lucas.graca.domain.entity.avaliacaoIndividual;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.crianca.Crianca;
import com.lucas.graca.domain.entity.questionario.Questionario;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "AvaliacaoIndividual")
public class AvaliacaoIndividual extends AbstractEntity implements Serializable
{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6783348711817492990L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	private StatusAvaliacaoIndividual status;
	
	/**
	 * 
	 */
	@NotNull
	private Boolean enabled;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne( fetch=FetchType.EAGER )
	private Questionario questionario;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne( fetch=FetchType.EAGER )
	private Crianca crianca;
	
	/**
	 * 
	 */
	@NotNull
	@ManyToOne( fetch=FetchType.EAGER )
	private User avaliador;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param status
	 * @param questionario
	 * @param crianca
	 * @param avaliador
	 */
	public AvaliacaoIndividual( Long id, StatusAvaliacaoIndividual status, Questionario questionario, Crianca crianca, User avaliador, Boolean enabled )
	{
		super( id );
		this.status = status;
		this.questionario = questionario;
		this.crianca = crianca;
		this.avaliador = avaliador;
		this.enabled = enabled;
	}

	/**
	 * 
	 */
	public AvaliacaoIndividual()
	{
		super();
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
		result = prime * result + ( ( avaliador == null ) ? 0 : avaliador.hashCode() );
		result = prime * result + ( ( crianca == null ) ? 0 : crianca.hashCode() );
		result = prime * result + ( ( enabled == null ) ? 0 : enabled.hashCode() );
		result = prime * result + ( ( questionario == null ) ? 0 : questionario.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
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
		AvaliacaoIndividual other = ( AvaliacaoIndividual ) obj;
		if ( avaliador == null )
		{
			if ( other.avaliador != null ) return false;
		}
		else if ( !avaliador.equals( other.avaliador ) ) return false;
		if ( crianca == null )
		{
			if ( other.crianca != null ) return false;
		}
		else if ( !crianca.equals( other.crianca ) ) return false;
		if ( enabled == null )
		{
			if ( other.enabled != null ) return false;
		}
		else if ( !enabled.equals( other.enabled ) ) return false;
		if ( questionario == null )
		{
			if ( other.questionario != null ) return false;
		}
		else if ( !questionario.equals( other.questionario ) ) return false;
		if ( status != other.status ) return false;
		return true;
	}

	/**
	 * 
	 */
	@PrePersist
	public void initToPersist()
	{
		this.enableAvaliacao();
		this.changeToRascunho();
	}
	
	/**
	 * 
	 */
	public void enableAvaliacao()
	{
		this.enabled = true;
	}
	
	/**
	 * 
	 */
	public void disableAvaliacao()
	{
		this.enabled = false;
	}
	
	/**
	 * 
	 */
	public void changeToRascunho()
	{
		this.status = StatusAvaliacaoIndividual.RASCUNHO;
	}
	
	/**
	 * 
	 */
	public void changeToEnviadoParaAvaliacao()
	{
		Assert.isTrue( this.status == StatusAvaliacaoIndividual.RASCUNHO || this.status == StatusAvaliacaoIndividual.REJEITADO, "Status inválido" );
		
		this.status = StatusAvaliacaoIndividual.ENVIADO_PARA_AVALIACAO;
	}
	
	/**
	 * 
	 */
	public void changeToAceito()
	{
		Assert.isTrue( this.status == StatusAvaliacaoIndividual.ENVIADO_PARA_AVALIACAO, "Status inválido" );
		
		this.status = StatusAvaliacaoIndividual.ACEITO;
	}
	
	/**
	 * 
	 */
	public void changeToRejeitado()
	{
		Assert.isTrue( this.status == StatusAvaliacaoIndividual.ENVIADO_PARA_AVALIACAO, "Status inválido" );
		
		this.status = StatusAvaliacaoIndividual.REJEITADO;
	}
	
	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the status
	 */
	public StatusAvaliacaoIndividual getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( StatusAvaliacaoIndividual status )
	{
		this.status = status;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled()
	{
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled( Boolean enabled )
	{
		this.enabled = enabled;
	}

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
	 * @return the avaliador
	 */
	public User getAvaliador()
	{
		return avaliador;
	}

	/**
	 * @param avaliador the avaliador to set
	 */
	public void setAvaliador( User avaliador )
	{
		this.avaliador = avaliador;
	}
}
