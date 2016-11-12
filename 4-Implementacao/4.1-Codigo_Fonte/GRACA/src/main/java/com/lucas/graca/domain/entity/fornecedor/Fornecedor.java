/**
 * 
 */
package com.lucas.graca.domain.entity.fornecedor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.planoatendimento.Responsavel;

/**
 * @author lucas
 *
 */

@Entity
@Audited
@Table( name = "fornecedor")
@DataTransferObject(javascript = "Fornecedor")
public class Fornecedor extends Pessoa 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2503939208765574488L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false, unique = true)
	private String razaoSocial;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false, unique = true)
	private String cnpj;
	
	/**
	 * 
	 */
	@Basic
	private String telefone;
	
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean ativo;

	/**
	 * 
	 */
	@ManyToOne( optional = false, fetch = FetchType.EAGER )
	public Responsavel responsavel;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param razaoSocial
	 * @param cnpj
	 * @param telefone
	 * @param ativo
	 * @param responsavel
	 */
	public Fornecedor( Long id, String razaoSocial, String cnpj, String telefone, Boolean ativo, Responsavel responsavel )
	{
		super(id);
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.ativo = ativo;
		this.responsavel = responsavel;
	}

	/**
	 * 
	 */
	public Fornecedor()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Fornecedor( Long id )
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
		result = prime * result + ( ( ativo == null ) ? 0 : ativo.hashCode() );
		result = prime * result + ( ( cnpj == null ) ? 0 : cnpj.hashCode() );
		result = prime * result + ( ( razaoSocial == null ) ? 0 : razaoSocial.hashCode() );
		result = prime * result + ( ( responsavel == null ) ? 0 : responsavel.hashCode() );
		result = prime * result + ( ( telefone == null ) ? 0 : telefone.hashCode() );
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
		Fornecedor other = ( Fornecedor ) obj;
		if ( ativo == null )
		{
			if ( other.ativo != null ) return false;
		}
		else if ( !ativo.equals( other.ativo ) ) return false;
		if ( cnpj == null )
		{
			if ( other.cnpj != null ) return false;
		}
		else if ( !cnpj.equals( other.cnpj ) ) return false;
		if ( razaoSocial == null )
		{
			if ( other.razaoSocial != null ) return false;
		}
		else if ( !razaoSocial.equals( other.razaoSocial ) ) return false;
		if ( responsavel == null )
		{
			if ( other.responsavel != null ) return false;
		}
		else if ( !responsavel.equals( other.responsavel ) ) return false;
		if ( telefone == null )
		{
			if ( other.telefone != null ) return false;
		}
		else if ( !telefone.equals( other.telefone ) ) return false;
		return true;
	}

	/**
	 * 
	 */
	public void disableFornecedor()
	{
		this.ativo = false;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void enableFornecedor()
	{
		this.ativo = true;
	}
	
	
	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the razaoSocial
	 */
	public String getRazaoSocial()
	{
		return razaoSocial;
	}

	/**
	 * @param razaoSocial the razaoSocial to set
	 */
	public void setRazaoSocial( String razaoSocial )
	{
		this.razaoSocial = razaoSocial;
	}

	/**
	 * @return the cnpj
	 */
	public String getCnpj()
	{
		return cnpj;
	}

	/**
	 * @param cnpj the cnpj to set
	 */
	public void setCnpj( String cnpj )
	{
		this.cnpj = cnpj;
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
	 * @return the ativo
	 */
	public Boolean getAtivo()
	{
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo( Boolean ativo )
	{
		this.ativo = ativo;
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
	
	
}
