/**
 * 
 */
package com.lucas.graca.domain.entity.caixa;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author eits
 *
 */
@DataTransferObject(type="enum")
public enum TipoMovimentacao
{
	ENTRADA,
	SAIDA
}
