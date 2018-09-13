package view_frame;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
//import java.io.File;
import java.io.IOException;
import java.net.URL;


public class SeatFrame extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage img = null;
	JLabel label = new JLabel();
	private int numSeat;
	
	public SeatFrame(int numSeat) {
		this.setNumSeat(numSeat);
		imgRead(getClass().getClassLoader().getResource("seatOff.png")); //aqua0
		setLayout(null);
		
		JPanel panImg = new InnerPanel();
		panImg.setBounds(0, 0, 60, 30);
		panImg.setOpaque(false);
		
		//상태정보 패널
		JPanel panContent = new JPanel();
		panContent.setLayout(null);
		panContent.setBounds(0, 0, 60, 30);
		label = new JLabel(Integer.toString(numSeat + 1));
		label.setBounds(25, 8, 80, 15);
		if(numSeat >= 9) label.setBounds(22, 8, 80, 15);
		label.setForeground(Color.YELLOW);
		label.setFont(new Font("Arial", Font.BOLD, 15)); 
		panContent.add(label);
		panContent.setOpaque(false);
		
		
		//제이레이어 패널
		
		JLayeredPane panLayered = new JLayeredPane();
		panLayered.setBounds(0, 0, 500, 400);  //
		panLayered.setLayout(null);
		panLayered.setOpaque(false);
		panLayered.add(panImg, new Integer(0));       //integer(0), 0)
		panLayered.add(panContent, new Integer(1));   //integer(1), 0)
		add(panLayered);
		setVisible(true);
		setOpaque(true);
		setFocusable(true);
	}
	
	public static void main(String [] args) {
		JFrame frameTest = new JFrame();
		frameTest.setTitle("시트패널");
		SeatFrame a = new SeatFrame(9);		
		frameTest.add(a);
		frameTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameTest.setSize(60, 70);
		frameTest.setVisible(true);
		
	}
	
	class InnerPanel extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(img, 0, 0, null);
		}
	}
	//////////////////////////////////////////////공사중
	public void imgRead(URL url) {
		try {
			img = ImageIO.read(url); //img = ImageIO.read(new File("img/" + filename + ".png"));	
		} catch (IOException e) {
		     System.out.println("이미지 불러오기 실패!"); 
	         System.exit(0);
	    }
	         repaint();
	}
	//////////////////////////////////////////////공사중
	

	public int getNumSeat() {
		return numSeat;
	}

	public void setNumSeat(int numSeat) {
		this.numSeat = numSeat;
	}
	
}
