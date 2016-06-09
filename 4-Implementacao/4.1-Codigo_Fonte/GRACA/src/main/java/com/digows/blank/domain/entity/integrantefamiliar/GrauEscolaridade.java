/**
 * 
 */
package com.digows.blank.domain.entity.integrantefamiliar;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author lucas
 *
 */
@DataTransferObject(type = "enum")
public enum GrauEscolaridade
{
	SEM_ESCOLARIDADE,				//0
	FUNDAMENTAL_COMPLETO,			//1
	MEDIO_INCOMPLETO,				//2
	MEDIO_COMPLETO,					//3
	SUPERIOR_INCOMPLETO,			//4
	SUPERIOR_COMPLETO				//5
}
