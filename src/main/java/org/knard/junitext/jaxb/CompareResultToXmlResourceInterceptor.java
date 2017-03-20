package org.knard.junitext.jaxb;

import java.io.InputStream;
import java.lang.reflect.AnnotatedElement;

import javax.xml.bind.JAXBElement;

import org.junit.ComparisonFailure;

import org.knard.junitext.Invocation;
import org.knard.junitext.InvocationContext;
import org.knard.junitext.JUnitInterceptor;

public class CompareResultToXmlResourceInterceptor extends JUnitInterceptor {
	
	private String resource;

	public CompareResultToXmlResourceInterceptor(AnnotatedElement annotatedElement, Class<?> testClass) throws Exception {
		CompareResultToXmlResource compareToXmlResource = annotatedElement
				.getAnnotation(CompareResultToXmlResource.class);
		this.resource = compareToXmlResource.value();
	}
	
	@Override
	public void interceptMethod(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		nextInvocation.invoke(ctx);
		Object result = ctx.getReturnValue();
		InputStream is= null;
		try{
			is = ctx.getTestClass().getClassLoader().getResourceAsStream(resource);
			@SuppressWarnings("unchecked")
			JAXBElement<Object> expectedResult = (JAXBElement<Object>) JAXBHelper.getInstance().getJaxbUnmarshaller()
					.unmarshal(is);
			if (!expectedResult.getValue().equals(result)) {
				throw new ComparisonFailure("result doesn't correspond to expected object", expectedResult.getValue().toString(),
						result.toString());
			}
		} finally {
			if( is != null ) {
				is.close();
			}
		}
		
	}
}
