package org.knard.junitext;

public interface JunitInterceptorFactory {

	public JUnitInterceptor create(Class<?> testClass, AnnotationConfiguration cfg) throws Exception;
	
}
