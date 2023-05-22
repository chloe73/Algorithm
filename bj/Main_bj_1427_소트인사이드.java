package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_bj_1427_소트인사이드 {
	
	static String N;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		N = br.readLine();
		int[] arr = new int[N.length()];
		
		for(int i=0;i<N.length();i++) {
			arr[i] = N.charAt(i)-'0';
		}
		
		Arrays.sort(arr);
		
		for(int i=N.length()-1;i>=0;i--) {
			sb.append(arr[i]+"");
		}
		
		System.out.println(sb.toString());
	}

}