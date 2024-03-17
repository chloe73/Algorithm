package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1213_팰린드롬_만들기 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] alpha = new int[26];
		String s = br.readLine();
		for(int i=0;i<s.length();i++) {
			int temp = s.charAt(i) - 'A';
			alpha[temp]++;
		} // input end
		
		int hol = 0;
		for(int i=0;i<alpha.length;i++) {
			if(alpha[i] % 2 != 0) hol++;
		}
		
		String answer = "";
		StringBuilder sb = new StringBuilder();
		if(hol > 1)
			answer += "I'm Sorry Hansoo";
		else {
			for(int i=0;i<26;i++) {
				for(int j=0;j<alpha[i]/2;j++) {
					sb.append((char) (i+'A'));
				}
			}
			
			answer += sb.toString();
			String end = sb.reverse().toString();
			sb = new StringBuilder();
			
			for(int i=0;i<26;i++) {
				if(alpha[i]%2 == 1) {
					sb.append((char) (i+'A'));
				}
			}
			answer += sb.toString() + end;
		}
		System.out.println(answer);
	}

}
