/**
 * 
 */
package com.lucas.graca.domain.entity.planoatendimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.integrantefamiliar.IntegranteFamiliar;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "Encaminhamento")
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
	private Integer numero;
	
	/**
	 * 
	 */
	@NotNull
	private String descricao;
	
	/**
	 * 
	 */
	@NotNull
	private String observação;
	
	/**
	 * 
	 */
	@NotNull
	private StatusEncaminhamento status;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="plano_atendimento_id")
	private PlanoAtendimento planoAtendimento;
	
	/**
	 * 
	 */
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User usuario;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="integrante_familiar_id")
	private IntegranteFamiliar integranteFamiliar;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param numero
	 * @param descricao
	 * @param observação
	 * @param status
	 * @param planoAtendimento
	 * @param usuario
	 * @param integranteFamiliar
	 */
	public Encaminhamento( Long id, Integer numero, String descricao, String observação, StatusEncaminhamento status, PlanoAtendimento planoAtendimento, User usuario, IntegranteFamiliar integranteFamiliar )
	{
		super(id);
		this.numero = numero;
		this.descricao = descricao;
		this.observação = observação;
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
		result = prime * result + ( ( numero == null ) ? 0 : numero.hashCode() );
		result = prime * result + ( ( observação == null ) ? 0 : observação.hashCode() );
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
		if ( numero == null )
		{
			if ( other.numero != null ) return false;
		}
		else if ( !numero.equals( other.numero ) ) return false;
		if ( observação == null )
		{
			if ( other.observação != null ) return false;
		}
		else if ( !observação.equals( other.observação ) ) return false;
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

	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the numero
	 */
	public Integer getNumero()
	{
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero( Integer numero )
	{
		this.numero = numero;
	}

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
	 * @return the observação
	 */
	public String getObservação()
	{
		return observação;
	}

	/**
	 * @param observação the observação to set
	 */
	public void setObservação( String observação )
	{
		this.observação = observação;
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
	public void setUsuario( User usuario )
	{
		this.usuario = usuario;
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
