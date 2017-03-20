package org.knard.junitext;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE })
public @interface Dependencies {

	Class<? extends Annotation>[] mandatory() default {};
	
	Class<? extends Annotation>[] optional() default {};

}
