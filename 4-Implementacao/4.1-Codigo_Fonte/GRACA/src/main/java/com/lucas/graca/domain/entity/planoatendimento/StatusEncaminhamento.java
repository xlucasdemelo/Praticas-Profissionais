/**
 * 
 */
package com.lucas.graca.domain.entity.planoatendimento;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author lucas
 *
 */
@DataTransferObject(type = "enum")
public enum StatusEncaminhamento 
{
	ABERTO,
	EM_EXECUCAO,
	CONCLUIDO,
	CANCELADO
}
