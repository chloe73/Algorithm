package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_12919_A와B2 {
	
	static String S,T;
	static int result;
	static StringBuilder sb;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		S = br.readLine();
		T = br.readLine();
		
		dfs(T);
		
		System.out.println(result);
	}

	private static void dfs(String temp) {
		
		if(temp.length() == S.length()) {
			
			if(temp.equals(S)) {
				result = 1;
			}
			
			return;
		}
		
		if(temp.charAt(0) == 'B') {
			sb = new StringBuilder(temp.substring(1)).reverse();
			dfs(sb.toString());
		}
		
		if(temp.charAt(temp.length()-1) == 'A') {
			String s = temp.substring(0,temp.length()-1);
			dfs(s);
		}

	}
}
