package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_2206_벽_부수고_이동하기 {
	
	static int N,M,result;
	static int[][] board;
	static boolean[][][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		board = new int[N][M];
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<M;j++) {
				board[i][j] = s.charAt(j)-'0';
			}
		} // input end
		
		result = Integer.MAX_VALUE;
		visited = new boolean[N][M][2];
		
		solve(0,0);
		
		System.out.println(result == Integer.MAX_VALUE ? -1 : result);
	}

	private static void solve(int r, int c) {
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {r,c,0,0});
		visited[r][c][0] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int cnt = temp[2];
			int flag = temp[3];
			
			if(x == N-1 && y == M-1) {
				result = Math.min(result, cnt+1);
			}
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny)) continue;
				
				if(board[nx][ny] == 0 && flag == 0 && !visited[nx][ny][0]) {
					visited[nx][ny][0] = true;
					q.add(new int[] {nx,ny,cnt+1,flag});
					continue;
				}
				
				if(board[nx][ny] == 0 && flag == 1 && !visited[nx][ny][1]) {
					visited[nx][ny][1] = true;
					q.add(new int[] {nx,ny,cnt+1,flag});
					continue;
				}
				
				if(board[nx][ny] == 1 && !visited[nx][ny][1] && flag == 0) {
					visited[nx][ny][1] = true;
					q.add(new int[] {nx,ny,cnt+1,1});
					continue;
				}
			}
		}
		
		return;
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}