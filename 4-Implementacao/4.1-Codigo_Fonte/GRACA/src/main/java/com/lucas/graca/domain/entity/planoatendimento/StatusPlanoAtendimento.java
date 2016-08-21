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
public enum StatusPlanoAtendimento
{
	RASCUNHO,
	EM_EXECUCAO,
	EM_PROCESSO_DESLIGAMENTO,
	EM_PROCESSO_REINTEGRAÇÃO,
	EM_PROCESSO_EMANCIPACAO,
	FINALIZADO
}
