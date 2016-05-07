/**
 * 
 */
package com.digows.blank.domain.entity.familia;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author lucas
 *
 */
@DataTransferObject(type = "enum")
public enum TipoImovel
{
	CASA,				// 1
	APARTAMENTO,		// 2
	INVASAO				// 3
}
