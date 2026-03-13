package Hospitallink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Link {

	public static Connection getConnection() {
		Connection connection = null;
		//数据库驱动
		String driveName="com.mysql.cj.jdbc.Driver";
		//连接的数据库
		String url="jdbc:mysql://localhost:3306/hwms";
		String user = "root";
		String password ="2004";
				
		//加载JDBC-MySQL数据库驱动
		try {
			Class.forName(driveName);
			connection = (Connection) DriverManager.getConnection(url,user,password);
			System.out.println("数据库连接成功");
		}
		catch(Exception e) {
			System.out.println("数据库连接失败:"+e.getMessage());
		}
		return connection;
	}
	

	public static void closeAll(ResultSet rs,PreparedStatement ps,Connection con){
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		if(ps != null){
			try{
				ps.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		if(con != null){
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
}
