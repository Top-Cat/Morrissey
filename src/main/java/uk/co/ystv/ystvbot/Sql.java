package uk.co.ystv.ystvbot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sql {
	
	private static String url = "jdbc:postgresql://ystv.co.uk/ystv?user=ystvweb&password=D5ZEwcfG7r69AeN&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
	private static Sql sql;
	
	public static Sql getInstance() {
		if (sql == null) {
			sql = new Sql();
		}
		return sql;
	}
	
	private Connection conn;

	public Sql() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url);
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int update(String sql) {
		try {
			final PreparedStatement pr = conn.prepareStatement(sql);
			int count = pr.executeUpdate();
			return count;
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int insert(String sql, Object[] values) {
		try {
			final PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (values != null) {
				for (int i = 1; i <= values.length; i++) {
					setValue(pr, i, values[i - 1]);
				}
			}
			pr.executeUpdate();
			final ResultSet rs = pr.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public ResultSet query(String sql) {
		return query(sql, null);
	}
	
	public ResultSet query(String sql, Object[] values) {
		try {
			final PreparedStatement pr = conn.prepareStatement(sql);
			if (values != null) {
				for (int i = 1; i <= values.length; i++) {
					setValue(pr, i, values[i - 1]);
				}
			}
			ResultSet results = pr.executeQuery();
			return results;
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setValue(PreparedStatement pr, int index, Object value) throws SQLException {
		if (value instanceof String) {
			pr.setString(index, (String) value);
		} else if (value instanceof Boolean) {
			pr.setBoolean(index, (Boolean) value);
		}
	}

}
