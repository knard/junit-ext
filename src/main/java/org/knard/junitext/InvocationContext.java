package org.knard.junitext;

public class InvocationContext implements Cloneable {
	private Class<?> testClass;
	private Object returnValue;
	private Object testInstance;

	public InvocationContext() {
	}

	public InvocationContext(Class<?> testClass, Object returnValue, Object testInstance) {
		super();
		this.testClass = testClass;
		this.returnValue = returnValue;
		this.testInstance = testInstance;
	}

	public Object getTestInstance() {
		return testInstance;
	}

	public void setTestInstance(Object testInstance) {
		this.testInstance = testInstance;
	}

	public Class<?> getTestClass() {
		return testClass;
	}

	public void setTestClass(Class<?> testClass) {
		this.testClass = testClass;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	@Override
	public InvocationContext clone() {
		return new InvocationContext(testClass, returnValue, testInstance);
	}
}
