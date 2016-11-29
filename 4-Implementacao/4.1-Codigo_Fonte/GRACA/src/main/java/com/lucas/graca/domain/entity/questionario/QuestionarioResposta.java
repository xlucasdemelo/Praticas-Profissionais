package com.lucas.graca.domain.entity.questionario;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.account.User;

import br.com.eits.common.domain.entity.AbstractEntity;

@Entity
@Audited
@DataTransferObject(javascript = "QuestionarioResposta")
public class QuestionarioResposta extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7716734764440455474L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Basic
	private Calendar dataFinalizacao;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable=false)
	private StatusQuestionarioResposta status;
	
	/**
	 * 
	 */
	@ManyToOne( fetch=FetchType.EAGER, optional= true )
	private VersaoQuestionario versao;
	
	/**
	 * 
	 */
	@ManyToOne( fetch=FetchType.EAGER, optional= true )
	private User usuario;

	/*-------------------------------------------------------------------
	 *				 		  CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param dataFinalizacao
	 * @param status
	 * @param versao
	 * @param usuario
	 */
	public QuestionarioResposta(Long id, Calendar dataFinalizacao, StatusQuestionarioResposta status, VersaoQuestionario versao,
			User usuario) {
		super(id);
		this.dataFinalizacao = dataFinalizacao;
		this.status = status;
		this.versao = versao;
		this.usuario = usuario;
	}

	/**
	 * 
	 */
	public QuestionarioResposta() {
		super();
	}

	/**
	 * @param id
	 */
	public QuestionarioResposta(Long id) {
		super(id);
	}
	
	/*-------------------------------------------------------------------
	 *				 		  BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dataFinalizacao == null) ? 0 : dataFinalizacao.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + ((versao == null) ? 0 : versao.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionarioResposta other = (QuestionarioResposta) obj;
		if (dataFinalizacao == null) {
			if (other.dataFinalizacao != null)
				return false;
		} else if (!dataFinalizacao.equals(other.dataFinalizacao))
			return false;
		if (status != other.status)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (versao == null) {
			if (other.versao != null)
				return false;
		} else if (!versao.equals(other.versao))
			return false;
		return true;
	}

	/**
	 * 
	 */
	@PrePersist
	private void changeToRascunho()
	{
		this.status = StatusQuestionarioResposta.RASCUNHO;
	}
	
	/**
	 * 
	 */
	public void changeToFinalizado()
	{
		this.status = StatusQuestionarioResposta.FINALIZADO;
	}
	
	/*-------------------------------------------------------------------
	 *				 	GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the versao
	 */
	public VersaoQuestionario getVersao() {
		return versao;
	}

	/**
	 * @param versao the versao to set
	 */
	public void setVersao(VersaoQuestionario versao) {
		this.versao = versao;
	}

	/**
	 * @return the usuario
	 */
	public User getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the dataFinalizacao
	 */
	public Calendar getDataFinalizacao() {
		return dataFinalizacao;
	}

	/**
	 * @param dataFinalizacao the dataFinalizacao to set
	 */
	public void setDataFinalizacao(Calendar dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	/**
	 * @return the status
	 */
	public StatusQuestionarioResposta getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusQuestionarioResposta status) {
		this.status = status;
	}
	
	
}