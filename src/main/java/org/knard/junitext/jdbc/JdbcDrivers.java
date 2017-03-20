package org.knard.junitext.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.knard.junitext.Factory;

@Factory(JdbcDriversInterceptorFactory.class)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JdbcDrivers {

	JdbcDriver[] value();

}
