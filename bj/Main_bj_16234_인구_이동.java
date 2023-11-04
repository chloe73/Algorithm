package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_16234_인구_이동 {
	
	static int N,L,R,result;
	static int[][] board, renewal;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static boolean[][] visited;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
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
		// 인구 이동은 하루 동안 다음과 같이 진행되고, 
		// 더 이상 아래 방법에 의해 인구 이동이 없을 때까지 지속된다.
		
		while(true) {
			
			// 1. 국경선을 공유하는 두 나라의 인구 차이가 L명 이상, R명 이하라면, 
			// 두 나라가 공유하는 국경선을 오늘 하루 동안 연다.
			// 2. 위의 조건에 의해 열어야하는 국경선이 모두 열렸다면, 인구 이동을 시작한다.
			boolean flag = false;
			visited = new boolean[N][N];
			renewal = new int[N][N];
			copy('a');
			
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					if(!visited[i][j])
						if(bfs(i,j)) flag = true;
				}
			}
			
			if(!flag) break;

			copy('b');
			
			result++;
		}
		
		return;
	}
	
	private static void copy(char c) {
		
		if(c == 'a') {
			for(int i=0;i<N;i++) {
				renewal[i] = Arrays.copyOf(board[i], N);
			}			
		}
		else {
			for(int i=0;i<N;i++) {
				board[i] = Arrays.copyOf(renewal[i], N);
			}
		}
	}
	
	private static boolean bfs(int i, int j) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {i,j});
		visited[i][j] = true;
		ArrayList<int[]> targetList = new ArrayList<>();
		targetList.add(new int[] {i,j});
		
		int num = board[i][j];
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];

			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny]) continue;
				
				if(Math.abs(board[x][y]-board[nx][ny]) >= L && Math.abs(board[x][y]-board[nx][ny]) <=R) {
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny});
					targetList.add(new int[] {nx,ny});
					num += board[nx][ny];
				}
					
			}
		}
		
		if(targetList.size() == 1) return false;
		
		num /= targetList.size();
		
		for(int[] t : targetList) {
			int x = t[0];
			int y = t[1];
			
			renewal[x][y] = num;
		}
		
		return true;
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}