package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main_bj_20125_쿠키의_신체_측정 {
	
	static int N;
	static int sx,sy; // 탐색 시작 위치
	static int tx,ty;
	static char[][] board;
	static int[] result;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		board = new char[N][N];
		
		boolean flag = false;
		for(int i=0;i<N;i++) {
			String s = br.readLine();
			for(int j=0;j<N;j++) {
				board[i][j] = s.charAt(j);
				if(!flag && board[i][j] == '*') {
					flag = true;
					sx = i;
					sy = j;
				}
			}
		}

		result = new int[5];
		
		solve();
		
		tx++;
		ty++;
		// 첫 번째 줄에는 심장이 위치한 행의 번호 x와 열의 번호 y를 공백으로 구분해서 출력한다.
		System.out.println(tx+" "+ty);
		// 두 번째 줄에는 각각 왼쪽 팔, 오른쪽 팔, 허리, 왼쪽 다리, 오른쪽 다리의 길이를 공백으로 구분해서 출력하여라.
		for(int i : result) {
			System.out.print(i+" ");
		}
	}

	private static void solve() {
		// 쿠키의 신체는 머리, 심장, 허리, 그리고 좌우 팔, 다리로 구성되어 있다.
		//         왼,오,위,아래
		int[] dx = {0,0,-1,1};
		int[] dy = {-1,1,0,0};
		
		// 머리 위치 : sx, sy
		// 머리는 심장 바로 윗 칸에 1칸 크기로 있다.
		// 머리 바로 아래가 심장 위치
		tx = sx+1;
		ty = sy;
		
		// 심장을 기준으로
		// 왼쪽 팔은 심장 바로 왼쪽에 붙어있고 왼쪽으로 뻗어 있으며, 오른쪽 팔은 심장 바로 오른쪽에 붙어있고 오른쪽으로 뻗어있다.
		
		int nx = tx;
		int ny = ty;
		int d = 0;
		int leftArm = 0;
		// 왼쪽 팔 사이즈 측정
		while(true) {
			nx += dx[d];
			ny += dy[d];
			
			if(!is_valid(nx, ny)) break;
			
			if(board[nx][ny] == '_') break;
			
			leftArm++;
		}
		result[0] = leftArm;
		
		// 오른쪽 팔 사이즈 측정
		d = 1;
		int rightArm = 0;
		nx = tx;
		ny = ty;
		while(true) {
			nx += dx[d];
			ny += dy[d];
			
			if(!is_valid(nx, ny)) break;
			
			if(board[nx][ny] == '_') break;
			
			rightArm++;
		}
		result[1] = rightArm;
		
		// 허리는 심장의 바로 아래 쪽에 붙어있고 아래 쪽으로 뻗어 있다.
		d = 3;
		int waist = 0;
		nx = tx;
		ny = ty;
		while(true) {
			nx += dx[d];
			ny += dy[d];
			
			if(board[nx+1][ny-1] == '*' && board[nx+1][ny+1] == '*') {
 				waist++;
				break;
			}
			
			waist++;
		}
		result[2] = waist;
		
		// 허리의 가장 마지막 좌표 -> 이를 기준으로 
		int kx = nx;
		int ky = ny;
		
		// 왼쪽 다리는 허리의 왼쪽 아래에, 
		int leftLeg = 1;
		nx = kx+1;
		ny = ky-1;
		while(true) {
			nx += dx[d];
			ny += dy[d];
			
			if(!is_valid(nx, ny)) break;
			
			if(board[nx][ny] == '_') break;
			
			leftLeg++;
		}
		result[3] = leftLeg;
		
		// 오른쪽 다리는 허리의 오른쪽 아래에 바로 붙어있고, 각 다리들은 전부 아래쪽으로 뻗어 있다.
		int rightLeg = 1;
		nx = kx+1;
		ny = ky+1;
		while(true) {
			nx += dx[d];
			ny += dy[d];
			
			if(!is_valid(nx, ny)) break;
			
			if(board[nx][ny] == '_') break;
			
			rightLeg++;
		}
		result[4] = rightLeg;
		
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
