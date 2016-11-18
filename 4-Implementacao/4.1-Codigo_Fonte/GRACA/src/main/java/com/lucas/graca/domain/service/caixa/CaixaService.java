/**
 * 
 */
package com.lucas.graca.domain.service.caixa;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.caixa.Banco;
import com.lucas.graca.domain.entity.caixa.Conta;
import com.lucas.graca.domain.entity.caixa.Movimentacao;
import com.lucas.graca.domain.entity.caixa.MovimentacaoCaixa;
import com.lucas.graca.domain.repository.caixa.IBancoRepository;
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
	@Autowired
	private IContaBancariaRepository contaBancariaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IMovimentacaoRepository movimentcacaoRepository ;
	
	/**
	 * 
	 */
	@Autowired
	private IMovimentacaoContaRepository movimentcacaoContaRepository ;
	
	/**
	 * 
	 */
	@Autowired
	private IBancoRepository bancoRepository;
	
	/*-------------------------------------------------------------------
	 *				 		SERVICES CONTA BANCARIA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param conta
	 * @return
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public Conta insertContaBancaria(Conta conta)
	{
		Assert.isNull( conta.getId(), "Id deve ser nulo" );
		Assert.notNull( conta, "Conta bancária não pode ser nula" );
		Assert.notNull( conta.getAgencia(), "Agência não pode ser nula" );
		Assert.notNull( conta.getNumero(), "Número não pode ser nulo" );
		Assert.notNull( conta.getBanco() );
		
		return this.contaBancariaRepository.save( conta );
	}
	
	/**
	 * 
	 * @param conta
	 * @return
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public Conta updateContaBancaria(Conta conta)
	{
		Assert.notNull( conta, "Conta bancária não pode ser nula" );
		Assert.notNull( conta.getId(), "Id Não pode ser nulo" );
		
		Conta contaBancariaBD = this.contaBancariaRepository.findOne( conta.getId() );
		Assert.notNull( contaBancariaBD, "Não existe nenhum registro com esse id" );
		
		Assert.notNull( conta.getAgencia(), "Agência não pode ser nula" );
		Assert.notNull( conta.getNumero(), "Número não pode ser nulo" );
		Assert.notNull( conta.getBanco() );
		
		return this.contaBancariaRepository.save( conta );
	}
	
	/**
	 * 
	 * @param contaBancaria
	 * @return
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public void disableContaBancaria( long id )
	{
		Conta conta = this.contaBancariaRepository.findOne( id );
		Assert.notNull( conta, "Não existe nenhum registro insecom esse id" );
		
		conta.disableContaBancaria();
		
		this.contaBancariaRepository.save( conta );
	}
	
	/**
	 * 
	 * @param id
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public void enableContaBancaria( long id )
	{
		Conta conta = this.contaBancariaRepository.findOne( id );
		Assert.notNull( conta, "Não existe nenhum registro com esse id" );
		
		conta.enableContaBancaria();
		
		this.contaBancariaRepository.save( conta );
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Conta> listContaBancariaByFilters( String filter, Boolean ativo, PageRequest pageable )
	{
		return this.contaBancariaRepository.listByFilters( filter, ativo, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Conta findContaBancariaById( long id )
	{
		Conta conta = this.contaBancariaRepository.findOne( id );
		Assert.notNull( conta, "Registro não encontrado" );
		
		return conta;
	}
	
	/*-------------------------------------------------------------------
	 *				 		SERVICES BANCO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param banco
	 * @return
	 */
	public Banco insertBanco( Banco banco )
	{
		Assert.notNull( banco );
		Assert.isNull( banco.getId(), "Id não pode ser nulo" );
		Assert.notNull( banco.getNome(), "Nome não pode ser nulo" );
		
		Assert.isNull( this.bancoRepository.findByNomeAndEnabled( banco.getNome(), true ), "Já existe um registro com esse nome");
		
		return this.bancoRepository.save( banco );
	}
	
	/**
	 * 
	 * @param filter
	 * @param pageable
	 * @return
	 */
	public Page<Banco> listBancosByFilters( String filter, Boolean ativo, PageRequest pageable )
	{
		return this.bancoRepository.listByFilters( filter, ativo, pageable );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void disableBanco( long id )
	{
		Banco banco = this.bancoRepository.findOne( id );
		Assert.notNull( banco, "Registro nao existe" );
		
		banco.disableBanco();
		this.bancoRepository.save( banco );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Banco enableBanco( long id )
	{
		Banco banco = this.bancoRepository.findOne( id );
		Assert.notNull( banco, "Registro nao existe" );
		
		banco.enableBanco();
		return this.bancoRepository.save( banco );
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
