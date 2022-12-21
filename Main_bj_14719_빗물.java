package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_14719_빗물 {
	
	static int H,W,result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<W;i++) { // 가로
			int h = Integer.parseInt(st.nextToken());
			for(int j=0;j<h;j++) { // 세로
				board[j][i] = 1;
			}
		} // input end
		
		result = 0;
		
		solve();
		
		System.out.println(result);
		
	}

	private static void solve() {
		// TODO Auto-generated method stub
		
	}

}
