/**
 * 
 */
package com.lucas.graca.domain.entity.documento;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author lucas
 *
 */
@DataTransferObject(type = "enum")
public enum TipoDocumento
{
	RG,
	CPF
}
