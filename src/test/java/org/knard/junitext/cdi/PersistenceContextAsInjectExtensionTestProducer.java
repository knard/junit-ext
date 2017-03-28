package org.knard.junitext.cdi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

public class PersistenceContextAsInjectExtensionTestProducer {

	@Produces
	public EntityManager create() {
		return (EntityManager) Proxy.newProxyInstance(
				PersistenceContextAsInjectExtensionTestProducer.class.getClassLoader(),
				new Class[] { EntityManager.class }, new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						return null;
					}
				});
	}

}
