package cn.fireup.yuanyang.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcUtil {

	private String driver;
	private String url;
	private String user;
	private String password;

	private PreparedStatement preparedStatement;
	private Connection connection;
	private ResultSet resultSet;

	public JdbcUtil() {
		
	}


	public Connection getConnection() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				Properties pro = new Properties();
				try {
					InputStream is = JdbcUtil.class.getClassLoader()
							.getResourceAsStream("DB.properties");
					pro.load(is);
					driver = pro.getProperty("driver");
					url = pro.getProperty("url");
					user = pro.getProperty("username");
					password = pro.getProperty("password");
					Class.forName(driver);
					connection = DriverManager.getConnection(url, user, password);
					//return connection;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		}).start();
		
		return null;
	}

	public  void close() {
		if(resultSet!=null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(preparedStatement!=null){
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public boolean execUpdate(String sql, List<Object> params)
			throws SQLException {

		boolean flag = false;

		int result = -1;

		preparedStatement = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				preparedStatement.setObject(index++, params.get(i));
			}
		}

		result = preparedStatement.executeUpdate();

		flag = result > 0 ? true : false;

		return flag;

	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSimpleResult(String sql, List<Object> params)
			throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();

		int index = 1;

		preparedStatement = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				preparedStatement.setObject(index, params.get(i));
			}
		}
		resultSet = preparedStatement.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String col_name = metaData.getColumnName(i + 1);
				Object col_value = resultSet.getObject(col_name);
				if (col_value == null) {
					col_value = " ";
				}
				map.put(col_name, col_value);

			}
		}

		return map;

	}

	public List<Map<String, Object>> findMultResult(String sql,
			List<Object> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		preparedStatement = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				preparedStatement.setObject(index, params.get(i));
			}
		}
		resultSet = preparedStatement.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();

			for (int i = 0; i < col_len; i++) {
				String col_name = metaData.getColumnName(i + 1);
				Object col_value = resultSet.getObject(col_name);
				if (col_value == null) {
					col_value = " ";
				}
				map.put(col_name, col_value);
			}

			list.add(map);
		}
		return list;
	}

	public <T> T findSimpleRefResult(String sql, List<Object> params,
			Class<T> cls) throws Exception {

		T resultObject = null;
		int index = 1;
		preparedStatement = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				preparedStatement.setObject(index, params.get(i));
			}
		}
		resultSet = preparedStatement.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			
			resultObject = cls.newInstance();
			for (int i = 0; i < col_len; i++) {
				String col_name = metaData.getColumnName(i + 1);
				Object col_value = resultSet.getObject(col_name);
				if (col_value == null) {
					col_value = " ";
				}
				Field field = cls.getDeclaredField(col_name);
				field.setAccessible(true);
				field.set(resultObject, col_value);
			}
		}

		return resultObject;
	}

	/**
	 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findMulRefResult(String sql, List<Object> params,
			Class<T> cls) throws Exception {

		List<T> list = new ArrayList<T>();
		int index = 1;
		preparedStatement = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				preparedStatement.setObject(index, params.get(i));
			}
		}
		resultSet = preparedStatement.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			
			T resultObject = cls.newInstance();
			for (int i = 0; i < col_len; i++) {
				String col_name = metaData.getColumnName(i + 1);
				Object col_value = resultSet.getObject(col_name);
				if (col_value == null) {
					col_value = " ";
				}
				Field field = cls.getDeclaredField(col_name);
				field.setAccessible(true);
				field.set(resultObject, col_value);
			}
			list.add(resultObject);
		}
		
		return list;
	}

}
