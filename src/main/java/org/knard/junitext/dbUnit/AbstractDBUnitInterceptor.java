package org.knard.junitext.dbUnit;

import java.util.List;

import javax.naming.InitialContext;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;

import org.knard.junitext.Invocation;
import org.knard.junitext.InvocationContext;
import org.knard.junitext.JUnitInterceptor;

public abstract class AbstractDBUnitInterceptor extends JUnitInterceptor {

	private List<DBUnit> dbuList;

	public AbstractDBUnitInterceptor(List<DBUnit> dbuList) {
		this.dbuList = dbuList;
	}

	@Override
	public void interceptMethod(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		setup(ctx);
		try {
			nextInvocation.invoke(ctx);
		} finally {
			tearDown(ctx);
		}
	}
	
	@Override
	public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		setup(ctx);
		try {
			nextInvocation.invoke(ctx);
		} finally {
			tearDown(ctx);
		}
	}

	private void tearDown(InvocationContext ctx) throws Exception {
		InitialContext intialContext = new InitialContext();
		for (DBUnit dbu : dbuList) {
			doDeleteAll(ctx, intialContext, dbu);
		}
	}

	private void setup(InvocationContext ctx) throws Exception {
		InitialContext intialContext = new InitialContext();
		for (DBUnit dbu : dbuList) {
			doDeleteAll(ctx, intialContext, dbu);
		}
		for (DBUnit dbu : dbuList) {
			doInsert(ctx, intialContext, dbu);
		}
	}

	private void doInsert(InvocationContext ctx, InitialContext intialContext, DBUnit dbu) throws Exception {
		IDatabaseConnection connection = null;
		try {
			connection = new DatabaseDataSourceConnection(intialContext, dbu.ds());
			IDataSet dataSet = dbu.inputType().create(ctx.getTestClass().getClassLoader(), dbu.value());
			DatabaseOperation.TRANSACTION(DatabaseOperation.INSERT).execute(connection, dataSet);

		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	private void doDeleteAll(InvocationContext ctx, InitialContext intialContext, DBUnit dbu) throws Exception {
		IDatabaseConnection connection = null;
		try {
			connection = new DatabaseDataSourceConnection(intialContext, dbu.ds());
			IDataSet dataSet = dbu.inputType().create(ctx.getTestClass().getClassLoader(), dbu.value());
			DatabaseOperation.TRANSACTION(DatabaseOperation.DELETE_ALL).execute(connection, dataSet);

		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

}
