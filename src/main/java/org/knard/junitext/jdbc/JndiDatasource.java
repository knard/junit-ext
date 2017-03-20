package org.knard.junitext.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.knard.junitext.Dependencies;
import org.knard.junitext.Factory;
import org.knard.junitext.initialContext.InitialContextFactory;

@Dependencies(optional={JdbcDriver.class, JdbcDrivers.class, InitialContextFactory.class})
@Factory(JndiDatasourceInterceptorFactory.class)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JndiDatasource {

	String user();

	String jndi();

	String url();

	String password();

}
