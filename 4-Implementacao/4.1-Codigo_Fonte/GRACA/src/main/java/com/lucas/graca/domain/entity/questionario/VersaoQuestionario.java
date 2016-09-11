/**
 * 
 */
package com.lucas.graca.domain.entity.questionario;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.account.User;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "VersaoQuestionario")
public class VersaoQuestionario extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 726509871919784184L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	private Integer numeroVersao;
	
	/**
	 * 
	 */
	@Basic
	private Calendar dataAvaliacao;
	
	/**
	 * 
	 */
	@ManyToOne( optional = true, fetch=FetchType.EAGER )
	private User usuarioAvaliador;
	
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
	private StatusVersaoQuestionario status;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param numeroVersao
	 * @param dataAvaliacao
	 * @param usuarioAvaliador
	 * @param questionario
	 * @param status
	 */
	public VersaoQuestionario( Long id, Integer numeroVersao, Calendar dataAvaliacao, User usuarioAvaliador, Questionario questionario, StatusVersaoQuestionario status )
	{
		super(id);
		this.numeroVersao = numeroVersao;
		this.dataAvaliacao = dataAvaliacao;
		this.usuarioAvaliador = usuarioAvaliador;
		this.questionario = questionario;
		this.status = status;
	}

	/**
	 * 
	 */
	public VersaoQuestionario()
	{
		super();
	}

	/**
	 * @param id
	 */
	public VersaoQuestionario( Long id )
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
		result = prime * result + ( ( dataAvaliacao == null ) ? 0 : dataAvaliacao.hashCode() );
		result = prime * result + ( ( numeroVersao == null ) ? 0 : numeroVersao.hashCode() );
		result = prime * result + ( ( questionario == null ) ? 0 : questionario.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
		result = prime * result + ( ( usuarioAvaliador == null ) ? 0 : usuarioAvaliador.hashCode() );
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
		VersaoQuestionario other = ( VersaoQuestionario ) obj;
		if ( dataAvaliacao == null )
		{
			if ( other.dataAvaliacao != null ) return false;
		}
		else if ( !dataAvaliacao.equals( other.dataAvaliacao ) ) return false;
		if ( numeroVersao == null )
		{
			if ( other.numeroVersao != null ) return false;
		}
		else if ( !numeroVersao.equals( other.numeroVersao ) ) return false;
		if ( questionario == null )
		{
			if ( other.questionario != null ) return false;
		}
		else if ( !questionario.equals( other.questionario ) ) return false;
		if ( status != other.status ) return false;
		if ( usuarioAvaliador == null )
		{
			if ( other.usuarioAvaliador != null ) return false;
		}
		else if ( !usuarioAvaliador.equals( other.usuarioAvaliador ) ) return false;
		return true;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void changeToRascunho()
	{
		this.status = StatusVersaoQuestionario.RASCUNHO;
	}
	
	/**
	 * 
	 */
	public void changeToAguardandoAprovacao()
	{
		this.status = StatusVersaoQuestionario.AGUARDANDO_APROVACAO;
	}
	
	/**
	 * 
	 */
	public void changeToAprovado()
	{
		this.status = StatusVersaoQuestionario.APROVADO;
	}
	
	/**
	 * 
	 */
	public void changeToRejeitado()
	{
		this.status = StatusVersaoQuestionario.REJEITADO;
	}
	
	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the numeroVersao
	 */
	public Integer getNumeroVersao()
	{
		return numeroVersao;
	}

	/**
	 * @param numeroVersao the numeroVersao to set
	 */
	public void setNumeroVersao( Integer numeroVersao )
	{
		this.numeroVersao = numeroVersao;
	}

	/**
	 * @return the dataAvaliacao
	 */
	public Calendar getDataAvaliacao()
	{
		return dataAvaliacao;
	}

	/**
	 * @param dataAvaliacao the dataAvaliacao to set
	 */
	public void setDataAvaliacao( Calendar dataAvaliacao )
	{
		this.dataAvaliacao = dataAvaliacao;
	}

	/**
	 * @return the usuarioAvaliador
	 */
	public User getUsuarioAvaliador()
	{
		return usuarioAvaliador;
	}

	/**
	 * @param usuarioAvaliador the usuarioAvaliador to set
	 */
	public void setUsuarioAvaliador( User usuarioAvaliador )
	{
		this.usuarioAvaliador = usuarioAvaliador;
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
	 * @return the status
	 */
	public StatusVersaoQuestionario getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( StatusVersaoQuestionario status )
	{
		this.status = status;
	}
}
