package org.knard.junitext.jdbc;

import java.lang.reflect.AnnotatedElement;

public class JdbcDriversInterceptor extends AbstractJdbcDriverInterceptor {

	public JdbcDriversInterceptor(AnnotatedElement annotatedElement) {
		super(annotatedElement.getAnnotation(JdbcDrivers.class).value());
	}
}
