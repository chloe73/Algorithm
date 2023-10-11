package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_2468_안전_영역 {
	
	static int N,H,result;
	static int[][] board;
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

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
		
		result = 0;
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		while(++H <= 100) {
			visited = new boolean[N][N];
			
			// 1. 높이 H보다 작은 칸은 물에 잠긴다.
			// 2. board 탐색하면서 안전한 영역 확인 후 안전 영역 개수 체크
			int cnt = 0;
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					// 방문 안 했고 현재 높이보다 높은 칸인 경우 탐색 시작
					if(!visited[i][j] && board[i][j] > H) {
						bfs(i,j);
						cnt++;
					}
				}
			}
			
			result = Math.max(result, cnt);
		}
	}

	private static void bfs(int i, int j) {
		
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {i,j});
		visited[i][j] = true;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny]) continue;
				
				if(board[nx][ny] > H) {
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny});
				}
			}
		}
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c >= N) return false;
		return true;
	}

}
