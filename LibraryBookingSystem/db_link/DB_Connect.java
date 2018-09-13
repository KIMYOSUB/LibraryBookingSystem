package db_link;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DB_Connect {
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://mobilekys.iptime.org:3306/test";
		String user = "daniel";
		String pass = "kyil5611";
		Connection con = null;
		
		Class.forName("org.gjt.mm.mysql.Driver");
		con = DriverManager.getConnection(url, user, pass);
		//System.out.println("접속 성공!");
		
		return con;
	}
	/*
		public static void main(String [] args) {
		boolean test = Query_Statement.loginTest("dany", "3886");		
		System.out.println("로그인 결과 :"+test);
	    }
	*/
}