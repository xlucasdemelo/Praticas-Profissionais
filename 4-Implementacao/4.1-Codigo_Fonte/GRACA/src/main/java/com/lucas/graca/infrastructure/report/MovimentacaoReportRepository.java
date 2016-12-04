package com.lucas.graca.infrastructure.report;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucas.graca.domain.entity.caixa.Conta;
import com.lucas.graca.domain.repository.caixa.IMovimentacaoReportRepository;
import com.lucas.graca.domain.repository.questionario.IQuestionarioReportRepository;

import br.com.eits.common.infrastructure.report.IReportManager;

@Component
public class MovimentacaoReportRepository implements IMovimentacaoReportRepository 
{
	/**
	 * 
	 */
	private static final String MOVIMENTACAO_CONTA_PATH = "/file/reports/movimentacao/template-movimentacao-contas.jasper";
	
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
	 * @param conta
	 * @param dataInicio
	 * @param dataFIm
	 * @return
	 */
	@Override
	public ByteArrayOutputStream gerarRelatorioMovimentacoesConta(List<Conta> contas, Calendar dataInicio,
			Calendar dataFIm) 
	{
		
		final List<Long> contasId = new ArrayList<Long>();
		
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
		
		if (contas != null)
		{
			for (Conta conta : contas) 
			{
				contasId.add(conta.getId());
			}
		}
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("logo_DIR", this.getClass().getResourceAsStream(IQuestionarioReportRepository.LOGO_DIR));
		parametros.put("contasId", contasId);
		parametros.put("inicio", dataInicio == null ? null : dataInicio.getTime() );
		parametros.put("termino", dataFIm == null ? null : dataFIm.getTime() );
		return this.reportManager.exportToPDF( parametros, MOVIMENTACAO_CONTA_PATH );
		
	}

}
