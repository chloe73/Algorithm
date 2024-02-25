package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main_bj_2665_미로만들기 {
	
	static int N,result;
	static char[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		result = Integer.MAX_VALUE; // 흰 방으로 바꾸어야 할 최소의 검은 방의 수
		board = new char[N][N];
		
		for(int i=0;i<N;i++) {
			// 0은 검은 방
			// 1은 흰 방
			board[i] = br.readLine().toCharArray();
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		Queue<int[]> q = new LinkedList<>();
//		Queue<int[]> q = new PriorityQueue<>((o1,o2) -> (o1[2] - o2[2]));
		int[][] visited = new int[N][N];
		for(int i=0;i<N;i++) {
			Arrays.fill(visited[i], Integer.MAX_VALUE);
		}
		q.add(new int[] {0,0,0});
		visited[0][0] = 0;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int cnt = temp[2];
			
			if(x == N-1 && y == N-1) {
				result = Math.min(result, cnt);
				continue;
			}
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!isValid(nx, ny)) continue;
				
				if(visited[nx][ny] > cnt) {
					visited[nx][ny] = cnt;
					if(board[nx][ny] == '0') {
						q.add(new int[] {nx,ny,cnt+1});
					}
					else {
						q.add(new int[] {nx,ny,cnt});
					}
				}
			
			}
		}
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
