package com.lucas.graca.domain.entity.questionario;

/**
 * 
 */
import java.io.Serializable;

import javax.persistence.Basic;
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
	@Basic
	private String respostaTexto;
	
	/**
	 * 
	 */
	@Basic
	private Boolean respostaBoolean;
	
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
	private QuestionarioResposta questionarioResposta;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param respostaTexto
	 * @param respostaBoolean
	 * @param questao
	 * @param questionarioResposta
	 */
	public Resposta(Long id, String respostaTexto, Boolean respostaBoolean, Questao questao,
			QuestionarioResposta questionarioResposta) {
		super(id);
		this.respostaTexto = respostaTexto;
		this.respostaBoolean = respostaBoolean;
		this.questao = questao;
		this.questionarioResposta = questionarioResposta;
	}

	/**
	 * 
	 */
	public Resposta() {
		super();
	}

	/**
	 * @param id
	 */
	public Resposta(Long id) {
		super(id);
	}

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((questao == null) ? 0 : questao.hashCode());
		result = prime * result + ((questionarioResposta == null) ? 0 : questionarioResposta.hashCode());
		result = prime * result + ((respostaBoolean == null) ? 0 : respostaBoolean.hashCode());
		result = prime * result + ((respostaTexto == null) ? 0 : respostaTexto.hashCode());
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
		Resposta other = (Resposta) obj;
		if (questao == null) {
			if (other.questao != null)
				return false;
		} else if (!questao.equals(other.questao))
			return false;
		if (questionarioResposta == null) {
			if (other.questionarioResposta != null)
				return false;
		} else if (!questionarioResposta.equals(other.questionarioResposta))
			return false;
		if (respostaBoolean == null) {
			if (other.respostaBoolean != null)
				return false;
		} else if (!respostaBoolean.equals(other.respostaBoolean))
			return false;
		if (respostaTexto == null) {
			if (other.respostaTexto != null)
				return false;
		} else if (!respostaTexto.equals(other.respostaTexto))
			return false;
		return true;
	}

	/*-------------------------------------------------------------------
	 *				 		 GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the respostaTexto
	 */
	public String getRespostaTexto() {
		return respostaTexto;
	}

	/**
	 * @param respostaTexto the respostaTexto to set
	 */
	public void setRespostaTexto(String respostaTexto) {
		this.respostaTexto = respostaTexto;
	}

	/**
	 * @return the respostaBoolean
	 */
	public Boolean getRespostaBoolean() {
		return respostaBoolean;
	}

	/**
	 * @param respostaBoolean the respostaBoolean to set
	 */
	public void setRespostaBoolean(Boolean respostaBoolean) {
		this.respostaBoolean = respostaBoolean;
	}

	/**
	 * @return the questao
	 */
	public Questao getQuestao() {
		return questao;
	}

	/**
	 * @param questao the questao to set
	 */
	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	/**
	 * @return the questionarioResposta
	 */
	public QuestionarioResposta getQuestionarioResposta() {
		return questionarioResposta;
	}

	/**
	 * @param questionarioResposta the questionarioResposta to set
	 */
	public void setQuestionarioResposta(QuestionarioResposta questionarioResposta) {
		this.questionarioResposta = questionarioResposta;
	}
	
	
	
	
}
