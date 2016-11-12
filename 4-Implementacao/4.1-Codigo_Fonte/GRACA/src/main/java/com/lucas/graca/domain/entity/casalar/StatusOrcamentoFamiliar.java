/**
 * 
 */
package com.lucas.graca.domain.entity.casalar;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author lucas
 *
 */

@DataTransferObject(type = "enum")
public enum StatusOrcamentoFamiliar
{
	RASCUNHO,
	EXPIRADO,
	VIGENTE
}
