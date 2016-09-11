/**
 * 
 */
package com.lucas.graca.domain.entity.questionario;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author lucas
 *
 */
@DataTransferObject(javascript="enum")
public enum TipoQuestao 
{
	TEXTO,
	BOOLEANO
}
