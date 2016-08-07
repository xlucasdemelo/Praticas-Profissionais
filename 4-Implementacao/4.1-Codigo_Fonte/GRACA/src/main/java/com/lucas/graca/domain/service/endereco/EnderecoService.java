/**
 * 
 */
package com.lucas.graca.domain.service.endereco;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.graca.domain.entity.endereco.Cidade;
import com.lucas.graca.domain.entity.endereco.Estado;
import com.lucas.graca.domain.entity.endereco.Pais;
import com.lucas.graca.domain.repository.endereco.ICidadeRepository;
import com.lucas.graca.domain.repository.endereco.IEstadoRepository;
import com.lucas.graca.domain.repository.endereco.IPaisRepository;

/**
 * @author lucas
 *
 */
@Service
@RemoteProxy
@Transactional
public class EnderecoService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Autowired
	private ICidadeRepository cidadeRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IEstadoRepository estadoRepository;
	
	/**
	 * 
	 */
	@Autowired
	private IPaisRepository paisRepository;
	
	/*-------------------------------------------------------------------
	 *				 		 SERVICES ENDEREÇO
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	public Page<Pais> listPaisesByFilters( String filters, PageRequest pageable )
	{
		return this.paisRepository.listPaisesByFilters( filters, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Pais findPaisById(Long id)
	{
		return this.paisRepository.findOne( id );
	}
	
	/**
	 * 
	 * @param filters
	 * @param paisId
	 * @param pageable
	 * @return
	 */
	public Page<Estado> listEstadosByFIlters(String filters, Long paisId, PageRequest pageable)
	{
		return this.estadoRepository.listEstadosByFilters( filters, paisId, pageable );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Estado findEstadoById( Long id )
	{
		return this.estadoRepository.findOne( id);
	}
	
	/**
	 * 
	 * @param filters
	 * @param estadoId
	 * @param pageable
	 * @return
	 */
	public Page<Cidade> listCidadesByFIlters(String filters, Long estadoId, PageRequest pageable)
	{
		return this.cidadeRepository.listEstadosByFilters( filters, estadoId, pageable );
	}
	
	/**
	 * 
	 * @param cidadeId
	 * @return
	 */
	public Cidade findCidadeById( Long cidadeId )
	{
		return this.cidadeRepository.findOne( cidadeId );
	}
}
