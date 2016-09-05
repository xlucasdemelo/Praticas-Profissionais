/**
 * 
 */
package com.lucas.graca.domain.service.casalar;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.entity.casalar.CasaLar;
import com.lucas.graca.domain.repository.casalar.ICasaLarRepository;
import com.lucas.graca.domain.repository.planoatendimento.IResponsavelRepository;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
@PreAuthorize("hasAuthority('"+UserRole.ATENDENTE_VALUE+"') || hasAuthority('"+UserRole.OPERADOR_ADMINISNTRATIVO_VALUE+"') "
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
}
