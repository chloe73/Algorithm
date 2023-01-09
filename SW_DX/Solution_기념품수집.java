package algo.SW_DX;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_기념품수집 {
	
	static int R,C,result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static boolean[] checked;
	static boolean[][] visited;

	public static void main(String[] args) throws IOException{
		System.setIn(new FileInputStream("input/input_기념품수집.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			st = new StringTokenizer(br.readLine());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			
			board = new int[R][C];
			checked = new boolean[26];
			visited = new boolean[R][C];
			for(int i=0;i<R;i++) {
				String s = br.readLine();
				for(int j=0;j<C;j++) {
					board[i][j] = s.charAt(j)-'A';
				}
			} // input end
			
			result = 0;
			
			visited[0][0] = true;
			checked[board[0][0]] = true;
			dfs(0,0,1);
			
			sb.append("#"+tc+" ").append(result).append("\n");
		}
		System.out.println(sb.toString());
	}

	private static void dfs(int x, int y, int cnt) {
		
		result = Math.max(result, cnt);
		
		for(int i=0;i<4;i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if(nx<0 || ny<0 || nx>=R || ny>=C) continue;
			
			if(visited[nx][ny]) continue;
			
			if(!checked[board[nx][ny]]) {
				visited[nx][ny] = true;
				checked[board[nx][ny]] = true;
				dfs(nx,ny,cnt+1);
				visited[nx][ny] = false;
				checked[board[nx][ny]] = false;
			}
			
		}
	}

}
