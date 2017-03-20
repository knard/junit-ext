package org.knard.junitext.sql;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

public class SQLInterceptor extends AbstractSQLInterceptor {

	public SQLInterceptor(AnnotatedElement annotatedElement) {
		super(Arrays.asList(annotatedElement.getAnnotation(SQL.class)));
	}
	

}
