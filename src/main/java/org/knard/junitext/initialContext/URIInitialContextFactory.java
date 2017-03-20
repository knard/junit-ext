package org.knard.junitext.initialContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class URIInitialContextFactory implements InitialContextFactory {

	private static Context rootContext = null;

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
		if (rootContext == null) {
			try {
				rootContext = new URIContext(new URI("ctx:/"));
			} catch (URISyntaxException e) {
				throw new NamingException(e.getMessage());
			}
		}
		return rootContext;
	}

}
