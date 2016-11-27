package com.lucas.graca.domain.entity.questionario;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject(type = "enum")
public enum StatusQuestionarioResposta 
{
	RASCUNHO,
	FINALIZADO
}
