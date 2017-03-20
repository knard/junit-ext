package org.knard.junitext.jdbc;

import java.lang.reflect.AnnotatedElement;

public class JndiDatasourceInterceptor extends AbstractJndiDatasourceInterceptor {

	public JndiDatasourceInterceptor(AnnotatedElement annotatedElement) {
		super(annotatedElement.getAnnotation(JndiDatasource.class));
	}

}
