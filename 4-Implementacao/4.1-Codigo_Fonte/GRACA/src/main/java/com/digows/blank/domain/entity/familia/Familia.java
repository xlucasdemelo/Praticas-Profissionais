/**
 * 
 */
package com.digows.blank.domain.entity.familia;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.digows.blank.domain.entity.endereco.Endereco;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "Familia")
public class Familia extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7451024831812785339L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	private boolean ativo = true;
	
	/**
	 * 
	 */
	@NotNull
	private String nome;
	
	/**
	 * 
	 */
	@NotNull
	private String telefone;
	
	/**
	 * 
	 */
	@NotNull
	private Integer numeroComodos;
	
	/**
	 * 
	 */
	@NotNull
	private String situacaoImovel;
	
	/**
	 * 
	 */
	@NotNull
	private String infraestrutura;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private TipoImovel tipoImovel;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="endereco_id")
	private Endereco endereco;

	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param nome
	 * @param telefone
	 * @param numeroComodos
	 * @param situacaoImovel
	 * @param infraestrutura
	 * @param tipoImovel
	 * @param endereco
	 */
	public Familia( Long id, String nome, String telefone, Integer numeroComodos, String situacaoImovel, String infraestrutura, TipoImovel tipoImovel, Endereco endereco, boolean ativo )
	{
		super(id);
		this.nome = nome;
		this.telefone = telefone;
		this.numeroComodos = numeroComodos;
		this.situacaoImovel = situacaoImovel;
		this.infraestrutura = infraestrutura;
		this.tipoImovel = tipoImovel;
		this.endereco = endereco;
		this.ativo = ativo;
	}

	/**
	 * 
	 */
	public Familia()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Familia( Long id )
	{
		super( id );
	}

	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the nome
	 */
	public String getNome()
	{
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome( String nome )
	{
		this.nome = nome;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone()
	{
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone( String telefone )
	{
		this.telefone = telefone;
	}

	/**
	 * @return the numeroComodos
	 */
	public Integer getNumeroComodos()
	{
		return numeroComodos;
	}

	/**
	 * @param numeroComodos the numeroComodos to set
	 */
	public void setNumeroComodos( Integer numeroComodos )
	{
		this.numeroComodos = numeroComodos;
	}

	/**
	 * @return the situacaoImovel
	 */
	public String getSituacaoImovel()
	{
		return situacaoImovel;
	}

	/**
	 * @param situacaoImovel the situacaoImovel to set
	 */
	public void setSituacaoImovel( String situacaoImovel )
	{
		this.situacaoImovel = situacaoImovel;
	}

	/**
	 * @return the infraestrutura
	 */
	public String getInfraestrutura()
	{
		return infraestrutura;
	}

	/**
	 * @param infraestrutura the infraestrutura to set
	 */
	public void setInfraestrutura( String infraestrutura )
	{
		this.infraestrutura = infraestrutura;
	}

	/**
	 * @return the tipoImovel
	 */
	public TipoImovel getTipoImovel()
	{
		return tipoImovel;
	}

	/**
	 * @param tipoImovel the tipoImovel to set
	 */
	public void setTipoImovel( TipoImovel tipoImovel )
	{
		this.tipoImovel = tipoImovel;
	}

	/**
	 * @return the ativo
	 */
	public boolean isAtivo()
	{
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo( boolean ativo )
	{
		this.ativo = ativo;
	}

	/**
	 * @return the endereco
	 */
	public Endereco getEndereco()
	{
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco( Endereco endereco )
	{
		this.endereco = endereco;
	}

	/*-------------------------------------------------------------------
	 *				 		     EQUALS AND HASCHCODE
	 *-------------------------------------------------------------------*/
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( endereco == null ) ? 0 : endereco.hashCode() );
		result = prime * result + ( ( infraestrutura == null ) ? 0 : infraestrutura.hashCode() );
		result = prime * result + ( ( nome == null ) ? 0 : nome.hashCode() );
		result = prime * result + ( ( numeroComodos == null ) ? 0 : numeroComodos.hashCode() );
		result = prime * result + ( ( situacaoImovel == null ) ? 0 : situacaoImovel.hashCode() );
		result = prime * result + ( ( telefone == null ) ? 0 : telefone.hashCode() );
		result = prime * result + ( ( tipoImovel == null ) ? 0 : tipoImovel.hashCode() );
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
		Familia other = ( Familia ) obj;
		if ( endereco == null )
		{
			if ( other.endereco != null ) return false;
		}
		else if ( !endereco.equals( other.endereco ) ) return false;
		if ( infraestrutura == null )
		{
			if ( other.infraestrutura != null ) return false;
		}
		else if ( !infraestrutura.equals( other.infraestrutura ) ) return false;
		if ( nome == null )
		{
			if ( other.nome != null ) return false;
		}
		else if ( !nome.equals( other.nome ) ) return false;
		if ( numeroComodos == null )
		{
			if ( other.numeroComodos != null ) return false;
		}
		else if ( !numeroComodos.equals( other.numeroComodos ) ) return false;
		if ( situacaoImovel == null )
		{
			if ( other.situacaoImovel != null ) return false;
		}
		else if ( !situacaoImovel.equals( other.situacaoImovel ) ) return false;
		if ( telefone == null )
		{
			if ( other.telefone != null ) return false;
		}
		else if ( !telefone.equals( other.telefone ) ) return false;
		if ( tipoImovel != other.tipoImovel ) return false;
		return true;
	}
	
	
}
