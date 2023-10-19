package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_1520_내리막길 {
//	제일 왼쪽 위 지점에서 출발하여 제일 오른쪽 아래 지점까지 항상 내리막길로만 이동하는 경로의 개수를 구하는 프로그램을 작성
	
	static int M,N;
	static int[][] board, dp;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken()); // row
		N = Integer.parseInt(st.nextToken()); // col

		board = new int[M][N];
		dp = new int[M][N];
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				dp[i][j] = -1;
			}
		} // input end
		
		System.out.println(solve(0, 0));
	}
	
	private static int solve(int x, int y) {
		
		if(x == M-1 && y == N-1) return 1;
		
		if(dp[x][y] == -1) {
			dp[x][y] = 0;
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny)) continue;
				
				if(board[nx][ny] < board[x][y]) {
					dp[x][y] += solve(nx, ny);
				}
				
			}
		}
		
		return dp[x][y];
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=M || c>=N) return false;
		return true;
	}

}
