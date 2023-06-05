package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_2573_빙산 {
	
	static int N,M,result;
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	// 한 덩어리의 빙산이 주어질 때, 이 빙산이 두 덩어리 이상으로 분리되는 최초의 시간(년)을 구하는 프로그램을 작성
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		boolean flag = false;
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(!flag && board[i][j] > 0) {
					flag = true;
				}
			}
		} // input end
		
		result = 0;
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		// 배열에서 빙산의 각 부분에 해당되는 칸에 있는 높이는 일년마다 그 칸에 동서남북 네 방향으로 붙어있는 0이 저장된 칸의 개수만큼 줄어든다. 
		// 단, 각 칸에 저장된 높이는 0보다 더 줄어들지 않는다. 
		// 바닷물은 호수처럼 빙산에 둘러싸여 있을 수도 있다.
		
		while(!checkStatus()) {
			// 만일 전부 다 녹을 때까지 두 덩어리 이상으로 분리되지 않으면 프로그램은 0을 출력한다.
			boolean flag = true;
			outer:for(int i=0;i<N;i++) {
				for(int j=0;j<M;j++) {
					if(board[i][j] != 0) {
						flag = false;
						break outer;
					}
				}
			}
			
			if(flag) {
				result = 0;
				break;
			}
			
			int[][] copy = new int[N][M];
			
			for(int i=0;i<N;i++) {
				for(int j=0;j<M;j++) {
					if(board[i][j] > 0) {
						int cnt = 0;
						for(int d=0;d<4;d++) {
							int nx = i + dx[d];
							int ny = j + dy[d];
							
							if(isValid(nx,ny) && board[nx][ny] == 0) cnt++;
						}
						
						int num = board[i][j] - cnt;
						if(num < 0) num = 0;
						copy[i][j] = num;
					}
				}
			}
			
			for(int i=0;i<N;i++) {
				for(int j=0;j<M;j++) {
					board[i][j] = copy[i][j];
				}
			}
			
			result++;
		}
	}
	
	private static boolean checkStatus() {
		int[][] boardArea = new int[N][M]; // 현재 board의 덩어리 확인하기 위한 변수
		
		int cnt = 1;
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j] > 0 && boardArea[i][j] == 0) {
					bfs(i,j,boardArea,cnt);
					cnt++;
				}
			}
		}
		
		if(cnt > 2) return true;
		return false;
	}
	
	private static void bfs(int kx, int ky, int[][] boardArea, int cnt) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {kx,ky});
		boardArea[kx][ky] = cnt;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!isValid(nx,ny) || boardArea[nx][ny] > 0) continue;
				
				if(board[nx][ny] > 0) {
					q.add(new int[] {nx,ny});
					boardArea[nx][ny] = cnt;
				}
			}
		}
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=M) return false;
		return true;
	}

}
