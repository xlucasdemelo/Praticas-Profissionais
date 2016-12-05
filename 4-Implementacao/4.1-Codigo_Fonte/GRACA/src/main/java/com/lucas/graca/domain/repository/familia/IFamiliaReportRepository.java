package com.lucas.graca.domain.repository.familia;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import com.lucas.graca.domain.entity.familia.Familia;

public interface IFamiliaReportRepository 
{
	
	/**
	 * 
	 */
	public static final String FAMILIAS_ATENDIDAS_REPORT = "Relatorio-familias-atendidas-%s.pdf";
	
	/**
	 * 
	 * @param conta
	 * @param dataInicio
	 * @param dataFIm
	 * @return
	 */
	public ByteArrayOutputStream gerarRelatorioFamiliasAtendidas( List<Familia> familias, Calendar dataInicio, Calendar dataFIm );
	
}
