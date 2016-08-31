package com.lucas.graca.domain.service;

import java.util.Calendar;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.account.User;
import com.lucas.graca.domain.entity.account.UserRole;
import com.lucas.graca.domain.repository.account.IUserRepository;

/**
 * 
 * @author rodrigo.p.fraga@gmail.com
 */
@Service
@RemoteProxy
@Transactional
public class AccountService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * Password encoder
	 */
	@Autowired
	private ShaPasswordEncoder passwordEncoder;

	/**
	 * Hash generator for encryption
	 */
	@Autowired
	private SaltSource saltSource;
	
	/**
	 * 
	 */
	@Autowired
	private MessageSource messageSorce;

	//Repositories
	/**
	 * 
	 */
	@Autowired
	private IUserRepository userRepository;

	/*-------------------------------------------------------------------
	 *				 		     SERVICES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 * @param event
	 */
	@PreAuthorize("isAuthenticated() && #user.id == principal.id")
	public void updateLastUserLogin( User user ) 
	{
		Assert.notNull( user );
		user = this.findUserById( user.getId() );
		user.setLastLogin( Calendar.getInstance() );
		this.userRepository.save( user );
    }
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('"+UserRole.ADMINISTRATOR_VALUE+"')")
	public User insertUser( User user )
	{
		Assert.notNull( user );
		
		user.validateUsuarioExterno();
		
		user.setEnabled( true );
		// encrypt password
		final String encodedPassword = this.passwordEncoder.encodePassword( user.getPassword(), this.saltSource.getSalt( user ) );
		user.setPassword( encodedPassword );

		return this.userRepository.save( user );
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public User updateUser( User user )
	{
		Assert.notNull( user );
		Assert.isNull( user.getPassword(), "Acesso negado" );
		
		User userBd = this.userRepository.findOne( user.getId() );
		
		userBd.mergeToUpdate( user );
		
		return this.userRepository.save( userBd );
	}
	
	/**
	 * 
	 * @param user
	 * @param role
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('"+UserRole.ADMINISTRATOR_VALUE+"')")
	public User changeUserRole( User user )
	{
		Assert.notNull( user );
		Assert.isNull( user.getPassword(), "Acesso negado" );
		
		User userBd = this.userRepository.findOne( user.getId() );
		
		userBd.mergeToUpdate( user );
		userBd.setRole( user.getRole() );
		
		return this.userRepository.save( userBd );
	}
	
	/**
	 * 
	 * @param user
	 * @param password
	 * @param confirmPassword
	 * @return
	 */
	public User changePassword( long userId, String password, String confirmPassword )
	{
		Assert.isTrue( password.equals( confirmPassword ), "As senhas precisam ser iguais" );
		
		User user = this.userRepository.findOne( userId );
		
		user.validateChangePassword();
		
		final String encodedPassword = this.passwordEncoder.encodePassword( password, this.saltSource.getSalt( user ) );
		user.setPassword( encodedPassword );
		
		return this.userRepository.save( user );
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public User findUserById( Long id )
	{
		final User user = this.userRepository.findOne( id );
		Assert.notNull( user, this.messageSorce.getMessage("repository.notFoundById", new Object[]{id}, LocaleContextHolder.getLocale()) );
		return user;
	}
	
	/**
	 * 
	 * @param pageable
	 * @param filters
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<User> listUsersByFilters( String filter, PageRequest pageable )
	{
		return this.userRepository.listByFilters( filter, pageable );
	}
}