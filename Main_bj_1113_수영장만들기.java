package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_1113_수영장만들기 {
	
	static int N,M,result,maxHeight,minHeight;
	static int[][] pool;
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		pool = new int[N][M];
		maxHeight = 0;
		minHeight = 10;
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<M;j++) {
				pool[i][j] = s.charAt(j)-'0';
				if(maxHeight < pool[i][j]) maxHeight = pool[i][j];
				if(minHeight > pool[i][j]) minHeight = pool[i][j];
			}
		} // input end
		
		visited = new boolean[N][M];
		for(int h= minHeight;h<=maxHeight;h++) {
			for(int i=0;i<N;i++) {
				for(int j=0;j<M;j++) {
					if(pool[i][j] == h && !visited[i][j]) {
						visited[i][j] = true;
						bfs(i,j,h);
					}
				}
			}
		}
		
		System.out.println(result);
	}

	private static void bfs(int i, int j, int h) {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(i,j));
		ArrayList<Point> candidate = new ArrayList<>();
		candidate.add(new Point(i, j));
		
		boolean flag = false;
		int minH = 10;
		while(!q.isEmpty()) {
			Point temp = q.poll();
			
			// 테두리면 pass
			if(temp.x == 0 || temp.y == 0 || temp.x == N-1 || temp.y == M-1) flag = true;
			
			for(int d=0;d<4;d++) {
				int nx = temp.x + dx[d];
				int ny = temp.y + dy[d];
				
				if(0<= nx && nx < N && 0 <= ny && ny < M) {
					
					// 웅덩이 후보
					if(!visited[nx][ny] && pool[nx][ny] == h) {
						visited[nx][ny] = true;
						q.add(new Point(nx, ny));
						candidate.add(new Point(nx, ny));
					}
						
					// 주변이 현재 값보다 작다면 xxx
					if(pool[nx][ny] < h) flag = true;
					
					// 주변 중 나보단 크지만 가장 작은 값 찾기
					if(pool[nx][ny] > h) {
						minH = Math.min(minH, pool[nx][ny]);
					}
				}
				
			}
		}
		
		if(!flag) {
			result += candidate.size() * (minH-h);
			for(int k=0;k<candidate.size();k++) {
				Point tmp = candidate.get(k);
				pool[tmp.x][tmp.y] = minH;
				visited[tmp.x][tmp.y] = false;
			}
		}
	}

}
