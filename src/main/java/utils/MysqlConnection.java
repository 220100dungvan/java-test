package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConnection {
	private static Connection conn;

	public static Connection cretateConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String URL = "jdbc:mysql://localhost:"+ ConstantUtils.DB_PORT+"/"+ConstantUtils.DB_NAME;
			conn = DriverManager.getConnection(URL, ConstantUtils.DB_USERNAME, ConstantUtils.DB_PASSWORD);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Kết nối không thành công");
		}
		return conn;
	}
}
