package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_19237_어른상어 {
	
	static int N,M,K,result,sharkCnt;
	static int[][] board; // 상어 번호 기록
	static int[][][] smell, dirOrder;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static Shark[] sharkArr;
	static class Shark {
		int x,y,d,nx,ny,nd;
		public Shark(int x, int y, int d, int nx, int ny, int nd) {
			this.x = x;
			this.y = y;
			this.d = d;
			this.nx = nx;
			this.ny = ny;
			this.nd = nd;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		smell = new int[N][N][2];
		sharkArr = new Shark[M];
		dirOrder = new int[M][4][4];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				int num = Integer.parseInt(st.nextToken())-1;
				board[i][j] = num;
				if(num >= 0) sharkArr[num] = new Shark(i, j, 0, 0, 0, 0);
			}
		}
		
		// 각 상어의 현재 방향 input
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<M;i++) {
			int d = Integer.parseInt(st.nextToken())-1;
			sharkArr[i].d = d;
		}
		
		// 각 상어 별 동서남북에 대한 방향 우선순위 input
		for(int i=0;i<M;i++) {
			for(int j=0;j<4;j++) {
				st = new StringTokenizer(br.readLine());
				for(int k=0;k<4;k++) {
					dirOrder[i][j][k] = Integer.parseInt(st.nextToken())-1;
				}
			}
		} // input end
		sharkCnt = M;
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				smell[i][j][0] = -1;
				smell[i][j][1] = -1;
			}
		}
		
		solve();
		
		System.out.println(result > 1000 ? -1 : result);
	}

	private static void solve() {
		
		while(result <= 1000) {
			if(sharkCnt == 1) break;
			
			mark_smell(); // 상어의 현재 위치에 냄새를 남긴다.
			
			move_shark(); // 상어가 이동한다.
			
			minus_smell(); // 상어 이동 후, 냄새 감소한다.
			
			result++;
//			System.out.println(sharkCnt);
		}
		
	}

	private static void minus_smell() {
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(smell[i][j][0] == -1) continue;
				
				if(smell[i][j][1] > -1) {
					smell[i][j][1]--;
				}
				if(smell[i][j][1] == 0) {
					smell[i][j][0] = -1;
				}
			}
		}
	}

	private static void move_shark() {
		
		// 상어가 이동할 칸 정하기
		for(int i=0;i<M;i++) {
			Shark temp = sharkArr[i];
			// 현재 상어 위치
			int x = temp.x;
			int y = temp.y;
			// 상어가 이동할 위치
			int kx = x;
			int ky = y;
			
			if(x == -1) continue; // 현재 격자에 없는 상어
			
			int temp_dir = temp.d; // 현재 상어가 바라보는 방향
			
			// 우선 아무 냄새가 없는 칸 찾기
			for(int d : dirOrder[i][temp_dir]) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(nx<0 ||ny<0 ||nx>=N || ny>=N) continue;
				
				if(smell[nx][ny][0] == -1) {
					kx = nx;
					ky = ny;
					temp.d = d;
					break;
				}
			}
			
			// 본인 냄새 있는 칸 찾기
			if(kx == x && ky == y) {
				for(int d : dirOrder[i][temp_dir]) {
					int nx = x + dx[d];
					int ny = y + dy[d];
					
					if(nx<0 ||ny<0 ||nx>=N || ny>=N) continue;
					
					if(smell[nx][ny][0] == i) {
						kx = nx;
						ky = ny;
						temp.d = d;
						break;
					}
				}
			}
			
			// 각 상어의 다음 이동할 칸의 위치 좌표 저장
			temp.nx = kx;
			temp.ny = ky;
			
			// 상어의 현재 칸은 -1로 세팅
			board[x][y] = -1;
		}
		
		// 상어 이동
		for(int i=0;i<M;i++) {
			Shark temp = sharkArr[i];
			
			if(temp.x == -1) continue;
			
			int nx = temp.nx;
			int ny = temp.ny;
			
			if(board[nx][ny] == -1) {
				board[nx][ny] = i;
				temp.x = nx;
				temp.y = ny;
			} else {
				if(board[nx][ny] > i) { // 현재 번호가 더 작다면
					// 기존 상어 퇴출
					sharkArr[board[nx][ny]].x = -1;
					sharkArr[board[nx][ny]].y = -1;
					
					temp.x = nx;
					temp.y = ny;
					
					board[nx][ny] = i;
				}
				else {
					temp.x = -1;
					temp.y = -1;
				}

				sharkCnt--;
			}
		}
		
	}

	private static void mark_smell() {
		
		for(int i=0;i<M;i++) {
			Shark temp = sharkArr[i];
			
			if(temp.x == -1) continue;
			
			smell[temp.x][temp.y][0] = i; // 상어 번호
			smell[temp.x][temp.y][1] = K; // K초
		}
		
	}

}
