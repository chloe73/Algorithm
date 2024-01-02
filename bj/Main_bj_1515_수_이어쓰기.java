package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1515_수_이어쓰기 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		
		int N = 0;
		int idx = 0;
		// 234092 -> N = 20
		// 82340329923 -> N = 43
		
		outer:while(true) {
			N++;
			String num = Integer.toString(N);
			
			for(int i=0;i<num.length();i++) {
				if(num.charAt(i) == input.charAt(idx)) {
					idx++;
				}
				if(idx == input.length()) {
					System.out.println(N);
					break outer;
				}
			}

		}
		
	}

}
