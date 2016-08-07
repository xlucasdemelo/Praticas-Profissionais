/**
 * 
 */
package com.digows.blank.test.domain.entity.account;

import org.junit.Assert;
import org.junit.Test;

import com.digows.blank.test.domain.AbstractUnitTests;
import com.lucas.graca.domain.entity.integrantefamiliar.IntegranteFamiliar;

/**
 * @author lucas
 *
 */
public class IntegranteFamiliarTests extends AbstractUnitTests
{
	/**
	 * 
	 */
	@Test
	public void disableIntegranteFamiliarMustPass()
	{
		IntegranteFamiliar integranteFamiliar = new IntegranteFamiliar();
		
		integranteFamiliar.disableIntegranteFamiliar();
		
		Assert.assertFalse( integranteFamiliar.getAtivo() );
	}
	
	/**
	 * 
	 */
	@Test
	public void disableIntegranteFamiliarIntegranteDisabled()
	{
		IntegranteFamiliar integranteFamiliar = new IntegranteFamiliar();
		integranteFamiliar.setAtivo( false );
		
		integranteFamiliar.disableIntegranteFamiliar();
		
		Assert.assertFalse( integranteFamiliar.getAtivo() );
	}
	
	/**
	 * 
	 */
	@Test
	public void enableIntegranteFamiliarMustPass()
	{
		IntegranteFamiliar integranteFamiliar = new IntegranteFamiliar();
		integranteFamiliar.setAtivo( false );
		
		integranteFamiliar.enableIntegranteFamiliar();
		
		Assert.assertTrue( integranteFamiliar.getAtivo() );
	}
}