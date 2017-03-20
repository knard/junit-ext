package org.knard.junitext.dbUnit;

import org.knard.junitext.AnnotationConfiguration;
import org.knard.junitext.JUnitInterceptor;
import org.knard.junitext.JunitInterceptorFactory;

public class DBUnitsInterceptorFactory implements JunitInterceptorFactory {

	@Override
	public JUnitInterceptor create(Class<?> testClass, AnnotationConfiguration cfg) {
		return new DBUnitsInterceptor(cfg.getAnnotatedElement());
	}

}
