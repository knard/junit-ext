package org.knard.junitext.jdbc;

import java.sql.Driver;
import java.sql.DriverManager;

import org.knard.junitext.Invocation;
import org.knard.junitext.InvocationContext;
import org.knard.junitext.JUnitInterceptor;

public abstract class AbstractJdbcDriverInterceptor extends JUnitInterceptor {

	private JdbcDriver[] drivers;
	
	public AbstractJdbcDriverInterceptor(JdbcDriver... drivers) {
		this.drivers=drivers;
	}
	
	@Override
	public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		registerDrivers();
		try {
			nextInvocation.invoke(ctx);
		} finally {
			unregisterDrivers();
		}
	}

	private void unregisterDrivers() throws Exception {
		for (JdbcDriver driver : drivers) {
			DriverManager.deregisterDriver((Driver) Class.forName(driver.value()).newInstance());
		}
	}

	private void registerDrivers() throws ClassNotFoundException {
		for (JdbcDriver driver : drivers) {
			Class.forName(driver.value());
		}
	}
}
