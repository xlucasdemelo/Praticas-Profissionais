package com.lucas.graca.domain.repository.questionario;

import java.io.ByteArrayOutputStream;

import com.lucas.graca.domain.entity.questionario.VersaoQuestionario;

public interface IQuestionarioReportRepository 
{
	public static final String LOGO_DIR = "/META-INF/resources/static/images/aldeias.jpg";
	
	/**
	 * 
	 */
	public static final String QUESTIONARIO_REPORT = "questionario-%s.pdf";
	
	/*-------------------------------------------------------------------
     *                          BEHAVIORS
     *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param questionarioResposta
	 * @return
	 */
	public ByteArrayOutputStream imprimirQuestionario(VersaoQuestionario lastVersion);
	
}
