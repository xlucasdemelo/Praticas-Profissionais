/**
 * 
 */
package com.lucas.graca.domain.service.caixa;

import java.math.BigDecimal;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.caixa.Conta;
import com.lucas.graca.domain.entity.caixa.Movimentacao;
import com.lucas.graca.domain.repository.caixa.IContaRepository;
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
	private IContaRepository contaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IMovimentacaoRepository movimentcacaoRepository ;
	
	/*-------------------------------------------------------------------
	 *				 		SERVICES CONTA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param conta
	 * @return
	 */
	public Conta insertConta(Conta conta)
	{
		Assert.isNull( conta.getId(), "Id deve ser nulo" );
		Assert.notNull( conta, "Conta bancária não pode ser nula" );
		Assert.notNull( conta.getDescricao(), "Descricao não pode ser nula" );
		Assert.notNull( conta.getNome(), "Nome não pode ser nulo" );
		
		Assert.isNull(this.contaRepository.findByNomeAndEnabled(conta.getNome(), true),
				"Já existe uma conta com este nome");
		
		if (conta.getSaldo() == null)
		{
			conta.setSaldo(new BigDecimal("0"));
		}
		
		return this.contaRepository.save( conta );
	}
	
	/**
	 * 
	 * @param conta
	 * @return
	 */
	@PreAuthorize( "hasAuthority('" + UserRole.CHEFE_ADMINISTRACAO_VALUE + "')" )
	public Conta updateConta(Conta conta)
	{
		Assert.notNull( conta, "Conta bancária não pode ser nula" );
		Assert.notNull( conta.getId(), "Id Não pode ser nulo" );
		
		Conta contaBD = this.contaRepository.findOne( conta.getId() );
		Assert.notNull( contaBD, "Não existe nenhum registro com esse id" );
		
		Assert.notNull( conta.getDescricao(), "Descricao não pode ser nula" );
		Assert.notNull( conta.getNome(), "Nome não pode ser nulo" );
		
		contaBD.setNome(conta.getNome());
		contaBD.setDescricao(conta.getDescricao());
		
		return this.contaRepository.save( contaBD );
	}
	
	/**
	 * 
	 * @param conta
	 * @return
	 */
	public void disableConta( long id )
	{
		Conta conta = this.contaRepository.findOne( id );
		Assert.notNull( conta, "Não existe nenhum registro insecom esse id" );
		
		conta.disable();
		
		this.contaRepository.save( conta );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void enableConta( long id )
	{
		Conta conta = this.contaRepository.findOne( id );
		Assert.notNull( conta, "Não existe nenhum registro com esse id" );
		
		conta.enable();
		
		this.contaRepository.save( conta );
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Conta> listContaByFilters( String filter, Boolean ativo, PageRequest pageable )
	{
		return this.contaRepository.listByFilters( filter, ativo, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public Conta findContaById( long id )
	{
		Conta conta = this.contaRepository.findOne( id );
		Assert.notNull( conta, "Registro não encontrado" );
		
		return conta;
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
	
}
