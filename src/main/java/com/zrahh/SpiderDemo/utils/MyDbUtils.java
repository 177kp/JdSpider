package com.zrahh.SpiderDemo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

public final class MyDbUtils {// 拒绝继承
	private static String className = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://192.168.138.128:3306/spider?useUnicode=true&characterEncoding=utf-8";
	private static String user = "spider";
	private static String password = "TeZDsfw856KBk4w7";
	private static QueryRunner queryRunner = new QueryRunner();

	public static final String INSERT_LOG = "INSERT INTO goods(goods_id,data_url,pic_url,title,price,param,`current_time`) VALUES(?,?,?,?,?,?,?)";

	// 拒绝new一个实例
	private MyDbUtils() {
	};

	static {// 调用该类时既注册驱动
		try {
			Class.forName(className);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static void main(String[] args) {
	}

	public static List<String> executeQuerySql(String sql) {
		List<String> result = new ArrayList<String>();
		try {
			List<Object[]> requstList = queryRunner.query(getConnection(), sql,
					new ArrayListHandler(new BasicRowProcessor() {
						@SuppressWarnings("hiding")
						@Override
						public <Object> List<Object> toBeanList(ResultSet rs,
								Class<Object> type) throws SQLException {
							return super.toBeanList(rs, type);
						}
					}));
			for (Object[] objects : requstList) {
				result.add(objects[0].toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void update(String sql, Object... params) {
		try {
			Connection connection = getConnection();
			queryRunner.update(connection, sql, params);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 获取连接
	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

}