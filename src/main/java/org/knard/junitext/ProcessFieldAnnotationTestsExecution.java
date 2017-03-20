package org.knard.junitext;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;

public class ProcessFieldAnnotationTestsExecution extends ProcessFieldAnnotation {

	public ProcessFieldAnnotationTestsExecution(HashSet<Class<? extends Annotation>> dependenciesToSkip) {
		super(true, dependenciesToSkip);
	}

	@Override
	protected Invocation createInvocation(List<JUnitInterceptor> interceptors) {
		return new TestsExecutionInterceptorInvocation(interceptors.iterator());
	}
}
