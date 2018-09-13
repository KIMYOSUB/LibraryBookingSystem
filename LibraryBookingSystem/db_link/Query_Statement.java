package db_link;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Query_Statement {
	
	public static boolean loginTest(String id, String password) {        //로그인 쿼리
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String getPass = null;
		int getAllow = 0;
		try {
			con = db_link.DB_Connect.getConnection();
			sql = "select password,adminAllow from member where id=?"; ///수정함
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				getPass = rs.getString("password");
				getAllow = rs.getInt("adminAllow"); ///수정함
				if(getPass.equals(password) && getAllow == 1) {                      ///수정함
					//System.out.println("데이터베이스에서 받아온 비밀먼호 : " + getPass);
					flag = true;
				}
				else if(getAllow == 0){
					JOptionPane.showMessageDialog(null, "관리자의 허락이 필요합니다.", "권한 거부", JOptionPane.INFORMATION_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "비밀번호를 확인해 주세요.", "로그인 실패", JOptionPane.INFORMATION_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null, "가입되지 않은 ID입니다.", "미가입 ID", JOptionPane.INFORMATION_MESSAGE);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("실패함");
		}finally {
			if(rs != null) try{rs.close();}catch(SQLException sqle){}            // R.esult;set 객체 해제

			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제

			if(con != null) try{con.close();}catch(SQLException sqle){}   // Connection 해제

		}
		return flag;
	}
//----------------------------------------------------------------------------------------------------------------------------------
	public static Vector<Integer> checkSeatNum () {      ///로그인 상태인 유저 찾아서 , 좌석 번호 알아내기          
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<Integer> v = new Vector<Integer>();
		
		try {
			con = db_link.DB_Connect.getConnection();
			sql = "select seatNum from member where state=? order by seatNum";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, 1);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				v.add(rs.getInt("seatNum"));
			}
	
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("실패함");
		}finally {
			if(rs != null) try{rs.close();}catch(SQLException sqle){}            // R.esult;set 객체 해제

			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제

			if(con != null) try{con.close();}catch(SQLException sqle){}   // Connection 해제

		}
		return v;
	}
	/*
	public static void main(String[] args) {
		Vector<Integer> v = checkSeatNum();
		int a = v.size(); //5가 돼야됨.
		for(int i = 0; i < a; i++) {
			System.out.print(v.get(i));
			System.out.print(" ");
		}
	}
	*/
//-----------------------------------------------------------------------------------------------------------------------------------
	public static int getSeatNum(String id) {               //좌석 정보 불러오는 쿼리
		int SeatNum = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			con = db_link.DB_Connect.getConnection();
			sql = "select seatNum from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				SeatNum = rs.getInt("seatNum");
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try{rs.close();}catch(SQLException sqle){}            // R.esult;set 객체 해제

			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제

			if(con != null) try{con.close();}catch(SQLException sqle){}   // Connection 해제
		}
		return SeatNum;
	}
//-----------------------------------------------------------------------------------------------------------------------------------
	public static void setLoginState(String id, boolean state) {               //아이디와 상태 받아와서 DB에 적용
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int i;
		if(state) i = 1;
		else	i = 0;
		try {
			con = db_link.DB_Connect.getConnection();
			sql = "update member set state = ? where id = ?"; 
			pstmt = con.prepareStatement(sql);	
			pstmt.setInt(1, i);
			pstmt.setString(2, id);
            pstmt.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			

			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제

			if(con != null) try{con.close();}catch(SQLException sqle){}   // Connection 해제
		}
	}
//-------------------------------------------------------------------------------------------------------------------------------------
	public static void setSeatNum(String id, int seatNum) {               // 로그인, 로그아웃 할 때 상태 변경
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			con = db_link.DB_Connect.getConnection();
			sql = "update member set seatNum = ? where id = ?"; 
			pstmt = con.prepareStatement(sql);
			if(seatNum == -1) pstmt.setNull(1, Types.INTEGER);
			else 
			pstmt.setInt(1, seatNum);
			pstmt.setString(2, id);
            pstmt.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			

			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제

			if(con != null) try{con.close();}catch(SQLException sqle){}   // Connection 해제
		}
	}
	
//-------------------------------------------------------------------------------------------------------------------------------------
/*
	public static boolean getLoginState(int num) {               //로그인 상태 불러오는 쿼리
		
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int getState;
		try {
			con = db_link.DB_Connect.getConnection();
			sql = "select state from member where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				getState = rs.getInt("state");
				if(getState == 1) {
					flag = true;
				}
				else {
					flag = false;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try{rs.close();}catch(SQLException sqle){}            // R.esult;set 객체 해제
			
			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제
			
			if(con != null) try{con.close();}catch(SQLException sqle){}   // Connection 해제
		}
		return flag;
	}*/
//----------------------------------------------------------------------------------------------------------------------------------
	public static boolean getLoginState(String id) {               //로그인 상태 불러오는 쿼리(id)

		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int getState;
		try {
			con = db_link.DB_Connect.getConnection();
			sql = "select state from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				getState = rs.getInt("state");
				if(getState == 1) {
					flag = true;
				}
				else {
					flag = false;
				}
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try{rs.close();}catch(SQLException sqle){}            // R.esult;set 객체 해제

			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제

			if(con != null) try{con.close();}catch(SQLException sqle){}   // Connection 해제
		}
		return flag;
	}

//-----------------------------------------------------------------------------------------------------------------------------------
/*	public static boolean getLoginState_sn(int seatNum) {               //로그인 상태 불러오는 쿼리
	
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int getState;
		try {
			con = db_link.DB_Connect.getConnection();
			sql = "select state from member where seatNum=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, seatNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				getState = rs.getInt("state");
				if(getState == 1) {
					flag = true;
				}
				else {
					flag = false;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try{rs.close();}catch(SQLException sqle){}            // R.esult;set 객체 해제
			
			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제
			
			if(con != null) try{con.close();}catch(SQLException sqle){}   // Connection 해제
		}
		return flag;
	}*/
//--------------------------------------------------------------------------------------------------------------------------------------
	public static boolean signUp(String id, String password) {               //아이디와 비밀번호 받아와서 DB에 저장
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		
		try {
			con = db_link.DB_Connect.getConnection();
			sql = "insert into member(id, password, state, seatNum) values(?, ?, ?, ?)"; 
			pstmt = con.prepareStatement(sql);	
			//auto_increment 사용
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setInt(3, 0);
			pstmt.setNull(4, Types.INTEGER);
            pstmt.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
			return flag;
		}finally {
			

			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제

			if(con != null) try{con.close();}catch(SQLException sqle){}   // Connection 해제
		}
		flag = true;
		return flag;
		
	}


//--------------------------------------------------------------------------------------------------------------------------------------
	public static Vector<String> getID() {               // 접속 중인 id 알아내기

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Vector<String> gI = new Vector<String>();
	
		try {
			con = db_link.DB_Connect.getConnection();
			sql = "select id from member where state=1 order by seatNum";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gI.add(rs.getString("id"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try{rs.close();}catch(SQLException sqle){}            // R.esult;set 객체 해제
			
			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}   // PreparedStatement 객체 해제
			
			if(con != null) try{con.close();}catch(SQLException sqle){}   // Connection 해제
		}
		return gI;
	}
}