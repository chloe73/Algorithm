package algo.SW_DX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 시간 : 1000개 테스트케이스를 합쳐서 C의 경우 1초 / C++의 경우 1초 / Java의 경우 1초
// 메모리 : 힙, 정적 메모리 합쳐서 256MB 이내, 스택 메모리 1MB 이내

public class Solution_삼각김밥_월드_2024_fh_01 {
	
	static int S,E,result;
	static int sx,sy,ex,ey; // 출발지 좌표와 도착지 좌표
	static int[] up_dx = {-1,0,0,-1};
	static int[] up_dy = {0,-1,1,-1};
	static int[] down_dx = {1,0,0,1};
	static int[] down_dy = {0,-1,1,1};
	static int[] dx = {-1,1,0,0,-1,1};
	static int[] dy = {0,0,-1,1,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			sb.append("#"+tc+" ");
			st = new StringTokenizer(br.readLine());
			S = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			
			int max = Math.max(S, E);
			result = Integer.MAX_VALUE;
			
			solve(max);
			
			sb.append(result+"\n");
		} // input end
		
		System.out.println(sb.toString());
	}

	private static void solve(int max) {
		
		int cnt = 1;
		int num = 1;
		int[][] board = new int[143][143];		
		
		outer:while(true) {
			
			if(max < num) {
				break;
			}
			
			for(int i=1;i<=cnt;i++) {
				if(max < num) {
					break outer;
				}
				
				if(S == num) {
					sx = cnt;
					sy = i;
				}
				
				if(E == num) {
					ex = cnt;
					ey = i;
				}
				
				board[cnt][i] = num;
				num++;
			}
			
			cnt++;
		}
		
		if(sx == ex && sy == ey) {
			result = 0;
			return;
		}
		else bfs(board);
		
		print(board);
	}
	
	private static void bfs(int[][] board) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {sx,sy,0});
		boolean[][] visited = new boolean[143][143];
		visited[sx][sy] = true;
		
		// 시작점과 도착점이 level 차이가 나는 경우인지 확인
		boolean up = false;
		boolean down = false;
		
		if(sx < ex) {
			// down
			down = true;
		}
		if(sx > ex) {
			// up
			up = true;
		}
		
		if(!up && !down) {
			// 시작점과 도착점이 같은 행에 있는 경우 두 좌표의 열 차이가 result이다.
			int bigger = Math.max(sy, ey);
			int smaller = Math.min(sy, ey);
			
			result = bigger - smaller;
			
//			while(!q.isEmpty()) {
//				int[] temp = q.poll();
//				int x = temp[0];
//				int y = temp[1];
//				int dist = temp[2];
//				
//				if(x == ex && y == ey) {
//					result = Math.min(result, dist);
//				}
//				
//				for(int d=0;d<6;d++) {
//					int nx = x + dx[d];
//					int ny = y + dy[d];
//					
//					if(!is_valid(nx, ny) || visited[nx][ny]) continue;
//					
//					if(board[nx][ny] != 0) {
//						visited[nx][ny] = true;
//						q.add(new int[] {nx,ny,dist+1});					
//					}
//				}
//			}
		}
		
		if(up) {
			// 아래로 이동해야 하는 경우
			// 같은 열에 있는 경우 최소거리는 두 좌표의 행 차이가 result이다.
			if(sy == ey) {
				result = ex-sx;
				return;
			}
			
			
			
			//(  | A의 Level - B의 Level | ) 
			// + ( | A의 dist - B의 dist | ) 
			// - 0.5 ( | A의 Level - B의 Level | )

//			float startMid = (1+sx) / 2;
//			if(sx % 2 == 0) startMid += 0.5;
//			float endMid = (1+ex) / 2;
//			if(ex % 2 == 0) endMid += 0.5;
//			
//			result = (int) ((sx - ex) + (Math.abs(startMid-sy)
//					+ Math.abs(endMid-ey))
//					- 0.5 * (sx-ex));
			
			while(!q.isEmpty()) {
				int[] temp = q.poll();
				int x = temp[0];
				int y = temp[1];
				int dist = temp[2];
				
				if(x == ex && y == ey) {
					result = Math.min(result, dist);
				}
				
				for(int d=0;d<4;d++) {
					int nx = x + up_dx[d];
					int ny = y + up_dy[d];
					
					if(!is_valid(nx, ny) || visited[nx][ny]) continue;
					
					if(board[nx][ny] != 0) {
						visited[nx][ny] = true;
						q.add(new int[] {nx,ny,dist+1});					
					}
				}
			}
		}
		
		if(down) {
			//(  | A의 Level - B의 Level | ) 
			// + ( | A의 dist - B의 dist | ) 
			// - 0.5 ( | A의 Level - B의 Level | )

//			float startMid = (1+sx) / 2;
//			if(sx % 2 == 0) startMid += 0.5;
//			float endMid = (1+ex) / 2;
//			if(ex % 2 == 0) endMid += 0.5;
//			
//			float a = ex - sx; //(  | A의 Level - B의 Level | ) 
//			// + ( | A의 dist - B의 dist | )  => Math.abs(b-c)
//			float b = Math.abs(startMid-sy);
//			float c = Math.abs(endMid-ey);
//			// - 0.5 ( | A의 Level - B의 Level | )
//			float d = (float) ((float) (ex-sx)*0.5);
//			
//			float num = a + b+c-d;
//			
//			System.out.println(num);
			
//			while(!q.isEmpty()) {
//				int[] temp = q.poll();
//				int x = temp[0];
//				int y = temp[1];
//				int dist = temp[2];
//				
//				if(x == ex && y == ey) {
//					result = Math.min(result, dist);
//				}
//				
//				for(int d=0;d<4;d++) {
//					int nx = x + down_dx[d];
//					int ny = y + down_dy[d];
//					
//					if(!is_valid(nx, ny) || visited[nx][ny]) continue;
//					
//					if(board[nx][ny] != 0) {
//						visited[nx][ny] = true;
//						q.add(new int[] {nx,ny,dist+1});					
//					}
//				}
//			}
		}
	}

	private static void print(int[][] board) {
		for(int i=1;i<143;i++) {
			for(int j=1;j<143;j++) {
				if(board[i][j] == 0) break;
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<1 || c<1 || r>143 || c>143) return false;
		return true;
	}

}
