package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1439_뒤집기 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String s = br.readLine();
		
		int result = 0;
		int[] arr = new int[2];
		
		if(s.length() > 0) {
			char temp = s.charAt(0);
			arr[temp-'0']++;
			
			for(int i=1;i<s.length();i++) {
				if(temp != s.charAt(i)) {
					temp = s.charAt(i);
					arr[temp-'0']++;
				}
			}
		}
		result = Math.min(arr[0], arr[1]);
		System.out.println(result);
	}

}
