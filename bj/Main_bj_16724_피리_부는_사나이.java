package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_16724_피리_부는_사나이 {
	
	static int N,M,result;
	static char[][] board;
	static boolean[][] visited, isCycle;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new char[N][M];
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<M;j++) {
				board[i][j] = s.charAt(j);
			}
		} // input end
		
		result = 0; // safe zone 최소 개수
		
		visited = new boolean[N][M];
		isCycle = new boolean[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(!visited[i][j]) {
					solve(i,j);
				}
			}
		}
		
		System.out.println(result);
	}
	
	private static void solve(int i, int j) {
		
		visited[i][j] = true;

		int d = getDir(board[i][j]);
		int nx = i + dx[d];
		int ny = j + dy[d];
		
		if(!visited[nx][ny]) {
			solve(nx, ny);
		}
		else {
			if(!isCycle[nx][ny]) result++;
		}
		
		isCycle[i][j] = true;
	}

	private static int getDir(char c) {
		if(c == 'U')
			return 0;
		else if(c == 'D')
			return 1;
		else if(c == 'L')
			return 2;
		return 3;
	}

}
