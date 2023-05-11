package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_14442_벽부수고이동하기2 {
	
	static int N,M,K,result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point {
		int x,y,breakCnt,dist;
		public Point(int x, int y, int breakCnt, int dist) {
			this.x = x;
			this.y = y;
			this.breakCnt = breakCnt;
			this.dist = dist;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<M;j++) {
				board[i][j] = s.charAt(j)-'0';
			}
		} // input end
		result = Integer.MAX_VALUE;
		
		bfs();
		if(result == Integer.MAX_VALUE) result = -1;
		System.out.println(result);
	}

	private static void bfs() {
		Queue<Point> q = new LinkedList<>()	;
		q.add(new Point(0, 0, 0, 1));
		boolean[][][] visited = new boolean[N][M][K+1];
		visited[0][0][0] = true;
		
		while(!q.isEmpty()) {
			Point p = q.poll();
			int x = p.x;
			int y = p.y;
			
			if(x == N-1 && y == M-1) {
				result = Math.min(result, p.dist);
			}
			
			for(int i=0;i<4;i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				int breakCnt = p.breakCnt;
				int dist = p.dist;
				
				if(nx<0 || ny<0 || nx>=N || ny>=M) continue;
				
				if(board[nx][ny] == 1 && breakCnt < K && !visited[nx][ny][breakCnt+1]) {
					visited[nx][ny][breakCnt+1] = true;
					q.add(new Point(nx, ny, breakCnt+1, dist+1));
				}
				
				if(board[nx][ny] == 0 && !visited[nx][ny][breakCnt]) {
					visited[nx][ny][breakCnt] = true;
					q.add(new Point(nx, ny, breakCnt, dist+1));
				}
			}
		}
	}

}
