/**
 * 
 */
package com.digows.blank.domain.repository.endereco;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digows.blank.domain.entity.endereco.Endereco;

/**
 * @author lucas
 *
 */
public interface IEnderecoService extends JpaRepository<Endereco, Long>
{

}
