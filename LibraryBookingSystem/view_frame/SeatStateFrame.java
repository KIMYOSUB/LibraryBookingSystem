package view_frame;

import main.Main;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;

import db_link.Query_Statement;

public class SeatStateFrame extends JFrame implements ActionListener, Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Main main;
    JButton login;
    JButton xbt;
    JLabel aSeat;
    JLabel uSeat;
    JLabel rSeat;
    JLabel user;
    int usedSeat;
    Thread th;
    JLabel dot;
    Font ftt;
    HashMap<Integer, String> hm;
    
	public SeatStateFrame() {
		setTitle("현재 열람실 현황");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(822, 620); // 830, 520
		setLayout(null);
		
		ImageIcon lb = new ImageIcon(getClass().getClassLoader().getResource("n_loginButton.jpg"));
		ImageIcon lb_r= new ImageIcon(getClass().getClassLoader().getResource("r_loginButton.jpg"));
		ImageIcon lb_p= new ImageIcon(getClass().getClassLoader().getResource("p_loginButton.jpg"));
		
		
		login = new JButton(lb);
		if(Main.getLoginState()) {
			login.setVisible(false);
		}
		
		login.setRolloverIcon(lb_r);
		login.setPressedIcon(lb_p);
		login.setBounds(50, 170, 98, 102);  //50, 135
		login.setBorderPainted(false);
		login.addActionListener(this);
		
		xbt = new JButton(new ImageIcon(getClass().getClassLoader().getResource("cancel.png")));
		xbt.setBounds(760, 15, 40, 40);
		xbt.setBorderPainted(false);
		xbt.setContentAreaFilled(false);
		xbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		
		int posXpanSeat = 0; 
		int posYpanSeat = 0;
		SeatFrame[] pan = new SeatFrame[40]; /////////////////////
		
		
		
		//좌석 라벨 위치 지정
		JPanel seat40 = new JPanel();
		seat40.setLayout(null);
		seat40.setOpaque(false);
		seat40.setBounds(65, 300, 710, 277); //
		int n = 0;
		for (int seat = 0; seat < 40; seat++) {
		    pan[seat] = new SeatFrame(seat);
		    if (seat % 8 == 0 && seat != 0) {
		        posXpanSeat = 0;
		        posYpanSeat += 60;
		    }
		    if(seat == (4 + 8*n)) { posXpanSeat += 100; n++;};
		    pan[seat].setBounds(posXpanSeat, posYpanSeat, 60, 30);
		    posXpanSeat += 75;
		    seat40.add(pan[seat]);
		} //여기 까지는 좌석들의 위치가 지정되고 이벤트도 없으면 이미지는 다 seatOff상태이다.
		
		//DB를 체크해서 로그인 되어 있는 유저들의 좌석 이미지 표시 및 이벤트를 달아준다.
		dbChecking(pan);
		//////////////////////////
		ftt = new Font("돋음체", Font.BOLD, 18);
		aSeat = new JLabel(Integer.toString(pan.length));
		aSeat.setBounds(173, 71, 30, 40);
		aSeat.setFont(ftt);
		aSeat.setForeground(Color.BLACK);
		uSeat = new JLabel(Integer.toString(usedSeat));
		uSeat.setBounds(390, 71, 30, 40);
		uSeat.setFont(ftt);
		uSeat.setForeground(Color.RED);
		rSeat = new JLabel(Integer.toString(pan.length - usedSeat));
		rSeat.setBounds(580, 71, 30, 40);
		rSeat.setFont(ftt);
		rSeat.setForeground(Color.YELLOW);
		user = new JLabel(" ");
		if(Main.getLoginState()) user.setText(Main.getPresentUser());
		user.setBounds(708, 71, 70, 40);
		user.setFont(ftt);
		user.setForeground(Color.ORANGE);
		
		//윈도우창 나올 때 화면 가운데로 호출 
		Dimension frameSize = this.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
		JLayeredPane layeredpane = new JLayeredPane();
		layeredpane.setBounds(0, 0, 840, 630); //
		layeredpane.setLayout(null);
		
		//배경
		MyPanel myPanel = new MyPanel();
		myPanel.setLayout(null);
		myPanel.setBounds(0, 0, 822, 620); 
		
		//시계
		JLabel cla = new ClockLabel();
		cla.setBounds(555, 592, 265, 20);
		
		dot = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("starDdong1.png")));
		dot.setBounds(100, 277, 15, 14); //0, 110, 15, 14 ///////////////////////////////////////
		
		
		//최종삽입
		layeredpane.add(dot, new Integer(1));
		layeredpane.add(myPanel, new Integer(0));
		layeredpane.add(seat40, new Integer(1));
		layeredpane.add(cla, new Integer(1));
		layeredpane.add(login, new Integer(1));
		layeredpane.add(xbt, new Integer(1));
		layeredpane.add(aSeat, new Integer(1)); layeredpane.add(uSeat, new Integer(1)); 
		layeredpane.add(user, new Integer(1));  layeredpane.add(rSeat, new Integer(1));
		
		add(layeredpane);
		
		th = new Thread(this);
		th.start();
		
		this.setUndecorated(true);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new SeatStateFrame();
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		main.showLoginFrame(this, main);
	}
	
	public void dbChecking(SeatFrame pan[]) {
		Vector<Integer> w;
		Vector<String> x;
		hm = new HashMap<Integer, String>();
		w = Query_Statement.checkSeatNum(); // DB에 로그인 되어있는 유저들의 좌석들이 순차적으로 저장 되어 있다.
		x = Query_Statement.getID();
		for(int i = 0; i < w.size(); i++) {
			hm.put(w.get(i), x.get(i));
		}
		
		usedSeat = w.size();
		
		int j = 0;                         // 40개의 좌석을 검사하면서 로그인한 좌석이 나오면 1씩 증가할 정수.
		boolean used;
		
		if(usedSeat == 0) {										//전 좌석이 비어있을 때
			used = false;
			for(int i = 0; i < 40; i++) {
				pan[i].addMouseListener(new LabelAction(this, i, used)); 
			}
		}
		else {                                                 //한명이라도 있을 때
		
			for(int i = 0; i < 40; i++) {
				used = false;
				if(j < usedSeat) {           				   // 액션 리스너 완료한 좌석 수 < 접속 중인 유저 전체수     
					if(i == w.get(j)) {
						pan[i].imgRead(getClass().getClassLoader().getResource("seatOn.png")); 
						used = true;
						j++;
					}
					else {
						
					}
				}
				pan[i].addMouseListener(new LabelAction(this, i, used));	
			}
		}
	}
	
	
	class MyPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Image image;
		public MyPanel() {
			image = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("readingroom7.jpg"));
			
		}
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(image, 0, 0, this);
		}
		@Override
		public void update(Graphics g) {
			super.update(g);
		}
	}
	
	class ClockLabel extends JLabel implements Runnable {
		
		private static final long serialVersionUID = 1L;
		
		ClockLabel() {
			
			new Thread(this).start();
			
		}

		public void run() {
			while(true) {
				try {
					Calendar c = Calendar.getInstance();
					int ap = c.get(Calendar.AM_PM);
					String app = "";
					if (ap == 1) app = "오후";
					if (ap == 0) app = "오전";
					int year = c.get(Calendar.YEAR);
					int hour = c.get(Calendar.HOUR);
					int min = c.get(Calendar.MINUTE);
					int second = c.get(Calendar.SECOND);
					String clockText = "현재시각 : ";
					clockText = clockText.concat(Integer.toString(year));
					clockText = clockText.concat("년 ");
					clockText = clockText.concat(app+" ");
					clockText = clockText.concat(Integer.toString(hour));
					clockText = clockText.concat("시 ");
					clockText = clockText.concat(Integer.toString(min));
					clockText = clockText.concat("분 ");
					clockText = clockText.concat(Integer.toString(second));
					clockText = clockText.concat("초");
					setText(clockText);
					setForeground(Color.BLUE);
					setFont(new Font("돋음체", Font.BOLD, 15));
					Thread.sleep(1000);
				} catch(InterruptedException e) { return; }
			}
		}
	}
	
	public void run() {
		int x = dot.getX();
		int y = dot.getY();
		while(true) {
			try {
				Thread.sleep(15);
			}
			catch(InterruptedException e) {
				return;
			}
			
			if(y == 277 && x >= 45 && x < 342) {   //첫 번째 라인
				x+=1;
			}
			else if(x == 342 && y <= 277 && y > 210) {
				y-=1;
			}
			else if (y == 210 && x <= 342 && x > 296) {
				x-=1;
				
			}
			else if (x == 296 && y <= 210 && y >= 164) {
				y-=1;
			}
			else if (y == 163 && x >= 296 && x < 759) {
				x+=1;
			}
			else if (x == 759 && y >= 163 && y < 579) {
				y+=1;
			}
			else if (y == 579 && x <= 759 && x >= 46) { 
				x-=1;
				if(x == 445) dot.setVisible(false);
				if(x == 388) dot.setVisible(true);
			}
	
			else if (x == 45 && y <= 579 && y >= 277) { 
				y-=1;
			}
			
			dot.setLocation(x, y);
		} //while
	}
	
	class LabelAction extends MouseAdapter {
		SeatStateFrame ssf;
		int i;
		boolean used;
		LabelAction(SeatStateFrame ssf, int i, boolean used) { //pan  
			this.i = i;
			this.used = used;
			this.ssf = ssf;
			
		}
		public void mousePressed(MouseEvent e) {
			if(!(Main.getLoginState())) {
				JOptionPane.showMessageDialog(null, "로그인이 필요합니다!", "message", JOptionPane.INFORMATION_MESSAGE);
			}
	
			else {
				if(used) {
					JOptionPane.showMessageDialog(null, "빈 좌석을 선택해 주세요", "message", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
				int result = 
	            JOptionPane.showConfirmDialog(null, "좌석 "+(i+1)+"번 이용하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
	           		if(result == JOptionPane.CLOSED_OPTION) {
	           			
	           		}
	           		else if (result == JOptionPane.YES_OPTION) {
	           			String id = Main.getM().lastElement().getId();
	           			Main.getM().lastElement().setSeatNume(i);
	           			Query_Statement.setLoginState(id, true);	
	           			Query_Statement.setSeatNum(id, i);
	         
	           			main.showConfirmFrame(ssf, main);
	           		}
	           		else {
	           			
	           		}
				}
			}
		}
		public void mouseEntered(MouseEvent e) {
			if(used) {
				
				user.setText(hm.get(i));
			}
			else
				user.setText("empty");
		}
		public void mouseExited(MouseEvent e) {
			if(Main.getLoginState()) {
				user.setText(Main.getPresentUser());
			}
			else
				user.setText(" ");
		}
	}

}
