package com.lucas.graca.infrastructure.report;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucas.graca.domain.entity.familia.Familia;
import com.lucas.graca.domain.repository.familia.IFamiliaReportRepository;
import com.lucas.graca.domain.repository.questionario.IQuestionarioReportRepository;

import br.com.eits.common.infrastructure.report.IReportManager;

@Component
public class FamiliaReportRepository implements IFamiliaReportRepository
{
	/**
	 * 
	 */
	private static final String FAMILIAS_ATENDIDAS_PATH = "/file/reports/familia/template-familias-atendidas.jasper";
	
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
	public ByteArrayOutputStream gerarRelatorioFamiliasAtendidas(List<Familia> familias, Calendar dataInicio,
			Calendar dataFIm) 
	{
		
		final List<Long> familiasId = new ArrayList<Long>();
		
		if ( dataInicio != null )
		{
			dataInicio.set( Calendar.HOUR_OF_DAY, 0 );
			dataInicio.set( Calendar.MINUTE, 0 );
			dataInicio.set( Calendar.SECOND, 0 );
			dataInicio.set( Calendar.MILLISECOND, 0 );
			
			if ( dataFIm == null )
			{
				dataFIm = Calendar.getInstance();
				dataFIm.set(Calendar.YEAR, 2100);;
			}
			else
			{
				dataFIm.set( Calendar.HOUR_OF_DAY, 23 );
				dataFIm.set( Calendar.MINUTE, 59 );
				dataFIm.set( Calendar.SECOND, 59 );
				dataFIm.set( Calendar.MILLISECOND, 999 );
			}

		}
		
		if (familias != null)
		{
			for (Familia familia: familias) 
			{
				familiasId.add(familia.getId());
			}
		}
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("logo_DIR", this.getClass().getResourceAsStream(IQuestionarioReportRepository.LOGO_DIR));
		parametros.put("familiasId", familiasId);
		parametros.put("inicio", dataInicio == null ? null : dataInicio.getTime() );
		parametros.put("termino", dataFIm == null ? null : dataFIm.getTime() );
		return this.reportManager.exportToPDF( parametros, FAMILIAS_ATENDIDAS_PATH );
		
	}

}
