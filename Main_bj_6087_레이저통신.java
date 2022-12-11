package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_6087_레이저통신 {
	
	static int W,H,ax,ay,bx,by,result;
	static char[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point {
		int x,y,cd,dir; // cd : 방향 바꾼 횟수, dir : 현재 방향
		public Point(int x, int y, int cd, int dir) {
			this.x = x;
			this.y = y;
			this.cd = cd;
			this.dir = dir;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		board = new char[H][W];
		boolean flag = false;
		for(int i=0;i<H;i++) {
			board[i] = br.readLine().toCharArray();
			for(int j=0;j<W;j++) {
				if(board[i][j] == 'C') {
					if(!flag) {
						ax = i;
						ay = j;
						flag = true;
					} else {
						bx = i;
						by = j;
					}
				}
			}
		} // input end
		
		result = Integer.MAX_VALUE;
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		Queue<Point> q = new LinkedList<>();
		int[][] visited = new int[H][W];
		for(int i=0;i<H;i++) {
			Arrays.fill(visited[i], Integer.MAX_VALUE);
		}
		q.add(new Point(ax, ay,0,-1));
		visited[ax][ay] = 0;
		
		while(!q.isEmpty()) {
			Point p = q.poll();
			
			if(p.x == bx && p.y == by) {
//				System.out.println(p.cd);
				result = Math.min(result, p.cd);
			}
			
			for(int i=0;i<4;i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];
				
				if(nx<0 ||ny<0 ||nx>=H ||ny>=W) continue;
				
				if(board[nx][ny] == '*') continue;
				
				if(p.dir == -1) {
					q.add(new Point(nx, ny, 0, i));
				} else if(p.dir != i && visited[nx][ny] >= p.cd+1) {
					q.add(new Point(nx, ny, p.cd+1, i));
					visited[nx][ny] = p.cd + 1;
				} else if(p.dir == i && visited[nx][ny] >= p.cd) {
					q.add(new Point(nx, ny, p.cd, i));
					visited[nx][ny] = p.cd;
				}
			}
		}
	}

}
