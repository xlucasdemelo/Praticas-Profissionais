/**
 * 
 */
package com.lucas.graca.domain.service.redeapoio;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.redeapoio.RedeApoio;
import com.lucas.graca.domain.repository.account.IUserRepository;
import com.lucas.graca.domain.repository.endereco.IEnderecoRepository;
import com.lucas.graca.domain.repository.planoatendimento.IResponsavelRepository;
import com.lucas.graca.domain.repository.redeApoio.IRedeApoioRepository;



/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"') ")
public class RedeApoioService
{
	
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private IRedeApoioRepository redeApoioRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IResponsavelRepository responsavelRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IUserRepository userRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IEnderecoRepository enderecoRepository;
	/*-------------------------------------------------------------------
	 *				 	SERVICES REDE DE APOIO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param redeApoio
	 * @return
	 */
	public RedeApoio insertRedeApoio( RedeApoio redeApoio )
	{
		Assert.notNull( redeApoio );
		Assert.isNull( redeApoio.getId(), "O ID deve ser nulo" );
		Assert.notNull( redeApoio.getNome(), "O campo nome é obrigatório" );
		
		if (redeApoio.getResponsavel() != null)
		{
			this.responsavelRepository.save( redeApoio.getResponsavel() );
		}
		
		if (redeApoio.getEndereco() != null)
		{
			this.enderecoRepository.saveAndFlush( redeApoio.getEndereco() );
		}
		
		return this.redeApoioRepository.save( redeApoio );
	}
	
	public Page<User> listUsersByRedeApoio( long redeApoioId, PageRequest pageable )
	{
		return this.userRepository.findByRedeApoioId( redeApoioId, pageable );
	}
	
	/**
	 * 
	 * @param redeApoio
	 */
	public RedeApoio updateRedeApoio( RedeApoio redeApoio )
	{
		Assert.notNull( redeApoio );
		Assert.notNull( redeApoio.getId(), "ID não pode ser nulo" );
		
		RedeApoio redeDB = this.redeApoioRepository.findOne( redeApoio.getId() );
		
		redeApoio.setEnabled( redeDB.getEnabled() );
		
		return this.redeApoioRepository.save( redeApoio );
	}
	
	/**
	 * 
	 * @param filter
	 * @param enabled
	 * @param pageable
	 * @return
	 */
	public Page<RedeApoio> listByFilters( String filter, boolean enabled, PageRequest pageable )
	{
		return this.redeApoioRepository.listByFilters( filter, enabled, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public RedeApoio findById( long id )
	{
		return this.redeApoioRepository.findOne( id );
	}
	
	/**
	 * 
	 * @param id
	 */
	public void disableRedeApoio( long id )
	{
		RedeApoio redeApoio = this.redeApoioRepository.findOne( id );
		redeApoio.disableRedeApoio();
		
		this.redeApoioRepository.save( redeApoio );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public RedeApoio enableRedeApoio( long id )
	{
		RedeApoio redeApoio = this.redeApoioRepository.findOne( id );
		redeApoio.enableRedeApoio();
		
		return this.redeApoioRepository.save( redeApoio );
	}
}
