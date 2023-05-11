package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_17142_연구소3 {
	
	static int N,M,result;
	static int[][] board,copy;
	static int[] tempVirus;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static ArrayList<Point> virusList;
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		board = new int[N][N];
		virusList = new ArrayList<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 2) virusList.add(new Point(i, j));
			}
		} // input end
		
		solve();
		
		System.out.println(result==Integer.MAX_VALUE?-1:result);
	}

	private static void solve() {
		result = Integer.MAX_VALUE; // 최소 시간 반환
		tempVirus = new int[M];
		
		// 전체 바이러스 리스트 조합 실행
		comb(0,0);
	}

	private static void comb(int idx, int cnt) {
		
		if(cnt == M) { // 조합이 만들어졌으면 바이러스 퍼뜨리러 이동
			int num = spread_virus();
			result = Math.min(result, num);
			return;
		}
		
		for(int i=idx;i<virusList.size();i++) {
			tempVirus[cnt] = i;
			comb(i+1,cnt+1);
		}
	}

	private static int spread_virus() {
		int cnt = 1; // 총 소요시간
		copy = new int[N][N];
		Queue<int[]> q = new LinkedList<int[]>();
		boolean[][] visited = new boolean[N][N];
		
		for(int i : tempVirus) {
			int x = virusList.get(i).x;
			int y = virusList.get(i).y;
			
			copy[x][y] = -1; // 바이러스 퍼지는 거 시작 위치
			q.add(new int[] {x,y,cnt});
			visited[x][y] = true;
		}
		
		while(!q.isEmpty()) {
			int size = q.size();
			
			for(int i=0;i<size;i++) {
				int[] temp = q.poll();
				int x = temp[0];
				int y = temp[1];
				int time = temp[2];
				
				
				for(int d=0;d<4;d++) {
					int nx = x + dx[d];
					int ny = y + dy[d];
					
					if(!isValid(nx, ny)) continue;
					
					// 해당 칸이 빈칸인 경우
					if(board[nx][ny] == 0 && !visited[nx][ny]) {
						copy[nx][ny] = cnt;
						visited[nx][ny] = true;
						q.add(new int[] {nx,ny,time+1});
					}
					
					else if(board[nx][ny] == 2 && !visited[nx][ny]) {
						visited[nx][ny] = true;
						q.add(new int[] {nx,ny,time+1});
					}
				}
			}
			cnt++;
		}
		
		boolean flag = true;
		int time = 0;
		
		outer:for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				
				flag = check_point(i,j);
				if(board[i][j] == 0 && copy[i][j] == 0) {
					flag = false;
					break outer;
				}
				if(board[i][j] == 0 && copy[i][j]>0) 
					time = Math.max(time, copy[i][j]);
//				System.out.print(copy[i][j]+" ");
			}
//			System.out.println();
		}
		// 벽이 다 가로막혀서 0일 수 밖에 없는 경우 처리 -> 반
		if(!flag) return Integer.MAX_VALUE;
		return time;
	}

	private static boolean check_point(int x, int y) {
		
		for(int d=0;d<4;d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			if(!isValid(nx,ny)) continue;
		}
		
		return true;
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}
}
