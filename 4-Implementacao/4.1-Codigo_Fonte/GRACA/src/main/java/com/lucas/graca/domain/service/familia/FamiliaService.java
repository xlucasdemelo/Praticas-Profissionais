/**
 * 
 */
package com.lucas.graca.domain.service.familia;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.familia.Familia;
import com.lucas.graca.domain.entity.familia.TipoImovel;
import com.lucas.graca.domain.entity.familia.TipoMoradia;
import com.lucas.graca.domain.entity.planoatendimentofamiliar.PlanoAtendimentoFamiliar;
import com.lucas.graca.domain.repository.endereco.IEnderecoRepository;
import com.lucas.graca.domain.repository.familia.IFamiliaRepository;
import com.lucas.graca.domain.repository.planoatendimento.IPlanoAtendimentoFamiliarRepository;

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
	
	/**
	 * 
	 */
	@Autowired
	private IPlanoAtendimentoFamiliarRepository planoAtendimentoFamiliarRepository ;
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
	 * @param filters
	 * @param pageable
	 * @return
	 */
	public Page<Familia> listFamiliasByFilters( String filters, PageRequest pageable )
	{
		return this.familiaRepository.listByFilters( filters, pageable );
	}
	
	/**
	 * 
	 * @param ativo
	 * @param inativo
	 * @param pageable
	 * @return
	 */
	public Page<Familia> listFamiliasByMoreFilters( Boolean ativo, Boolean inativo, PageRequest pageable )
	{
		return this.familiaRepository.listByMoreFilters( ativo, inativo, pageable );
	}
	
	/**
	 * 
	 * @param familia
	 * @return
	 */
	public Familia insertFamilia( Familia familia )
	{
		Assert.notNull( familia );
		
		if (familia.getEndereco() != null)
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
		
		List<PlanoAtendimentoFamiliar> planosAtendimentoFamiliar = this.planoAtendimentoFamiliarRepository.findByFamiliaId(familia.getId());
		
		for ( PlanoAtendimentoFamiliar planoAtendimentoFamiliar : planosAtendimentoFamiliar )
		{
			Assert.isTrue( !planoAtendimentoFamiliar.isEmExecucao(), "Família possuí um plano de atendimento em execução. finalize-o para excluir este registro" );
		}
		
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
