/**
 * 
 */
package com.lucas.graca.domain.service.casalar;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.aquisicaoCompra.AquisicaoProduto;
import com.lucas.graca.domain.entity.aquisicaoCompra.FornecedorAquisicao;
import com.lucas.graca.domain.entity.aquisicaoCompra.StatusAquisicao;
import com.lucas.graca.domain.entity.aquisicaoCompra.TipoAquisicao;
import com.lucas.graca.domain.entity.aquisicaoCompra.TipoPagamento;
import com.lucas.graca.domain.entity.casalar.Beneficiado;
import com.lucas.graca.domain.entity.casalar.CasaLar;
import com.lucas.graca.domain.entity.casalar.FornecedorRequisicao;
import com.lucas.graca.domain.entity.casalar.OrcamentoFamiliar;
import com.lucas.graca.domain.entity.casalar.RequisicaoCompra;
import com.lucas.graca.domain.entity.casalar.StatusRequisicaoCompra;
import com.lucas.graca.domain.entity.crianca.Crianca;
import com.lucas.graca.domain.repository.aquisicaoCompra.IAquisicaoProdutoRepository;
import com.lucas.graca.domain.repository.casalar.IBeneficiadoRepository;
import com.lucas.graca.domain.repository.casalar.ICasaLarRepository;
import com.lucas.graca.domain.repository.casalar.IFornecedorRequisicaoRepository;
import com.lucas.graca.domain.repository.casalar.IOrcamentoFamiliarRepository;
import com.lucas.graca.domain.repository.casalar.IRequisicaoCompraRepository;
import com.lucas.graca.domain.repository.crianca.ICriancaRepository;
import com.lucas.graca.domain.repository.planoatendimento.IResponsavelRepository;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"') || hasAuthority('"+UserRole.OPERADOR_ADMINISTRATIVO_VALUE+"') "
		+ "|| hasAuthority('"+UserRole.COLABORADOR_EXTERNO_VALUE+"') ")
public class CasaLarService 
{

	/**
	 * 
	 */
	@Autowired
	private ICasaLarRepository casaLarRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IResponsavelRepository responsavelRepository;
	
