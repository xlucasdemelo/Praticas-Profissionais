package com.lucas.graca.infrastructure.report;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucas.graca.domain.entity.questionario.VersaoQuestionario;
import com.lucas.graca.domain.repository.questionario.IQuestionarioReportRepository;

import br.com.eits.common.infrastructure.report.IReportManager;

@Component
public class QuestionarioReportRepository implements IQuestionarioReportRepository
{
	
	private static final String IMPRIMIR_QUESTIONARIO_PATH = "/file/reports/questionario/imprimir/template-imprimir-questionario.jasper";
	
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
	
	/**
	 * 
	 */
	@Override
	public ByteArrayOutputStream imprimirQuestionario(VersaoQuestionario lastVersion) 
	{
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("logo_DIR", this.getClass().getResourceAsStream(IQuestionarioReportRepository.LOGO_DIR));
		parametros.put("versao_questionario_id", lastVersion.getId());
		return this.reportManager.exportToPDF( parametros, IMPRIMIR_QUESTIONARIO_PATH );
	}

}
