package org.knard.junitext.jaxb;

import java.lang.reflect.Field;

import org.knard.junitext.AnnotationConfiguration;
import org.knard.junitext.JUnitInterceptor;
import org.knard.junitext.JunitInterceptorFactory;

public class FromXmlResourceInterceptorFactory implements JunitInterceptorFactory {

	@Override
	public JUnitInterceptor create(Class<?> testClass, AnnotationConfiguration cfg) {
		return new FromXmlResourceInterceptor((Field) cfg.getAnnotatedElement(), testClass.getClassLoader());
	}

}
