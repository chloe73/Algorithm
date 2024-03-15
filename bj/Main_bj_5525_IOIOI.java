package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_5525_IOIOI {
	
	static int N,M,result;
	static String s;
	static String target = "IOI";

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		s = br.readLine();
		result = 0;
		
		while(--N > 0) {
			target += "OI";
		}
		
		// OOIOIOIOIIOII
		for(int i=0;i<=M-target.length();i++) {
			String temp = s.substring(i, i+target.length());
			
			if(temp.equals(target))
				result++;
		}
		
		System.out.println(result);
	}

}
