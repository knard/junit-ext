package org.knard.junitext.cdi;

import org.knard.junitext.Invocation;
import org.knard.junitext.InvocationContext;
import org.knard.junitext.JUnitInterceptor;

public class CdiInterceptor extends JUnitInterceptor {

	private WeldContext weldContext;

	@Override
	public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		weldContext = new WeldContext();
		try {
			nextInvocation.invoke(ctx);
		} finally {
			weldContext.shutdown();
		}
	}

	@Override
	public void interceptTestCreation(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		final Class<?> c = ctx.getTestClass();
		ctx.setReturnValue(weldContext.getBean(c));
	}

}
