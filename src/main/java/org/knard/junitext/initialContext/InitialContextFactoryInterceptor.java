package org.knard.junitext.initialContext;

import java.lang.reflect.AnnotatedElement;

import org.knard.junitext.Invocation;
import org.knard.junitext.InvocationContext;
import org.knard.junitext.JUnitInterceptor;

public class InitialContextFactoryInterceptor extends JUnitInterceptor {

	private final String INITIAL_CONTEXT_FACTORY_PROPERTY = "java.naming.factory.initial";
	private String oldValue;
	private String value;

	public InitialContextFactoryInterceptor(AnnotatedElement annotatedElement) {
		InitialContextFactory initialContextFactory = annotatedElement.getAnnotation(InitialContextFactory.class);
		this.value = initialContextFactory.value();
	}

	@Override
	public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		setup(ctx);
		try {
			nextInvocation.invoke(ctx);
		} finally {
			tearDown();
		}
	}

	@Override
	public void interceptMethod(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		setup(ctx);
		try {
			nextInvocation.invoke(ctx);
		} finally {
			tearDown();
		}
	}

	private void tearDown() {
		if (oldValue != null) {
			System.setProperty(INITIAL_CONTEXT_FACTORY_PROPERTY, oldValue);
			oldValue = null;
		}
	}

	private void setup(InvocationContext ctx) {
		oldValue = System.setProperty(INITIAL_CONTEXT_FACTORY_PROPERTY, value);
	}

}
