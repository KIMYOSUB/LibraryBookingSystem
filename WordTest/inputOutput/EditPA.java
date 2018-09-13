package inputOutput;

import java.util.*;

import javax.swing.JOptionPane;

import java.io.*;

public class EditPA {                                                              // 수정해줄 클래스(덮어쓰기에 가깝다)
	 
	private Vector<String> prc = new Vector<String>();                                   // 문제 문자열을 저장해줄 벡터 
	
	
	public EditPA(int position, String newData, String url) {
		
		File fi = new File(url);	
		
		try {
			
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(fi), "UTF-8"));
			//BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fi), "UTF-8"));
			String dummy = "";
			
			dummy = br1.readLine() + "\r\n";  
			String read = null;
			String last = null;
			for(int i = 0; i < position; i++) {
				read=br1.readLine();	
				prc.add(read);
				dummy += (read + "\r\n");
			}
			
			br1.readLine(); // 원래 텍스트파일에 들어있던 데이터 스킵
			prc.add(newData);
			dummy += (newData + "\r\n");
			last = newData;
			
			while((read=br1.readLine()) != null) { //나머지 데이터들 저장
				prc.add(read);
				dummy += (read + "\r\n");
				last = read;
			}
		
			dummy = dummy.replaceAll(last + "\r\n", last); // 맨 마지막 줄 공백 제거
			
			//FileWriter fw = new FileWriter(url);
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fi), "UTF-8"));
			bw1.write(dummy);
			bw1.close();
			//bw1.close();
			br1.close();
		}
		catch (IOException e) {
			System.out.println("입출력 오류");
			JOptionPane.showMessageDialog(null, "경로명을 제대로 설정해주세요.", "오류", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public static void main(String[] args) {
		new EditPA(2 ,"되는지 안되는지 테스트 ","Text\\Problem.txt");
	}
}
