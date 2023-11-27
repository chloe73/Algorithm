package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_14940_쉬운_최단거리 {
	
	static int N,M;
	static int tx,ty;
	static int[][] board,result;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		board = new int[N][M];
		result = new int[N][M];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 2) {
					tx = i;
					ty = j;
					continue;
				}
				if(board[i][j] == 1) {
					result[i][j] = -1;
					continue;
				}
			}
		} // input end
		
		bfs();
		// 각 지점에서 목표지점까지의 거리를 출력한다.
		// 현재 좌표에서 목표지점까지의 거리 출력
		// 원래 갈 수 있는 땅인 부분 중에서 도달할 수 없는 위치는 -1을 출력
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				sb.append(result[i][j]+" ");
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

	private static void bfs() {
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][M];
		q.add(new int[] {tx,ty,0});
		visited[tx][ty] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int dist = temp[2];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny]) continue;
				
				if(board[nx][ny] != 0) {
					visited[nx][ny] = true;
					result[nx][ny] = dist + 1;
					q.add(new int[] {nx,ny,dist+1});
				}
			}
		}
		
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}