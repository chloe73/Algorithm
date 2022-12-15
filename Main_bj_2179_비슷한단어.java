package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_bj_2179_비슷한단어 {
	
	static int N,maxLen;
	static String[] a,b;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		for(int i=0;i<N;i++) {
			a[i] = br.readLine();
			b[i] = a[i];
		}
		
		Arrays.sort(b);
		
	}

	private static int check_prefix(String c, String d) {
		int cnt = 0;
		
		
		
		return cnt;
	}
}
