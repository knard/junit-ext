package org.knard.junitext.jdbc;

import java.lang.reflect.AnnotatedElement;

public class JdbcDriverInterceptor extends AbstractJdbcDriverInterceptor {

	public JdbcDriverInterceptor(AnnotatedElement annotatedElement) {
		super(annotatedElement.getAnnotation(JdbcDriver.class));
	}

}
