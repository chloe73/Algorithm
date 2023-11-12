package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_2589_보물섬 {
	
	static int N,M,result;
	static char[][] board;
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new char[N][M];
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<M;j++) {
				board[i][j] = s.charAt(j);
			}
		} // input end
		
		result = Integer.MIN_VALUE;
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] == 'L') {
					visited = new boolean[N][M];
					bfs(i,j);
				}
			}
		}
		
		System.out.println(result);
	}

	private static void bfs(int i, int j) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {i,j,0});
		visited[i][j] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int cnt = temp[2];
			
			result = Math.max(result, cnt);
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny] || board[nx][ny] == 'W') continue;
				
				visited[nx][ny] = true;
				q.add(new int[] {nx,ny,cnt+1});
			}
		}
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}
