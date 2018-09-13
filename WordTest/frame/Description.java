package frame;

import javax.swing.*;
import java.awt.*;
public class Description extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea h;
	public Description() {
		setTitle("도움말");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container c2 = getContentPane();
		c2.setLayout(null);
		
		h = new JTextArea("♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣\n\n"
						  + "   본 프로그램은 2개의 txt파일을 읽어서 단어 맞추기 프로그램으로 만들어줍니다.\n"
						  + "   문제는 랜덤으로 자동 출제됩니다.(중복 없음)\n\n"
				          + "   사용법 :\n\n"
				          + "   1.기존 경로 Text\\Problem.txt, Text\\Answer.txt 파일들을 수정해서 사용하셔도되고\n\n"
				          + "   2.아니면 문제를 저장할 txt파일, 답을 저장할 txt파일을 만드세요.\n\n"
				          + "   2.Option에서 경로를 설정하세요. \n\n"
				          + "   3.Words 옵션에서 문제추가 기능으로 txt파일 수정안하고 추가하실수 있습니다.\n\n"
				          + "   4.텍스트파일을 수정해서 추가 하고 싶은 경우, 파일 첫 줄은 비우세요. \n"
				          + "      인코딩 문제로 첫 줄만 깨집니다.\n\n"
				          + "   ex) ※※※첫 줄은 비워둡니다.※※※\n"
				          + "         사과\n"
				          + "         포도\n"
				          + "         오렌지\n\n"
				          + "   ※ 답 파일과 문제파일 순서가 같아야합니다. \n"
				          + "       정답 대소문자 구분 없음. \n"
				          + "       정답 띄어쓰기 구분 없음.\n\n"
				          + "♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣♣");
		h.setBounds(15, 5, 450, 530);
		h.setOpaque(false);
		h.setEditable(false);
		c2.add(h);
		c2.setBackground(Color.LIGHT_GRAY);
		setSize(500, 550);
		setVisible(true);
		
		
		
	}
	public static void main(String [] args) {
		new Description();
	}
}
