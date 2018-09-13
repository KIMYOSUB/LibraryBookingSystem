package view_frame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Calendar;
import main.Main;
import model.Member;

public class ConfirmFrame extends JFrame {
	/**
	 * 
	 */
	Member m;
	Main main;
	private static final long serialVersionUID = 1L;
	private JButton bk; //뒤로가기
	private JButton close;
	private JLabel lb;
	private JLabel lb1;
	private JLabel lb2;
	private Font f1;
	Calendar d;
	public ConfirmFrame() {
		setTitle("ConfirmFrame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(228, 231, 200)); //new Color(237, 243, 223)
		setSize(830, 500); //830, 450
		 
		d = Calendar.getInstance(); //객체 생성 및 현재 일시분초...셋팅
		int ap = d.get(Calendar.AM_PM);
		String ntime = new String();
		String app = "";
		if (ap == 1) app = "오후";
		if (ap == 0) app = "오전";
		ntime = String.valueOf(d.get(Calendar.YEAR)) + "년 ";
		ntime += String.valueOf(d.get(Calendar.MONTH)+1) + "월 ";
		ntime += String.valueOf(d.get(Calendar.DATE)) + "일 ";
		ntime += app;
		ntime += String.valueOf(d.get(Calendar.HOUR)) + "시 ";
		ntime += String.valueOf(d.get(Calendar.MINUTE)) + "분";
		lb1 = new JLabel(ntime);
		lb1.setBounds(310, 270, 450, 40); //240
		lb1.setFont(new Font("고딕", Font.PLAIN, 20));
		lb1.setForeground(new Color(52, 143 ,177));
		
		String userName = Main.getPresentUser() + "님";
		lb2 = new JLabel(userName);
		lb2.setBounds(370, 190, 100, 27);
		lb2.setFont(new Font("양재백두체B", Font.ITALIC, 25));
		lb2.setForeground(Color.RED);
		
		bk = new JButton(new ImageIcon(getClass().getClassLoader().getResource("homeButton.jpg")));
		bk.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("homeButton1.png")));
		bk.addActionListener(new HomeActionButton(this));
		
		f1 = new Font("굴림", Font.BOLD, 30);
		
		lb = new JLabel(Integer.toString(Main.getSeatNum()+1)+"번 좌석 예약이 완료되었습니다.");
		lb.setFont(f1);
		lb.setBounds(195, 230, 550, 30); // 255
		
		
		bk.setBounds(690, 360, 100, 100); //700, 330
		bk.setBorderPainted(false);
		bk.setContentAreaFilled(false);
		Dimension frameSize = this.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
		this.setUndecorated(true);
		c.add(new NorthBar());
		c.add(bk);
		c.add(lb);
		c.add(lb1);
		c.add(lb2);
		setVisible(true);
	}
	
	class NorthBar extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JLabel la1;
		NorthBar() {
			setLayout(null);
			la1 = new JLabel("예약 안내");
			la1.setFont(new Font("맑은 고딕", Font.BOLD, 25));
			la1.setForeground(Color.WHITE);
			la1.setBounds(40, 25, 140, 30);
			Color color1 = new Color(16, 64, 60); //22, 110, 132
			close = new JButton(new ImageIcon(getClass().getClassLoader().getResource("cancel1.png")));
			close.setBounds(770, 20, 40, 40);
			close.setBorderPainted(false);
			close.setContentAreaFilled(false);
			close.setFocusPainted(false);
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			
			add(close);
			add(la1);
			setBackground(color1);
			setSize(830, 80);
		}
	}
	public static void main(String[] args) {
		new ConfirmFrame();
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	class HomeActionButton implements ActionListener {
		ConfirmFrame cf;
		HomeActionButton(ConfirmFrame cf) {
			this.cf = cf;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			main.setLoginState(false);
			main.showSeatStateFrame(cf, main);
		}
		
	}
	
}
