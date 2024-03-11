package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_12904_A와B {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String s = br.readLine();
		String t = br.readLine();
		
		while(s.length() < t.length()) {
			StringBuilder sb = new StringBuilder();
			// 문자열의 뒤에 A를 추가한다.
			if(t.endsWith("A")) {
				t = t.substring(0,t.length()-1);
			}
			// 문자열을 뒤집고 뒤에 B를 추가한다.
			else if(t.endsWith("B")) {
				t = t.substring(0,t.length()-1);
				t = sb.append(t).reverse().toString();
			}
		}
		
		if(s.equals(t))
			System.out.println(1);
		else
			System.out.println(0);
	}

}