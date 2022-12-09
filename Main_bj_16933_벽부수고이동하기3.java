package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_16933_벽부수고이동하기3 {
	
	static int N,M,K,result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point {
		int x,y,dist,destroy,day;

		public Point(int x, int y, int dist, int destroy, int day) {
			super();
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.destroy = destroy;
			this.day = day;
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
				board[i][j] = s.charAt(j) - '0';
			}
		} // input end
		
		result = -1;
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(0, 0, 1, 0, 0));
		boolean[][][][] visited = new boolean[N][M][K+1][2];
		visited[0][0][0][0] = true;
		
		while(!q.isEmpty()) {
			Point p = q.poll();
			int x = p.x;
			int y = p.y;
			
			if(x == N-1 && y == M-1) {
				result = p.dist;
				return;
			}
			
			for(int i=0;i<4;i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				// 범위를 벗어난 경우 패스
				if(nx < 0 ||ny < 0 ||nx >= N ||ny >= M) continue;
				
				if(board[nx][ny] == 0) {
					if(p.day == 0 && !visited[nx][ny][p.destroy][1]) {
						visited[nx][ny][p.destroy][1] = true;
						q.add(new Point(nx,ny,p.dist+1,p.destroy,1));
					} else if(p.day == 1 && !visited[nx][ny][p.destroy][0]) {
						visited[nx][ny][p.destroy][0] = true;
						q.add(new Point(nx,ny,p.dist+1,p.destroy,0));
					}
				}
				else {
					if(p.destroy < K && p.day == 0 &&!visited[nx][ny][p.destroy+1][1]) {
						visited[nx][ny][p.destroy+1][1] = true;
						q.add(new Point(nx,ny,p.dist+1,p.destroy+1,1));
					}
					else if(p.destroy < K && p.day == 1 &&!visited[x][y][p.destroy][0]) {
						visited[x][y][p.destroy][0] = true;
						q.add(new Point(x,y,p.dist+1,p.destroy,0));
					}
				}
			}
		}
	}

}