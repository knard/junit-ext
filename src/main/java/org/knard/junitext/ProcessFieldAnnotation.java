package org.knard.junitext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.knard.junitext.directedGraph.DirectedGraph;

public abstract class ProcessFieldAnnotation extends JUnitInterceptor {

	private boolean staticField;
	private HashSet<Class<? extends Annotation>> dependenciesToSkip;

	public ProcessFieldAnnotation(boolean staticField, HashSet<Class<? extends Annotation>> dependenciesToSkip) {
		this.staticField = staticField;
		this.dependenciesToSkip = dependenciesToSkip;
	}

	@Override
	public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		delegate(nextInvocation, ctx);
	}

	public void delegate(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		Class<?> testClass = ctx.getTestClass();
		processFields(testClass, ctx.clone());
		nextInvocation.invoke(ctx);
	}

	@Override
	public void interceptMethod(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		delegate(nextInvocation, ctx);
	}

	private void processFields(Class<?> klass, InvocationContext ctx) throws Exception {
		Class<?> superKlass = klass.getSuperclass();
		if (superKlass != null) {
			processFields(superKlass, ctx);
		}
		for (Field f : klass.getDeclaredFields()) {
			if (Modifier.isStatic(f.getModifiers()) == staticField) {
				processField(f, klass, ctx);
			}
		}
	}

	private void processField(Field f, Class<?> testClass, InvocationContext ctx) throws Exception {
		Map<Class<? extends Annotation>, AnnotationDefintion> definitions = Helper.getAnnotationDefinitions(f);
		DirectedGraph<Class<? extends Annotation>> directedGraph = Helper.getConfiguredDirectedGraph(definitions,
				dependenciesToSkip);
		List<Class<? extends Annotation>> executionPlan = directedGraph.getTopologicalSort();
		List<JUnitInterceptor> interceptors = Helper.instanciate(executionPlan, definitions, testClass);
		Invocation invocation = createInvocation(interceptors);
		invocation.invoke(ctx);
	}

	abstract protected Invocation createInvocation(List<JUnitInterceptor> interceptors);
}
