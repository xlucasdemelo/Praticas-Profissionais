/**
 * 
 */
package com.lucas.graca.domain.entity.crianca;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.lucas.graca.domain.entity.casalar.CasaLar;
import com.lucas.graca.domain.entity.endereco.Endereco;
import com.lucas.graca.domain.entity.familia.Familia;
import com.lucas.graca.domain.entity.familia.Sexo;
import com.lucas.graca.domain.entity.integrantefamiliar.GrauEscolaridade;
import com.lucas.graca.domain.entity.integrantefamiliar.IntegranteFamiliar;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@Table( name = "crianca",
uniqueConstraints = { @UniqueConstraint( columnNames = { "id", "casa_lar_id" } ) } )
@DataTransferObject(javascript = "Crianca")
@AttributeOverride(name = "ocupacao", column = @Column(name = "ocupacao"))
public class Crianca extends IntegranteFamiliar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2132183578830591508L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Column
	private String peso;
	
	//TODO Casa lar
	
	/**
	 * 
	 */
	@Column
	private String motivoAcolhimento;
	
	/**
	 * 
	 */
	@Column
	private String numeroProcesso;
	
	/**
	 * 
	 */
	@Column
	private String altura;
	
	/**
	 * DATA de elaboracao do plano individual avaliativo
	 */
	@Column
	private Calendar dataElaboracaoPIA;
	
	/**
	 * 
	 */
	@Column
	private Calendar dataLimite;
	
	/**
	 * 
	 */
	@Column
	private Calendar dataAcolhimento;
	
	/**
	 * 
	 */
	@NotNull
	private Etnia etnia;

	/**
	 * 
	 */
	@Column
	private String entidadeAcolhimento;
	
	/**
	 * 
	 */
	@ManyToOne (optional= true, fetch = FetchType.EAGER)
	@JoinColumn(name="casa_lar_id")
	private CasaLar casaLar;
	
	/**
	 * 
	 */
	@Transient
	private List<DocumentoCrianca> documentosCrianca;
	
	/*-------------------------------------------------------------------
	 *				 		     CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @param id
	 * @param nome
	 * @param dataNascimento
	 * @param ocupacao
	 * @param rendaMensal
	 * @param filiacao
	 * @param telefone
	 * @param sexo
	 * @param endereco
	 * @param ativo
	 * @param grauEscolaridade
	 * @param peso
	 * @param motivoAcolhimento
	 * @param numeroProcesso
	 * @param altura
	 * @param dataElaboracaoPIA
	 * @param dataLimite
	 * @param dataAcolhimento
	 * @param familia2
	 * @param etnia
	 */
	public Crianca( Long id, String nome, Calendar dataNascimento, String ocupacao, BigDecimal rendaMensal, String filiacao, String telefone, 
			Sexo sexo, Endereco endereco, Boolean ativo, Familia familia, GrauEscolaridade grauEscolaridade, String peso, 
			String motivoAcolhimento, String numeroProcesso, String altura, Calendar dataElaboracaoPIA, Calendar dataLimite, 
			Calendar dataAcolhimento, Etnia etnia, String entidadeAcolhimento, CasaLar casaLar )
	{
		super( id, nome, dataNascimento, ocupacao, rendaMensal, filiacao, telefone, sexo, endereco, ativo, familia, grauEscolaridade );
		this.peso = peso;
		this.motivoAcolhimento = motivoAcolhimento;
		this.numeroProcesso = numeroProcesso;
		this.altura = altura;
		this.dataElaboracaoPIA = dataElaboracaoPIA;
		this.dataLimite = dataLimite;
		this.dataAcolhimento = dataAcolhimento;
		this.etnia = etnia;
		this.entidadeAcolhimento = entidadeAcolhimento;
		this.casaLar = casaLar;
	}

	/**
	 * 
	 */
	public Crianca()
	{
		super();
	}

	/**
	 * @param id
	 */
	public Crianca( Long id )
	{
		super( id );
	}
	
	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	
	public Integer getIdade()
	{
		return Calendar.getInstance().get( Calendar.YEAR ) - this.getDataNascimento().get( Calendar.YEAR );
	}
	
	/*-------------------------------------------------------------------
	 *				 		     GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * @return the peso
	 */
	public String getPeso()
	{
		return peso;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso( String peso )
	{
		this.peso = peso;
	}

	/**
	 * @return the motivoAcolhimento
	 */
	public String getMotivoAcolhimento()
	{
		return motivoAcolhimento;
	}

	/**
	 * @param motivoAcolhimento the motivoAcolhimento to set
	 */
	public void setMotivoAcolhimento( String motivoAcolhimento )
	{
		this.motivoAcolhimento = motivoAcolhimento;
	}

	/**
	 * @return the numeroProcesso
	 */
	public String getNumeroProcesso()
	{
		return numeroProcesso;
	}

	/**
	 * @param numeroProcesso the numeroProcesso to set
	 */
	public void setNumeroProcesso( String numeroProcesso )
	{
		this.numeroProcesso = numeroProcesso;
	}

	/**
	 * @return the altura
	 */
	public String getAltura()
	{
		return altura;
	}

	/**
	 * @param altura the altura to set
	 */
	public void setAltura( String altura )
	{
		this.altura = altura;
	}

	/**
	 * @return the dataElaboracaoPIA
	 */
	public Calendar getDataElaboracaoPIA()
	{
		return dataElaboracaoPIA;
	}

	/**
	 * @param dataElaboracaoPIA the dataElaboracaoPIA to set
	 */
	public void setDataElaboracaoPIA( Calendar dataElaboracaoPIA )
	{
		this.dataElaboracaoPIA = dataElaboracaoPIA;
	}

	/**
	 * @return the dataLimite
	 */
	public Calendar getDataLimite()
	{
		return dataLimite;
	}

	/**
	 * @param dataLimite the dataLimite to set
	 */
	public void setDataLimite( Calendar dataLimite )
	{
		this.dataLimite = dataLimite;
	}

	/**
	 * @return the dataAcolhimento
	 */
	public Calendar getDataAcolhimento()
	{
		return dataAcolhimento;
	}

	/**
	 * @param dataAcolhimento the dataAcolhimento to set
	 */
	public void setDataAcolhimento( Calendar dataAcolhimento )
	{
		this.dataAcolhimento = dataAcolhimento;
	}

	/**
	 * @return the etnia
	 */
	public Etnia getEtnia()
	{
		return etnia;
	}

	/**
	 * @param etnia the etnia to set
	 */
	public void setEtnia( Etnia etnia )
	{
		this.etnia = etnia;
	}

	/**
	 * @return the entidadeAcolhimento
	 */
	public String getEntidadeAcolhimento()
	{
		return entidadeAcolhimento;
	}

	/**
	 * @param entidadeAcolhimento the entidadeAcolhimento to set
	 */
	public void setEntidadeAcolhimento( String entidadeAcolhimento )
	{
		this.entidadeAcolhimento = entidadeAcolhimento;
	}

	/**
	 * @return the documentosCrianca
	 */
	public List<DocumentoCrianca> getDocumentosCrianca()
	{
		return documentosCrianca;
	}

	/**
	 * @param documentosCrianca the documentosCrianca to set
	 */
	public void setDocumentosCrianca( List<DocumentoCrianca> documentosCrianca )
	{
		this.documentosCrianca = documentosCrianca;
	}

	/**
	 * @return the casaLar
	 */
	public CasaLar getCasaLar()
	{
		return casaLar;
	}

	/**
	 * @param casaLar the casaLar to set
	 */
	public void setCasaLar( CasaLar casaLar )
	{
		this.casaLar = casaLar;
	}

}
