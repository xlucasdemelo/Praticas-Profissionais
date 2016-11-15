/**
 * 
 */
package com.lucas.graca.domain.entity.aquisicaoCompra;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author lucas
 *
 */
@DataTransferObject(type = "enum")
public enum TipoPagamento
{
	A_VISTA,
	A_PRAZO
}
