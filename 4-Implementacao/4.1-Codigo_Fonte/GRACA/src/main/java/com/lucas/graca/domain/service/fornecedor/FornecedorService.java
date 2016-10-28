/**
 * 
 */
package com.lucas.graca.domain.service.fornecedor;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.fornecedor.Fornecedor;
import com.lucas.graca.domain.repository.fornecedor.IFornecedorRepository;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
@PreAuthorize( "hasAuthority('" + UserRole.OPERADOR_ADMINISTRATIVO_VALUE + "')" )
public class FornecedorService  
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private IFornecedorRepository fornecedorRepository; 
	
	/*-------------------------------------------------------------------
	 *				 		 SERVICES FORNECDOR
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @return
	 */
	public Fornecedor insertFornecedor(Fornecedor fornecedor)
	{
		Assert.notNull( fornecedor, "Fornecedor não pode ser nulo" );
		Assert.notNull( fornecedor.getRazaoSocial(), "Razão social não pode ser nula" );
		Assert.notNull( fornecedor.getCnpj(), "CNPJ não pode ser nulo" );
		Assert.notNull( fornecedor.getResponsavel(), "Responsável não pode ser nulo" );
		
		Assert.isNull( fornecedor.getId(), "Id precisa ser nulo" );
		
		return this.fornecedorRepository.save( fornecedor );
	}
	
	/**
	 * 
	 * @param fornecedor
	 * @return
	 */
	public Fornecedor updateFornecedor(Fornecedor fornecedor)
	{
		Assert.notNull( fornecedor, "Fornecedor não pode ser nulo" );
		Assert.notNull( fornecedor.getRazaoSocial(), "Razão social não pode ser nula" );
		Assert.notNull( fornecedor.getCnpj(), "CNPJ não pode ser nulo" );
		Assert.notNull( fornecedor.getResponsavel(), "Responsável não pode ser nulo" );
		
		Assert.notNull( fornecedor.getId(), "Id não pode ser nulo" );
		
		return this.fornecedorRepository.save( fornecedor );
	}
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<Fornecedor> listFornecedoresByFilters( String filter, Boolean ativo, PageRequest pageable )
	{
		return this.fornecedorRepository.listByFilters( filter, ativo, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Fornecedor findById(long id)
	{
		return this.fornecedorRepository.findOne( id );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void disableFornecedor( long id )
	{
		Fornecedor fornecedor = this.fornecedorRepository.findOne( id );
		Assert.notNull( fornecedor, "Fornecedor não encontrado" );
		fornecedor.disableFornecedor();
		
		this.fornecedorRepository.saveAndFlush( fornecedor );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void enableFornecedor( long id )
	{
		Fornecedor fornecedor = this.fornecedorRepository.findOne( id );
		Assert.notNull( fornecedor, "Fornecedor não encontrado" );
		fornecedor.enableFornecedor();
		
		this.fornecedorRepository.saveAndFlush( fornecedor );
	}
}
