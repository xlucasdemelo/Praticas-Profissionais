/**
 * 
 */
package com.digows.blank.domain.service.familia;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.digows.blank.domain.entity.familia.Familia;
import com.digows.blank.domain.entity.familia.TipoImovel;
import com.digows.blank.domain.entity.familia.TipoMoradia;
import com.digows.blank.domain.repository.endereco.IEnderecoRepository;
import com.digows.blank.domain.repository.familia.IFamiliaRepository;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
public class FamiliaService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private IFamiliaRepository familiaRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IEnderecoRepository enderecoRepository;
	
	/*-------------------------------------------------------------------
	 *				 		 SERVICES FAMILIA
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Familia findFamiliaById(Long id)
	{
		return this.familiaRepository.findOne( id );
	}
	
	/**
	 * 
	 * @return
	 */
	public Page<Familia> listFamiliasByFilters( String filters, PageRequest pageable )
	{
		return this.familiaRepository.listByFilters( filters, pageable );
	}
	
	/**
	 * 
	 * @param familia
	 * @return
	 */
	public Familia insertFamilia( Familia familia )
	{
		Assert.notNull( familia );
		this.enderecoRepository.save( familia.getEndereco() );
		return this.familiaRepository.save( familia );
	}
	
	/**
	 * 
	 * @param familia
	 * @return
	 */
	public Familia updateFamilia( Familia familia )
	{
		Assert.notNull( familia );
		return this.familiaRepository.save( familia );
	}
	
	/**
	 * 
	 * @param familia
	 * @return
	 */
	public Familia disableFamilia( Familia familia )
	{
		Assert.notNull( familia );
		
		familia = this.familiaRepository.findOne( familia.getId() );
		familia.disableFamilia();
		
		return this.familiaRepository.save( familia );
	}
	
	/**
	 * 
	 * @param familia
	 * @return
	 */
	public Familia enableFamilia( Familia familia )
	{
		Assert.notNull( familia );
		
		familia = this.familiaRepository.findOne( familia.getId() );
		familia.enableFamilia();
		
		return this.familiaRepository.save( familia );
	}
	
	/**
	 * 
	 * @return
	 */
	public TipoImovel[] listAllTiposImovel()
	{
		return TipoImovel.values(  );
	}
	
	/**
	 * 
	 * @return
	 */
	public TipoMoradia[] listAllTiposMoradia()
	{
		return TipoMoradia.values(  );
	}
}
