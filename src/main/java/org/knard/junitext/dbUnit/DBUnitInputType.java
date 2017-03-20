package org.knard.junitext.dbUnit;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

public enum DBUnitInputType {
	xml {
		@Override
		public IDataSet create(ClassLoader cl, String value) throws DataSetException {
			return new XmlDataSet(cl.getResourceAsStream(value));
		}
	};

	public abstract IDataSet create(ClassLoader cl, String value) throws DataSetException;
}
