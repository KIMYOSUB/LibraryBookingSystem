package main;


import view_frame.SeatStateFrame;
import view_frame.SignUpFrame;

import java.util.Vector;

import model.Member;
import view_frame.ConfirmFrame;
import view_frame.LoginFrame;


public class Main {
	private static String presentUser;
	private static boolean loginState = false;
	private SeatStateFrame ssf;
	private LoginFrame lf;
	private ConfirmFrame cf;
	private SignUpFrame suf;
	private static Vector <Member> m = new Vector<Member>();
	
	
	
    public static void main(String[] args) { //throws Exception
        Main main = new Main();
       
    	main.ssf = new SeatStateFrame();
        main.ssf.setMain(main);
        
    }
     
    public void showLoginFrame(SeatStateFrame ssf, Main main){  
    	ssf.dispose();
    	main.lf = new LoginFrame();
    	main.lf.setMain(main);
    }
    
    public void showSeatStateFrame(LoginFrame loginframe, Main main) {
    	loginframe.dispose();
    	main.ssf = new SeatStateFrame();
    	main.ssf.setMain(main);
    }
    
    public void showSeatStateFrame(ConfirmFrame cf, Main main) {
    	cf.dispose();
    	main.ssf = new SeatStateFrame();
    	main.ssf.setMain(main);
    }
    
    public void showSeatStateFrame(SignUpFrame suf, Main main) {
    	suf.dispose();
    	main.ssf = new SeatStateFrame();
    	main.ssf.setMain(main);
    }
    
    public void showConfirmFrame(SeatStateFrame ssf, Main main) {
    	ssf.dispose();
    	main.cf = new ConfirmFrame();
    	main.cf.setMain(main);
    }
    
    public void showSignUpFrame(LoginFrame loginframe, Main main) {
    	loginframe.dispose();
    	main.suf = new SignUpFrame();
    	main.suf.setMain(main);
    }

	public static boolean getLoginState() {
		return loginState;
	}

	public void setLoginState(boolean loginState) {
		Main.loginState = loginState;
	}
	
	public static Vector <Member> getM() {
		return m;
	}

	public void setM(Vector <Member> m) {
		Main.m = m;
	}

	public static String getPresentUser() {
		return presentUser;
	}

	public static void setPresentUser(String presentUser) {
		Main.presentUser = presentUser;
	}
	
	public static int getSeatNum() {
		return Main.getM().lastElement().getSeatNume();
	}

	public SignUpFrame getSuf() {
		return suf;
	}

	public void setSuf(SignUpFrame suf) {
		this.suf = suf;
	}
     
}
