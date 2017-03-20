package org.knard.junitext;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class AnnotationDefintion {
	private boolean runLast = false;
	private AnnotatedElement annotatedElement;
	private Class<? extends Annotation> annotationType;
	private Class<? extends JunitInterceptorFactory> factory;
	private Class<? extends Annotation>[] optionalDependencies;
	private Class<? extends Annotation>[] mandatoryDependencies;

	public boolean isRunLast() {
		return runLast;
	}

	public void setRunLast(boolean runLast) {
		this.runLast = runLast;
	}

	public Class<? extends Annotation> getAnnotationType() {
		return annotationType;
	}

	public void setAnnotationType(Class<? extends Annotation> annotationType) {
		this.annotationType = annotationType;
	}

	public Class<? extends JunitInterceptorFactory> getFactory() {
		return factory;
	}

	public void setFactory(Class<? extends JunitInterceptorFactory> factory) {
		this.factory = factory;
	}

	public Class<? extends Annotation>[] getOptionalDependencies() {
		return optionalDependencies;
	}

	public void setOptionalDependencies(Class<? extends Annotation>[] optionalDependencies) {
		this.optionalDependencies = optionalDependencies;
	}

	public Class<? extends Annotation>[] getMandatoryDependencies() {
		return mandatoryDependencies;
	}

	public void setMandatoryDependencies(Class<? extends Annotation>[] mandatoryDependencies) {
		this.mandatoryDependencies = mandatoryDependencies;
	}

	public AnnotatedElement getAnnotatedElement() {
		return annotatedElement;
	}

	public void setAnnotatedElement(AnnotatedElement annotatedElement) {
		this.annotatedElement = annotatedElement;
	}

}
