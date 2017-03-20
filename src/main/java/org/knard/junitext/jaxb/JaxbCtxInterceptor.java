package org.knard.junitext.jaxb;

import java.lang.reflect.AnnotatedElement;

import javax.xml.bind.JAXBContext;

import org.knard.junitext.Invocation;
import org.knard.junitext.InvocationContext;
import org.knard.junitext.JUnitInterceptor;

public class JaxbCtxInterceptor extends JUnitInterceptor {

	private String jaxbCtx;

	public JaxbCtxInterceptor(AnnotatedElement annotatedElement) {
		this.jaxbCtx = annotatedElement.getAnnotation(JaxbCtx.class).value();
	}

	@Override
	public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(jaxbCtx);
		JAXBHelper.getInstance().setJaxbMarshaller(jaxbContext.createMarshaller());
		JAXBHelper.getInstance().setJaxbUnmarshaller(jaxbContext.createUnmarshaller());
		try {
			nextInvocation.invoke(ctx);
		} finally {
			JAXBHelper.getInstance().removeJaxbMarshaller();
			JAXBHelper.getInstance().removeJaxbUnmarshaller();
		}
	}
}
