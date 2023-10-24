package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_2839_설탕_배달 {
	
	static int N, result;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		solve();

		System.out.println(result);
	}

	private static void solve() {
		
		int cnt = 0;
		
		while(true) {
			if(N % 5 == 0) {
				result = N / 5 + cnt;
				break;
			}
			
			if(N < 0) {
				result = -1;
				break;
			}
			
			N = N - 3;
			cnt++;
		}
		
	}

}