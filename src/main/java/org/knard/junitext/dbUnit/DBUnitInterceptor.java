package org.knard.junitext.dbUnit;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

public class DBUnitInterceptor extends AbstractDBUnitInterceptor {

	public DBUnitInterceptor(AnnotatedElement annotatedElement) {
		super(Arrays.asList(annotatedElement.getAnnotation(DBUnit.class)));
	}
}
