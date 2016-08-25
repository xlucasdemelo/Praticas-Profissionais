/**
 * 
 */
package com.lucas.graca.domain.entity.planoatendimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Check;
import org.hibernate.envers.Audited;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.integrantefamiliar.IntegranteFamiliar;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "Encaminhamento")
@Check(constraints = "status <> 2 OR observacao IS NOT NULL")
public class Encaminhamento extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1013823663772216989L;
	
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
	private String observacao;
	
	/**
	 * 
	 */
	@NotNull
	private StatusEncaminhamento status;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="plano_atendimento_id")
	private PlanoAtendimento planoAtendimento;
	
	/**
	 * 
	 */
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User usuario;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="integrante_familiar_id")
	private IntegranteFamiliar integranteFamiliar;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param numero
	 * @param descricao
	 * @param observacao
	 * @param status
	 * @param planoAtendimento
	 * @param usuario
	 * @param integranteFamiliar
	 */
	public Encaminhamento( Long id, String descricao, String observacao, StatusEncaminhamento status, PlanoAtendimento planoAtendimento, User usuario, IntegranteFamiliar integranteFamiliar )
	{
		super(id);
		this.descricao = descricao;
		this.observacao = observacao;
		this.status = status;
		this.planoAtendimento = planoAtendimento;
		this.usuario = usuario;
		this.integranteFamiliar = integranteFamiliar;
	}

	/**
	 * 
	 */
	public Encaminhamento()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Encaminhamento( Long id )
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
		result = prime * result + ( ( integranteFamiliar == null ) ? 0 : integranteFamiliar.hashCode() );
		result = prime * result + ( ( observacao == null ) ? 0 : observacao.hashCode() );
		result = prime * result + ( ( planoAtendimento == null ) ? 0 : planoAtendimento.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
		result = prime * result + ( ( usuario == null ) ? 0 : usuario.hashCode() );
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
		Encaminhamento other = ( Encaminhamento ) obj;
		if ( descricao == null )
		{
			if ( other.descricao != null ) return false;
		}
		else if ( !descricao.equals( other.descricao ) ) return false;
		if ( integranteFamiliar == null )
		{
			if ( other.integranteFamiliar != null ) return false;
		}
		else if ( !integranteFamiliar.equals( other.integranteFamiliar ) ) return false;
		if ( observacao == null )
		{
			if ( other.observacao != null ) return false;
		}
		else if ( !observacao.equals( other.observacao ) ) return false;
		if ( planoAtendimento == null )
		{
			if ( other.planoAtendimento != null ) return false;
		}
		else if ( !planoAtendimento.equals( other.planoAtendimento ) ) return false;
		if ( status != other.status ) return false;
		if ( usuario == null )
		{
			if ( other.usuario != null ) return false;
		}
		else if ( !usuario.equals( other.usuario ) ) return false;
		return true;
	}
	
	/**
	 * 
	 */
	public void changeToEmExecucao()
	{
		this.status = StatusEncaminhamento.EM_EXECUCAO; 
	}
	
	/**
	 * 
	 */
	public void changeToCancelado()
	{
		Assert.isTrue( SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals( this.usuario )
				|| SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains( UserRole.ADMINISTRATOR ) ,
				"Somente o usuário criador ou o adminisntrador podem cancelar um encaminhamento" );
		
		Assert.isTrue( this.status != StatusEncaminhamento.CONCLUIDO, "Não é permitido cancelar um encaminhamento concluido" );
		
		this.status = StatusEncaminhamento.CANCELADO;
	}
	
	/**
	 * 
	 */
	public void changeToConcluido()
	{
		Assert.isTrue( SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals( this.usuario )
				|| SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains( UserRole.ADMINISTRATOR ) ,
				"Somente o usuário criador ou o adminisntrador podem cancelar um encaminhamento" );
		Assert.isTrue( this.status == StatusEncaminhamento.EM_EXECUCAO, "Para concluir um encaminhamento ele deve estar em execução" );
		
		this.status = StatusEncaminhamento.CONCLUIDO;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void initilizeToPersist()
	{
		this.changeToEmExecucao();
		this.setUsuario();
	}
	
	/**
	 * 
	 * @param encaminhamento
	 */
	public void mergeToUpdate( Encaminhamento encaminhamento )
	{
		this.descricao = encaminhamento.getDescricao();
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
	 * @return the observacao
	 */
	public String getobservacao()
	{
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setobservacao( String observacao )
	{
		this.observacao = observacao;
	}

	/**
	 * @return the status
	 */
	public StatusEncaminhamento getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( StatusEncaminhamento status )
	{
		this.status = status;
	}

	/**
	 * @return the planoAtendimento
	 */
	public PlanoAtendimento getPlanoAtendimento()
	{
		return planoAtendimento;
	}

	/**
	 * @param planoAtendimento the planoAtendimento to set
	 */
	public void setPlanoAtendimento( PlanoAtendimento planoAtendimento )
	{
		this.planoAtendimento = planoAtendimento;
	}

	/**
	 * @return the usuario
	 */
	public User getUsuario()
	{
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario()
	{
		this.usuario = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
}
