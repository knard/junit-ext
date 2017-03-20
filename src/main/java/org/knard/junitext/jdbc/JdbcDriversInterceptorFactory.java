package org.knard.junitext.jdbc;

import org.knard.junitext.AnnotationConfiguration;
import org.knard.junitext.JUnitInterceptor;
import org.knard.junitext.JunitInterceptorFactory;

public class JdbcDriversInterceptorFactory implements JunitInterceptorFactory {

	@Override
	public JUnitInterceptor create(Class<?> testClass, AnnotationConfiguration cfg) {
		return new JdbcDriversInterceptor(cfg.getAnnotatedElement());
	}

}
