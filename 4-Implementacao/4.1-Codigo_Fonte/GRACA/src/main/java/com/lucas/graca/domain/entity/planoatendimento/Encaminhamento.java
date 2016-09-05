/**
 * 
 */
package com.lucas.graca.domain.entity.planoatendimento;

import java.io.Serializable;
import java.util.Calendar;

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
import com.lucas.graca.domain.entity.planoatendimentofamiliar.PlanoAtendimentoFamiliar;

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
	@NotNull
	private Calendar dataFinal;
	
	/**
	 * 
	 */
	@NotNull
	private TipoEncaminhamento tipo;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="plano_atendimento_id")
	private PlanoAtendimentoFamiliar planoAtendimentoFamiliar;
	
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
	
	/**
	 * 
	 */
	@ManyToOne(optional=true, fetch = FetchType.EAGER)
	private Responsavel responsavel;
	
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
	public Encaminhamento( Long id, String descricao, String observacao, StatusEncaminhamento status, PlanoAtendimentoFamiliar planoAtendimentoFamiliar, 
			User usuario, IntegranteFamiliar integranteFamiliar, Calendar dataFinal, Responsavel responsavel, TipoEncaminhamento tipo )
	{
		super(id);
		this.descricao = descricao;
		this.observacao = observacao;
		this.status = status;
		this.planoAtendimentoFamiliar = planoAtendimentoFamiliar;
		this.usuario = usuario;
		this.integranteFamiliar = integranteFamiliar;
		this.dataFinal = dataFinal;
		this.responsavel = responsavel;
		this.tipo = tipo;
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
		result = prime * result + ( ( dataFinal == null ) ? 0 : dataFinal.hashCode() );
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( integranteFamiliar == null ) ? 0 : integranteFamiliar.hashCode() );
		result = prime * result + ( ( observacao == null ) ? 0 : observacao.hashCode() );
		result = prime * result + ( ( planoAtendimentoFamiliar == null ) ? 0 : planoAtendimentoFamiliar.hashCode() );
		result = prime * result + ( ( responsavel == null ) ? 0 : responsavel.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
		result = prime * result + ( ( tipo == null ) ? 0 : tipo.hashCode() );
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
		if ( dataFinal == null )
		{
			if ( other.dataFinal != null ) return false;
		}
		else if ( !dataFinal.equals( other.dataFinal ) ) return false;
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
		if ( planoAtendimentoFamiliar == null )
		{
			if ( other.planoAtendimentoFamiliar != null ) return false;
		}
		else if ( !planoAtendimentoFamiliar.equals( other.planoAtendimentoFamiliar ) ) return false;
		if ( responsavel == null )
		{
			if ( other.responsavel != null ) return false;
		}
		else if ( !responsavel.equals( other.responsavel ) ) return false;
		if ( status != other.status ) return false;
		if ( tipo != other.tipo ) return false;
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
	
	/**
	 * 
	 */
	public Boolean isEmExecucao()
	{
		if (this.status == StatusEncaminhamento.EM_EXECUCAO)
		{
			return true;
		}
		
		return false;
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
	public PlanoAtendimento getPlanoAtendimentoFamiliar()
	{
		return planoAtendimentoFamiliar;
	}

	/**
	 * @param planoAtendimento the planoAtendimento to set
	 */
	public void setPlanoAtendimento( PlanoAtendimentoFamiliar planoAtendimento )
	{
		this.planoAtendimentoFamiliar = planoAtendimento;
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

	/**
	 * @return the observacao
	 */
	public String getObservacao()
	{
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao( String observacao )
	{
		this.observacao = observacao;
	}

	/**
	 * @return the dataFinal
	 */
	public Calendar getDataFinal()
	{
		return dataFinal;
	}

	/**
	 * @param dataFinal the dataFinal to set
	 */
	public void setDataFinal( Calendar dataFinal )
	{
		this.dataFinal = dataFinal;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario( User usuario )
	{
		this.usuario = usuario;
	}

	/**
	 * @return the responsavel
	 */
	public Responsavel getResponsavel()
	{
		return responsavel;
	}

	/**
	 * @param responsavel the responsavel to set
	 */
	public void setResponsavel( Responsavel responsavel )
	{
		this.responsavel = responsavel;
	}

	/**
	 * @return the tipo
	 */
	public TipoEncaminhamento getTipo()
	{
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo( TipoEncaminhamento tipo )
	{
		this.tipo = tipo;
	}
}
