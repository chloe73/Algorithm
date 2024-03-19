package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_4963_섬의_개수 {
	
	static int W,H;
	static int[][] board;
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0,-1,-1,1,1};
	static int[] dy = {0,0,-1,1,-1,1,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		while(true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			if(W == 0 && H == 0) {
				break;
			}
			
			board = new int[H][W];
			for(int i=0;i<H;i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0;j<W;j++) {
					board[i][j] = Integer.parseInt(st.nextToken());
				}
			} // input end
			
			int result = 0;
			
			visited = new boolean[H][W];
			
			for(int i=0;i<H;i++) {
				for(int j=0;j<W;j++) {
					if(!visited[i][j] && board[i][j] == 1) {
						solve(i,j);
						result++;
					}
				}
			}
			
			sb.append(result+"\n");
		}
		
		System.out.println(sb.toString());
	}

	private static void solve(int i, int j) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {i,j});
		visited[i][j] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int d=0;d<8;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!isValid(nx, ny) || visited[nx][ny]) continue;
				
				if(board[nx][ny] == 1) {
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny});
				}
			}
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=H || c>=W) return false;
		return true;
	}
}
