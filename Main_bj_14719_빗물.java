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
		
		board = new int[H][W];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<W;i++) { // 가로
			int h = Integer.parseInt(st.nextToken());
			for(int j=H-1;j>=H-h;j--) { // 세로
				board[j][i] = 1;
			}
		} // input end
		
		result = 0;
		
		solve();
		
		System.out.println(result);
		
	}

	private static void solve() {
		
		for(int i=H-1;i>=0;i--) { // 맨 아래(행)부터 탐색 시작
			
			for(int j=0;j<W;j++) { // 하나씩 열 탐색
				
				if(board[i][j] == 1) continue;
				
				if(board[i][j] == 0) {
					// 맨 아래 행인 경우는 양 옆만 확인하면 됨.
					if(i == H-1 && isValid(i, j-1) && isValid(i, j+1)) {
						if(board[i][j-1] != 0) {
							board[i][j] = 2;
							result++;							
						}
					}
					// 맨 아래 행이 아닌 경우 : 양 옆, 아래 확인
					else if(i != H-1 && isValid(i, j-1) && isValid(i, j+1) && isValid(i-1, j)) {
						if(board[i+1][j] != 0 &&board[i][j-1] != 0) {
							board[i][j] = 2;
							result++;
						}
					}
					
				}
			}
		}
		
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=H || c >=W) return false;
		return true;
	}
}
