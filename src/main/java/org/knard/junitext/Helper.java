package org.knard.junitext;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.knard.junitext.directedGraph.DirectedGraph;
import org.knard.junitext.directedGraph.NodePriority;

public class Helper {
	public static Map<Class<? extends Annotation>, AnnotationDefintion> getAnnotationDefinitions(
			AnnotatedElement annotatedElement) {
		Map<Class<? extends Annotation>, AnnotationDefintion> definitions = new HashMap<Class<? extends Annotation>, AnnotationDefintion>();

		for (Annotation annotation : annotatedElement.getAnnotations()) {
			Factory factory = annotation.annotationType().getAnnotation(Factory.class);
			if (factory != null) {
				AnnotationDefintion annotationDefintion = new AnnotationDefintion();
				annotationDefintion.setAnnotationType(annotation.annotationType());
				annotationDefintion.setFactory(factory.value());
				annotationDefintion.setAnnotatedElement(annotatedElement);
				Dependencies dependencies = annotation.annotationType().getAnnotation(Dependencies.class);
				if (dependencies != null) {
					annotationDefintion.setMandatoryDependencies(dependencies.mandatory());
					annotationDefintion.setOptionalDependencies(dependencies.optional());
				} else {
					annotationDefintion.setMandatoryDependencies(new Class[0]);
					annotationDefintion.setOptionalDependencies(new Class[0]);
				}
				annotationDefintion.setRunLast(annotation.annotationType().getAnnotation(RunLate.class) != null);
				definitions.put(annotation.annotationType(), annotationDefintion);
			}
		}
		return definitions;
	}

	public static DirectedGraph<Class<? extends Annotation>> getConfiguredDirectedGraph(
			Map<Class<? extends Annotation>, AnnotationDefintion> definitions,
			Set<Class<? extends Annotation>> dependenciesToSkip) {
		DirectedGraph<Class<? extends Annotation>> directedGraph = new DirectedGraph<Class<? extends Annotation>>();
		for (Entry<Class<? extends Annotation>, AnnotationDefintion> e : definitions.entrySet()) {
			AnnotationDefintion defintion = e.getValue();
			boolean nodeAdded = false;
			for (Class<? extends Annotation> mandatorydependency : defintion.getMandatoryDependencies()) {
				if (!dependenciesToSkip.contains(mandatorydependency)) {
					directedGraph.addDirectedEdge(e.getKey(), mandatorydependency);
					nodeAdded = true;
				}
			}
			for (Class<? extends Annotation> optionaldependency : defintion.getOptionalDependencies()) {
				if (!dependenciesToSkip.contains(optionaldependency) && definitions.containsKey(optionaldependency)) {
					directedGraph.addDirectedEdge(e.getKey(), optionaldependency);
					nodeAdded = true;
				}
			}
			if (!nodeAdded) {
				directedGraph.addNode(e.getKey());
			}
			directedGraph.setPriority(e.getKey(), defintion.isRunLast() ? NodePriority.LOW : NodePriority.NORMAL);
		}
		return directedGraph;
	}

	public static List<JUnitInterceptor> instanciate(List<Class<? extends Annotation>> executionPlan,
			Map<Class<? extends Annotation>, AnnotationDefintion> definitions, Class<?> testClass) throws Exception {
		List<JUnitInterceptor> interceptors = new ArrayList<JUnitInterceptor>(executionPlan.size());
		for (Class<? extends Annotation> c : executionPlan) {
			AnnotationDefintion def = definitions.get(c);
			AnnotationConfiguration cfg = new AnnotationConfiguration();
			cfg.setAnnotatedElement(def.getAnnotatedElement());
			interceptors.add(def.getFactory().newInstance().create(testClass, cfg));
		}
		return interceptors;
	}

}
