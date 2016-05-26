/**
 * 
 */
package com.digows.blank.domain.entity.integrantefamiliar;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.digows.blank.domain.entity.endereco.Endereco;
import com.digows.blank.domain.entity.familia.Familia;
import com.digows.blank.domain.entity.familia.Sexo;
import com.ibm.icu.math.BigDecimal;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@DataTransferObject(javascript = "Familia")
public class IntegranteFamiliar extends AbstractEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2835512132734340833L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@NotNull
	private String nome;
	
	/**
	 * 
	 */
	@NotNull
	private Calendar dataNascimento;
	
	/**
	 * 
	 */
	@NotNull
	private String ocupacao;
	
	/**
	 * 
	 */
	@NotNull
	private BigDecimal rendaMensal;
	
	/**
	 * 
	 */
	@NotNull
	private String filiacao;
	
	/**
	 * 
	 */
	@NotNull
	private String telefone;
	
	/**
	 * 
	 */
	@NotNull
	private Sexo sexo;
	
	/**
	 * 
	 */
	@NotNull
	private Boolean ativo;
	
	/**
	 * 
	 */
	@ManyToOne (fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="endereco_id")
	private Endereco endereco;
	
	/**
	 * 
	 */
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name="familia_id")
	private Familia familia;
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param id
	 * @param nome
	 * @param dataNascimento
	 * @param ocupacao
	 * @param rendaMensal
	 * @param filiacao
	 * @param telefone
	 * @param sexo
	 * @param endereco
	 */
	public IntegranteFamiliar(Long id, String nome, Calendar dataNascimento, String ocupacao, BigDecimal rendaMensal, String filiacao, String telefone, Sexo sexo, Endereco endereco, Boolean ativo, Familia familia )
	{
		super(id);
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.ocupacao = ocupacao;
		this.rendaMensal = rendaMensal;
		this.filiacao = filiacao;
		this.telefone = telefone;
		this.sexo = sexo;
		this.endereco = endereco;
		this.ativo = ativo;
		this.familia = familia;
	}

	/**
	 * 
	 */
	public IntegranteFamiliar()
	{
		super();
	}

	/**
	 * @param id
	 */
	public IntegranteFamiliar( Long id )
	{
		super( id );
	}

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	public void disableIntegranteFamiliar()
	{
		this.ativo = false;
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void enableIntegranteFamiliar()
	{
		this.ativo = true;
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
	 * @return the dataNascimento
	 */
	public Calendar getDataNascimento()
	{
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento( Calendar dataNascimento )
	{
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the ocupacao
	 */
	public String getOcupacao()
	{
		return ocupacao;
	}

	/**
	 * @param ocupacao the ocupacao to set
	 */
	public void setOcupacao( String ocupacao )
	{
		this.ocupacao = ocupacao;
	}

	/**
	 * @return the rendaMensal
	 */
	public BigDecimal getRendaMensal()
	{
		return rendaMensal;
	}

	/**
	 * @param rendaMensal the rendaMensal to set
	 */
	public void setRendaMensal( BigDecimal rendaMensal )
	{
		this.rendaMensal = rendaMensal;
	}

	/**
	 * @return the filiacao
	 */
	public String getFiliacao()
	{
		return filiacao;
	}

	/**
	 * @param filiacao the filiacao to set
	 */
	public void setFiliacao( String filiacao )
	{
		this.filiacao = filiacao;
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
	 * @return the sexo
	 */
	public Sexo getSexo()
	{
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo( Sexo sexo )
	{
		this.sexo = sexo;
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
	 * @return the familia
	 */
	public Familia getFamilia()
	{
		return familia;
	}

	/**
	 * @param familia the familia to set
	 */
	public void setFamilia( Familia familia )
	{
		this.familia = familia;
	}
	
	
}