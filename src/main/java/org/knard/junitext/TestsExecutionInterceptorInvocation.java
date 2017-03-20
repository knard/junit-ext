package org.knard.junitext;

import java.util.Iterator;

public class TestsExecutionInterceptorInvocation implements Invocation{

	private Iterator<JUnitInterceptor> interceptors;

	public TestsExecutionInterceptorInvocation(Iterator<JUnitInterceptor> interceptors) {
		this.interceptors=interceptors;
	}
	
	@Override
	public void invoke(InvocationContext ctx) throws Exception {
		if( interceptors.hasNext() ) {
			interceptors.next().interceptTestsExecution(this, ctx);
		}
	}

}
