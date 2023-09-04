package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_회전하는_빙하 {
//	시간 제한: 1000ms
//	메모리 제한: 80MB
	static int N,Q,sumIce,sizeIce;
	static int[] cmd;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()); // 회전 가능 레벨
		Q = Integer.parseInt(st.nextToken()); // 회전 횟수.

		// board 크기 
		N = (int) Math.pow(2, n);
		board = new int[N][N];
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine());
		
		cmd = new int[Q];
		for(int i=0;i<Q;i++) {
			cmd[i] = Integer.parseInt(st.nextToken());
		} // input end
		
		for(int i : cmd) {
			i = (int) Math.pow(2, i);
			if(i > 1) 
				divide_and_rotate(i);
			check_ice();
		}
		
		find_biggest_ice_and_sumIce();
		
		System.out.println(sumIce);
		System.out.println(sizeIce);
	}
	
	private static void find_biggest_ice_and_sumIce() {
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				sumIce += board[i][j];
				
				if(board[i][j] > 0) {
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
							
							if(nx<0||ny<0||nx>=N||ny>=N || visited[nx][ny] || board[nx][ny] == 0) continue;
							
							visited[nx][ny] = true;
							q.add(new int[] {nx,ny});
							cnt++;
						}
					}
					
					sizeIce = Math.max(sizeIce, cnt);
				}
			}
		}
	}

	private static void divide_and_rotate(int L) {
		int[][] tempBoard = new int[N][N];
		
		for(int i=0;i<N;i+=L) {
			for(int j=0;j<N;j+=L) {
				rotate_board(i,j,L,tempBoard);
			}
		}
		
		board = tempBoard;
		
	}
	
	private static void print() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}

	private static void check_ice() {
		int[][] temp = new int[N][N];
		for(int i=0;i<N;i++) {
			temp[i] = Arrays.copyOf(board[i], N);
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] == 0) continue;
				
				int cnt = 0;
				
				for(int d=0;d<4;d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					
					if(nx<0||ny<0 || nx>=N || ny>=N) continue;
					
					if(board[nx][ny] == 0) continue;
					
					cnt++;
				}
				
				if(cnt < 3) temp[i][j]--;
			}
		}
		
		board = temp;
	}

	private static void rotate_board(int x, int y, int L, int[][] tempBoard) {
		if(L == 2) {
			tempBoard[x][y+1] = board[x][y];
			tempBoard[x+1][y+1] = board[x][y+1];
			tempBoard[x+1][y] = board[x+1][y+1];
			tempBoard[x][y] = board[x+1][y];
		}
		
		else if(L > 2) {
			int half = L / 2;
			
			for(int i=0;i<half;i++) {
				for(int j=0;j<half;j++) {
					tempBoard[x+i][y+j+half] = board[x+i][y+j];
				}
			}
			
			for(int i=0;i<half;i++) {
				for(int j=0;j<half;j++) {
					tempBoard[x+half+i][y+half+j] = board[x+i][y+j+half];
				}
			}
			
			for(int i=0;i<half;i++) {
				for(int j=0;j<half;j++) {
					tempBoard[x+half+i][y+j] = board[x+half+i][y+half+j];
				}
			}
			
			for(int i=0;i<half;i++) {
				for(int j=0;j<half;j++) {
					tempBoard[x+i][y+j] = board[x+half+i][y+j];
				}
			}
		}
	}

}