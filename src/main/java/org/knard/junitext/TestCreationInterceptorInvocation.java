package org.knard.junitext;

import java.util.Iterator;

public class TestCreationInterceptorInvocation implements Invocation {

	private Iterator<JUnitInterceptor> interceptors;

	public TestCreationInterceptorInvocation(Iterator<JUnitInterceptor> interceptors) {
		this.interceptors = interceptors;
	}

	@Override
	public void invoke(InvocationContext ctx) throws Exception {
		if (interceptors.hasNext()) {
			interceptors.next().interceptTestCreation(this, ctx);
		}
	}

}
