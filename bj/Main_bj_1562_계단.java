package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1562_계단 {
	
	static int N;
	static long[][][] dp;
	static final int MOD = 1000000000;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		dp = new long[N+1][10][1 << 10];
		
		for(int i=1;i<=9;i++) {
			dp[1][i][1 << i] = 1;
		}
		
		for(int n=2;n<=N;n++) {
			for(int k=0;k<=9;k++) {
				for(int v=0;v< (1 << 10);v++) {
					int cur = v | (1 << k);
					if(k == 0) {
						dp[n][k][cur] += dp[n-1][k+1][v] % MOD;
					}
					else if(k == 9) {
						dp[n][k][cur] += dp[n-1][k-1][v] % MOD;
					}
					else {
						dp[n][k][cur] += (dp[n-1][k-1][v] % MOD + dp[n-1][k+1][v] % MOD);
					}
					
					dp[n][k][cur] %= MOD;
				}
			}
		}
		
		int result = 0;
		
		for(int k=0;k<=9;k++) {
			result += dp[N][k][(1<<10)-1] % MOD;
			result %= MOD;
		}

		System.out.println(result);
	}

}