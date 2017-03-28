package org.knard.junitext.cdi;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PersistenceContextAsInjectExtensionTestBean {

	@PersistenceContext
	public EntityManager entityManager;
}
