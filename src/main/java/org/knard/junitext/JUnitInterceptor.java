package org.knard.junitext;

public class JUnitInterceptor {

	public void interceptTestCreation(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		nextInvocation.invoke(ctx);
	}

	public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		nextInvocation.invoke(ctx);
	}

	public void interceptMethod(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		nextInvocation.invoke(ctx);
	}

}
