package com.lucas.graca.domain.repository.produto;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import com.lucas.graca.domain.entity.fornecedor.Fornecedor;
import com.lucas.graca.domain.entity.produto.Produto;

public interface IProdutoReportRepository 
{
	
	/**
	 * 
	 */
	public static final String PRODUTO_ADQUIRIDO_REPORT = "relatorio-produtos-adquiridos-%s.pdf";
	
	/*-------------------------------------------------------------------
     *                          BEHAVIORS
     *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param questionarioResposta
	 * @return
	 */
	public ByteArrayOutputStream gerarRelatorioProdutosAdquiridos( List<Fornecedor> fornecedores, List<Produto> produtos, 
			Calendar dataInicio, Calendar dataFIm );
	
}
