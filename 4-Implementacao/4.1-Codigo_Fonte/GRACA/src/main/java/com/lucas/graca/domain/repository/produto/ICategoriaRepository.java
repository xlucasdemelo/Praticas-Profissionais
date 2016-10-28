/**
 * 
 */
package com.lucas.graca.domain.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.graca.domain.entity.produto.Categoria;

/**
 * @author eits
 *
 */
public interface ICategoriaRepository extends JpaRepository<Categoria, Long>
{

}
