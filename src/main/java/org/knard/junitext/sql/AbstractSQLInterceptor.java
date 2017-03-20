package org.knard.junitext.sql;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.knard.junitext.Invocation;
import org.knard.junitext.InvocationContext;
import org.knard.junitext.JUnitInterceptor;

public abstract class AbstractSQLInterceptor extends JUnitInterceptor {

	private List<SQL> sqls;

	public AbstractSQLInterceptor(List<SQL> sqls) {
		this.sqls = sqls;
	}

	@Override
	public void interceptTestsExecution(Invocation nextInvocation, InvocationContext ctx) throws Exception {
		for (SQL s : sqls) {
			String dsName = s.ds();
			String filename = s.files();
			char sep = s.separator();
			String charset = s.charset().trim();
			if (charset.length() == 0) {
				charset = null;
			}
			executeScript(ctx.getTestClass().getClassLoader(), dsName, filename, sep, charset);
		}
		nextInvocation.invoke(ctx);
	}

	private void executeScript(ClassLoader cl, String dsName, String filename, char sep, String charset)
			throws Exception {
		List<String> queries = readQueries(cl, filename, sep, charset);
		if (queries.size() > 0) {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup(dsName);
			Connection connection = null;
			try {
				connection = ds.getConnection();
				for (String query : queries) {
					Statement s = connection.createStatement();
					s.execute(query);
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

	}

	private List<String> readQueries(ClassLoader cl, String filename, char sep, String charset) throws IOException {
		List<String> queries = new ArrayList<String>();
		Reader reader = null;
		try {
			if (charset == null) {
				reader = new InputStreamReader(cl.getResourceAsStream(filename));
			} else {
				reader = new InputStreamReader(cl.getResourceAsStream(filename), charset);
			}
			StringBuilder builder = new StringBuilder();
			int next = -1;
			while ((next = reader.read()) != -1) {
				char nextChar = (char) (0xfff & next);
				if (nextChar == sep) {
					String query = builder.toString().trim();
					if (query.length() > 0) {
						queries.add(query);
					}
					builder = new StringBuilder();
				} else {
					builder.append(nextChar);
				}
			}
			String query = builder.toString().trim();
			if (query.length() > 0) {
				queries.add(query);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return queries;
	}

}
