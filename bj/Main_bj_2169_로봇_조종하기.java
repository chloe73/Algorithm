package algo.bj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_bj_2169_로봇_조종하기 {
	
	static int N,M;
	static int[][] board, dp;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		solve();
		
		bw.write(dp[N-1][M-1]+"");
		bw.flush();
		br.close();
		bw.close();
	}

	private static void solve() {
		dp = new int[N][M];
		dp[0][0] = board[0][0];
		
		// 첫번째 행
		for(int j=1;j<M;j++) {
			dp[0][j] = dp[0][j-1] + board[0][j];
		}
		
		// 2번째 행부터 아래로 
		for(int i=1;i<N;i++) {
			int[] arr = new int[M];
			int[] arr2 = new int[M];
			
			// 왼쪽 또는 위에서 오는 경우
			arr[0] = dp[i-1][0] + board[i][0];
			for(int j=1;j<M;j++) {
				arr[j] = Math.max(dp[i-1][j], arr[j-1]) + board[i][j];
			}
			
			// 오른쪽 또는 위에서 오는 경우
			arr2[M-1] = dp[i-1][M-1] + board[i][M-1];
			for(int j=M-2;j>=0;j--) {
				arr2[j] = Math.max(dp[i-1][j], arr2[j+1]) + board[i][j];
			}
			
			// 왼쪽 , 오른쪽 중 비
			for(int j=0;j<M;j++) {
				dp[i][j] = Math.max(arr[j], arr2[j]);
			}
		}
	
	}

}
