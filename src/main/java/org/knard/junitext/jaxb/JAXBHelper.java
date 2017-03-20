package org.knard.junitext.jaxb;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBHelper {
	private final static JAXBHelper instance = new JAXBHelper();
	private ThreadLocal<Marshaller> jaxbMarshaller = new ThreadLocal<Marshaller>();
	private ThreadLocal<Unmarshaller> jaxbUnmarshaller = new ThreadLocal<Unmarshaller>();

	private JAXBHelper() {
	}

	public Marshaller getJaxbMarshaller() {
		return jaxbMarshaller.get();
	}

	public Unmarshaller getJaxbUnmarshaller() {
		return jaxbUnmarshaller.get();
	}

	public void setJaxbMarshaller(Marshaller jaxbMarshaller) {
		this.jaxbMarshaller.set(jaxbMarshaller);
	}

	public void setJaxbUnmarshaller(Unmarshaller jaxbUnmarshaller) {
		this.jaxbUnmarshaller.set(jaxbUnmarshaller);
	}

	public void removeJaxbMarshaller() {
		this.jaxbMarshaller.remove();
	}

	public void removeJaxbUnmarshaller() {
		this.jaxbUnmarshaller.remove();
	}

	public static JAXBHelper getInstance() {
		return instance;
	}
}
