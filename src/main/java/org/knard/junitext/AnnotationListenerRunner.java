package org.knard.junitext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import org.knard.junitext.directedGraph.DirectedGraph;
import org.knard.junitext.directedGraph.GraphException;

public class AnnotationListenerRunner extends BlockJUnit4ClassRunner {

	List<JUnitInterceptor> executions;
	private HashSet<Class<? extends Annotation>> classAnnotations;

	public AnnotationListenerRunner(Class<?> klass) throws InitializationError {
		super(klass);

		Map<Class<? extends Annotation>, AnnotationDefintion> defintions = Helper.getAnnotationDefinitions(klass);
		DirectedGraph<Class<? extends Annotation>> directedGraph = Helper.getConfiguredDirectedGraph(defintions,
				new HashSet<Class<? extends Annotation>>());

		List<Class<? extends Annotation>> executionPlan = null;
		try {
			executionPlan = directedGraph.getTopologicalSort();
		} catch (GraphException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		classAnnotations = new HashSet<Class<? extends Annotation>>(executionPlan);
		try {
			executions = Helper.instanciate(executionPlan, defintions, klass);
		} catch (Exception e) {
			throw new InitializationError(e);
		}
	}

	protected void validateTestMethods(List<Throwable> errors) {
		List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Test.class);
		for (FrameworkMethod eachTestMethod : methods) {
			if (eachTestMethod.getMethod().getParameterTypes().length != 0) {
				errors.add(
						new Exception("Method " + eachTestMethod.getMethod().getName() + " should have no parameters"));
			}
		}
	}

	@Override
	protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				InvocationContext ctx = new InvocationContext();
				ctx.setTestClass(getTestClass().getJavaClass());
				ctx.setTestInstance(test);
				Map<Class<? extends Annotation>, AnnotationDefintion> definitions = Helper
						.getAnnotationDefinitions(method.getMethod());
				DirectedGraph<Class<? extends Annotation>> directedGraph = Helper
						.getConfiguredDirectedGraph(definitions, classAnnotations);
				List<JUnitInterceptor> interceptors = Helper.instanciate(directedGraph.getTopologicalSort(),
						definitions, ctx.getTestClass());
				interceptors.add(new ProcessFieldAnnotationTestMethodExecution(classAnnotations));
				interceptors.add(new JUnitInterceptor() {
					@Override
					public void interceptMethod(Invocation nextInvocation, InvocationContext ctx) throws Exception {
						try {
							ctx.setReturnValue(method.invokeExplosively(ctx.getTestInstance()));
						} catch (Throwable e) {
							throw new InvocationTargetException(e);
						}
					}
				});
				TestMethodInterceptorInvocation testMethodInterceptorInvocation = new TestMethodInterceptorInvocation(
						interceptors.iterator());
				testMethodInterceptorInvocation.invoke(ctx);
			}
		};
	}

	@Override
	public void run(final RunNotifier notifier) {
		InvocationContext ctx = new InvocationContext();
		ctx.setTestClass(getTestClass().getJavaClass());
		List<JUnitInterceptor> interceptors = new ArrayList<JUnitInterceptor>(executions);
		interceptors.add(new ProcessFieldAnnotationTestsExecution(classAnnotations));
		interceptors.add(new JUnitInterceptor() {
			@Override
			public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
				AnnotationListenerRunner.super.run(notifier);
			}
		});
		TestsExecutionInterceptorInvocation testsExecutionInterceptorInvocation = new TestsExecutionInterceptorInvocation(
				interceptors.iterator());
		try {
			testsExecutionInterceptorInvocation.invoke(ctx);
		} catch (Exception e) {
			notifier.fireTestFailure(new Failure(getDescription(), e));
		}
	}

	@Override
	protected Object createTest() throws Exception {
		InvocationContext ctx = new InvocationContext();
		ctx.setTestClass(getTestClass().getJavaClass());
		List<JUnitInterceptor> interceptors = new ArrayList<JUnitInterceptor>(executions);
		interceptors.add(new JUnitInterceptor() {
			@Override
			public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
				ctx.setReturnValue(AnnotationListenerRunner.super.createTest());
			}
		});
		TestCreationInterceptorInvocation testCreationInterceptorInvocation = new TestCreationInterceptorInvocation(
				interceptors.iterator());
		testCreationInterceptorInvocation.invoke(ctx);
		return ctx.getReturnValue();
	}

}
