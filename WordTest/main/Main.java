package main;

import frame.MenuCoverFrame;
import frame.MainFrame;


public class Main {

	public static String prURL = "Text\\Problem.txt";
	public static String asURL = "Text\\Answer.txt";
	MenuCoverFrame mcf;
	MainFrame mf;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.mcf = new MenuCoverFrame();
		main.mcf.setMain(main);
	}
	
	public void showMainFrame(MenuCoverFrame mcf, Main main){  
    	mcf.dispose();                                         
    	
    	main.mf = new MainFrame();
    	main.mf.setMain(main);
    }
	
	public void showMenuCoverFrame(MainFrame mf, Main main) {
		mf.dispose();
		
		main.mcf = new MenuCoverFrame();
		main.mcf.setMain(main);
	}
	
	public void setprURL(String prURL) {
		Main.prURL = prURL;
	}
	
	public void setasURL(String asURL) {
		Main.asURL = asURL;
	}
} 
