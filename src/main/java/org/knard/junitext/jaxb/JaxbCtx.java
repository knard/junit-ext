package org.knard.junitext.jaxb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.knard.junitext.Factory;

@Factory(JaxbCtxInterceptorFactory.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JaxbCtx {

	String value();

}
