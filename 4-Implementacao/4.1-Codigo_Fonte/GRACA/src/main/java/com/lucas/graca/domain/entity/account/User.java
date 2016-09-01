package com.lucas.graca.domain.entity.account;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Check;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.lucas.graca.domain.entity.redeapoio.RedeApoio;

import br.com.eits.common.domain.entity.AbstractEntity;

/**
 * 
 * @since 02/06/2014
 * @version 1.0
 * @category
 */
@Entity
@Audited
@Table(name = "\"user\"")
@DataTransferObject(javascript = "User")
@Check(constraints = "role <> 2 OR rede_apoio_id IS NOT NULL")
public class User extends AbstractEntity implements Serializable, UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4052986759552589018L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	// Basic
	/**
	 * 
	 */
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String name;
	/**
	 * 
	 */
	@Email
	@NotNull
	@Column(nullable = false, length = 144, unique = true)
	private String email;
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean enabled;
	/**
	 * 
	 */
	@NotBlank
	@Length(min = 8)
	@Column(nullable = false, length = 100)
	private String password;
	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private UserRole role;
	/**
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastLogin;
	
	/**
	 * 
	 */
	@ManyToOne(optional=true, fetch=FetchType.EAGER)
	private RedeApoio redeApoio;
	
	/*-------------------------------------------------------------------
	 * 		 					CONSTRUCTORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	public User()
	{
	}

	/**
	 * 
	 * @param id
	 */
	public User( Long id )
	{
		super( id );
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param email
	 * @param enabled
	 */
	public User( Long id, String name, String email, Boolean enabled )
	{
		super( id );
		this.email = email;
		this.name = name;
		this.enabled = enabled;
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param email
	 * @param enabled
	 * @param role
	 */
	public User( Long id, String name, String email, Boolean enabled, UserRole role, RedeApoio redeApoio )
	{
		super( id );
		this.email = email;
		this.name = name;
		this.enabled = enabled;
		this.role = role;
		this.redeApoio = redeApoio;
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param email
	 * @param enabled
	 * @param role
	 * @param password
	 */
	public User( Long id, String name, String email, Boolean enabled, UserRole role, String password )
	{
		super( id );
		this.email = email;
		this.name = name;
		this.enabled = enabled;
		this.password = password;
		this.role = role;
	}

	/*-------------------------------------------------------------------
	 *							BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Override
	@Transient
	public Set<GrantedAuthority> getAuthorities()
	{
		final Set<GrantedAuthority> authorities = new HashSet<>();

		if ( role == null )
		{
			return null;
		}
		
		authorities.add( role );

		if ( role.equals( UserRole.ADMINISTRATOR ) )
		{
			authorities.add( UserRole.COLABORADOR_EXTERNO );
			authorities.add( UserRole.ATENDENTE );
			authorities.add( UserRole.OPERADOR_ATENDIMENTOS );
			authorities.add( UserRole.CHEFE_ADMINISTRACAO );
			authorities.add( UserRole.OPERADOR_ADMINISNTRATIVO );
		}

		if ( role.equals( UserRole.ATENDENTE ) )
		{
			authorities.add( UserRole.OPERADOR_ATENDIMENTOS );
		}
		
		if (role.equals( UserRole.COLABORADOR_EXTERNO ) )
		{
			authorities.add( UserRole.COLABORADOR_EXTERNO );
		}
		
		if ( role.equals( UserRole.CHEFE_ADMINISTRACAO ) )
		{
			authorities.add( UserRole.OPERADOR_ADMINISNTRATIVO );
		}
		
		return authorities;
	}
	
	/**
	 * 
	 */
	public void validateChangePassword()
	{
		Assert.isTrue( (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal() == this
				|| SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains( UserRole.ADMINISTRATOR),
				"Operação não permitida");
	}
	
	/**
	 * 
	 * @param user
	 */
	public void mergeToUpdate( User user )
	{
		this.email = user.getEmail();
		this.name = user.getEmail();
	}
	
	/**
	 * 
	 */
	public void validateUsuarioExterno()
	{
		if (this.role == UserRole.COLABORADOR_EXTERNO)
		{
			Assert.notNull( this.redeApoio, "Para adicionar um usuário externo é obrigatório selecionar uma rede de apoio" );
		}
	}
	
	/**
	 * 
	 */
	@PrePersist
	public void enableUser()
	{
		this.enabled = true;
	}
	
	/**
	 * 
	 */
	public void disableUser()
	{
		this.enabled = false;
	}
	
	/*-------------------------------------------------------------------
	 *							GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	
	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isAccountNonExpired()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isAccountNonLocked()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isEnabled()
	{
		return this.enabled;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	@Transient
	public String getPassword()
	{
		return this.password;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	@Transient
	public String getUsername()
	{
		return this.email;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( email == null ) ? 0 : email.hashCode() );
		result = prime * result + ( ( enabled == null ) ? 0 : enabled.hashCode() );
		result = prime * result + ( ( lastLogin == null ) ? 0 : lastLogin.hashCode() );
		result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
		result = prime * result + ( ( password == null ) ? 0 : password.hashCode() );
		result = prime * result + ( ( redeApoio == null ) ? 0 : redeApoio.hashCode() );
		result = prime * result + ( ( role == null ) ? 0 : role.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj ) return true;
		if ( !super.equals( obj ) ) return false;
		if ( getClass() != obj.getClass() ) return false;
		User other = ( User ) obj;
		if ( email == null )
		{
			if ( other.email != null ) return false;
		}
		else if ( !email.equals( other.email ) ) return false;
		if ( enabled == null )
		{
			if ( other.enabled != null ) return false;
		}
		else if ( !enabled.equals( other.enabled ) ) return false;
		if ( lastLogin == null )
		{
			if ( other.lastLogin != null ) return false;
		}
		else if ( !lastLogin.equals( other.lastLogin ) ) return false;
		if ( name == null )
		{
			if ( other.name != null ) return false;
		}
		else if ( !name.equals( other.name ) ) return false;
		if ( password == null )
		{
			if ( other.password != null ) return false;
		}
		else if ( !password.equals( other.password ) ) return false;
		if ( redeApoio == null )
		{
			if ( other.redeApoio != null ) return false;
		}
		else if ( !redeApoio.equals( other.redeApoio ) ) return false;
		if ( role != other.role ) return false;
		return true;
	}

	/*-------------------------------------------------------------------
	 *						GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail( String email )
	{
		this.email = email;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled()
	{
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled( Boolean enabled )
	{
		this.enabled = enabled;
	}

	/**
	 * @return the role
	 */
	public UserRole getRole()
	{
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole( UserRole userRole )
	{
		this.role = userRole;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword( String password )
	{
		this.password = password;
	}

	/**
	 * @return the lastLogin
	 */
	public Calendar getLastLogin()
	{
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin( Calendar lastLogin )
	{
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the redeApoio
	 */
	public RedeApoio getRedeApoio()
	{
		return redeApoio;
	}

	/**
	 * @param redeApoio the redeApoio to set
	 */
	public void setRedeApoio( RedeApoio redeApoio )
	{
		this.redeApoio = redeApoio;
	}
}