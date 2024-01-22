package algo.SW_DX.day6_그래프_다익스트라;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Solution_보급로 {
	
	static int N,result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			N = Integer.parseInt(br.readLine());
			board = new int[N][N];
			for(int i=0;i<N;i++) {
				String s = br.readLine();
				for(int j=0;j<N;j++) {
					board[i][j] = s.charAt(j)-'0';
				}
			} // input end
			
			result = Integer.MAX_VALUE;
			
			solve();
			
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

	private static void solve() {
		boolean[][] visited = new boolean[N][N];
		int[][] time = new int[N][N];
		for(int i=0;i<N;i++) {
			Arrays.fill(time[i], Integer.MAX_VALUE);
		}
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {0,0});
		visited[0][0] = true;
		time[0][0] = board[0][0];
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			if(x == N-1 && y == N-1) {
				result = Math.min(result, time[N-1][N-1]);
			}
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!isValid(nx, ny)) continue;
				
				if(!visited[nx][ny] || time[nx][ny] > time[x][y] + board[nx][ny]) {
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny});
					time[nx][ny] = time[x][y] + board[nx][ny];
				}
			}
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
