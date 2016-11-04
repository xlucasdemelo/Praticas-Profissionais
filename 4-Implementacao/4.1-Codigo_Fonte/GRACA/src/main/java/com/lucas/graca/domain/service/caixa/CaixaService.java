/**
 * 
 */
package com.lucas.graca.domain.service.caixa;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.caixa.ContaBancaria;
import com.lucas.graca.domain.entity.caixa.Movimentacao;
import com.lucas.graca.domain.entity.caixa.MovimentacaoCaixa;
import com.lucas.graca.domain.repository.caixa.IContaBancariaRepository;
import com.lucas.graca.domain.repository.caixa.IMovimentacaoContaRepository;
import com.lucas.graca.domain.repository.caixa.IMovimentacaoRepository;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
@PreAuthorize( "hasAuthority('" + UserRole.OPERADOR_ADMINISTRATIVO_VALUE + "')" )
public class CaixaService
{
	/**
	 * 
	 */
	private IContaBancariaRepository contaBancariaRepository;
	
	/**
	 * 
	 */
	private IMovimentacaoRepository movimentcacaoRepository ;
	
	/**
	 * 
	 */
	private IMovimentacaoContaRepository movimentcacaoContaRepository ;
	
	/*-------------------------------------------------------------------
	 *				 		SERVICES CONTA BANCARIA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param contaBancaria
	 * @return
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public ContaBancaria insertContaBancaria(ContaBancaria contaBancaria)
	{
		Assert.isNull( contaBancaria.getId(), "Id deve ser nulo" );
		Assert.notNull( contaBancaria, "Conta bancária não pode ser nula" );
		Assert.notNull( contaBancaria.getAgencia(), "Agência não pode ser nula" );
		Assert.notNull( contaBancaria.getNumero(), "Número não pode ser nulo" );
		Assert.notNull( contaBancaria.getBanco() );
		
		return this.contaBancariaRepository.save( contaBancaria );
	}
	
	/**
	 * 
	 * @param contaBancaria
	 * @return
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public ContaBancaria updateContaBancaria(ContaBancaria contaBancaria)
	{
		Assert.notNull( contaBancaria, "Conta bancária não pode ser nula" );
		Assert.notNull( contaBancaria.getId(), "Id Não pode ser nulo" );
		
		ContaBancaria contaBancariaBD = this.contaBancariaRepository.findOne( contaBancaria.getId() );
		Assert.notNull( contaBancariaBD, "Não existe nenhum registro com esse id" );
		
		Assert.notNull( contaBancaria.getAgencia(), "Agência não pode ser nula" );
		Assert.notNull( contaBancaria.getNumero(), "Número não pode ser nulo" );
		Assert.notNull( contaBancaria.getBanco() );
		
		return this.contaBancariaRepository.save( contaBancaria );
	}
	
	/**
	 * 
	 * @param contaBancaria
	 * @return
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public void disableContaBancaria( long id )
	{
		ContaBancaria contaBancaria = this.contaBancariaRepository.findOne( id );
		Assert.notNull( contaBancaria, "Não existe nenhum registro com esse id" );
		
		contaBancaria.disableContaBancaria();
		
		this.contaBancariaRepository.save( contaBancaria );
	}
	
	/**
	 * 
	 * @param id
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public void enableContaBancaria( long id )
	{
		ContaBancaria contaBancaria = this.contaBancariaRepository.findOne( id );
		Assert.notNull( contaBancaria, "Não existe nenhum registro com esse id" );
		
		contaBancaria.enableContaBancaria();
		
		this.contaBancariaRepository.save( contaBancaria );
	}
	
	/**
	 * 
	 * @return
	 */
	public Page<ContaBancaria> listContaBancariaByFilters( String filter, Boolean ativo, PageRequest pageable )
	{
		return this.contaBancariaRepository.listByFilters( filter, ativo, pageable );
	}
	
	/*-------------------------------------------------------------------
	 *				 		SERVICES MOVIMENTAÇÃO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param movimentacao
	 * @return
	 */
	public Movimentacao insertMovimentacao( Movimentacao movimentacao )
	{
		Assert.notNull( movimentacao, "Movimentação não pode ser nula" );
		Assert.isNull( movimentacao.getId(), "Id deve ser nulo" );
		
//		Assert.notNull( movimentacao.getNumeroDocumento(), "Número do documento não pode ser nulo" );
//		Assert.notNull( movimentacao.getDataEmissao(), "Data de emissão não pode ser nula" );
//		Assert.notNull( movimentacao.getDataPagamento(), "Data de pagamento não pode ser nulo" );
//		Assert.notNull( movimentacao.getTipoDocumento(), "Tipo de documento não pode ser nulo" );
//		Assert.notNull( movimentacao.getTipoMovimentacao(), "Tipo de movimentação não pode ser nulo" );
//		
//		Assert.notNull( movimentacao.getFormaPagamento(), "Forma de pagamento não pode ser nulo" );
//		movimentacao.validateFormaPagamento();
		
		return this.movimentcacaoRepository.save( movimentacao );
	}
	
	/**
	 * 
	 */
	public void removeMovimentacao( long id )
	{
		this.movimentcacaoRepository.delete( id );
	}
	
	/*-------------------------------------------------------------------
	 *				    SERVICES MOVIMENTAÇÃO CONTA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param movimentacaoCaixa
	 * @return
	 */
	public MovimentacaoCaixa insertMovimentacaoConta( MovimentacaoCaixa movimentacaoCaixa )
	{
		Assert.notNull( movimentacaoCaixa );
		Assert.notNull( movimentacaoCaixa.getValor(), "Valor não pode ser nulo" );
		Assert.notNull( movimentacaoCaixa.getContaBancaria() );
		Assert.notNull( movimentacaoCaixa.getMovimentacao() );
		
		return this.movimentcacaoContaRepository.save( movimentacaoCaixa );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void removeMovimentacaoConta( long id )
	{
		MovimentacaoCaixa movimentacaoConta = this.movimentcacaoContaRepository.findOne( id );
		Assert.notNull( movimentacaoConta, "Registro não encontrado" );
		
		this.movimentcacaoContaRepository.delete( movimentacaoConta );
	}
	
	/**
	 * 
	 * @return
	 */
	public MovimentacaoCaixa findMovimentacaoContaById( long id )
	{
		MovimentacaoCaixa movimentacaoConta = this.movimentcacaoContaRepository.findOne( id );
		Assert.notNull( movimentacaoConta, "Registro não encontrado" );
		
		return movimentacaoConta;
	}
	
	/**
	 * 
	 * @param movimentacaoId
	 */
	public Page<MovimentacaoCaixa> listMovimentacoesContaByMovimentacao( long movimentacaoId, PageRequest pageable )
	{
		return this.movimentcacaoContaRepository.listByMovimentacao( movimentacaoId, pageable );
	}
}