	/**
	 * 
	 */
	@Autowired
	private ICriancaRepository criancaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IOrcamentoFamiliarRepository orcamentoFamiliarRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IRequisicaoCompraRepository requisicaoCompraRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IBeneficiadoRepository beneficiadoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IFornecedorRequisicaoRepository fornecedorRequisicaoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IAquisicaoProdutoRepository aquisicaoProdutoRepository;
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param casaLar
	 * @return
	 */
	@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"')")
	public CasaLar insertCasaLar( CasaLar casaLar )
	{
		Assert.notNull( casaLar );
		Assert.isNull( casaLar.getId(), "Id precisa ser nulo" );
		Assert.notNull( casaLar.getNumero() );
		Assert.notNull( casaLar.getCuidadoraResidente(), "Cuidadora residente é obrigatório" );
		Assert.notNull( casaLar.getCuidadoraResidente().getNome(), "Nome da cuidadora residente é obrigatório" );
		Assert.notNull( casaLar.getCuidadoraApoiadora(), "Cuidadora apoiadora é obrigatório" );
		Assert.notNull( casaLar.getCuidadoraApoiadora().getNome(), "Nome da cuidadora residente é obrigatório" );
		
		this.responsavelRepository.saveAndFlush( casaLar.getCuidadoraResidente() );
		this.responsavelRepository.saveAndFlush( casaLar.getCuidadoraApoiadora() );
		
		return this.casaLarRepository.save( casaLar );
	}
	
	/**
	 * 
	 */
	@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"')")
	public CasaLar updateCasaLar( CasaLar casaLar )
	{
		Assert.notNull( casaLar );
		Assert.notNull( casaLar.getId(), "Id não pode ser nulo" );
		
		Assert.notNull( casaLar.getCuidadoraResidente(), "Cuidadora residente é obrigatório" );
		Assert.notNull( casaLar.getCuidadoraResidente().getNome(), "Nome da cuidadora residente é obrigatório" );
		Assert.notNull( casaLar.getCuidadoraApoiadora(), "Cuidadora apoiadora é obrigatório" );
		Assert.notNull( casaLar.getCuidadoraApoiadora().getNome(), "Nome da cuidadora residente é obrigatório" );
		
		this.responsavelRepository.save( casaLar.getCuidadoraResidente() );
		this.responsavelRepository.save( casaLar.getCuidadoraApoiadora() );
		
		return this.casaLarRepository.save( casaLar );
	}
	
	
	public Page<Crianca> listCriancasByCasaLar(long casaLarId, PageRequest pageable)
	{
		return this.criancaRepository.findByCasaLarId( casaLarId, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public CasaLar findCasaLarById( long id )
	{
		return this.casaLarRepository.findOne( id );
	}
	
	/**
	 * 
	 */
	@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"')")
	public void removeCasaLar(long id)
	{
		CasaLar casaLar = this.casaLarRepository.findOne( id );
		casaLar.disableCasaLar();
		
		/**
		 * 
		 */
		List<Crianca>criancasCasaLar = this.criancaRepository.findByCasaLarId( id, null ).getContent();
		
		for ( Crianca crianca : criancasCasaLar )
		{
			crianca.setCasaLar( null );
			this.criancaRepository.save( crianca );
		}
		
		this.casaLarRepository.delete( casaLar );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	public Page<CasaLar> listByFilters( String filter, PageRequest pageable )
	{
		return this.casaLarRepository.listByFilters( filter, pageable );
	}
	
	/*-------------------------------------------------------------------
	 *				 		  ORÇAMENTO FAMILIAR
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param orcamentoFamiliar
	 * @return
	 */
	public OrcamentoFamiliar insertOrcamentoFamiliar(OrcamentoFamiliar orcamentoFamiliar)
	{
		Assert.notNull( orcamentoFamiliar );
		Assert.isNull( orcamentoFamiliar.getId(), "Id deve ser nulo" );
		Assert.notNull( orcamentoFamiliar.getPeriodo(), "Período não pode ser nulo" );
		Assert.notNull( orcamentoFamiliar.getRendaPerCapitaAlimentacao(), "Renda per capita alimentação" );
		Assert.notNull( orcamentoFamiliar.getRendaPerCapitaHigiene(), "Renda per capita higiene" );
		Assert.notNull( orcamentoFamiliar.getCasaLar(), "Informe a casa lar" );
		
		return this.orcamentoFamiliarRepository.save( orcamentoFamiliar );
	}
	
	/**
	 * 
	 * @param orcamentoFamiliar
	 * @return
	 */
	public OrcamentoFamiliar updateOrcamentoFamiliar(OrcamentoFamiliar orcamentoFamiliar)
	{
		Assert.notNull( orcamentoFamiliar );
		Assert.notNull( orcamentoFamiliar.getId(), "Id não deve ser nulo" );
		Assert.notNull( orcamentoFamiliar.getPeriodo(), "Período não pode ser nulo" );
		Assert.notNull( orcamentoFamiliar.getRendaPerCapitaAlimentacao(), "Renda per capita alimentação" );
		Assert.notNull( orcamentoFamiliar.getRendaPerCapitaHigiene(), "Renda per capita higiene" );
		
		return this.orcamentoFamiliarRepository.save( orcamentoFamiliar );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removeOrcamentoFamiliar( long id )
	{
		final OrcamentoFamiliar orcamentoFamiliar = this.orcamentoFamiliarRepository.findOne( id );
		Assert.notNull( orcamentoFamiliar, "Orçamento familiar não existe" );
		Assert.isTrue( orcamentoFamiliar.isRascunho(), "Somente orçamentos em rascunho poderão ser excluídos" );
		
		this.orcamentoFamiliarRepository.delete( orcamentoFamiliar );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public OrcamentoFamiliar findOrcamentoFamiliarById( long id )
	{
		final OrcamentoFamiliar orcamentoFamiliar = this.orcamentoFamiliarRepository.findOne( id );
		Assert.notNull( orcamentoFamiliar, "Orçamento familiar não existe" );
		
		return orcamentoFamiliar;
	}
	
	/**
	 * 
	 * @param casaLarId
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<OrcamentoFamiliar> listOrcamentosFamiliaresByCasaLarAndFilters( Long casaLarId, PageRequest pageable )
	{
		return this.orcamentoFamiliarRepository.listOrcamentosFamiliaresByCasaLar( casaLarId, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public OrcamentoFamiliar changeOrcamentoFamiliarToVigente( long id )
	{
		final OrcamentoFamiliar orcamentoFamiliar = this.orcamentoFamiliarRepository.findOne( id );
		Assert.notNull( orcamentoFamiliar, "Orçamento familiar não existe" );
		
		Assert.isNull( this.orcamentoFamiliarRepository.
				findByCasaLarAndPeriodoAndStatusVigente( orcamentoFamiliar.getCasaLar().getId(), orcamentoFamiliar.getPeriodo() ),
				" Já existem um orçamento familiar vigente para este período");
		
		orcamentoFamiliar.changeToVigente();
		
		return this.orcamentoFamiliarRepository.save( orcamentoFamiliar );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public OrcamentoFamiliar changeOrcamentoFamiliarToExpirado( long id )
	{
		final OrcamentoFamiliar orcamentoFamiliar = this.orcamentoFamiliarRepository.findOne( id );
		Assert.notNull( orcamentoFamiliar, "Orçamento familiar não existe" );
		
		orcamentoFamiliar.changeToExpirado();
		
		return orcamentoFamiliar;
	}
	
	/*-------------------------------------------------------------------
	 *				 	   REQUISIÇÃO DE COMPRA
	 *-------------------------------------------------------------------*/

	/**
	 * 
	 * @param requisicaoCompra
	 * @return
	 */
	public RequisicaoCompra insertRequisicaoCompra( RequisicaoCompra requisicaoCompra )
	{
		Assert.notNull( requisicaoCompra, "Requisição é obrigatória" );
		Assert.notNull( requisicaoCompra.getDescricao(), "Descrição é obrigatória" );
		Assert.notNull( requisicaoCompra.getCasaLar(), "Casa lar é obrigatória" );
		
		return this.requisicaoCompraRepository.save( requisicaoCompra );
	}
	
	/**
	 * 
	 * @param requisicaoCompra
	 * @return
	 */
	public RequisicaoCompra updateRequisicaoCompra( RequisicaoCompra requisicaoCompra )
	{
		Assert.notNull( requisicaoCompra, "Requisição é obrigatória" );
		Assert.notNull( requisicaoCompra.getDescricao(), "Descrição é obrigatória" );
		Assert.notNull( requisicaoCompra.getCasaLar(), "Casa lar é obrigatória" );
		
		return this.requisicaoCompraRepository.save( requisicaoCompra );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removeRequisicaoCompra(long id)
	{
		RequisicaoCompra requisicao = this.requisicaoCompraRepository.findOne( id );
		Assert.notNull( requisicao, "Requisição não encontrada" );
		
		Assert.isTrue( requisicao.getStatus() == StatusRequisicaoCompra.RASCUNHO,
				"Para excluir o status deve ser rascunho");
		
		this.requisicaoCompraRepository.delete( requisicao );
	}
	
	/**
	 * 
	 * @param casaLarId
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<RequisicaoCompra> listRequisicoesByCasaLarAndFilters( Long casaLarId, String filter, PageRequest pageable )
	{
		return this.requisicaoCompraRepository.listByCasaLarAndFilters( casaLarId, filter, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public RequisicaoCompra findRequisicaoCompraById(long id)
	{
		RequisicaoCompra requisicao = this.requisicaoCompraRepository.findOne( id );
		Assert.notNull( requisicao, "Requisição não encontrada" );
		
		return requisicao;
	}
	
	/**
	 * 
	 * @param id
	 */
	public AquisicaoProduto changeToEmAberto(long id)
	{
		RequisicaoCompra requisicao = this.requisicaoCompraRepository.findOne( id );
		Assert.notNull( requisicao, "Requisição não encontrada" );
		
		requisicao.changeToEmAberto();
		
		this.requisicaoCompraRepository.saveAndFlush( requisicao );
		
		return this.insertAquisicaoProdutoTipoCompraRequisitada( requisicao );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void changeToConcluido(long id)
	{
		RequisicaoCompra requisicao = this.requisicaoCompraRepository.findOne( id );
		Assert.notNull( requisicao, "Requisição não encontrada" );
		
		requisicao.changeToConcluido();
		
		this.requisicaoCompraRepository.save( requisicao );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void changeToRecusado(long id)
	{
		RequisicaoCompra requisicao = this.requisicaoCompraRepository.findOne( id );
		Assert.notNull( requisicao, "Requisição não encontrada" );
		
		requisicao.changeToRecusado();
		
		this.requisicaoCompraRepository.save( requisicao );
	}
	
	/*-------------------------------------------------------------------
	 *				 	   SERVICES BENEFICIADO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param beneficiado
	 * @return
	 */
	public Beneficiado insertBeneficiado(Beneficiado beneficiado)
	{
		Assert.notNull( beneficiado, "Beneficiado é obrigatório" );
		Assert.notNull( beneficiado.getCrianca() );
		Assert.notNull( beneficiado.getRequisicaoCompra() );
		
		return this.beneficiadoRepository.save( beneficiado );
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Beneficiado> listBeneficiadosByCasaLar(long requisicaoCompraId, PageRequest pageable)
	{
		return this.beneficiadoRepository.findByRequisicaoCompraId( requisicaoCompraId, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Beneficiado findBeneficiadoById( long id )
	{
		Beneficiado beneficiado = this.beneficiadoRepository.findOne( id );
		Assert.notNull( beneficiado, "beneficiado não existe" );
		
		return beneficiado; 
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removeBeneficiado(long id)
	{
		Beneficiado beneficiado = this.beneficiadoRepository.findOne( id );
		Assert.notNull( beneficiado, "beneficiado não existe" );
		
		Assert.isTrue( beneficiado.getRequisicaoCompra().getStatus() == StatusRequisicaoCompra.RASCUNHO, 
				"Para remover o beneficiado, o status deve ser rascunho" );
		
		this.beneficiadoRepository.delete( beneficiado );
	}
	
	/*-------------------------------------------------------------------
	 *				 	  SERVICES FORNECEDOR REQUISIÇÃO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @return
	 */
	public FornecedorRequisicao insertFornecedorRequisicao(FornecedorRequisicao fornecedorRequisicao)
	{
		Assert.notNull( fornecedorRequisicao, "Fornecedore é obrigatório" );
		Assert.notNull( fornecedorRequisicao.getRequisicaoCompra() );
		Assert.notNull( fornecedorRequisicao.getFornecedor() );
		
		return this.fornecedorRequisicaoRepository.save( fornecedorRequisicao );
	}
	
	/**
	 * 
	 * @return
	 */
	public void removeFornecedorRequisicao(long id)
	{
		FornecedorRequisicao fornecedorRequisicao = this.fornecedorRequisicaoRepository.findOne( id );
		Assert.notNull( fornecedorRequisicao, "Registro não existe" );
		
		Assert.isTrue( fornecedorRequisicao.getRequisicaoCompra().getStatus() == StatusRequisicaoCompra.RASCUNHO, 
				"Para remover um fornecedor o status deve ser rascunho");
		
		this.fornecedorRequisicaoRepository.delete( fornecedorRequisicao );
	}
	
	/**
	 * 
	 * @param requisicaoCompraId
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<FornecedorRequisicao> listFornecedoresByRequisicao( long requisicaoCompraId, PageRequest pageable )
	{
		return this.fornecedorRequisicaoRepository.findByRequisicaoCompraId( requisicaoCompraId, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public FornecedorRequisicao findFornecedorRequisicaoById(long id)
	{
		FornecedorRequisicao fornecedorRequisicao = this.fornecedorRequisicaoRepository.findOne( id );
		Assert.notNull( fornecedorRequisicao, "Registro não existe" );
		
		return fornecedorRequisicao;
	}
	
	/*-------------------------------------------------------------------
	 *				 SERVICES AQUISICAO DE PRODUTOS
	 *-------------------------------------------------------------------*/
	
	/**
	 * Método para inserir uma aquisicao de produto automaticamente para aquela requisição
	 * @param requisicaoCompra
	 * @return
	 */
	private AquisicaoProduto insertAquisicaoProdutoTipoCompraRequisitada(RequisicaoCompra requisicaoCompra)
	{
		AquisicaoProduto aquisicaoProduto = new AquisicaoProduto();
		
		aquisicaoProduto.setTipoAquisicao( TipoAquisicao.COMPRA_REQUISITADA );
		aquisicaoProduto.setTipoPagamento( TipoPagamento.A_PRAZO );
		
		aquisicaoProduto = this.aquisicaoProdutoRepository.saveAndFlush( aquisicaoProduto );
		
		//Busca todos os fornecedores indicados naquela requisição
		List<FornecedorRequisicao> fornecedoresRequisicao = this.fornecedorRequisicaoRepository
				.findByRequisicaoCompraId( requisicaoCompra.getId(), null ).getContent();
		
		//Transforma os fornecedores da requisição em fornecedores da Aquisição
		for ( FornecedorRequisicao fornecedorRequisicao : fornecedoresRequisicao )
		{
			FornecedorAquisicao fornecedorAquisicao = new FornecedorAquisicao();
			fornecedorAquisicao.setAquisicaoProduto( aquisicaoProduto );
			fornecedorAquisicao.setFornecedor( fornecedorRequisicao.getFornecedor() );
		}
		
		aquisicaoProduto.setStatus( StatusAquisicao.ABERTO );
		return this.aquisicaoProdutoRepository.save( aquisicaoProduto );
	}
	
}


