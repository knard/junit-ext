package org.knard.junitext;

import java.lang.reflect.AnnotatedElement;

public class AnnotationConfiguration {
	private AnnotatedElement annotatedElement;

	public AnnotatedElement getAnnotatedElement() {
		return annotatedElement;
	}

	public void setAnnotatedElement(AnnotatedElement annotatedElement) {
		this.annotatedElement = annotatedElement;
	}

}
