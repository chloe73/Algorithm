package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_4179_불 {
	
	static int R,C,startX,startY,result;
	static char[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static Queue<int[]> fq = new LinkedList<>();
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
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		board = new char[R][C];
		for(int i=0;i<R;i++) {
			String s = br.readLine();
			board[i] = s.toCharArray();
			for(int j=0;j<C;j++) {
				if(board[i][j] == 'J') {
					startX = i;
					startY = j;
					board[i][j] = '.';
				} else if(board[i][j] == 'F') {
					fq.add(new int[] {i, j});
				}
			}
		} // input end
		
		result = Integer.MAX_VALUE;
		
		solve();
		
		if(result == Integer.MAX_VALUE) System.out.println("IMPOSSIBLE");
		else System.out.println(result);
	}

	private static void solve() {
		Queue<int[]> jq = new LinkedList<>();
		jq.add(new int[] {startX,startY,1});
		boolean[][] visited = new boolean[R][C];
		visited[startX][startY] = true;
		
		
		while(!jq.isEmpty()) {
			
			// fire 먼저 이동
			int size = fq.size();
			for(int k=0;k<size;k++) {
				int[] temp = fq.poll();
				int x = temp[0];
				int y = temp[1];
				
				for(int i=0;i<4;i++) {
					int nx = x + dx[i];
					int ny = y + dy[i];
					
					if(!isValid(nx, ny) || board[nx][ny] != '.') continue;
					
					board[nx][ny] = '#';
					fq.add(new int[] {nx,ny});
				}
			}
			
			size = jq.size();
			for(int k=0;k<size;k++) {
				int[] temp = jq.poll();
				int x = temp[0];
				int y = temp[1];
				int time = temp[2];
				
				for(int i=0;i<4;i++) {
					int nx = x + dx[i];
					int ny = y + dy[i];
					
					if(!isValid(nx, ny)) {
						result = time;
						return;
					}
					
					if(visited[nx][ny] || board[nx][ny] != '.') continue;
					
					visited[nx][ny] = true;
					jq.add(new int[] {nx,ny,time+1});
				}
			}
		}

	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=R || c>=C) return false;
		return true;
	}
}
