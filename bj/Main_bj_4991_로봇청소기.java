package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_4991_로봇청소기 {
	
	static int W,H,result,cntDirtyArea;
	static char[][] board;
	static boolean[] isSelected;
	static int[][] dist;
	static Point[] list;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point {
		int x,y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		while(true) {
			st = new StringTokenizer(br.readLine());
			W = Integer.parseInt(st.nextToken()); // 열
			H = Integer.parseInt(st.nextToken()); // 행
			if(W == 0 && H == 0) break;
			
			board = new char[H][W];
			list = new Point[11];
			cntDirtyArea = 1;
			
			for(int i=0;i<H;i++) {
				char[] s = br.readLine().toCharArray();
				for(int j=0;j<W;j++) {
					board[i][j] = s[j];
					if(board[i][j] == 'o') {
						list[0] = new Point(i, j);
					} else if(board[i][j] == '*') {
						list[cntDirtyArea++] = new Point(i, j);
					}
				}
			} // input end
			
			result = Integer.MAX_VALUE;
			dist = new int[cntDirtyArea+1][cntDirtyArea+1];
			isSelected = new boolean[cntDirtyArea];
			if(calculateDist()) {
				clean(0, 0, 0);
				sb.append(result).append("\n");
			} else {
				sb.append(-1).append("\n");
			}
			
		} // while end
		
		System.out.println(sb.toString());
	}
	
	public static boolean calculateDist() {
		for(int i=0;i<cntDirtyArea;i++) {
			for(int j=i+1;j<cntDirtyArea;j++) {
				int d = dist(list[i], list[j]);
				if(d == -1) return false;
				else dist[i][j] = dist[j][i] = d;
			}
		}
		return true;
	}
	
	public static int dist(Point start, Point end) {
		Queue<Point> q = new LinkedList<>();
		boolean[][] visited = new boolean[H][W];
		q.add(start);
		visited[start.x][start.y] = true;
		
		int num = 0;
		while(!q.isEmpty()) {
			int size = q.size();
			++num;
			
			while(size-->0) {
				Point now = q.poll();
				for(int d=0;d<4;d++) {
					int nx = now.x + dx[d];
					int ny = now.y + dy[d];
					if(nx <0 || ny<0 || nx>=H || ny>=W || visited[nx][ny]|| board[nx][ny] == 'x') continue;
					
					if(nx == end.x && ny == end.y) return num;
					visited[nx][ny] = true;
					q.add(new Point(nx,ny));
				}
			}
		}
		return -1;
	}
	
	public static void clean(int now, int cnt, int sum) {
		if(cnt == cntDirtyArea-1) {
			result = Math.min(result, sum);
			return;
		}
		
		for(int i=1;i<cntDirtyArea;i++) {
			if(isSelected[i]) continue;
			
			isSelected[i] = true;
			clean(i, cnt+1, sum+dist[now][i]);
			isSelected[i] = false;
		}
	}
}