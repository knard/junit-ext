package org.knard.junitext;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;

public class ProcessFieldAnnotationTestMethodExecution extends ProcessFieldAnnotation {

	public ProcessFieldAnnotationTestMethodExecution(HashSet<Class<? extends Annotation>> dependenciesToSkip) {
		super(false, dependenciesToSkip);
	}

	@Override
	protected Invocation createInvocation(List<JUnitInterceptor> interceptors) {
		return new TestMethodInterceptorInvocation(interceptors.iterator());
	}
}
