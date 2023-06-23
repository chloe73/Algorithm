package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_14890_경사로 {
	
	static int N,L,result;
	static int[][] board;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		// 각 행 마다 확인
		for(int i=0;i<N;i++) {
			boolean[] isChecked = new boolean[N];
			boolean flag = true;
			
			outer:for(int j=0;j<N-1;j++) {
				// 현재 값 - 다음 값
				int dif = board[i][j] - board[i][j+1];
				
				if(dif > 1 || dif < -1) {
					flag = false;
					break outer;
				}
				
				// 다음 칸이 더 높다.
				else if(dif == 1) {
					for(int k=1;k<=L;k++) {
						if(j+k >= N || isChecked[j+k]) {
							flag = false;
							break outer;
						}
						if(board[i][j]-1 != board[i][j+k]) {
							flag = false;
							break outer;
						}
						isChecked[j+k] = true;
					}	
				}
				
				// 다음 칸이 더 낮다.
				else if(dif == -1) {
					for(int k=0;k<L;k++) {
						if(j-k < 0 || isChecked[j-k]) {
							flag = false;
							break outer;
						}
						if(board[i][j] != board[i][j-k]) {
							flag = false;
							break outer;
						}
						isChecked[j-k] = true;
					}
				}
				
			}
			
			if(flag) result++;
		}
		
		
		// 각 열 마다 확인
		for(int j=0;j<N;j++) {
			boolean[] isChecked = new boolean[N];
			boolean flag = true;
			
			outer:for(int i=0;i<N-1;i++) {
				// 현재 값 - 다음 값
				int dif = board[i][j] - board[i+1][j];
				
				if(dif > 1 || dif < -1) {
					flag = false;
					break outer;
				}
				
				// 다음 칸이 더 높다.
				else if(dif == 1) {
					for(int k=1;k<=L;k++) {
						if(i+k >= N || isChecked[i+k]) {
							flag = false;
							break outer;
						}
						if(board[i][j]-1 != board[i+k][j]) {
							flag = false;
							break outer;
						}
						isChecked[i+k] = true;
					}	
				}
				
				// 다음 칸이 더 낮다.
				else if(dif == -1) {
					for(int k=0;k<L;k++) {
						if(i-k < 0 || isChecked[i-k]) {
							flag = false;
							break outer;
						}
						if(board[i][j] != board[i-k][j]) {
							flag = false;
							break outer;
						}
						isChecked[i-k] = true;
					}
				}
				
			}
			
			if(flag) result++;
		}
	}

}
