package org.knard.junitext.cdi;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;

import org.jboss.weld.util.annotated.ForwardingAnnotatedField;
import org.jboss.weld.util.annotated.ForwardingAnnotatedType;

public class PersistenceContextAsInjectExtension implements Extension {
	public static final class InjectLiteral extends AnnotationLiteral<Inject> implements Inject {
		private static final long serialVersionUID = -6622183596325874593L;
	}

	private static final Annotation INJECT = new InjectLiteral();

	public <X> void processAnnotatedType(@Observes ProcessAnnotatedType<X> pat) {
		pat.setAnnotatedType(wrap(pat.getAnnotatedType()));
	}

	private <X> AnnotatedType<X> wrap(final AnnotatedType<X> annotatedType) {
		final Set<AnnotatedField<? super X>> wrappedFields = wrap(annotatedType.getFields());
		return new ForwardingAnnotatedType<X>() {

			@Override
			public AnnotatedType<X> delegate() {
				return annotatedType;
			}

			@Override
			public Set<AnnotatedField<? super X>> getFields() {
				return wrappedFields;
			}
		};
	}

	private <X> Set<AnnotatedField<? super X>> wrap(Set<AnnotatedField<? super X>> fields) {
		Set<AnnotatedField<? super X>> result = new HashSet<AnnotatedField<? super X>>();
		for (AnnotatedField<? super X> annotatedField : fields) {
			result.add(wrap(annotatedField));
		}
		return result;
	}

	private <X> AnnotatedField<? super X> wrap(final AnnotatedField<? super X> annotatedField) {
		Set<Annotation> orignials = annotatedField.getAnnotations();
		final boolean wrapped = needToWrap(annotatedField);
		final Set<Annotation> annotations =  wrapped ? wrapAnnotations(orignials)
				: orignials;
		return new ForwardingAnnotatedField<X>() {

			@SuppressWarnings("unchecked")
			@Override
			protected AnnotatedField<X> delegate() {
				return (AnnotatedField<X>) annotatedField;
			}

			@Override
			public Set<Annotation> getAnnotations() {
				return annotations;
			}
			
			@Override
			public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
				if( Inject.class.equals(annotationType) && wrapped) {
					return true;
				}
				return super.isAnnotationPresent(annotationType);
			}
			
			@Override
			public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
				if( Inject.class.equals(annotationType) && wrapped) {
					return (A) INJECT;
				}
				return super.getAnnotation(annotationType);
			}
		};
	}

	private <X> boolean needToWrap(AnnotatedField<? super X> annotatedField) {
		return annotatedField.isAnnotationPresent(PersistenceContext.class)
				&& !annotatedField.isAnnotationPresent(Inject.class);
	}

	private Set<Annotation> wrapAnnotations(Set<Annotation> annotations) {
		Set<Annotation> result = new HashSet<Annotation>(annotations);
		result.add(INJECT);
		return result;
	}
}
