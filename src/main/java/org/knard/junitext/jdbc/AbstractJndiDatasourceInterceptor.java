package org.knard.junitext.jdbc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.knard.junitext.Invocation;
import org.knard.junitext.InvocationContext;
import org.knard.junitext.JUnitInterceptor;

public abstract class AbstractJndiDatasourceInterceptor extends JUnitInterceptor {
	private JndiDatasource[] dsList;

	public AbstractJndiDatasourceInterceptor(JndiDatasource... dsList) {
		this.dsList = dsList;
	}

	@Override
	public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		registerDatasources();
		try {
			nextInvocation.invoke(ctx);
		} finally {
			unregisterDatasources();
		}
	}

	private void unregisterDatasources() throws Exception {
		InitialContext initialContext = new InitialContext();
		for (JndiDatasource jndiDs : dsList) {
			initialContext.unbind(jndiDs.jndi());
		}
	}

	private void registerDatasources() throws NamingException {
		InitialContext initialContext = new InitialContext();
		for (JndiDatasource jndiDs : dsList) {
			DataSource ds = new DefaultDatasource(jndiDs.url(), jndiDs.user(), jndiDs.password());
			initialContext.bind(jndiDs.jndi(), ds);
		}
	}

}
