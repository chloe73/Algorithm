package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_7576_토마토 {
	
	static int M,N,result;
	static int[][] board;
	static boolean[][] visited;
	static Queue<int[]> q;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		q = new LinkedList<>();
		visited = new boolean[N][M];
		boolean isAllRiped = true;
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 1) {
					q.add(new int[] {i,j});
					visited[i][j] = true;
				}
				if(board[i][j] == 0) {
					isAllRiped = false;
				}
			}
		} // input end
		
		if(isAllRiped) {
			result = 0;
			System.out.println(result);			
		}
		else {
			solve();
			// bfs 탐색이 끝난 후 모든 토마토가 익었는지 체크
			boolean flag = is_completed();
			System.out.println(flag ? result : -1);
		}
	}
	
	private static boolean is_completed() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] == 0) return false;
			}
		}
		return true;
	}

	private static void solve() {
		
		while(!q.isEmpty()) {
			int size = q.size();
			boolean flag = false;
			
			for(int i=0;i<size;i++) {
				int[] temp = q.poll();
				int x = temp[0];
				int y = temp[1];
				
				for(int d=0;d<4;d++) {
					int nx = x + dx[d];
					int ny = y + dy[d];
					
					if(!is_valid(nx, ny) || visited[nx][ny]) continue;
					
					if(board[nx][ny] == 0) {
						board[nx][ny] = 1;
						visited[nx][ny] = true;
						flag = true;
						q.add(new int[] {nx,ny});
						continue;
					}
				}
			}
			if(flag)
				result++;
		}
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 ||c<0 || r>=N || c>=M) return false;
		return true;
	}

}
