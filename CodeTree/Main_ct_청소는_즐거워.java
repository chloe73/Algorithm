package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_ct_청소는_즐거워 {

	// 정가운데부터 시작하여 아래 그림과 같이 나선형으로 바닥 청소를 하려 합니다.

	static int N, result;
	static int[][] board;
	static int tx,ty,td;
	// 왼쪽 - 아래쪽 - 오른쪽 - 위쪽 순서로 이동하며 청소를 합니다.
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {-1, 0, 1, 0};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		tx = N / 2;
		ty = N / 2;
		td = 0;
		
		solve();

		System.out.println(result);
	}

	private static void solve() {
		
		while(!is_end(tx,ty)) {
			// 현재 위치에서 현재 방향으로 한 칸 이동
			int nx = tx + dx[td];
			int ny = ty + dy[td];
			
			clean(nx,ny);
			
			// 방향 바뀜.
			td = change_dir(td);
			tx = nx;
			ty = ny;
		}
		
	}
	
	private static void clean(int nx, int ny) {
		
		int[][] plus = new int[N][N];
		int curr = board[nx][ny];
		board[nx][ny] = 0;
		int alpha = curr;
		int num1 = curr * 1 / 100;
		int num2 = curr * 2 / 100;
		int num5 = curr * 5 / 100;
		int num7 = curr * 7 / 100;
		int num10 = curr * 10 / 100;
		
		if(td == 0) {
			if(is_valid(nx-1,ny)) {
				plus[nx-1][ny] = num7;
				alpha -= num7;
			}
			else result += num7;
			
			if(is_valid(nx+1,ny)) {
				plus[nx+1][ny] = num7;
				alpha -= num7;
			}
			else result += num7;
			
			if(is_valid(nx-2,ny)) {
				plus[nx-2][ny] = num2;
				alpha -= num2;
			}
			else result += num2;
			
			if(is_valid(nx+2,ny)) {
				plus[nx+2][ny] = num2;
				alpha -= num2;
			}
			else result += num2;
			
			if(is_valid(nx-1,ny+1)) {
				plus[nx-1][ny+1] = num1;
				alpha -= num1;
			}
			else result += num1;
			
			if(is_valid(nx+1,ny+1)) {
				plus[nx+1][ny+1] = num1;
				alpha -= num1;
			}
			else result += num1;
			
			if(is_valid(nx-1,ny-1)) {
				plus[nx-1][ny-1] = num10;
				alpha -= num10;
			}
			else result += num10;
			
			if(is_valid(nx+1,ny-1)) {
				plus[nx+1][ny-1] = num10;
				alpha -= num10;
			}
			else result += num10;
			
			if(is_valid(nx,ny-2)) {
				plus[nx][ny-2] = num5;
				alpha -= num5;
			}
			else result += num5;
			
			if(is_valid(nx,ny-1))
				plus[nx][ny-1] = alpha;
			else result += alpha;
		}
		
		else if(td == 1) {
			if(is_valid(nx,ny-1)) {
				plus[nx][ny-1] = num7;
				alpha -= num7;
			}
			else result += num7;
			
			if(is_valid(nx,ny+1)) {
				plus[nx][ny+1] = num7;
				alpha -= num7;
			}
			else result += num7;
			
			if(is_valid(nx,ny-2)) {
				plus[nx][ny-2] = num2;
				alpha -= num2;
			}
			else result += num2;
			
			if(is_valid(nx,ny+2)) {
				plus[nx][ny+2] = num2;
				alpha -= num2;
			}
			else result += num2;
			
			if(is_valid(nx-1,ny-1)) {
				plus[nx-1][ny-1] = num1;
				alpha -= num1;
			}
			else result += num1;
			
			if(is_valid(nx-1,ny+1)) {
				plus[nx-1][ny+1] = num1;
				alpha -= num1;
			}
			else result += num1;
			
			if(is_valid(nx+1,ny-1)) {
				plus[nx+1][ny-1] = num10;
				alpha -= num10;
			}
			else result += num10;
			
			if(is_valid(nx+1,ny+1)) {
				plus[nx+1][ny+1] = num10;
				alpha -= num10;
			}
			else result += num10;
			
			if(is_valid(nx-2,ny)) {
				plus[nx-2][ny] = num5;
				alpha -= num5;
			}
			else result += num5;
			
			if(is_valid(nx-1,ny))
				plus[nx-1][ny] = alpha;
			else result += alpha;
		}
		
		else if(td == 2) {
			if(is_valid(nx-1,ny)) {
				plus[nx-1][ny] = num7;
				alpha -= num7;
			}
			else result += num7;
			
			if(is_valid(nx+1,ny)) {
				plus[nx+1][ny] = num7;
				alpha -= num7;
			}
			else result += num7;
			
			if(is_valid(nx-2,ny)) {
				plus[nx-2][ny] = num2;
				alpha -= num2;
			}
			else result += num2;
			
			if(is_valid(nx+2,ny)) {
				plus[nx+2][ny] = num2;
				alpha -= num2;
			}
			else result += num2;
			
			if(is_valid(nx-1,ny-1)) {
				plus[nx-1][ny-1] = num1;
				alpha -= num1;
			}
			else result += num1;
			
			if(is_valid(nx+1,ny-1)) {
				plus[nx+1][ny-1] = num1;
				alpha -= num1;
			}
			else result += num1;
			
			if(is_valid(nx-1,ny+1)) {
				plus[nx-1][ny+1] = num10;
				alpha -= num10;
			}
			else result += num10;
			
			if(is_valid(nx+1,ny+1)) {
				plus[nx+1][ny+1] = num10;
				alpha -= num10;
			}
			else result += num10;
			
			if(is_valid(nx,ny+2)) {
				plus[nx][ny+2] = num5;
				alpha -= num5;
			}
			else result += num5;
			
			if(is_valid(nx,ny+1))
				plus[nx][ny+1] = alpha;
			else result += alpha;
		}
		
		else {
			if(is_valid(nx,ny-1)) {
				plus[nx][ny-1] = num7;
				alpha -= num7;
			}
			else result += num7;
			
			if(is_valid(nx,ny+1)) {
				plus[nx][ny+1] = num7;
				alpha -= num7;
			}
			else result += num7;
			
			if(is_valid(nx,ny-2)) {
				plus[nx][ny-2] = num2;
				alpha -= num2;
			}
			else result += num2;
			
			if(is_valid(nx,ny+2)) {
				plus[nx][ny+2] = num2;
				alpha -= num2;
			}
			else result += num2;
			
			if(is_valid(nx+1,ny-1)) {
				plus[nx+1][ny-1] = num1;
				alpha -= num1;
			}
			else result += num1;
			
			if(is_valid(nx+1,ny+1)) {
				plus[nx+1][ny+1] = num1;
				alpha -= num1;
			}
			else result += num1;
			
			if(is_valid(nx-1,ny-1)) {
				plus[nx-1][ny-1] = num10;
				alpha -= num10;
			}
			else result += num10;
			
			if(is_valid(nx-1,ny+1)) {
				plus[nx-1][ny+1] = num10;
				alpha -= num10;
			}
			else result += num10;
			
			if(is_valid(nx-2,ny)) {
				plus[nx-2][ny] = num5;
				alpha -= num5;
			}
			else result += num5;
			
			if(is_valid(nx-1,ny))
				plus[nx-1][ny] = alpha;
			else result += alpha;
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(plus[i][j] > 0)	board[i][j] += plus[i][j];
			}
		}
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
	
	private static int change_dir(int d) {
		if(d==3) return 0;
		return d+1;
	}
	
	private static boolean is_end(int x, int y) {
		if(x == 0 && y == 0) return true;
		return false;
	}

}