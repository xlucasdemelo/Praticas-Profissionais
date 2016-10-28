/**
 * 
 */
package com.lucas.graca.domain.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.produto.Marca;

/**
 * @author eits
 *
 */
public interface IMarcaRepository extends JpaRepository<Marca, Long>
{

}
