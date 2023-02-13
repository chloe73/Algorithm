package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_bj_2667_단지번호붙이기 {
	
	static int N, result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static boolean[][] visited;
	static int[] apartments;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		visited = new boolean[N][N];
		for(int i=0;i<N;i++) {
			String row = br.readLine();
			for(int j=0;j<N;j++) {
				board[i][j] = row.charAt(j)-'0';
			}
		} // input end
		
		apartments = new int[N*N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] == 1 && !visited[i][j]) {
					result++;
					dfs(i,j);
				}
			}
		}
		sb.append(result+"\n");
		
		Arrays.sort(apartments);
		for(int i=0;i<apartments.length;i++) {
			if(apartments[i] > 0) sb.append(apartments[i]+"\n");
		}
		
		// 총 단지 수 
		
		System.out.println(sb.toString());
	}

	private static void dfs(int x, int y) {
		visited[x][y] = true;
		apartments[result]++;
		
		for(int i=0;i<4;i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if(nx >= 0 && ny>= 0 && nx<N && ny<N) {
				if(board[nx][ny] == 1 && !visited[nx][ny]) dfs(nx,ny);
			}
		}
		
	}

}
