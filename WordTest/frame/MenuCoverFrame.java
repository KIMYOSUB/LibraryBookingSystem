package frame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import inputOutput.EditPA;
import inputOutput.SaveWords;
import inputOutput.WritePA;
import main.Main;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MenuCoverFrame extends JFrame implements Runnable { 			// 첫 화면 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Main main;
	JButton sb;
	JLabel li;
	Thread th;
	Color fbC = new Color(244, 250, 241);
	Color baC = new Color(207, 201, 151);
	Color fC = new Color(6, 77, 137); //220, 240, 250
	public MenuCoverFrame() {
		setTitle("정보처리기사 단어시험");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		creatMenu();
		
		BackGround ima = new BackGround();
		
		ima.setBounds(0, 0, 700, 350);
		ima.setLayout(null);
		
		li = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("starDdong.png")));
		li.setBounds(265, 195, 56, 52);
		
		sb = new JButton(new ImageIcon(getClass().getClassLoader().getResource("start1.png")));
		sb.setRolloverIcon(new ImageIcon(getClass().getClassLoader().getResource("start_p1.png")));
		sb.setBorderPainted(false);
		sb.setContentAreaFilled(false);
		sb.setBounds(295, 210, 120, 63);
		sb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.showMainFrame(MenuCoverFrame.this, main);
			}
			
		});
		
		setSize(710, 400);
		
		JLayeredPane lp = new JLayeredPane();
		lp.setBounds(0, 0, 700, 350); //
		lp.setLayout(null);
		
		lp.add(ima, new Integer(0));
		lp.add(sb, new Integer(1));
		lp.add(li, new Integer(2));
		add(lp);
		
		Dimension frameSize = this.getSize();
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
		
		th = new Thread(this);
		th.start();
		
		setVisible(true);
		
	}
	
	void creatMenu() {                                                          //메뉴 만드는 메소드
		JMenuBar mb = new JMenuBar();
		JMenu option = new JMenu("Option");
		JMenu words = new JMenu("Words");
		
		JMenuItem url = new JMenuItem("경로 설정");
		JMenuItem help = new JMenuItem("사용설명서");
		JMenuItem ext = new JMenuItem("종료");
		
		JMenuItem sav = new JMenuItem("문제 추가");
		JMenuItem sL = new JMenuItem("문제 목록");
		
		url.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//경로 설정 창 열기
				new Urlset();
			}
			
		});
		
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//프로그램 설명서
				new Description();
			}
		});
		
		ext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);		// 종료
			}
			
		});
		
		sav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new WriteSet(); 	// 문제, 답 저장
			}
		});
		
		sL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ShowList();		//문제, 답 목록 보기
			}
		});
		
		
		option.add(url);
		option.addSeparator();
		option.add(help);
		option.addSeparator();
		option.add(ext);
		
		words.add(sav);
		words.addSeparator();
		words.add(sL);
		
		mb.add(option);
		mb.add(words);
		
		setJMenuBar(mb);
	}
	class BackGround extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Image image;
		public BackGround() {
			image = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("cover3.jpg"));
			
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
	class ShowList extends JFrame implements ListSelectionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JList<String> li1;
		JList<String> li2;
		JScrollPane sp1;
		JScrollPane sp2;
		JLabel mun;
		JLabel dap;
		JTextField editField;
		JButton editButton;
		JButton refresh;
		JButton add;
		SaveWords sw;
		String nb;
		String problem;
		int index;
		int index1;
		
		void setNum() {
			sw = new SaveWords();
			if(sw.prc.size() != sw.anc.size()) {
				JOptionPane.showMessageDialog(null, "문제 개수와 답 개수가 다릅니다, 확인해주세요", "오류", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
			for(int i = 0; i<sw.prc.size(); i++) {
				nb = (Integer.toString(i+1)+". ");
				sw.prc.set(i, nb.concat(sw.prc.get(i)));
				sw.anc.set(i, nb.concat(sw.anc.get(i)));
			}
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// 선택된 이름 얻기
			if(li1.isSelectionEmpty()) return; //list.setListData()메소드 사용시 선택이 풀려버려 problem값이 null이라 오류가 생긴다. 그래서 방지한다.
			problem = (String) li1.getSelectedValue();  
			index = li1.getSelectedIndex();
			String ind = Integer.toString(index+1);
			problem = problem.replaceAll(ind + ". ", ""); 
			editField.setText(problem);
		}
		
		ShowList() {
			setTitle("목록 보기");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Container c = getContentPane();
			c.setLayout(null);
			setNum();
			li1 = new JList<String>(sw.prc);
			li2 = new JList<String>(sw.anc);
			
			li1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			li2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			li1.setBorder(BorderFactory.createLineBorder(fbC, 1));
			li2.setBorder(BorderFactory.createLineBorder(fbC, 1));
			li1.addListSelectionListener(this);
			
			
			li1.setBackground(fbC);
			li1.setForeground(fC);
			li2.setBackground(fbC);
			li2.setForeground(fC);
			li2.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					index1 = li2.getSelectedIndex();
					li1.setSelectedIndex(index1);
					li1.ensureIndexIsVisible(index1);
				}
				
			});
			
		
			mun = new JLabel("문제 :");
			mun.setForeground(Color.BLACK);
			dap = new JLabel("답 :");
			dap.setForeground(Color.BLACK);
			mun.setBounds(180, 20, 40, 20);
			dap.setBounds(40, 20, 30, 20);
			refresh = new JButton(new ImageIcon(getClass().getClassLoader().getResource("refresh.png")));
			refresh.setBounds(650, 350, 30, 30);
			refresh.setContentAreaFilled(false);
			refresh.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setNum();
					li1.setListData(sw.prc);
					li2.setListData(sw.anc);
				}
				
			});
			add = new JButton("추가");
			add.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new WriteSet();
					
				}
				
			});
			
			editField = new JTextField(30);
			editField.setBackground(fbC);
			editField.setForeground(fC);
			editField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			editButton = new JButton("수정");
			editButton.setBackground(baC);
			editButton.setForeground(Color.BLACK);
			editField.setBounds(180, 355, 370, 20);
			editButton.setBounds(570, 355, 60, 20);
			editButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new EditPA(index, editField.getText(), Main.prURL);
					setNum();
					li1.setListData(sw.prc);
					li2.setListData(sw.anc);
					JOptionPane.showMessageDialog(null, "수정완료!", "완료", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			add.setBounds(40, 350, 60, 30);
			add.setBackground(baC);
			add.setForeground(Color.BLACK);
			sp1 = new JScrollPane(li1);
			sp2 = new JScrollPane(li2);
			sp1.setBounds(180, 40, 500, 300);
			sp2.setBounds(40, 40, 110, 300);
			
			add.setFocusPainted(false);
			refresh.setFocusPainted(false);
			editButton.setFocusPainted(false);
			
			c.add(sp1); c.add(mun); c.add(refresh);
			c.add(sp2); c.add(dap); c.add(add); c.add(editField); c.add(editButton);
			c.setBackground(baC);
			setSize(700, 420);
			setLocationRelativeTo(null);
			setVisible(true);
		}
	}
	
	
	class WriteSet extends JFrame {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JLabel ans;
		JLabel pro;
		JTextField an;
		JTextArea pr;
		JScrollPane sp;
		JButton sb;
		JLabel sc;
		
		WriteSet() {
			setTitle("문제답 저장");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Container c = getContentPane();
			c.setLayout(null);
			
			ans = new JLabel("답:");
			ans.setForeground(Color.BLACK);
			pro = new JLabel("문제:");
			pro.setForeground(Color.BLACK);
			an = new JTextField(20);
			an.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			an.setBackground(fbC);
			an.setForeground(Color.BLACK);
			an.addFocusListener(new FocusAdapter() {

				@Override
				public void focusGained(FocusEvent e) {
					sc.setVisible(false);
					
				}
				
			});
			pr = new JTextArea(100, 20);
			pr.setBorder(BorderFactory.createLineBorder(fbC, 1));
			pr.setBackground(fbC);
			pr.setForeground(Color.BLACK);
			sb = new JButton("저장");
			sb.setBackground(baC);
			sb.setForeground(Color.BLACK);
			sc = new JLabel("저장완료!");
			sc.setForeground(Color.RED);
			sc.setVisible(false);
			sb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String pp = pr.getText();
					pp = pp.replaceAll("(\r\n|\r|\n|\n\r)", " ");
					new WritePA(pp, an.getText());
					sc.setVisible(true);
					an.setText("");
					pr.setText("");
				}
				
			});
			sb.setFocusPainted(false);
			
			pr.setLineWrap(true);
			sp = new JScrollPane(pr);
			sp.setBounds(20, 100, 250, 200);
			ans.setBounds(20, 10, 50, 20); 
			pro.setBounds(20, 70, 50, 20); 
			sb.setBounds(20, 320, 60, 20);
			an.setBounds(20, 40, 150, 20);  
			//pr.setBounds(20, 100, 250, 200); 
			sc.setBounds(90, 320, 100, 20);
			c.add(ans); c.add(pro); c.add(an); c.add(sp); c.add(sb); c.add(sc); 
			setSize(300, 380);
			c.setBackground(baC);
			Dimension frameSize = this.getSize();
			Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((windowSize.width - frameSize.width) / 2, (windowSize.height - frameSize.height) / 2);
			setVisible(true);
		}
		
	}
	class Urlset extends JFrame{                                  

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JFileChooser fc;
		JLabel pla;
		JLabel ala;
		JTextField ptf;												// 문제 txt파일 경로
		JTextField atf;												// 답   txt파일 경로
		JButton pb;
		JButton ab;
		JButton pb1;
		JButton ab1;
		JButton setAll;
		JTextArea dc; 
		JScrollPane jp;
		Urlset() {
			setTitle("텍스트 파일 경로설정");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			Container c1 = getContentPane();
			c1.setLayout(null);
			
			fc = new JFileChooser();
			
			pla = new JLabel("문제 경로 :");
			pla.setBounds(10, 10, 200, 20);
			
			ala = new JLabel("답  경로 :");
			ala.setBounds(10, 40, 200, 20);
			
			ptf = new JTextField(100);
			ptf.setBounds(80, 10, 100, 20);
			ptf.setText(Main.prURL);
			
			atf = new JTextField(100);
			atf.setBounds(80, 40, 100, 20);
			atf.setText(Main.asURL);
			
			pb = new JButton("열기1");
			pb.setBounds(185, 10, 70, 20);
			pb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int returnVal = fc.showOpenDialog(c1);
		            if( returnVal == JFileChooser.APPROVE_OPTION)
		            {
		                //열기 버튼을 누르면
		                File file = fc.getSelectedFile();
		                ptf.setText(file.toString());
		                String gpt = ptf.getText();
						main.setprURL(gpt);
						JOptionPane.showMessageDialog(null, "경로 : "+gpt, "변경완료!", JOptionPane.INFORMATION_MESSAGE);
		            }
		            else
		            {
		                //취소 버튼을 누르면
		            }
					
				}
				
			});
			
			ab = new JButton("열기2");
			ab.setBounds(185, 40, 70, 20);
			ab.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int returnVal = fc.showOpenDialog(c1);
		            if( returnVal == JFileChooser.APPROVE_OPTION)
		            {
		                //열기 버튼을 누르면
		                File file = fc.getSelectedFile();
		                atf.setText(file.toString());
		                String gat = atf.getText();
						main.setasURL(gat);
						JOptionPane.showMessageDialog(null, "경로 : "+gat, "변경완료!", JOptionPane.INFORMATION_MESSAGE);
		            }
		            else
		            {
		                //취소 버튼을 누르면
		            }
					
				}
				
			});
			
			pb1 = new JButton("직접입력");
			pb1.setBounds(265, 10, 90, 20);
			pb1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					main.setprURL(ptf.getText());
					JOptionPane.showMessageDialog(null, "경로 : "+ptf.getText(), "변경완료!", JOptionPane.INFORMATION_MESSAGE);
				}
				
			});
			
			ab1 = new JButton("직접입력");
			ab1.setBounds(265, 40, 90, 20);
			ab1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					main.setasURL(atf.getText());
					JOptionPane.showMessageDialog(null, "경로 : "+atf.getText(), "변경완료!", JOptionPane.INFORMATION_MESSAGE);
				}
				
			});
			
			setAll = new JButton("setAll");
			setAll.setBounds(10, 152, 80, 25);
			setAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					main.setprURL(ptf.getText());
					main.setasURL(atf.getText());
					JOptionPane.showMessageDialog(null, "모든 경로 변경완료!!", "확인", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			dc = new JTextArea("ex) C:\\Users\\kys\\Desktop\\sample.txt\n"
					+ "기본 경로 : \"Text\\Problem.txt\", \"Text\\Answer.txt\"\n"
					+ "텍스트파일 꼭 'UTF-8'로 저장", 100, 2);
			
			
			jp = new JScrollPane(dc);
			jp.setBounds(10, 70, 345, 75);
			
			c1.add(pla);
			c1.add(ala);
			c1.add(ptf);
			c1.add(atf);
			c1.add(pb);
			c1.add(ab);
			c1.add(pb1);
			c1.add(ab1);
			c1.add(jp);
			c1.add(setAll);
			
			setSize(390, 210);
			setVisible(true);
		}
		
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	public static void main(String[] args) {
		new MenuCoverFrame();
	}

	@Override
	public void run() {
		int x = li.getX();
		int y = li.getY();
		while(true) {
			try {
				Thread.sleep(50);
			}
			catch(InterruptedException e) {
				return;
			}
			if(x < 380) {
				x++;
			}
			if(x == 380) {
				x = 265;
			}
			li.setLocation(x, y);
		}
	}
}
