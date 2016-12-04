package com.lucas.graca.domain.repository.produto;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

import com.lucas.graca.domain.entity.casalar.CasaLar;
import com.lucas.graca.domain.entity.fornecedor.Fornecedor;
import com.lucas.graca.domain.entity.produto.Produto;

public interface IProdutoReportRepository 
{
	
	/**
	 * 
	 */
	public static final String PRODUTO_ADQUIRIDO_REPORT = "Relatorio-produtos-adquiridos-%s.pdf";
	
	/**
	 * 
	 */
	public static final String PRODUTO_REPASSADO_REPORT = "Relatorio-produtos-repassados-%s.pdf";
	
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
	
	/**
	 * 
	 * @param fornecedores
	 * @param produtos
	 * @param dataInicio
	 * @param dataFIm
	 * @return
	 */
	public ByteArrayOutputStream gerarRelatorioProdutosRepassados( List<CasaLar> casasLares, List<Produto> produtos, 
			Calendar dataInicio, Calendar dataFIm );
	
}
