package algo.codetree1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_2019_fh_pm_2_바이러스_백신 {
	
	static int N,M,result;
	static int totalHospital; // 총 병원 개수
	static int[][] board;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static ArrayList<int[]> hList; // 병원 위치 정보
	static boolean[] isChecked;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken()); // 병원 선택 개수

		board = new int[N][N];
		hList = new ArrayList<>();
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				// 0은 바이러스, 1은 벽 그리고 2는 병원이 있음을 의미
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 2) {
					hList.add(new int[] {i,j});
					totalHospital++;
				}
			}
		} // input end
		
		result = Integer.MAX_VALUE;
		isChecked = new boolean[totalHospital];
		
		comb(0,0);
		
		// 만약 모든 바이러스를 없앨 수 있는 방법이 없다면 −1을 출력합니다.
		System.out.println(result == Integer.MAX_VALUE ? -1 : result);
	}

	private static void comb(int idx, int cnt) {
		if(cnt == M) {
			int time = bfs();
			if(time != -1)
				result = Math.min(result, time);
			return;
		}
		
		for(int i=idx;i<totalHospital;i++) {
			if(!isChecked[i]) {
				isChecked[i] = true;
				comb(i+1, cnt+1);
				isChecked[i] = false;				
			}
		}
	}
	
	private static int bfs() {
		int time = 0;
		
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		
		for(int i=0;i<totalHospital;i++) {
			if(isChecked[i]) {
				int[] temp = hList.get(i);
				int x = temp[0];
				int y = temp[1];
				q.add(new int[] {x,y,0});
				visited[x][y] = true;
			}
		}
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			int cnt = temp[2];
			
			if(board[x][y] == 0)
				time = Math.max(time, cnt);
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny]) continue;
				
				// 0은 바이러스, 1은 벽 그리고 2는 병원이 있음을 의미
				if(board[nx][ny] == 0 || board[nx][ny] == 2) {
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny,cnt+1});					
				}
			}
		}
		
		// 바이러스가 모두 사라졌는지 확인
		boolean flag = true;
		outer:for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(board[i][j] == 0 && !visited[i][j]) {
					flag = false;
					break outer;
				}
			}
		}
		
		if(!flag) return -1;
		
		return time;
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
