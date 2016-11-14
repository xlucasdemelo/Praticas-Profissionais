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
import com.lucas.graca.domain.entity.casalar.Beneficiado;
import com.lucas.graca.domain.entity.casalar.CasaLar;
import com.lucas.graca.domain.entity.casalar.OrcamentoFamiliar;
import com.lucas.graca.domain.entity.casalar.RequisicaoCompra;
import com.lucas.graca.domain.entity.casalar.StatusRequisicaoCompra;
import com.lucas.graca.domain.entity.crianca.Crianca;
import com.lucas.graca.domain.repository.casalar.IBeneficiadoRepository;
import com.lucas.graca.domain.repository.casalar.ICasaLarRepository;
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
		
		this.requisicaoCompraRepository.delete( requisicao );
	}
	
	/**
	 * 
	 * @param casaLarId
	 * @param pageable
	 * @return
	 */
	public Page<RequisicaoCompra> listByCasaLarAndFilters( Long casaLarId, String filter, PageRequest pageable )
	{
		return this.requisicaoCompraRepository.listByCasaLarAndFilters( casaLarId, filter, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public RequisicaoCompra findById(long id)
	{
		RequisicaoCompra requisicao = this.requisicaoCompraRepository.findOne( id );
		Assert.notNull( requisicao, "Requisição não encontrada" );
		
		return requisicao;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void changeToEmAberto(long id)
	{
		RequisicaoCompra requisicao = this.requisicaoCompraRepository.findOne( id );
		Assert.notNull( requisicao, "Requisição não encontrada" );
		
		requisicao.changeToEmAberto();
		
		this.requisicaoCompraRepository.save( requisicao );
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
	
	/**
	 * 
	 * @param beneficiado
	 * @return
	 */
	public Beneficiado insertBeneficiado(Beneficiado beneficiado)
	{
		Assert.notNull( beneficiado, "Beneficiado é obrigatório" );
		
		return this.beneficiadoRepository.save( beneficiado );
	}
	
	/**
	 * 
	 * @return
	 */
	public Page<Beneficiado> listBeneficiadosByCasaLar(long requisicaoCompraId, PageRequest pageable)
	{
		return this.beneficiadoRepository.findByRequisicaoCompraId( requisicaoCompraId, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
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
	
//	public FornecedorRequ
}






