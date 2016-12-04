package com.lucas.graca.domain.repository.caixa;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import com.lucas.graca.domain.entity.caixa.Conta;

public interface IMovimentacaoReportRepository 
{
	
	/**
	 * 
	 */
	public static final String MOVIMENTACAO_CONTA_REPORT = "Relatorio-movimentacoes-conta-%s.pdf";
	
	/**
	 * 
	 * @param conta
	 * @param dataInicio
	 * @param dataFIm
	 * @return
	 */
	public ByteArrayOutputStream gerarRelatorioMovimentacoesConta( List<Conta> contas, Calendar dataInicio, Calendar dataFIm );
	
}
