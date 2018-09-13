package model;
import db_link.Query_Statement;

public class Member { //현재 프로그램에 로그인한 멤버, 전체 멤버X
	
	private String id;
	private String password;
	private boolean state;
	private int seatNume;
	
	
	
	public Member(String id, String password) {
		setId(id);
		setPassword(password);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getState(int seatNum) {
		return state;
	}
	public void setState(String id, boolean state) {
		Query_Statement.setLoginState(id, state);
	}
	public int getSeatNume() {
		return seatNume;
	}
	public void setSeatNume(int seatNume) {
		this.seatNume = seatNume;
	}
}
