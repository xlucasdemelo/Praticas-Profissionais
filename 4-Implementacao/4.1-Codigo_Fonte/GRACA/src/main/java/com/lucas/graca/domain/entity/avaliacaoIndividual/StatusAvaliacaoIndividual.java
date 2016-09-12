/**
 * 
 */
package com.lucas.graca.domain.entity.avaliacaoIndividual;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author lucas
 *
 */
@DataTransferObject(javascript = "enum")
public enum StatusAvaliacaoIndividual
{
	RASCUNHO,
	ENVIADO_PARA_AVALIACAO,
	ACEITO,
	REJEITADO
}
