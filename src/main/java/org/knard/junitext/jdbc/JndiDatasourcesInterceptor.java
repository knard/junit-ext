package org.knard.junitext.jdbc;

import java.lang.reflect.AnnotatedElement;

public class JndiDatasourcesInterceptor extends AbstractJndiDatasourceInterceptor {

	public JndiDatasourcesInterceptor(AnnotatedElement annotatedElement) {
		super(annotatedElement.getAnnotation(JndiDatasources.class).value());
	}

}
