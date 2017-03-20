package org.knard.junitext;

import java.util.Iterator;

public class TestMethodInterceptorInvocation  implements Invocation{

	private Iterator<JUnitInterceptor> interceptors;

	public TestMethodInterceptorInvocation(Iterator<JUnitInterceptor> interceptors) {
		this.interceptors=interceptors;
	}
	
	@Override
	public void invoke(InvocationContext ctx) throws Exception {
		if( interceptors.hasNext() ) {
			interceptors.next().interceptMethod(this, ctx);
		}
	}

}
