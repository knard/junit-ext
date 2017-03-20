package org.knard.junitext.sql;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

public class SQLsInterceptor extends AbstractSQLInterceptor {

	public SQLsInterceptor(AnnotatedElement annotatedElement) {
		super(Arrays.asList(annotatedElement.getAnnotation(SQLs.class).value()));
	}

}
