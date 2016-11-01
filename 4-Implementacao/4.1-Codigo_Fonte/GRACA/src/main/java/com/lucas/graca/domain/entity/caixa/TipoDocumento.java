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
public enum TipoDocumento
{
	NOTA_FISCAL,
	FOLHA_PAGAMENTO,
	FATURA,
	GUIA_PREVIDENCIA_SOCIAL
}
