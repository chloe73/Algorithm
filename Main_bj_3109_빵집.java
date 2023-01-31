package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_3109_빵집 {
	
	static int R,C,result;
	static char[][] board;
	static int[] dx = {0,-1,1};
	static int[] dy = {1,1,1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		board = new char[R][C];
		for(int i=0;i<R;i++) {
			String s = br.readLine();
			board[i] = s.toCharArray();
		} // input end
		
		for(int i=0;i<R;i++) {
			if(solve(i,0)) result++;
		}
		
		System.out.println(result);
	}

	private static boolean solve(int x, int y) {
		
		board[x][y] = '*';
		
		if(y == C-1) return true;
		
		if(x>0 && board[x-1][y+1] == '.') { // 오른쪽 위
			if(solve(x-1,y+1)) return true;
		}
		
		if(board[x][y+1] == '.') { // 오른쪽
			if(solve(x,y+1)) return true;
		}

		if(x+1 < R && board[x+1][y+1] == '.') { // 오른쪽 아래
			if(solve(x+1,y+1)) return true;
		}
		
		return false;
		
	}
	
}
