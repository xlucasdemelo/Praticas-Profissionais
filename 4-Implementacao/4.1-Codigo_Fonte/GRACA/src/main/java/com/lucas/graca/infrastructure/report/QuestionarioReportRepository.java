package com.lucas.graca.infrastructure.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucas.graca.domain.entity.questionario.QuestionarioResposta;
import com.lucas.graca.domain.repository.questionario.IQuestionarioReportRepository;

import br.com.eits.common.infrastructure.report.IReportManager;

@Component
public class QuestionarioReportRepository implements IQuestionarioReportRepository
{

	private static final String IMPRIMIR_QUESTIONARIO_RESPOSTA_PATH = "/file/reports/questionario/imprimir/template-imprimir-questionario-resposta.jasper";
	
	/*-------------------------------------------------------------------
	 *                          ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private IReportManager reportManager;
	
	/*-------------------------------------------------------------------
	 *                          REPORTS
	 *-------------------------------------------------------------------*/
	
	@Override
	public ByteArrayOutputStream imprimirQuestionario(QuestionarioResposta questionarioResposta) 
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		return this.reportManager.exportToPDF( parametros, IMPRIMIR_QUESTIONARIO_RESPOSTA_PATH );
	}

}
