package inputOutput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import main.Main;

public class WritePA {								// 프로그램 내에서 문제와 답을 저장(텍스트파일에 추가)
	
	public WritePA(String p, String a) {
		
		File pro = new File(Main.prURL);
		File ans = new File(Main.asURL);
		try {
		
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pro, true), "UTF-8"));
			BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ans, true), "UTF-8"));
			PrintWriter pw1 = new PrintWriter(bw1, true);
			PrintWriter pw2 = new PrintWriter(bw2, true);
			pw1.write("\r\n"+p);
			pw1.flush();
			pw1.close();
			pw2.write("\r\n"+a);
			pw2.flush();
			pw2.close();
		} catch(IOException e) {  }
		
	}
	public static void main(String[] args) {
		new WritePA("grape","포도");
	}
}
