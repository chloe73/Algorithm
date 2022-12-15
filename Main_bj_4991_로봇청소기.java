package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_4991_로봇청소기 {
	
	static int W,H,result;
	static char[][] board;
	static int[][] dist;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static boolean[] selected;
	static ArrayList<Point> dust = new ArrayList<>();
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		while(true) {
			st = new StringTokenizer(br.readLine());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			if(W == 0 && H == 0) break;
			
			board = new char[H][W];
			for(int i=0;i<H;i++) {
				board[i] = br.readLine().toCharArray();
				for(int j=0;j<W;j++) {
					if(board[i][j] == 'o') {
						dust.add(0, new Point(i, j));
					} else if(board[i][j] == '*') {
						dust.add(new Point(i, j));
					}
				}
			} // input end
			
			result = Integer.MAX_VALUE;
			dist = new int[dust.size()+1][dust.size()+1];
			selected = new boolean[dust.size()];
			
			if(get_dist()) {
				clean(0,0,0);
				sb.append(result).append("\n");
			} else {
				sb.append(-1).append("\n");
			}
		}
		System.out.println(sb.toString());
	}

	private static void clean(int now, int cnt, int sum) {
		
		if(cnt == dust.size()-1) {
			result = Math.min(result, sum);
			return;
		}
		
		for(int i=1;i<dust.size();i++) {
			if(selected[i]) continue;
			selected[i] = true;
			clean(i, cnt+1, sum+dist[now][i]);
			selected[i] = false;
		}
	}

	private static boolean get_dist() {
		
		for(int i=0;i<dust.size();i++) {
			for(int j=i+1;j<dust.size();j++) {
				int d = bfs(dust.get(i), dust.get(j));
				if(d == -1) return false;
				else dist[i][j] = dist[j][i] = d;
			}
		}
		return true;
	}

	private static int bfs(Point s, Point e) {
		
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[H][W];
		q.add(new int[] {s.x,s.y,0});
		visited[s.x][s.y] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int cnt = temp[2];
			
			if(x == e.x && y == e.y) {
				return cnt;
			}
			
			for(int i=0;i<4;i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(nx<0 || ny<0 || nx>=H || ny>=H) continue;
				
				if(visited[nx][ny] || board[nx][ny] == 'x') continue;
				
				q.add(new int[] {nx,ny,cnt+1});
				visited[nx][ny] = true;
			}
		}
		
		return -1;
	}
}
