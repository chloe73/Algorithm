package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// gold 3 시간 제한 : 1초
public class Main_bj_20057_마법사상어와토네이도 {
	
	static int N,result;
	static int tx,ty,dirCnt=1; // 방향을 바꿀 때마다 방향 별 이동하는 칸의 수가 증가한다.
	static int dir=0, cnt=1; // 방향 이동 및 이동하는 칸의 수 증가하기 위한 변수
	static int[][] board;
	// 				   ← ↓ → ↑	
	static int[] dx = {0,1,0,-1};
	static int[] dy = {-1,0,1,0};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		// 토네이도의 처음 시작 위치
		tx = N / 2;
		ty = N / 2;
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
		
		// 방향을 바꿀 때마다 방향 별 이동하는 칸의 수가 증가한다.
		
		int dir = 0;
		int cnt = 1; // 2번에 한 번 dirCnt 증가를 위한 변수
		
		while(true) {
			// 종료 조건 tx , ty가 (0,1)에 도착했을 때
			if(tx == 0 && ty == 1) {
				// 한 칸 왼쪽으로 이동 후, 격자 밖으로 나가는 모래 양 구하기
				int temp = board[0][0];
				int a = temp * 7 / 100;
				int b = temp * 1 / 100;
				int c = temp * 2 / 100;
				int d = temp * 10 / 100;
				int e = temp * 5 / 100;
				int f = temp * 10 / 100;
				
				// alpha 
				
				int g = temp * 7 / 100;
				int h = temp * 1 / 100;
				int i = temp * 2 / 100;
				
				int sum = a + b + c + d + e + f + g + h + i;
				
				int alpha = temp - sum;
				
				result = alpha + (a+b+c+d+e+f);
				break;
			}
			
			// 현재 위치에서 한 칸 이동하면서 모래 이동을 먼저 처리한 뒤, 현재 위치 변경
			move();
			
		}
		
	}

	private static void move() {
		
		int nx = tx;
		int ny = ty;
		
		for(int k=0;k<dirCnt;k++) { // 해당 방향으로 이동해야 할 칸의 수 만큼 반복한 뒤, 방향 바꿈
			
			if(tx == 0 && ty == 1) return;
			
			// 다음에 이동할 칸
			nx = tx + dx[dir];
			ny = ty + dy[dir];
			
			int temp = board[nx][ny]; // 정해진 비율과 alpha로 이동할 모래의 양
			
			move_sand(nx,ny); // 해당 위치의 모래가 비율만큼 이동
			
			// 모래 이동 후 현재 위치 바꿈
			tx = nx;
			ty = ny;
		}
		
		// 현재 반복하는 횟수가 짝수라면 같은 방향으로 이동할 칸의 수 증가시켜줘야 함
		if(cnt % 2 == 0) dirCnt++;
		dir = (dir+1) % 4;
		cnt++;
	}

	private static void move_sand(int nx, int ny) {
		
		// 이동 방향에 따라 비율이 달라짐
		int temp = board[nx][ny];
		int a = temp * 7 / 100;
		int b = temp * 1 / 100;
		int c = temp * 2 / 100;
		int d = temp * 10 / 100;
		int e = temp * 5 / 100;
		
		// alpha 
		
		int sum = 2 * (a + b + c + d) + e;
		
		int alpha = temp - sum;
		
		if(dir == 0) { // 좌
			if(isValid(nx-1,ny)) board[nx-1][ny] += a;
			if(isValid(nx+1,ny)) board[nx+1][ny] += a;
			if(isValid(nx-1,ny+1)) board[nx-1][ny+1] += b;
			if(isValid(nx+1,ny+1)) board[nx+1][ny+1] += b;
			if(isValid(nx-2,ny)) board[nx-2][ny] += c;
			if(isValid(nx+2,ny)) board[nx+2][ny] += c;
			if(isValid(nx-1,ny-1)) board[nx-1][ny-1] += d;
			if(isValid(nx+1,ny-1)) board[nx+1][ny-1] += d;
			if(isValid(nx,ny-2)) board[nx][ny-2] += e;
			if(isValid(nx,ny-1)) board[nx][ny-1] = alpha;
		}
		
		else if(dir == 1) { // 하
			if(isValid(nx-1,ny-1)) board[nx-1][ny-1] += b; // 1
			if(isValid(nx-1,ny+1)) board[nx-1][ny+1] += b; // 1
			
			if(isValid(nx,ny-2)) board[nx][ny-2] += c; // 2
			if(isValid(nx,ny-1)) board[nx][ny-1] += a; // 7
			if(isValid(nx,ny+1)) board[nx][ny+1] += a; // 7
			if(isValid(nx,ny+2)) board[nx][ny+2] += c; // 2
			
			if(isValid(nx+1,ny-1)) board[nx+1][ny-1] += d; // 10
			if(isValid(nx+1,ny+1)) board[nx+1][ny+1] += d; // 10
			
			if(isValid(nx+2,ny)) board[nx+2][ny] += e; // 5
			
			if(isValid(nx+1,ny)) board[nx+1][ny] = alpha;
		}
		
		else if(dir == 2) { // 우
			if(isValid(nx-2,ny)) board[nx-2][ny] += c;
			if(isValid(nx+2,ny)) board[nx+2][ny] += c;
			
			if(isValid(nx-1,ny)) board[nx-1][ny] += a;
			if(isValid(nx+1,ny)) board[nx+1][ny] += a;
			
			if(isValid(nx-1,ny-1)) board[nx-1][ny-1] += b;
			if(isValid(nx+1,ny-1)) board[nx+1][ny-1] += b;
			
			if(isValid(nx-1,ny+1)) board[nx-1][ny+1] += d;
			if(isValid(nx+1,ny+1)) board[nx+1][ny+1] += d;
			
			if(isValid(nx,ny+2)) board[nx][ny+2] += e;
			
			if(isValid(nx,ny+1)) board[nx][ny+1] = alpha;
		}
		
		else if(dir == 3) { // 상
			if(isValid(nx,ny-1)) board[nx][ny-1] += a; // 7
			if(isValid(nx,ny+1)) board[nx][ny+1] += a; // 7
			
			if(isValid(nx+1,ny-1)) board[nx+1][ny-1] += b; // 1
			if(isValid(nx+1,ny+1)) board[nx+1][ny+1] += b; // 1
			
			if(isValid(nx,ny-2)) board[nx][ny-2] += c; // 2
			if(isValid(nx,ny+2)) board[nx][ny+2] += c; // 2
			
			if(isValid(nx-1,ny-1)) board[nx-1][ny-1] += d; // 10
			if(isValid(nx-1,ny+1)) board[nx-1][ny+1] += d; // 10
			
			if(isValid(nx-2,ny)) board[nx-2][ny] += e; // 5
			
			if(isValid(nx-1,ny)) board[nx-1][ny] = alpha;
		}
		
		board[nx][ny] = 0;
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
