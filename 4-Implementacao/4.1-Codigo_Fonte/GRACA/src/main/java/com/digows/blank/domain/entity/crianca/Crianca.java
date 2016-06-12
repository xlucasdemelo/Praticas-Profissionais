/**
 * 
 */
package com.digows.blank.domain.entity.crianca;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.digows.blank.domain.entity.integrantefamiliar.IntegranteFamiliar;

/**
 * @author lucas
 *
 */
@Entity
@Audited
@Table(name="Crianca")
@DataTransferObject(javascript = "Crianca")
public class Crianca extends IntegranteFamiliar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2132183578830591508L;
	
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
	@Column
	@NotNull
	private Etnia etnia;
}
