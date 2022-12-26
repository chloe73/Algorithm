package algo.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1316_그룹단어체커 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		int result = 0;

		for(int i=0;i<N;i++) {
			String s = br.readLine();
			boolean flag = true;
			boolean[] check = new boolean[26];
			
			for(int j=0;j<s.length();j++) {
				int idx = s.charAt(j) - 'a';
				if(check[idx]) {
					if(s.charAt(j) != s.charAt(j-1)) flag = false;
				}
				else check[idx] = true;
			}
			
			if(flag) result++;
		}
		
		System.out.println(result);
	}

}
