/**
 * 
 */
package com.lucas.graca.domain.entity.caixa;

import javax.persistence.Entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * @author eits
 *
 */

@Entity
@Audited
@DataTransferObject(javascript = "Movimentacao")
public class Movimentacao extends AbstractEntity
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6373630682825041548L;
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
}
