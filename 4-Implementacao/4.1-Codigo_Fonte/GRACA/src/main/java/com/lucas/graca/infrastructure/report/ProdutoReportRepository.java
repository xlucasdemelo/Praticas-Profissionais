package com.lucas.graca.infrastructure.report;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucas.graca.domain.entity.casalar.CasaLar;
import com.lucas.graca.domain.entity.fornecedor.Fornecedor;
import com.lucas.graca.domain.entity.produto.Produto;
import com.lucas.graca.domain.repository.produto.IProdutoReportRepository;
import com.lucas.graca.domain.repository.questionario.IQuestionarioReportRepository;

import br.com.eits.common.infrastructure.report.IReportManager;

@Component
public class ProdutoReportRepository implements IProdutoReportRepository
{
	
	/**
	 * 
	 */
	private static final String PRODUTOS_ADQUIRIDOS_PATH = "/file/reports/produto/produtosadquiridos/template-produtos-adquiridos.jasper";

	/**
	 * 
	 */
	private static final String PRODUTOS_REPASSADOS_PATH = "/file/reports/produto/produtosrepassados/template-produtos-repassados.jasper";
	
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
	public ByteArrayOutputStream gerarRelatorioProdutosAdquiridos(List<Fornecedor> fornecedores, List<Produto> produtos,
			Calendar dataInicio, Calendar dataFIm) 
	{
		
		final List<Long> produtosId = new ArrayList<Long>();
		final List<Long> fornecedoresId = new ArrayList<Long>();
		
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
		
		if (produtos != null)
		{
			for (Produto produto : produtos) 
			{
				produtosId.add(produto.getId());
			}
		}
		
		if (fornecedores != null)
		{
			for (Fornecedor fornecedor : fornecedores) 
			{
				fornecedoresId.add(fornecedor.getId());
			}
		}
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("logo_DIR", this.getClass().getResourceAsStream(IQuestionarioReportRepository.LOGO_DIR));
		parametros.put("produtosId", produtosId);
		parametros.put("fornecedoresId", fornecedoresId);
		parametros.put("inicio", dataInicio == null ? null : dataInicio.getTime() );
		parametros.put("termino", dataFIm == null ? null : dataFIm.getTime() );
		return this.reportManager.exportToPDF( parametros, PRODUTOS_ADQUIRIDOS_PATH );
	}

	/**
	 * 
	 */
	@Override
	public ByteArrayOutputStream gerarRelatorioProdutosRepassados(List<CasaLar> casasLares, List<Produto> produtos,
			Calendar dataInicio, Calendar dataFIm) 
	{
		
		final List<Long> produtosId = new ArrayList<Long>();
		final List<Long> casasLaresId = new ArrayList<Long>();
		
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
		
		if (produtos != null)
		{
			for (Produto produto : produtos) 
			{
				produtosId.add(produto.getId());
			}
		}
		
		if (casasLares != null)
		{
			for (CasaLar casaLar : casasLares) 
			{
				casasLaresId.add(casaLar.getId());
			}
		}
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("logo_DIR", this.getClass().getResourceAsStream(IQuestionarioReportRepository.LOGO_DIR));
		parametros.put("produtosId", produtosId);
		parametros.put("casasLaresId", casasLaresId);
		parametros.put("inicio", dataInicio == null ? null : dataInicio.getTime() );
		parametros.put("termino", dataFIm == null ? null : dataFIm.getTime() );
		return this.reportManager.exportToPDF( parametros, PRODUTOS_REPASSADOS_PATH );
		
	}
}
