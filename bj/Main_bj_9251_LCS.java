package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_9251_LCS {
	
	static char[] A,B;
	static int[][] dp;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String a = br.readLine();
		String b = br.readLine();

		int len1 = a.length();
		int len2 = b.length();
		
		A = new char[len1+1];
		B = new char[len2+1];
		
		for(int i=1;i<=len1;i++) {
			A[i] = a.charAt(i-1);
		}
		
		for(int i=1;i<=len2;i++) {
			B[i] = b.charAt(i-1);
		} // input end
		
		dp = new int[len2+1][len1+1];
		
		for(int i=1;i<=len2;i++) {
			for(int j=1;j<=len1;j++) {
				if(B[i] == A[j]) {
					// 대각선 확인
					dp[i][j] = dp[i-1][j-1] + 1;
				}
				else {
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				}
			}
		}
		
		System.out.println(dp[len2][len1]);
	}

}