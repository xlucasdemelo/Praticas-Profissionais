package com.lucas.graca.domain.repository.account.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.graca.domain.entity.account.User;

public class IUserRepositoryImpl implements UserDetailsService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Autowired
	private EntityManager entityManager;
	
	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	/* 
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername( String email ) throws UsernameNotFoundException
	{
		try
		{
			final String hql = "FROM User user "
							+ "WHERE user.email = :email";
			
			final TypedQuery<User> query = this.entityManager.createQuery( hql, User.class );
			query.setParameter("email", email);
			
			return query.getSingleResult();
		}
		catch (NoResultException e)
		{
			throw new UsernameNotFoundException("This email '"+email+"' was not found");
		}
	}
}
