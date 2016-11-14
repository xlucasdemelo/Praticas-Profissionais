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
public enum StatusRequisicaoCompra
{
	RASCUNHO,
	EM_ABERTO,
	CONCLUIDA,
	RECUSADO
}
