package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_20058_마법사상어와파이어스톰 {
	
	static int N,Q,totalIce,groundSize;
	static int[][] board;
	static int[] cmd;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		N = (int) Math.pow(2, N);
		board = new int[N][N];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		cmd = new int[Q];
		st =  new StringTokenizer(br.readLine());
		for(int i=0;i<Q;i++) {
			cmd[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		for(int i : cmd) {
			i = (int) Math.pow(2, i); // 2^i
			solve(i);
		}
		find_answer();
		System.out.println(totalIce);
		System.out.println(groundSize);
	}

	private static void find_answer() {
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				totalIce += board[i][j];
				if(board[i][j] > 0 && !visited[i][j]) {
					
					q.add(new int[] {i,j});
					visited[i][j] = true;
					
					int cnt = 1;
					
					while(!q.isEmpty()) {
						int[] temp = q.poll();
						int x = temp[0];
						int y = temp[1];
						
						for(int d=0;d<4;d++) {
							int nx = x + dx[d];
							int ny = y + dy[d];
							
							if(nx<0 || ny<0 || nx>=N ||ny>=N) continue;
							
							if(visited[nx][ny] || board[nx][ny] == 0) continue;
							
							visited[nx][ny] = true;
							q.add(new int[] {nx,ny});
							cnt++;
						}
					}
					
					groundSize = Math.max(groundSize, cnt);
				}
			}
		}
		
	}

	private static void solve(int L) {
		
		// 1. 2^L * 2^L 크기로 격자를 나눈다.
		// 2. 모든 부분 격자 시계방향으로 90도 회전
		int[][] temp_board = new int[N][N];
		for(int i=0;i<N;i+=L) {
			for(int j=0;j<N;j+=L) {
				rotate_board(i,j,L,temp_board);			
			}
		}
		board = temp_board;
		
		// 3. 각 칸의 인접 칸에 얼음있는 칸이 3개 미만이라면 얼음의 양 -1 줄어든다. (bfs)
		check_ice();
		
	}

	private static void check_ice() {
		int[][] temp = new int[N][N];
		for(int i=0;i<N;i++) {
			temp[i] = Arrays.copyOf(board[i], N);
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] == 0) continue;
				
				int x = i;
				int y = j;
				int cnt = 0;
				
				for(int d=0;d<4;d++) {
					int nx = x + dx[d];
					int ny = y + dy[d];
					
					if(nx<0 || ny<0 || nx>=N || ny>=N) continue;
					
					if(board[nx][ny] == 0) continue;
					
					cnt++;
				}
				
				if(cnt < 3) temp[x][y]--;
			}
		}
		board = temp;
	}

	private static void rotate_board(int x, int y, int L, int[][] temp_board) {
		
		for(int i=0;i<L;i++) {
			for(int j=0;j<L;j++) {
				temp_board[x+j][y+L-1-i] = board[x+i][y+j]; 
			}
		}
	}

}
