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


public class SignUpFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage img =null;
	JTextField tf;
	JTextField tf1;
	JPasswordField pf;
	JLabel note;
	JButton bt;
	JButton cls;
	Main main;
	SeatStateFrame ssf;
	
	public SignUpFrame() {
		setTitle("SignUp Frame");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		JLayeredPane layeredpane = new JLayeredPane();
		layeredpane.setBounds(0, 0, 415, 495); // 580, 386
		layeredpane.setLayout(null);
		
		try {
			img = ImageIO.read(getClass().getClassLoader().getResource("signUp1.jpg")); 
		}catch(IOException e) {
			System.out.println("이미지 불러오기 실패");
			System.exit(0);
		}
		
		MyPanel panel = new MyPanel();
		panel.setBounds(0, 0, 415, 495); //580, 386
		//화면 가운데로 나오게
		Dimension frameSize = panel.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
		
		
		tf = new JTextField(15);
		tf.setBounds(60, 159, 250, 30); //176, 132, 250, 30
		tf.setOpaque(false);
		tf.setForeground(Color.WHITE);
		tf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		layeredpane.add(tf);
		
		tf1 = new JTextField(15);
		tf1.setBounds(60, 249, 250, 30); //176, 132, 250, 30
		tf1.setOpaque(false);
		tf1.setForeground(Color.WHITE);
		tf1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		layeredpane.add(tf1);
		
		note = new JLabel("*이메일은 실제로 저장되지는 않습니다.");
		Font ft = new Font("돋음체", Font.ITALIC, 12);
		note.setFont(ft);
		note.setBounds(60, 289, 250, 15);
		note.setForeground(Color.RED);
		layeredpane.add(note);
		
		pf = new JPasswordField(15);
		pf.setBounds(60, 340, 250, 30); //176, 190, 250, 30
		pf.setOpaque(false);
		pf.setForeground(Color.WHITE);
		pf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		layeredpane.add(pf);
		
		cls = new JButton(new ImageIcon(getClass().getClassLoader().getResource("signup_cancel.png")));
		cls.setBounds(345, 25, 50, 50);
		cls.setBorderPainted(false);
		cls.setContentAreaFilled(false);
		cls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.showSeatStateFrame(SignUpFrame.this, main);
			}
		});
		
		
		
		bt = new JButton(new ImageIcon(getClass().getClassLoader().getResource("sign_upBT.png"))); //lb5
		bt.setBounds(49, 406, 318, 40); //148, 253, 290, 36
		
		bt.addActionListener(this);
		
		//bt.setBorderPainted(false);
		//bt.setFocusPainted(false);
		//bt.setContentAreaFilled(false);
		
		layeredpane.add(bt);
		layeredpane.add(cls);
		layeredpane.add(panel);
		add(layeredpane);
		setSize(415, 495); //580, 386
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
 
            boolean success = Query_Statement.signUp(id, password);
 
            if (success) {
            	JOptionPane.showMessageDialog(null, "관리자의 가입승인을 기다리세요", "감사합니다", JOptionPane.INFORMATION_MESSAGE);
            	main.showSeatStateFrame(this, main);
            }
            else {
            		JOptionPane.showMessageDialog(null, "DB연결 실패!");
            }
            
        }
        password = null;
    }
 
		
	
	public static void main(String[] args) {
		new SignUpFrame();
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
	

		
}
