/**
 * 
 */
package com.digows.blank.domain.entity.crianca;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author lucas
 *
 */
@DataTransferObject(type = "enum")
public enum GrauParentesco
{
	MAE,
	PAI,
	IRMAO,
	PRIMO,
	AVO_M,
	AVO_F,
	OUTRO
}
