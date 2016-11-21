package com.lucas.graca.domain.entity.produto;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject(type = "enum")
public enum StatusRepasse 
{
	RASCUNHO,
	APROVADO,
	RECUSADO
}
