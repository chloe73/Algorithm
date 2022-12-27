package algo.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1522_문자열교환 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String s = br.readLine();
		
		int result = Integer.MAX_VALUE;
		
		int a = 0;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i) == 'a') a++;
		}
		
		for(int i=0;i<s.length();i++) {
			int b = 0;
			for(int j=i;j<i+a;j++) {
				int idx = j % s.length();
				if(s.charAt(idx) == 'b') b++;
			}
			result = Math.min(result, b);
		}
		
		System.out.println(result);
	}

}
