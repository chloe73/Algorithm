package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_bj_17484_진우의_달_여행 {
	
	static int N,M,result;
	static int[][] board;
	static int[] visited;
	static int[] dx = {1,1,1};
	static int[] dy = {-1,0,1};
	static class Point {
		int x,y,d;
		public Point(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
		
		result = Integer.MAX_VALUE;
		
		// 연료를 최대한 아끼며 지구의 어느위치에서든 출발하여 달의 어느위치든 착륙하는 것
		for(int i=0;i<M;i++) {
			visited = new int[N];
			visited[0] = i;
			dfs(0,i,-1);
		}
		
		System.out.println(result);
	}

	private static void dfs(int x, int y, int d) {
		
		if(x == N-1) {
			int sum = board[0][visited[0]];
			for(int i=1;i<N;i++) {
				sum += board[i][visited[i]];
			}
			result = Math.min(result, sum);
			return;
		}
		
		for(int i=0;i<3;i++) {
			int nx = x+1;
			int ny = y + dy[i];
			if(is_valid(ny) && i != d) {
				visited[x] = ny;
				dfs(nx,ny,i);
			}
		}
		
	}
	
	private static boolean is_valid(int c) {
		if(c<0 || c>=M) return false;
		return true;
	}

}

/*
 처음에는 bfs로 풀어서 1%에서 "틀렸습니다."가 떴음.
 */

//public class Main_bj_17484_진우의_달_여행 {
//	
//	static int N,M,result;
//	static int[][] board,visited;
//	// 
//	static int[] dx = {1,1,1};
//	static int[] dy = {-1,0,1};
//	static class Point {
//		int x,y,d;
//		public Point(int x, int y, int d) {
//			this.x = x;
//			this.y = y;
//			this.d = d;
//		}
//	}
//
//	public static void main(String[] args) throws IOException{
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		StringTokenizer st = new StringTokenizer(br.readLine());
//		
//		N = Integer.parseInt(st.nextToken());
//		M = Integer.parseInt(st.nextToken());
//
//		board = new int[N][M];
//		for(int i=0;i<N;i++) {
//			st = new StringTokenizer(br.readLine());
//			for(int j=0;j<M;j++) {
//				board[i][j] = Integer.parseInt(st.nextToken());
//			}
//		} // input end
//		
//		visited = new int[N][M];
//		for(int i=0;i<N;i++) {
//			Arrays.fill(visited[i], Integer.MAX_VALUE);
//		}
//		result = Integer.MAX_VALUE;
//		
//		solve();
//		
//		System.out.println(result);
//	}
//
//	private static void solve() {
//		// 연료를 최대한 아끼며 지구의 어느위치에서든 출발하여 달의 어느위치든 착륙하는 것
//		Queue<Point> q = new LinkedList<>();
//		for(int i=0;i<M;i++) {
//			q.add(new Point(0, i, -1));
//			visited[0][i] = board[0][i];
//		}
//		
//		while(!q.isEmpty()) {
//			Point temp = q.poll();
//			
//			if(temp.x == N) {
//				continue;
//			}
//			
//			for(int i=0;i<3;i++) {
//				// 우주선은 전에 움직인 방향으로 움직일 수 없다. 즉, 같은 방향으로 두번 연속으로 움직일 수 없다.
//				if(i == temp.d) {
//					continue;
//				}
//				
//				int nx = temp.x + dx[i];
//				int ny = temp.y + dy[i];
//				
//				if(!is_valid(nx, ny)) continue;
//				
//				if(visited[nx][ny] > visited[temp.x][temp.y] + board[nx][ny]) {
//					visited[nx][ny] = visited[temp.x][temp.y] + board[nx][ny];
//					q.add(new Point(nx, ny, i));
//				}
//			}
//		}
//		
//		for(int i=0;i<M;i++) {
//			result = Math.min(result, visited[N-1][i]);
//		}
//		
//	}
//	
//	private static boolean is_valid(int r, int c) {
//		if(r<0 || c<0 || r>=N || c>=M) return false;
//		return true;
//	}
//
//}

