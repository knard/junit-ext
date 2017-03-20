package org.knard.junitext.jaxb;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.xml.bind.JAXBElement;

import org.knard.junitext.Invocation;
import org.knard.junitext.InvocationContext;
import org.knard.junitext.JUnitInterceptor;

public class FromXmlResourceInterceptor extends JUnitInterceptor {

	private Field field;
	private String resource;
	private ClassLoader cl;

	public FromXmlResourceInterceptor(Field f, ClassLoader cl) {
		this.field = f;
		this.resource = field.getAnnotation(FromXmlResource.class).value();
		this.cl = cl;
	}

	@Override
	public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		inject(null);
		nextInvocation.invoke(ctx);
	}

	@Override
	public void interceptMethod(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		inject(ctx.getTestInstance());
		nextInvocation.invoke(ctx);
	}

	private void inject(Object testInstance) throws Exception {
		InputStream is = null;
		try {
			is = cl.getResourceAsStream(resource);
			if (is == null) {
				throw new FileNotFoundException("can't find resource " + resource + " in classpath");
			}
			Object value = ((JAXBElement<?>) JAXBHelper.getInstance().getJaxbUnmarshaller().unmarshal(is)).getValue();
			boolean accessible = field.isAccessible();
			if (!accessible) {
				field.setAccessible(true);
			}
			field.set(testInstance, value);
			if (!accessible) {
				field.setAccessible(false);
			}
		} finally {
			if( is != null ) {
				is.close();
			}
		}
		
	}
}
