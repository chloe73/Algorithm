package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_색깔_폭탄 {
	
	static int N,M,result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static PriorityQueue<Point> pq;
	static boolean[][] visited;
	static class Point implements Comparable<Point>{
		int x,y;
		int redCnt;
		int cnt;
		public Point(int x, int y, int redCnt, int cnt) {
			this.x = x;
			this.y = y;
			this.redCnt = redCnt;
			this.cnt = cnt;
		}
		@Override
		public int compareTo(Point o) {
			if(this.cnt == o.cnt) {	
				if(this.redCnt == o.redCnt) {
					if(this.x == o.x) {
						return this.y - o.y;
					}
					return o.x - this.x;
				}
				return this.redCnt - o.redCnt;
			}
			return o.cnt - this.cnt;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				// 빨간색 폭탄 : -2로 표기
				if(board[i][j] == 0) board[i][j] = -2;
			}
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		while(is_possible()) {
			// 1. 현재 가장 큰 폭탄 묶음 찾기
			find_biggest_bomb();
			
			// 2. 선택된 폭탄 모두 제거
			int score = remove_bomb();
			System.out.println("score :" + score);
			// 3. 중력 작용
			gravity();
			
			// 4. 반시계 90도 회전
			rotate_board();
			
			// 5. 다시 중력 작용
			gravity();
			
			result += score*score;
			System.out.println("===============");
			print();
		}
	}
	
	private static void print() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	
	// 반시계 90도 회전
	private static void rotate_board() {
		int[][] copy = new int[N][N];
		int row = N-1;
		
		for(int j=0;j<N;j++) {
			for(int i=0;i<N;i++) {
				copy[row][i] = board[i][j];
			}
			row--;
		}
		
		board = copy;
	}

	private static void gravity() {
		
		for(int i=N-2;i>=0;i--) {
			for(int j=0;j<N;j++) {
				if(board[i][j] >= 0 && board[i][j] <= M) {
					int row = i;
					int cnt = 0;
					
					while(++row < N) {
						if(board[row][j] == -7) cnt++;
						else if(board[row][j] == -1 || (board[row][j] >= 0 && board[i][j] <= M)) break;
					}
					if(cnt > 0) {
						board[i+cnt][j] = board[i][j];
						board[i][j] = -7;
						
					}
				}
			}
		}
		
		for(int j=0;j<N;j++) {
			int cnt = 0;
			for(int i=N-1;i>=0;i--) {
				if(board[i][j] == -1) {
					cnt = 0;
					continue;
				}
				if(board[i][j] == 0) {
					cnt++;
					continue;
				}
				if(board[i][j] >= 1 && board[i][j] <= M) {
					if(cnt > 0) {
						board[i+cnt][j] = board[i][j];
						board[i][j] = 0;
					}
				}
			}
		}
	}

	private static int remove_bomb() {
		Point target = pq.poll();
		
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		q.add(new int[] {target.x,target.y,1});
		int num = board[target.x][target.y];
		visited[target.x][target.y] = true;
		int count = 0;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny]) continue;
				
				if(board[nx][ny] == -1) continue;
				
				if(board[nx][ny] == -2 || board[nx][ny] == num) {
					count++;
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny});
					board[nx][ny] = 0;
					continue;
				}
			}
		}
		
		board[target.x][target.y] = 0;
		
		return count+1;
	}

	private static void find_biggest_bomb() {
		pq = new PriorityQueue<>();
		visited = new boolean[N][N];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(!visited[i][j] && board[i][j] >= 1 && board[i][j] <= M) {
					visited[i][j] = true;
					bfs(i,j,true);
				}
			}
		}
	}
	
	// 폭탄 구역 체크하는 함수 -> 기준점 찾기
	private static boolean bfs(int r, int c, boolean sim) {
		boolean flag = false;
		
		Queue<int[]> q = new LinkedList<>();
//		boolean[][] visited = new boolean[N][N];
		q.add(new int[] {r,c,1});
		int num = board[r][c];
		visited[r][c] = true;
		int count = 1;
		int redCount = 0;
		// 기준점
		int tx = r;
		int ty = c;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			// 기준점 갱신
			if(board[x][y] != -2) {
				if(tx < x) {
					tx = x;
					ty = y;
				}
				
				else if(tx == x && ty > y) {
					ty = y;
				}
			}
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny]) continue;
				
				if(board[nx][ny] == -1) continue;
				
				// 빨간색 폭탄이거나 똑같은 색깔인 경우
				if(board[nx][ny] == -2 || board[nx][ny] == num) {
					count++;
					if(board[nx][ny] == -2) redCount++;
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny});
					continue;
				}
			}
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(visited[i][j]) {
					if(board[i][j] == -2) visited[i][j] = false;
				}
			}
		}
		
		if(count >= 2) flag = true;
		
		if(flag && sim) { // 가장 큰 폭탄 묶음 찾는 경우에만
			pq.add(new Point(tx, ty, redCount,count));
		}
		
		return flag;
	}

	private static boolean is_possible() {
		visited = new boolean[N][N];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(!visited[i][j] && board[i][j] >= 1 && board[i][j] <= M) {
					if(bfs(i,j,false)) return true;
				}
			}
		}
		
		return false;
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
