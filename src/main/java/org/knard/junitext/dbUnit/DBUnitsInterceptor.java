package org.knard.junitext.dbUnit;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

public class DBUnitsInterceptor extends AbstractDBUnitInterceptor {

	public DBUnitsInterceptor(AnnotatedElement annotatedElement) {
		super( Arrays.asList(annotatedElement.getAnnotation(DBUnits.class).value()));
	}

}
