package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main_bj_9655_돌_게임 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N = Integer.parseInt(br.readLine());
		
		// N이 홀수면 상근이가 이기고 짝수면 창영이가 이기는 게임이다.
//		System.out.println(N%2 == 0 ? "CY" : "SK");
		
		// dp로도 풀 수 있는 문제임.
		int[] dp = new int[1001];
		
		dp[1] = 1; // 상근 win
		dp[2] = 2; // 창영 win
		dp[3] = 1; // 상근 win
		
		for(int i=4;i<=N;i++) {
			dp[i] = Math.min(dp[i-1], dp[i-3])+1;
		}
		
//		if(dp[N] % 2 == 0) {
//			System.out.println("CY");
//		}
//		else {
//			System.out.println("SK");
//		}
		
		bw.write(dp[N]%2 == 0 ? "CY" : "SK");
		bw.close();
	}

}
