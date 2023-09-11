package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_ct_나무_타이쿤 {
	
	static int N,M,result;
	static int[][] board;
	// 방향 1 ~ 8
	static int[] dx = {0,0,-1,-1,-1,0,1,1,1};
	static int[] dy = {0,1,1,0,-1,-1,-1,0,1};
	static ArrayList<Point> nList;
	static ArrayList<int[]> cmdList;
	static class Point {
		int x,y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
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
			 }
		 }
		 
		 // 초기 특수 영양제 위치
		 nList = new ArrayList<>();
		 nList.add(new Point(N-2, 0));
		 nList.add(new Point(N-2, 1));
		 nList.add(new Point(N-1, 0));
		 nList.add(new Point(N-1, 1));
		 
		 cmdList = new ArrayList<>();
		 for(int i=0;i<M;i++) {
			 st = new StringTokenizer(br.readLine());
			 int d = Integer.parseInt(st.nextToken()); // 이동 방향
			 int p = Integer.parseInt(st.nextToken()); // 이동 칸 수
			 
			 cmdList.add(new int[] {d,p});
		 } // input end
		 
		 solve();
		 
		 System.out.println(result);
	}

	private static void solve() {
		for(int[] turn : cmdList) {
			 // 1. 특수 영양제를 이동 규칙에 따라 이동시킵니다.
			move(turn[0], turn[1]);
			
			// 2. 특수 영양제를 이동 시킨 후 해당 땅에 특수 영양제를 투입합니다.
			// 3. 특수 영양제를 투입한 리브로수의 대각선으로 인접한 방향에 높이가 1 이상인 리브로수가 있는 만큼 높이가 더 성장합니다. 
			// 대각선으로 인접한 방향이 격자를 벗어나는 경우에는 세지 않습니다.
			insert();
			
			// 특수 영양제를 투입한 리브로수를 제외하고 높이가 2 이상인 리브로수는 
			// 높이 2를 베어서 잘라낸 리브로수로 특수 영양제를 사고, 해당 위치에 특수 영양제를 올려둡니다.
			cut_and_renewal();
		}
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				result += board[i][j];
			}
		}
	}
	
	private static void cut_and_renewal() {
		
		boolean[][] visited = new boolean[N][N];
		
		for(Point temp : nList) {
			visited[temp.x][temp.y] = true;
		}
		
		nList.clear();
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(visited[i][j]) continue;
				if(board[i][j] >= 2) {
					board[i][j] -= 2;
					nList.add(new Point(i, j));
				}
			}
		}
				
	}

	private static void insert() {
		
		int[][] plus = new int[N][N];
		
		// 2. 특수 영양제를 이동 시킨 후 해당 땅에 특수 영양제를 투입합니다.
		for(Point temp : nList) {
			board[temp.x][temp.y]++;
		}
		
		// 3. 특수 영양제를 투입한 리브로수의 대각선으로 인접한 방향에 높이가 1 이상인 리브로수가 있는 만큼 높이가 더 성장합니다. 
		// 대각선으로 인접한 방향이 격자를 벗어나는 경우에는 세지 않습니다.
		for(Point temp : nList) {
			int d = 2;
			int cnt = 0;
			// 대각선 확인 : 2,4,6,8
			while(d <= 8) {
				
				int nx = temp.x + dx[d];
				int ny = temp.y + dy[d];
				
				d+=2;
				
				if(nx<0 || ny<0 || nx>=N || ny>=N) continue;
				
				if(board[nx][ny] >= 1) cnt++;
				
			}
			
			plus[temp.x][temp.y] = cnt;
		}
		
		for(Point temp : nList) {
			board[temp.x][temp.y] += plus[temp.x][temp.y];
		}
		
	}

	private static void move(int d, int p) {
		
		for(Point temp : nList) {
			int nx = temp.x;
			int ny = temp.y;

			for(int i=0;i<p;i++) {
				nx += dx[d];
				ny += dy[d];
				
				int[] arr = is_valid(nx, ny);
				
				nx = arr[0];
				ny = arr[1];
				
//				if(arr[0] != -7) continue;
			}
			
			// 구름 d 방향으로 s칸만큼 이동
//			int nx = (N + temp.x + dx[d] * (p % N)) % N;
//			int ny = (N + temp.y + dy[d] * (p % N)) % N;
			
			temp.x = nx;
			temp.y = ny;
		}
		
	}

	private static int[] is_valid(int r, int c) {
		int[] dir = new int[2];
		
		if(r == -1 && c>=0 && c<N) {
			dir[0] = N-1;
			dir[1] = c;
		}
		else if(r == N && c>=0 && c<N) {
			dir[0] = 0;
			dir[1] = c;
		}
		else if(r>=0 && r<N && c == -1) {
			dir[0] = r;
			dir[1] = N-1;
		}
		else if(r>=0 && r<N && c == N) {
			dir[0] = r;
			dir[1] = 0;
		}
		else if(r>=0 && r<N && c>=0 && c<N) {
			dir[0] = r;
			dir[1] = c;
		}
		else if(r == -1 && c == -1){
			dir[0] = N-1;
			dir[1] = N-1;
		}
		else if(r == -1 && c == N) {
			dir[0] = N-1;
			dir[1] = 0;
		}
		else if(r == N && c == -1) {
			dir[0] = 0;
			dir[1] = N-1;
		}
		else if(r == N && c == N) {
			dir[0] = 0;
			dir[1] = 0;
		}
		
		return dir;
	}

}
