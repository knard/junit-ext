package org.knard.junitext.cdi;

import static org.junit.Assert.assertNotNull;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

public class PersistenceContextAsInjectExtensionTest {

	@Test
	public void test() {
		Weld weld = new Weld();
		WeldContainer container = weld.initialize();
		PersistenceContextAsInjectExtensionTestBean bean = container.instance().select(PersistenceContextAsInjectExtensionTestBean.class).get();
		assertNotNull(bean.entityManager);
	}
	
}
