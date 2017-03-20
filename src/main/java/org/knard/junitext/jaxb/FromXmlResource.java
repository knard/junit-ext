package org.knard.junitext.jaxb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.knard.junitext.Dependencies;
import org.knard.junitext.Factory;

@Dependencies(mandatory={JaxbCtx.class})
@Factory(FromXmlResourceInterceptorFactory.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FromXmlResource {

	String value();

}
