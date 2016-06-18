/**
 * 
 */
package com.lucas.graca.domain.repository.endereco;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.endereco.Endereco;

/**
 * @author lucas
 *
 */
public interface IEnderecoRepository extends JpaRepository<Endereco, Long>
{

}
