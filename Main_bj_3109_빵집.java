package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_3109_빵집 {
	
	static int R,C,result;
	static char[][] board,copy;
	static int[] dx = {0,-1,1};
	static int[] dy = {1,1,1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		board = new char[R][C];
		copy = new char[R][C];
		for(int i=0;i<R;i++) {
			String s = br.readLine();
			board[i] = s.toCharArray();
		} // input end
		
		result = Integer.MIN_VALUE;
		solve(0,0,0);
		
		System.out.println(result);
	}

	private static void solve(int x, int y, int cnt) {
		
		if(y == C-1) { // 현재 열이 마지막 열이라면 
			result = Math.max(result, cnt);
			return;
		}
		
		if(board[x][y] == '.') {
			
		}
		
	}

	private static void copy() {
		
		for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) {
				copy[i][j] = board[i][j];
			}
		}
	}
}
