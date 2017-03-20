package org.knard.junitext.dbUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.knard.junitext.Dependencies;
import org.knard.junitext.Factory;
import org.knard.junitext.initialContext.InitialContextFactory;

@Dependencies(optional={InitialContextFactory.class})
@Factory(DBUnitsInterceptorFactory.class)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DBUnits {

	DBUnit[] value();

}
