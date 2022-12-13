package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_2933_미네랄 {
	
	static int R,C,N;
	static char[][] cave;
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
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		cave = new char[R][C];
		for(int i=0;i<R;i++) {
			cave[i] = br.readLine().toCharArray();
		}
		
		N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<N;i++) {
			int h = R - Integer.parseInt(st.nextToken());
			
			breakMineral(h,i);
			bfs();
		} // input end
		
		for(int i=0;i<R;i++) {
			sb.append(cave[i]).append("\n");
		}
		System.out.println(sb.toString());
	}

	private static void bfs() {
		Queue<Point> q = new LinkedList<>();
		boolean[][] visited = new boolean[R][C];
		ArrayList<Point> cluster = new ArrayList<>();
		
		for(int j=0;j<C;j++) {
			if(cave[R-1][j] == '.' || visited[R-1][j]) continue;
			
			visited[R-1][j] = true;
			q.add(new Point(R-1, j));
			
			while(!q.isEmpty()) {
				Point temp = q.poll();
				
				for(int i=0;i<4;i++) {
					int nx = temp.x + dx[i];
					int ny = temp.y + dy[i];
					
					if(!isValid(nx, ny) || visited[nx][ny] || cave[nx][ny] == '.') continue;
					
					visited[nx][ny] = true;
					q.add(new Point(nx, ny));
				}
			}
		}
		
		// 공중에 떠 있는 클러스터 찾기
		for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) {
				if(!visited[i][j] && cave[i][j] == 'x') {
					cave[i][j] = '.';
					cluster.add(new Point(i, j));
				}
			}
		}
		
		if(cluster.isEmpty()) return;
		
		boolean down = true;
		while(down) {
			for(Point p : cluster) {
				int nx = p.x + 1;
				int ny = p.y;
				
				if(!isValid(nx, ny) || cave[nx][ny] == 'x') {
					down = false;
					break;
				}
			}
			
			if(down) {
				for(Point p:cluster) {
					p.x++;
				}
			}
		}
		
		for(Point p : cluster) {
			cave[p.x][p.y] = 'x';
		}
	}

	private static void breakMineral(int row, int i) {
		
		if(i%2 == 0) {
			for(int j=0;j<C;j++) {
				if(cave[row][j] == 'x') {
					cave[row][j] = '.';
					break;
				}
			}
		} else {
			for(int j=C-1;j>=0;j--) {
				if(cave[row][j] == 'x') {
					cave[row][j] = '.';
					break;
				}
			}
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 ||c<0 ||r>=R ||c>=C) return false;
		return true;
	}
}
