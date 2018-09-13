package inputOutput;

import java.util.*;

import javax.swing.JOptionPane;

import java.io.*;
import main.Main;

public class SaveWords {
	 
	public Vector<String> prc = new Vector<String>();                                   // 문제 문자열을 저장해줄 벡터 
	public Vector<String> anc = new Vector<String>();									 // 답 문자열을 저장해줄 벡터

	public SaveWords() {
		
		File pro = new File(Main.prURL);
		File ans = new File(Main.asURL);
		
		try {
			
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(pro), "UTF-8"));
			BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(ans), "UTF-8"));
			br1.readLine(); //한글을 사용해야 하는데 첫 줄을 저장할 때 안보이지만 유니코드도 같이 읽혀서 문제가 된다 그래서 빼준다. 
			br2.readLine();
			String read = null;
			
			while((read=br1.readLine()) != null) {
				prc.add(read);
			}
			
			while((read=br2.readLine()) != null) {
				anc.add(read);
			}
			
			br1.close();
			br2.close();
		}
		catch (IOException e) {
			System.out.println("입출력 오류");
			JOptionPane.showMessageDialog(null, "경로명을 제대로 설정해주세요.", "오류", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public static void main(String[] args) {
		new SaveWords();
		
	}
}
