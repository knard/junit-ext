package org.knard.junitext.cdi;

import org.knard.junitext.AnnotationConfiguration;
import org.knard.junitext.JUnitInterceptor;
import org.knard.junitext.JunitInterceptorFactory;

public class CdiInterceptorFactory implements JunitInterceptorFactory{

	@Override
	public JUnitInterceptor create(Class<?> testClass, AnnotationConfiguration cfg) {
		return new CdiInterceptor();
	}

}
