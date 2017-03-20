package org.knard.junitext.cdi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.knard.junitext.Factory;
import org.knard.junitext.RunLate;

@Factory(CdiInterceptorFactory.class)
@RunLate
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface CDI {
}
