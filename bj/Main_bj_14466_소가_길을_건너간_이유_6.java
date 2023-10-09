package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_14466_소가_길을_건너간_이유_6 {
	
	static int N,K,R,result;
	static int[][] board;
	static boolean[][] bridge;
	static ArrayList<int[]> cowList;
	static boolean[] isChecked;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		board = new int[N][N];
		bridge = new boolean[N][N];
		
		for(int i=0;i<R;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int d = Integer.parseInt(st.nextToken())-1;
			
			bridge[a][b] = true;
			bridge[c][d] = true;
		}
		
		cowList = new ArrayList<>();
		
		for(int i=0;i<K;i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken())-1;
			int b = Integer.parseInt(st.nextToken())-1;
			
			cowList.add(new int[] {a,b});
		} // input end
		
		isChecked = new boolean[K];
		solve(0,0);
		
		System.out.println(result);
	}

	private static void solve(int idx, int cnt) {
		
		if(cnt == 2) {
			int start = -1;
			int end = -1;
			
			outer:for(int i=0;i<K;i++) {
				if(isChecked[i]) {
					if(start == -1) {
						start = i;
						continue;
					}
					end = i;
					break outer;
				}
			}
			
			if(!bfs(start,end)) result++;
			
			return;
		}
		
		for(int i=idx;i<K;i++) {
			if(!isChecked[i]) {
				isChecked[i] = true;
				solve(i+1, cnt+1);
				isChecked[i] = false;
			}
		}
		
	}

	private static boolean bfs(int start, int end) {
		boolean[][] visited = new boolean[N][N];
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {cowList.get(start)[0], cowList.get(start)[1]});
		visited[cowList.get(start)[0]][cowList.get(start)[1]] = true;
		
		boolean flag = false;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int x = temp[0];
			int y = temp[1];
			
			if(x == cowList.get(end)[0] && y == cowList.get(end)[1]) {
				flag = true;
				break;
			}
			
			for(int d=0;d<4;d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				
				if(!is_valid(nx, ny) || visited[nx][ny]) continue;
				
				if(bridge[x][y] && bridge[nx][ny]) continue;
				
				visited[nx][ny] = true;
				q.add(new int[] {nx,ny});
			}
			
		}
		
		if(!flag) {
			q = new LinkedList<>();
			visited = new boolean[N][N];
			q.add(new int[] {cowList.get(start)[0], cowList.get(start)[1]});
			visited[cowList.get(start)[0]][cowList.get(start)[1]] = true;
			
			while(!q.isEmpty()) {
				int[] temp = q.poll();
				int x = temp[0];
				int y = temp[1];
				
				if(x == cowList.get(end)[0] && y == cowList.get(end)[1]) {
					flag = true;
					break;
				}
				
				for(int d=0;d<4;d++) {
					int nx = x + dx[d];
					int ny = y + dy[d];
					
					if(!is_valid(nx, ny) || visited[nx][ny]) continue;
					
					if(bridge[x][y] && bridge[nx][ny]) {						
						visited[nx][ny] = true;
						q.add(new int[] {nx,ny});
					}
					
				}
			}
		}
		
		return flag;
	}
	
	private static boolean is_valid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
