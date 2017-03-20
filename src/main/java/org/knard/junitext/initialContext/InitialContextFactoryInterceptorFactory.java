package org.knard.junitext.initialContext;

import org.knard.junitext.AnnotationConfiguration;
import org.knard.junitext.JUnitInterceptor;
import org.knard.junitext.JunitInterceptorFactory;

public class InitialContextFactoryInterceptorFactory implements JunitInterceptorFactory {

	@Override
	public JUnitInterceptor create(Class<?> testClass, AnnotationConfiguration cfg) {
		return new InitialContextFactoryInterceptor(cfg.getAnnotatedElement());
	}

}
