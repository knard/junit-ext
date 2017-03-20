package org.knard.junitext.initialContext;

import java.net.URI;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.CompoundName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class URIContext implements Context {

	protected static final Properties syntax = new Properties();
	private final Map<URI, Object> mapping;
	private URI baseURI;
	private NameParser nameParser = new NameParser() {

		@Override
		public Name parse(String name) throws NamingException {
			return new CompoundName(name, syntax);
		}
	};

	public URIContext(URI baseURI) throws NamingException {
		this(baseURI, new HashMap<URI, Object>());
	}

	private URIContext(URI baseURI, Map<URI, Object> mapping) throws NamingException {
		this.baseURI = baseURI;
		this.mapping = mapping;
	}

	@Override
	public Object lookup(Name name) throws NamingException {
		URI targetURI = getURI(name, false);
		Object result = mapping.get(targetURI);
		if (result == null) {
			throw new NameNotFoundException("can't find : " + targetURI.toString());
		}
		return result;
	}

	private URI getURI(Name name, boolean isNewRoot) {
		URI targetURI = baseURI;
		int size = name.size() - 1;
		for (int i = 0; i < size; i++) {
			targetURI = targetURI.resolve(name.get(i) + "/");
		}
		if (name.size() > 0) {
			if (isNewRoot) {
				targetURI = targetURI.resolve(name.get(size) + "/");
			} else {
				targetURI = targetURI.resolve(name.get(size));
			}
		}

		return targetURI;
	}

	@Override
	public Object lookup(String name) throws NamingException {
		return lookup(new CompositeName(name));
	}

	@Override
	public void bind(Name name, Object obj) throws NamingException {
		URI targetURI = getURI(name, false);
		mapping.put(targetURI, obj);
	}

	@Override
	public void bind(String name, Object obj) throws NamingException {
		bind(new CompositeName(name), obj);
	}

	@Override
	public void rebind(Name name, Object obj) throws NamingException {
		bind(name, obj);
	}

	@Override
	public void rebind(String name, Object obj) throws NamingException {
		rebind(new CompositeName(name), obj);
	}

	@Override
	public void unbind(Name name) throws NamingException {
		bind(name, null);
	}

	@Override
	public void unbind(String name) throws NamingException {
		unbind(new CompositeName(name));
	}

	@Override
	public void rename(Name oldName, Name newName) throws NamingException {
		Object obj = lookup(oldName);
		bind(newName, obj);
	}

	@Override
	public void rename(String oldName, String newName) throws NamingException {
		rename(new CompositeName(oldName), new CompositeName(newName));
	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void destroySubcontext(Name name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void destroySubcontext(String name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Context createSubcontext(Name name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Context createSubcontext(String name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Object lookupLink(Name name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Object lookupLink(String name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public NameParser getNameParser(Name name) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public NameParser getNameParser(String name) throws NamingException {
		return nameParser;
	}

	@Override
	public Name composeName(Name name, Name prefix) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String composeName(String name, String prefix) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Object addToEnvironment(String propName, Object propVal) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Object removeFromEnvironment(String propName) throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void close() throws NamingException {
	}

	@Override
	public String getNameInNamespace() throws NamingException {
		throw new UnsupportedOperationException("not implemented");
	}

}
