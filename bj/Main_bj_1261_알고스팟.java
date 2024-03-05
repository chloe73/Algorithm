package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_bj_1261_알고스팟 {
	
	static int N,M,result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point implements Comparable<Point>{
		int x,y,cnt,d;
		public Point(int x, int y, int cnt, int d) {
			this.x = x;
			this.y = y;
			this.cnt = cnt;
			this.d = d;
		}
		public int compareTo(Point o) {
			return this.cnt - o.cnt;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[M][N];
		for(int i=0;i<M;i++) {
			String s = br.readLine();
			for(int j=0;j<N;j++) {
				board[i][j] = s.charAt(j)-'0';
			}
		} // input end
		
		result = Integer.MAX_VALUE;
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.add(new Point(0, 0, 0, 0));
		int[][] dist = new int[M][N];
		for(int i=0;i<M;i++) {
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}
		dist[0][0] = 0;
		
		while(!pq.isEmpty()) {
			Point temp = pq.poll();
			
			if(temp.x == M-1 && temp.y == N-1) {
				result = Math.min(result, temp.cnt);
				continue;
			}
			
			for(int d=0;d<4;d++) {
				int nx = temp.x + dx[d];
				int ny = temp.y + dy[d];
				
				if(!isValid(nx, ny)) continue;
				
				if(dist[nx][ny] > temp.d + 1) {
					dist[nx][ny] = temp.d + 1;
					if(board[nx][ny] == 1) {
						pq.add(new Point(nx, ny, temp.cnt + 1, dist[nx][ny]));
					}
					else {
						pq.add(new Point(nx, ny, temp.cnt, dist[nx][ny]));
					}
				}
			}
		}
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=M || c>=N) return false;
		return true;
	}

}
