package org.knard.junitext.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.knard.junitext.Dependencies;
import org.knard.junitext.Factory;
import org.knard.junitext.initialContext.InitialContextFactory;
import org.knard.junitext.jdbc.JdbcDriver;
import org.knard.junitext.jdbc.JdbcDrivers;
import org.knard.junitext.jdbc.JndiDatasource;
import org.knard.junitext.jdbc.JndiDatasources;

@Dependencies(optional = { InitialContextFactory.class, JdbcDriver.class, JdbcDrivers.class, JndiDatasources.class,
		JndiDatasource.class })
@Factory(SQLInterceptorFactory.class)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SQL {

	String files();

	String ds();

	char separator() default ';';

	String charset() default "";

}
