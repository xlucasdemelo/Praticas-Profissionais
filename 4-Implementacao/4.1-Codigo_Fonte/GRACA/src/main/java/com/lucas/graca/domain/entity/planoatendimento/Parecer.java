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
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.planoatendimentofamiliar.PlanoAtendimentoFamiliar;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "Parecer")
public class Parecer extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4319400583954879141L;
	
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
	private TipoEncaminhamento tipo;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="usuario_id")
	private User usuario;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="plano_atendimento_id")
	private PlanoAtendimentoFamiliar planoAtendimentoFamiliar;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param descricao
	 * @param tipo
	 * @param usuario
	 * @param planoAtendimento
	 */
	public Parecer( Long id, Calendar created, String descricao, TipoEncaminhamento tipo, User usuario, PlanoAtendimentoFamiliar planoAtendimento )
	{
		super(id);
		this.descricao = descricao;
		this.tipo = tipo;
		this.usuario = usuario;
		this.planoAtendimentoFamiliar = planoAtendimento;
		this.created = created;
	}

	/**
	 * 
	 */
	public Parecer()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Parecer( Long id )
	{
		super( id );
	}
	
	/*-------------------------------------------------------------------
	 *				 		BEHAVIORS
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
		result = prime * result + ( ( planoAtendimentoFamiliar == null ) ? 0 : planoAtendimentoFamiliar.hashCode() );
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
		Parecer other = ( Parecer ) obj;
		if ( descricao == null )
		{
			if ( other.descricao != null ) return false;
		}
		else if ( !descricao.equals( other.descricao ) ) return false;
		if ( planoAtendimentoFamiliar == null )
		{
			if ( other.planoAtendimentoFamiliar != null ) return false;
		}
		else if ( !planoAtendimentoFamiliar.equals( other.planoAtendimentoFamiliar ) ) return false;
		if ( tipo != other.tipo ) return false;
		if ( usuario == null )
		{
			if ( other.usuario != null ) return false;
		}
		else if ( !usuario.equals( other.usuario ) ) return false;
		return true;
	}
	
	/*-------------------------------------------------------------------
	 *				 		 GETTERS AND SETTERS
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
	public void setUsuario(  )
	{
		this.usuario = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	/**
	 * @return the planoAtendimento
	 */
	public PlanoAtendimentoFamiliar getPlanoAtendimentoFamiliar()
	{
		return planoAtendimentoFamiliar;
	}

	/**
	 * @param planoAtendimento the planoAtendimento to set
	 */
	public void setPlanoAtendimentoFamiliar( PlanoAtendimentoFamiliar planoAtendimento )
	{
		this.planoAtendimentoFamiliar = planoAtendimento;
	}
}
