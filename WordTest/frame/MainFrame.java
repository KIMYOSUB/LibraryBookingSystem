package frame;

import javax.swing.*;

import main.Main;
import inputOutput.SaveWords;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {                       // 문제 푸는 화면
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Main main;
	String []array;
	String []array1;
	JButton submit;
	JButton home;
	JButton showAn; 
	ButtonGroup g;
	JRadioButton rb1;
	JRadioButton rb2;
	JRadioButton rb3;
	JTextField answer;
	JLabel sa; 
	JLabel la1;
	JLabel co;
	JLabel ic;
	JLabel info;		
	Font f1;
	JTextArea ques;
	JScrollPane sp;
	SaveWords sw = new SaveWords();
	JudgeThread jt;
	JButton rsc;
	
	int size = sw.prc.size();
	int count = 0;
	int count1 = 0;
	int rd;
	boolean toggle = true;
	
	public MainFrame() {
		setTitle("다용도 단어 시험");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pp = new PrPanel();
		pp.setBounds(20, 150, 655, 200);
		pp.setLayout(null);
		
		Container c = getContentPane();
		c.setLayout(null);
		
		f1 = new Font("맑은 고딕", Font.BOLD, 15);             		//폰트1
		
		rb1 = new JRadioButton("Default", true);
		rb1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				 if(e.getStateChange() == ItemEvent.SELECTED) {
					 c.setBackground(new Color(163,204,163));  			// 청록색
					 ques.setFont(new Font("고딕", Font.PLAIN, 20));
					 ques.setForeground(Color.BLACK);
					 la1.setForeground(Color.BLACK);
					info.setForeground(Color.BLUE);
				 }
				
			}
			
		});
		rb2 = new JRadioButton("Kakao");
		rb2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					c.setBackground(new Color(255, 235, 51)); 					
					ques.setFont(new Font("HY동녘B", Font.PLAIN, 20));
					ques.setForeground(new Color(69, 44, 42));         			 
					la1.setForeground(new Color(69, 44, 42));
					info.setForeground(new Color(69, 44, 42));
				 }
				
			}
			
		});
		rb3 = new JRadioButton("Sky");
		rb3.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					c.setBackground(new Color(214,230,245));				// 하늘색
					ques.setFont(new Font("HY얕은샘물M", Font.BOLD, 20));
					ques.setForeground(Color.PINK);          
					la1.setForeground(Color.PINK);
					info.setForeground(Color.PINK); 
				 }
				
			}
			
		});
		rsc = new JButton("Color"); 
		rsc.setBounds(605, 125, 70, 20);
		rsc.setContentAreaFilled(false);
		rsc.setFocusPainted(false);
		rsc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setColor();
			}

			private void setColor() {
				int r = (int)(Math.random()*250 + 5);
				int g = (int)(Math.random()*250 + 5);
				int b = (int)(Math.random()*250 + 5);
				Color rc = new Color(r, g, b);
				c.setBackground(rc);
			}
		});
		g = new ButtonGroup();
		g.add(rb1); g.add(rb2); g.add(rb3);
		rb1.setBounds(320, 10, 70, 30);
		rb1.setBorderPainted(false);
		rb1.setContentAreaFilled(false);
		rb2.setBounds(390, 10, 70, 30);
		rb2.setBorderPainted(false);
		rb2.setContentAreaFilled(false);
		rb3.setBounds(460, 10, 70, 30);
		rb3.setBorderPainted(false);
		rb3.setContentAreaFilled(false);
		
		
		la1 = new JLabel("정답 : ");                           		//라벨1
		la1.setFont(f1);
		la1.setForeground(Color.BLACK);
		la1.setBounds(20, 12, 50, 20);
		
		info = new JLabel("문항 수 : "+count+" / "+size);
		info.setForeground(Color.BLUE);
		info.setFont(new Font("돋움", Font.BOLD, 18));
		info.setBounds(20, 90, 250, 20);
		
		answer = new JTextField(30);                        		//정답 TextField
		answer.setBounds(67, 14, 150, 20);
		
		showAn = new JButton(new ImageIcon(getClass().getClassLoader().getResource("answerButton1.png")));
		showAn.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("answerButton2.png")));
		showAn.setBounds(20, 50, 60, 30);
		showAn.setBorderPainted(false);
		showAn.setContentAreaFilled(false);
		showAn.setEnabled(false);
		showAn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sa.setText(array1[count -1]);
				sa.setVisible(true);
			}
			
		});
		
		sa = new JLabel(array1[count -1]);
		sa.setBounds(90, 55, 200, 20);
		sa.setFont(new Font("돋움",Font.PLAIN,16));
		sa.setForeground(Color.RED);
		sa.setVisible(false);
		
		co = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("correct1.png")));
		ic = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("incorrect1.png")));
		co.setBounds(20, 115, 30, 30);  
		ic.setBounds(20, 115, 30, 30);  
		co.setVisible(false);
		ic.setVisible(false);
		
		home = new JButton(new ImageIcon(getClass().getClassLoader().getResource("back1.png")));
		home.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("back2.png")));
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.setBounds(580, 10, 100, 39);
		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showMenuCoverFrame(MainFrame.this, main);
			}
			
		});
		
		submit = new JButton(new ImageIcon(getClass().getClassLoader().getResource("Submit1.png")));                       			//제출 버튼
		submit.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("Submit2.png")));
		submit.setBorderPainted(false);
		submit.setContentAreaFilled(false);
		submit.setBounds(227, 10, 85, 28);
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ans = answer.getText();
				boolean result = figureOut(ans);
			
				if(result == true) {
					toggle = true;
					sa.setVisible(false);
					showAn.setEnabled(false);
					jt = new JudgeThread(co);
					jt.start();
					answer.setText("");
					
					if(count == size) {
						if(count1 != 0) { 
							int rss = JOptionPane.showConfirmDialog(null, "틀린문제가 있습니다, 이 부분만 다시 하겠습니까?",
									"Confirm", JOptionPane.YES_NO_OPTION);
							if(rss == JOptionPane.CLOSED_OPTION) {
								System.exit(0);
							}
							else if(rss == JOptionPane.YES_OPTION) {
								count = 0;        //초기화
								count1 = 0;
								shuffleProblem(); //문제 섞고
								pick();           //다시 골라내서 출력
								info.setText("문항 수 : "+count+" / "+size);
							}
							else {
								count1 = 0;
								again();
							}
						}
						else {
							again();
						}
					} //
					else pick();
					info.setText("문항 수 : "+count+" / "+size);
				}
				else {
					showAn.setEnabled(true);
					jt = new JudgeThread(ic);
					jt.start();
			
					if(toggle) {
						sw.prc.add(count1, array[count -1]);
						sw.anc.add(count1, array1[count -1]);
						count1++;
						toggle = false;
					}
				}
			}
		});
		
		c.add(submit); c.add(la1); c.add(answer); c.add(co); c.add(ic); c.add(info); c.add(home);
		c.add(pp); c.add(rb1); c.add(rb2); c.add(rb3); c.add(showAn); c.add(sa); c.add(rsc);
		c.setBackground(new Color(163,204,163));  //	
		
		setVisible(true);
		setSize(700, 400);
		
		Dimension frameSize = this.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
	}
	
	class PrPanel extends JPanel {								//문제부분 판넬
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		PrPanel() {
			ques = new JTextArea(10, 20);
			ques.setBackground(Color.WHITE);
			ques.setFont(new Font("고딕", Font.PLAIN, 20)); 
			ques.setLineWrap(true);
			ques.setEditable(false);
			shuffleProblem();
			pick();
			sp = new JScrollPane(ques);
			sp.setBounds(0, 0, 655, 200);
			//ques.setBounds(0, 0, 655, 200);
			add(sp);
		}
	}
	
	class JudgeThread extends Thread {                 //맞다 틀리다 표시해주는 쓰레드
		JLabel ci;
		JudgeThread(JLabel ci) {
			this.ci = ci;
		}
		public void run() {
			try {
				ci.setVisible(true);
				Thread.sleep(1500);
				ci.setVisible(false);
			} catch (InterruptedException e) {
				return;
			}

			return;
		}
	}
	
	public void again() {
		int rs = JOptionPane.showConfirmDialog(null, "끝났습니다, 다시하시겠습니까?",
				"Confirm", JOptionPane.YES_NO_OPTION);
		if(rs == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
		else if(rs == JOptionPane.YES_OPTION) {
			sw = new SaveWords();
			count = 0;        //초기화
			shuffleProblem(); //문제 섞고
			pick();           //다시 골라내서 출력
			info.setText("문항 수 : "+count+" / "+size);
		}
		else {
			main.showMenuCoverFrame(MainFrame.this, main);
		}
	}
	
	public void pick() { 										 // 섞여진 배열에서 순서대로 문제를 뽑아 보여줌	 
		ques.setText(array[count]);
		count++;
	}
	
	public void shuffleProblem() {								// 중복없이 문제를 섞어 배열에 저장
		size = sw.prc.size();
		if(size != sw.anc.size()) {
			JOptionPane.showMessageDialog(null, "문제 개수와 답 개수 다릅니다, 확인요망", "오류", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		array = new String[size];
		array1 = new String[size];
		for(int i=0; i<size; i++) {
			rd = (int)(Math.random()*size + 0);
			array[i] = sw.prc.get(rd);
			array1[i] = sw.anc.get(rd);
			
			for(int j=0; j<i; j++) {
				if(array[j].equals(sw.prc.get(rd))) {
					i--;
					break;
				}
			}
		}
		sw.prc.clear();
		sw.anc.clear();
	}
	
	public boolean figureOut(String ans) {								// 정답 맞나 확인
		String aas = array1[count -1];
		aas = aas.replaceAll(" ","");                               // 공백 제거
		ans = ans.replaceAll(" ","");
		  
		if(ans.equalsIgnoreCase(aas)) {		//대소문자 구분 없음
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public static void main(String[] args) {
		
	}
}
