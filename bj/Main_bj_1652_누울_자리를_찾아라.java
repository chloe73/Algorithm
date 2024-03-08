package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_bj_1652_누울_자리를_찾아라 {
	
	static int N;
	static char[][] board;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		board = new char[N][N];
		
		for(int i=0;i<N;i++) {
			board[i] = br.readLine().toCharArray();
		} // input end
		
		int row = 0;
		int col = 0;
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				// 가로 확인
				if(board[i][j] == '.' && j+1 < N && board[i][j+1] == '.') {
					if(j+2 >= N || (j+2 < N && board[i][j+2] == 'X')) {
						row++;
					}
				}
				
				// 세로 확인
				if(board[i][j] == '.' && i+1 < N && board[i+1][j] == '.') {
					if(i+2 >= N || (i+2 < N && board[i+2][j] == 'X')) {
						col++;
					}
				}
			}
		}
		
		System.out.println(row + " " + col);
	}

}
