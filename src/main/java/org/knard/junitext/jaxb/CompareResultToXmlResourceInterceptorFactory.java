package org.knard.junitext.jaxb;

import org.knard.junitext.AnnotationConfiguration;
import org.knard.junitext.JUnitInterceptor;
import org.knard.junitext.JunitInterceptorFactory;

public class CompareResultToXmlResourceInterceptorFactory implements JunitInterceptorFactory {

	@Override
	public JUnitInterceptor create(Class<?> testClass, AnnotationConfiguration cfg) throws Exception {
		return new CompareResultToXmlResourceInterceptor(cfg.getAnnotatedElement(), testClass);
	}

}
