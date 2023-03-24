package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_23288_주사위굴리기2 {
	
	static int N,M,K,result;
	static int[][] board;
	static int[] dx = {0,1,0,-1};
	static int[] dy = {1,0,-1,0};
	static Dice dice;
	static class Dice {
		int x,y,d;
		int up,down;
		int front,back;
		int left,right;
		public Dice(int x, int y, int d, int up, int down, int front, int back, int left, int right) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
			this.up = up;
			this.down = down;
			this.front = front;
			this.back = back;
			this.left = left;
			this.right = right;
		}
		@Override
		public String toString() {
			return "Dice [x=" + x + ", y=" + y + ", d=" + d + ", up=" + up + ", down=" + down + ", front=" + front
					+ ", back=" + back + ", left=" + left + ", right=" + right + "]";
		}
		
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		} // input end
		
		dice = new Dice(0, 0, 0, 1, 6, 5, 2, 4, 3);
		
		solve();
		
		System.out.println(result);

	}

	private static void solve() {
		for(int i=0;i<K;i++) {			
			// 주사위 이동
			move_dice();
//			System.out.println("==== move_dice() ====");
//			System.out.println(dice.toString());
			// 점수 획득
			earn_points();
//			System.out.println("==== earn_points() ====");
//			System.out.println(dice.toString());
		}
	}

	private static void earn_points() {
		
		int A = dice.down;
		int B = board[dice.x][dice.y];
		
		if(A > B) {
			dice.d = change_dir(dice.d, 1);
		}
		else if(A < B) {
			dice.d = change_dir(dice.d, 0);
		}
		
		int cnt = bfs(dice.x,dice.y);
		result += (B * cnt);
	}

	private static int bfs(int r, int c) {
		Queue<int[]> q = new LinkedList<int[]>();
		boolean[][] visited = new boolean[N][M];
		q.add(new int[] {r,c});
		visited[r][c] = true;
		int cnt = 1;
		int num = board[r][c];
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int i=0;i<4;i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(nx<0 || ny<0 || nx>=N || ny>=M) continue;
				
				if(visited[nx][ny]) continue;
				
				if(board[nx][ny] == num) {
					visited[nx][ny] = true;
					cnt++;
					q.add(new int[] {nx,ny});
				}
			}
		}
		return cnt;
	}

	private static void move_dice() {
		int dir = dice.d;
		
		int nx = dice.x + dx[dir];
		int ny = dice.y + dy[dir];
		
		if(nx<0 || ny<0 || nx>=N || ny>=M) {
			// 이동 방향 반대로 바꾸기
			if(dir == 0) dice.d = 2;
			else if(dir == 1) dice.d = 3;
			else if(dir == 2) dice.d = 0;
			else dice.d = 1;
			
			dir = dice.d;
			nx = dice.x + dx[dir];
			ny = dice.y + dy[dir];
		}
		
		dice.d = dir;
		dice.x = nx;
		dice.y = ny;
		
		// 주사위 이동 후 주사위 위,아래,왼,오,앞,뒤 숫자 세팅
		dice_init(dice.d);
	}
	
	private static void dice_init(int d) {
		
		if(d == 0) { // 동
			int temp = dice.up;
			dice.up = dice.left;
			dice.left = dice.down;
			dice.down = dice.right;
			dice.right = temp;
		}
		
		else if(d == 1) { // 남
			int temp = dice.up;
			dice.up = dice.back;
			dice.back = dice.down;
			dice.down = dice.front;
			dice.front = temp;
		}
		
		else if(d == 2) { // 서
			int temp = dice.up;
			dice.up = dice.right;
			dice.right = dice.down;
			dice.down = dice.left;
			dice.left = temp;
		}
		
		else { // 북
			int temp = dice.up;
			dice.up = dice.front;
			dice.front = dice.down;
			dice.down = dice.back;
			dice.back = temp;
		}
	}

	private static int change_dir(int d, int flag) {
		
		if(flag == 1) {
			// 시계방향 회전
			if(d == 3) return 0;
			return d+1;
		}
		
		else {
			// 반시계 방향 회전
			if(d == 0) return 3;
			return d-1;
		}
	}

}
