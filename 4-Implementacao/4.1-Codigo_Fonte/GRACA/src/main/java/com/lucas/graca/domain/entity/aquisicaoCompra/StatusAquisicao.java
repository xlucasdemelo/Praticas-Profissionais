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
public enum StatusAquisicao
{
	RASCUNHO,
	CONCLUIDO,
	RECUSADO,
	ABERTO
}
