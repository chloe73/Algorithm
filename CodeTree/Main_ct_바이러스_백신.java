package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_바이러스_백신 {
	
	static int N,M,totalHospitalCnt,result;
	static int[][] board;
	static ArrayList<Point> hospitalList;
	static boolean[] isChecked;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Point {
		int x, y;
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
		
		board =  new int[N][N];
		hospitalList = new ArrayList<>();
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 2) {
					totalHospitalCnt++;
					hospitalList.add(new Point(i,j));
				}
			}
		} // input end
		result = Integer.MAX_VALUE;
		isChecked = new boolean[totalHospitalCnt];
		solve(0,0);
		
		// M개의 병원을 적절히 골라 모든 바이러스를 없애는 데 필요한 최소 시간을 출력합니다. 
		// 만약 모든 바이러스를 없앨 수 있는 방법이 없다면 −1을 출력합니다.
		System.out.println(result == Integer.MAX_VALUE ? -1 : result);
	}

	private static void solve(int idx, int cnt) {
		// M개의 병원을 고르게 되면, 골라진 병원들을 시작으로 매 초마다 상하좌우로 인접한 지역 중 벽을 제외한 지역에 백신이 공급되기 때문에 그 자리에 있던 바이러스는 사라지게 됩니다.
		if(cnt == M) {
			int time = bfs();
			if(time != -1)
				result = Math.min(result, time);
			return;
		}
		
		for(int i=idx;i<totalHospitalCnt;i++) {
			isChecked[i] = true;
			solve(i+1,cnt+1);
			isChecked[i] = false;
		}
	}

	private static int bfs() {
		// 0은 바이러스, 1은 벽 그리고 2는 병원
		int cnt = 0;
		boolean[][] visited = new boolean[N][N];
		Queue<int[]> q = new LinkedList<>();
		
		for(int i=0;i<totalHospitalCnt;i++) {
			if(isChecked[i]) {
				Point p = hospitalList.get(i);
				q.add(new int[] {p.x,p.y,0});
				visited[p.x][p.y] = true;
			}
		}
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int dist = temp[2];
			
			if(board[x][y] == 0) {
				if(cnt < dist) cnt =dist;				
			}
			
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx,ny) || visited[nx][ny]) continue;
				
				if(board[nx][ny] == 0 || board[nx][ny] == 2) {
					q.add(new int[] {nx,ny,dist+1});
					visited[nx][ny] = true;
				}
			}
		}
		boolean flag = true;
		outer:for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(!visited[i][j] && board[i][j] == 0) {
					flag = false;
					break outer;
				}
			}
		}
		
		if(!flag) return -1;
		
		return cnt;
	}

	private static boolean is_valid(int r, int c) {
		if(r<0 ||c<0 ||r>=N || c>=N) return false;
		return true;
	}

}
