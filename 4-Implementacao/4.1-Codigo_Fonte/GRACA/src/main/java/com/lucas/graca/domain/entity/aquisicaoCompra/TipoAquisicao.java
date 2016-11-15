/**
 * 
 */
package com.lucas.graca.domain.entity.aquisicaoCompra;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author lucas
 *
 */
@DataTransferObject(type = "enum")
public enum TipoAquisicao
{
	COMPRA_SEDE,
	DOACAO,
	COMPRA_REQUISITADA,
	COMPRA_UNIFICADA
}
