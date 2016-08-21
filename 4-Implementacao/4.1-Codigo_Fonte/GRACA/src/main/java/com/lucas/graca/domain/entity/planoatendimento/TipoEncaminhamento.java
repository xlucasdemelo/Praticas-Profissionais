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
public enum TipoEncaminhamento
{
	SAUDE,
	ESPORTE_E_LAZER,
	EDUCACAO_E_PROFISSIONALIZACAO,
	CONVIVIO_FAMILIAR
}
