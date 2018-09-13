package view_frame;

import java.awt.*;
import javax.swing.*;

import db_link.Query_Statement;
import main.Main;

import java.awt.event.*;
import java.awt.image.BufferedImage;
//import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.Member;

public class LoginFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage img =null;
	JTextField tf;
	JPasswordField pf;
	JButton bt;
	JButton cls;
	JButton su;
	JButton sch;
	Main main;
	SeatStateFrame ssf;
	
	
	public LoginFrame() {
		setTitle("Login Frame");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		JLayeredPane layeredpane = new JLayeredPane();
		layeredpane.setBounds(0, 0, 384, 508); //0, 0, 414, 494 
		layeredpane.setLayout(null);
		
		try {
			img = ImageIO.read(getClass().getClassLoader().getResource("logiin0.png")); //loginFrame3.jpg
		}catch(IOException e) {
			System.out.println("이미지 불러오기 실패");
			System.exit(0);
		}
		
		MyPanel panel = new MyPanel();
		panel.setBounds(0, 0, 384, 508); //414, 494
		//화면 가운데로 나오게
		Dimension frameSize = panel.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
		
		Font ftt = new Font("맑은 고딕", Font.PLAIN, 16); //17
		
		tf = new JTextField("아이디", 15);
		tf.setBounds(34, 171, 250, 30); //60, 158, 250, 30
		tf.setFont(ftt);
		tf.setOpaque(false);
		tf.setForeground(Color.GRAY);
		tf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		tf.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (tf.getText().equals("아이디")) {
		            tf.setText("");
		            tf.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (tf.getText().isEmpty()) {
		            tf.setForeground(Color.GRAY);
		            tf.setText("아이디");
		        }
		    }
		    });
		layeredpane.add(tf);
		
		
		pf = new JPasswordField("비밀번호",15);
		pf.setEchoChar((char) 0);
		pf.setBounds(36, 240, 250, 30); //60, 248, 250, 30
		pf.setFont(ftt);
		pf.setOpaque(false);
		pf.setForeground(Color.GRAY);
		String pwd = new String(pf.getPassword());
		pf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		pf.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (pwd.equals("비밀번호")) {
		            pf.setText("");
		            pf.setForeground(Color.BLACK);
		            pf.setEchoChar('*');
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (pf.getPassword().length == 0) {
		            pf.setForeground(Color.GRAY);
		            pf.setEchoChar((char) 0);
		            pf.setText("비밀번호");
		        }
		    }
		    });
		layeredpane.add(pf);
		
		cls = new JButton(new ImageIcon(getClass().getClassLoader().getResource("loginButton0.jpg")));//login_cancel.png
		cls.setBounds(345, 1, 38, 40); //345, 25, 50, 50
		cls.setBorderPainted(false);
		cls.setContentAreaFilled(false);
		cls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		su = new JButton(new ImageIcon(getClass().getClassLoader().getResource("sign_upBT.jpg")));//con_signup.jpg 
		su.setBounds(207, 437, 50, 21); //175, 420, 61, 17
		su.setBorderPainted(false);
		su.setFocusPainted(false);
		su.setContentAreaFilled(false);
		su.addActionListener(new JoinUs(this, main));
		
		bt = new JButton(new ImageIcon(getClass().getClassLoader().getResource("loginButton00.jpg"))); //loginBT.png
		bt.setBounds(31, 336, 322, 64); //48, 362, 318, 40
		bt.addActionListener(this);
		
		sch = new JButton(new ImageIcon(getClass().getClassLoader().getResource("null.jpg")));
		sch.setBounds(127, 438, 67, 18); //175, 420, 61, 17
		sch.setBorderPainted(false);
		sch.setFocusPainted(false);
		sch.setContentAreaFilled(false);
		sch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "준비중입니다", "죄송합니다", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		
		//bt.setBorderPainted(false);
		//bt.setFocusPainted(false);
		//bt.setContentAreaFilled(false);
		
		layeredpane.add(bt);
		layeredpane.add(cls);
		layeredpane.add(su);
		layeredpane.add(sch);
		layeredpane.add(panel);
		add(layeredpane);
		setSize(384, 508); //414, 494
		this.setUndecorated(true);
		setVisible(true);
		
		
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String id = tf.getText();
        char[] pass = pf.getPassword();
        String password = new String(pass);
        
        if (id.equals("") || password.equals("")) {
            // 메시지를 날린다.
            JOptionPane.showMessageDialog(null, "빈칸이 있네요");
        } else {
 
            // 로그인 참 거짓 여부를 판단
            boolean existLogin = Query_Statement.loginTest(id, password);
 
            if (existLogin) {
                // 로그인 성공일 경우
            	if(Query_Statement.getLoginState(id)) {         //현재 이 id의 유저가 사용 중인가?
            		int sn = Query_Statement.getSeatNum(id);    //이 유저의 좌석번호
            		
            		int result = 
            		JOptionPane.showConfirmDialog(null, "좌석 "+(sn+1)+"번 반납하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
            		if(result == JOptionPane.CLOSED_OPTION) {
            			System.exit(0);
            		}
            		else if (result == JOptionPane.YES_OPTION) {
            			Query_Statement.setLoginState(id, false);
            			main.setLoginState(false);
            			JOptionPane.showMessageDialog(null, "반납이 완료되었습니다!", "감사합니다", JOptionPane.INFORMATION_MESSAGE);
            			Query_Statement.setSeatNum(id, -1);
            			main.showSeatStateFrame(this, main);
            			
            		}
            		else {
            			System.exit(0);
            		}
            			
            	}
            	else {
            		JOptionPane.showMessageDialog(null, "로그인 성공");
            		Main.getM().add(new Member(id, password)); 
            		main.setLoginState(true);
            		Main.setPresentUser(id);
            		main.showSeatStateFrame(this, main);
            		}
            } else {
                // 로그인 실패일 경우
                JOptionPane.showMessageDialog(null, "로그인 실패");
            }
 
        }
        password = null;
 
    }
 
		
	
	public static void main(String[] args) {
		new LoginFrame();
	}
	
	class MyPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public void setMain(Main main) {
		this.main = main;
		
	}
	
	class JoinUs implements ActionListener{
		LoginFrame lf;
		JoinUs(LoginFrame lf, Main main) {
			this.lf = lf;
			
		}
		public void actionPerformed(ActionEvent e) {
			main.showSignUpFrame(lf, main);
		}
	}
		
}
